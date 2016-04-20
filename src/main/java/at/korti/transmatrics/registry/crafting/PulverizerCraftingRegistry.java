package at.korti.transmatrics.registry.crafting;

import at.korti.transmatrics.Transmatrics;
import at.korti.transmatrics.api.crafting.ICraftingRegistry;
import at.korti.transmatrics.util.helper.InventoryHelper;
import at.korti.transmatrics.util.helper.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

import static net.minecraft.util.EnumFacing.*;

/**
 * Created by Korti on 15.03.2016.
 */
public final class PulverizerCraftingRegistry implements ICraftingRegistry<ItemStack> {

    private static PulverizerCraftingRegistry instance;
    private static Logger logger = Transmatrics.logger;

    private List<PulverizerCraftingEntry> recipes;

    private PulverizerCraftingRegistry() {
        this.recipes = new LinkedList<>();
    }

    public static PulverizerCraftingRegistry getInstance() {
        if (instance == null) {
            instance = new PulverizerCraftingRegistry();
        }
        return instance;
    }

    public PulverizerCraftingRegistry register(ItemStack input, int craftingTime, ItemStack... outputs) {
        return register(input, craftingTime, 1f, outputs);
    }

    public PulverizerCraftingRegistry register(ItemStack input, int craftingTime, float secondOutputChance, ItemStack... outputs) {
        return (PulverizerCraftingRegistry) register(new PulverizerCraftingEntry(input, craftingTime, secondOutputChance, outputs));
    }

    @Override
    public ICraftingRegistry register(ICraftingEntry entry) {
        try {
            recipes.add((PulverizerCraftingEntry) entry);
        } catch (Exception e) {
            logger.error(String.format("Can't register pulverizer recipe with the inputs=%s.",
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
        return get(inputs[0]);
    }

    public PulverizerCraftingEntry get(ItemStack stack) {
        String[] oreDicts = ItemStackHelper.getOreDictionaryNames(stack);
        for (ICraftingEntry<ItemStack, ItemStack> entry : recipes) {
            if (stack != null && stack.getItem().equals(entry.getInputs()[0].getItem()) &&
                    stack.getItemDamage() == entry.getInputs()[0].getItemDamage()) {
                return (PulverizerCraftingEntry) entry;
            }
            for (String oreDictRecipe : entry.getInputsOreDictionary()) {
                for (String oreDictEntry : oreDicts) {
                    if (oreDictRecipe.equals(oreDictEntry)) {
                        return (PulverizerCraftingEntry) entry;
                    }
                }
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
        return new int[]{0};
    }

    @Override
    public int[] getOutputSlotsIds() {
        return new int[]{1, 2};
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
        ICraftingEntry entry = get(inputs);
        return entry.getOutputChances()[slot];
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

    public static class PulverizerCraftingEntry implements ICraftingEntry<ItemStack, ItemStack> {

        private ItemStack input;
        private ItemStack[] outputs;
        private int craftingTime;
        private float secondOutputChance;

        public PulverizerCraftingEntry(ItemStack input, int craftingTime, float secondOutputChance, ItemStack... outputs) {
            this.input = input;
            this.outputs = outputs;
            this.craftingTime = craftingTime;
            this.secondOutputChance = secondOutputChance;
        }

        @Override
        public ItemStack[] getInputs() {
            return new ItemStack[]{input};
        }

        @Override
        public ItemStack[] getOutputs() {
            return outputs;
        }

        @Override
        public String[] getInputsOreDictionary() {
            return ItemStackHelper.getOreDictionaryNames(input);
        }

        @Override
        public int getCraftingTime() {
            return craftingTime;
        }

        @Override
        public float[] getOutputChances() {
            return new float[]{0f, 1f, secondOutputChance};
        }
    }
}
