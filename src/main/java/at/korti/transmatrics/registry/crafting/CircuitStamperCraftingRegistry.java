package at.korti.transmatrics.registry.crafting;

import at.korti.transmatrics.Transmatrics;
import at.korti.transmatrics.api.crafting.ICraftingRegistry;
import at.korti.transmatrics.item.electronic.ItemCircuitBoard;
import at.korti.transmatrics.util.helper.InventoryHelper;
import at.korti.transmatrics.util.helper.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

import static at.korti.transmatrics.api.Constants.TransmatricsItem.CIRCUIT_BOARDER;
import static net.minecraft.util.EnumFacing.*;

/**
 * Created by Korti on 15.04.2016.
 */
public final class CircuitStamperCraftingRegistry implements ICraftingRegistry<ItemStack> {

    private static CircuitStamperCraftingRegistry instance;

    private List<CircuitStamperCraftingEntry> recipes;
    private Logger logger = Transmatrics.logger;

    private CircuitStamperCraftingRegistry() {
        recipes = new LinkedList<>();
    }

    public static CircuitStamperCraftingRegistry getInstance() {
        if (instance == null) {
            instance = new CircuitStamperCraftingRegistry();
        }
        return instance;
    }

    public CircuitStamperCraftingRegistry register(ItemStack blankCircuit, ItemStack conductor, int conductorColor, int craftingTime) {
        return (CircuitStamperCraftingRegistry) register(new CircuitStamperCraftingEntry(blankCircuit, conductor, conductorColor, craftingTime));
    }

    public CircuitStamperCraftingRegistry register(ItemStack blankCircuit, ItemStack conductor, int craftingTime) {
        return (CircuitStamperCraftingRegistry) register(new CircuitStamperCraftingEntry(blankCircuit, conductor, craftingTime));
    }

    @Override
    public ICraftingRegistry register(ICraftingEntry entry) {
        try {
            recipes.add((CircuitStamperCraftingEntry) entry);
        } catch (Exception e) {
            logger.error(String.format("Can't register circuit stamper recipe with the inputs=%s.",
                    entry.getInputs().toString()), e);
        }
        return this;
    }

    @Override
    public ICraftingEntry get(int index) {
        return recipes.get(index);
    }

    @Override
    public ICraftingEntry get(ItemStack... inputs) {
        if (inputs.length <= 1) {
            return find(inputs[0]);
        }
        return get(inputs[0], inputs[1]);
    }

    public CircuitStamperCraftingEntry find(ItemStack stack) {
        for (CircuitStamperCraftingEntry entry : recipes) {
            if (entry.blankCircuit.isItemEqual(stack) || entry.conductor.isItemEqual(stack)) {
                return entry;
            }
        }
        return null;
    }

    public CircuitStamperCraftingEntry get(ItemStack blankCircuit, ItemStack conductor) {
        for (CircuitStamperCraftingEntry entry : recipes) {
            if (entry.blankCircuit.isItemEqual(blankCircuit) && entry.conductor.isItemEqual(conductor)) {
                return entry;
            }
        }
        return null;
    }

    @Override
    public ICraftingEntry remove(int index) {
        return recipes.remove(index);
    }

    @Override
    public boolean remove(ICraftingEntry entry) {
        return recipes.remove(entry);
    }

    @Override
    public int size() {
        return recipes.size();
    }

    @Override
    public int[] getInputSlotsIds() {
        return new int[]{0, 1};
    }

    @Override
    public int[] getOutputSlotsIds() {
        return new int[]{2};
    }

    @Override
    public int inventorySize() {
        return getInputSlotsIds().length + getOutputSlotsIds().length;
    }

    @Override
    public int getStackLimit() {
        return 64;
    }

    @Override
    public float getChanceForSlot(int slot, ItemStack... inputs) {
        return get(inputs).getOutputChances()[slot];
    }

    @Override
    public EnumFacing[] getInputFaces() {
        return new EnumFacing[]{UP, NORTH, EAST, SOUTH, WEST, DOWN};
    }

    @Override
    public EnumFacing[] getOutputFaces() {
        return new EnumFacing[]{UP, NORTH, EAST, SOUTH, WEST, DOWN};
    }

    @Override
    public int[] getSlotsForFacing(EnumFacing facing) {
        return InventoryHelper.getSlotsForFacing(this, facing);
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack itemStackIn, EnumFacing facing) {
        return InventoryHelper.canInsertItem(this, slot, facing);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack itemStackIn, EnumFacing facing) {
        return InventoryHelper.canExtractItem(this, slot, facing);
    }

    @Override
    public boolean decreaseItemForSlot(int slot) {
        return true;
    }

    public static class CircuitStamperCraftingEntry implements ICraftingEntry<ItemStack, ItemStack> {

        private static ItemCircuitBoard circuitBoard = (ItemCircuitBoard) CIRCUIT_BOARDER.getItem();

        private ItemStack blankCircuit;
        private ItemStack conductor;
        private ItemStack circuit;
        private int craftingTime;

        public CircuitStamperCraftingEntry(ItemStack blankCircuit, ItemStack conductor, int conductorColor, int craftingTime) {
            this.blankCircuit = blankCircuit;
            this.conductor = conductor;
            this.circuit = new ItemStack(CIRCUIT_BOARDER.getItem());
            circuitBoard.setColorForItemStack(circuit, 0, blankCircuit.getItem().getColorFromItemStack(blankCircuit, 0));
            circuitBoard.setColorForItemStack(circuit, 1, conductorColor);
            this.craftingTime = craftingTime;
        }

        public CircuitStamperCraftingEntry(ItemStack blankCircuit, ItemStack conductor, int craftingTime) {
            this(blankCircuit, conductor, conductor.getItem().getColorFromItemStack(conductor, 0), craftingTime);
        }

        @Override
        public ItemStack[] getInputs() {
            return new ItemStack[]{blankCircuit, conductor};
        }

        @Override
        public ItemStack[] getOutputs() {
            return new ItemStack[]{circuit};
        }

        @Override
        public String[] getInputsOreDictionary() {
            return ArrayUtils.addAll(ItemStackHelper.getOreDictionaryNames(blankCircuit),
                    ItemStackHelper.getOreDictionaryNames(conductor));
        }

        @Override
        public int getCraftingTime() {
            return craftingTime;
        }

        @Override
        public float[] getOutputChances() {
            return new float[]{1f, 1f, 1f};
        }
    }
}
