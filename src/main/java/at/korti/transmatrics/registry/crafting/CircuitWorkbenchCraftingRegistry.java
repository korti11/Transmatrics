package at.korti.transmatrics.registry.crafting;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.crafting.ICraftingRegistry;
import at.korti.transmatrics.item.NBTColoredMetaItem;
import at.korti.transmatrics.item.electronic.ItemCircuit;
import at.korti.transmatrics.item.electronic.ItemCircuitBoard;
import at.korti.transmatrics.item.electronic.ItemElectronicParts;
import at.korti.transmatrics.util.helper.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

import java.util.Arrays;

import static at.korti.transmatrics.api.Constants.TransmatricsItem.CIRCUIT;
import static net.minecraft.util.EnumFacing.*;

/**
 * Created by Korti on 21.04.2016.
 */
public final class CircuitWorkbenchCraftingRegistry implements ICraftingRegistry<ItemStack> {

    @Override
    public ICraftingRegistry register(ICraftingEntry entry) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ICraftingEntry get(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ICraftingEntry get(ItemStack... inputs) {
        if(inputs.length == 1 || inputs.length == 2) {
            if (CircuitStamperCraftingRegistry.getInstance().get(inputs) != null) {
                if(inputs.length == 1) {
                    return new CircuitWorkbenchCraftingEntry(inputs[0], null);
                } else {
                    return new CircuitWorkbenchCraftingEntry(inputs[0], inputs[1]);
                }
            } else if (inputs[0].getItem() instanceof ItemElectronicParts) {
                return new CircuitWorkbenchCraftingEntry(null, null, inputs);
            }
        } else {
            return new CircuitWorkbenchCraftingEntry(inputs[0], inputs[1],
                    Arrays.copyOfRange(inputs, 2, inputs.length));
        }
        return null;
    }

    @Override
    public ICraftingEntry remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(ICraftingEntry entry) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public int[] getInputSlotsIds() {
        int[] ids = new int[12];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = i;
        }
        return ids;
    }

    @Override
    public int[] getOutputSlotsIds() {
        return new int[]{12};
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
        return 1;
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

    public class CircuitWorkbenchCraftingEntry implements ICraftingEntry<ItemStack, ItemStack> {

        public ItemStack output;

        public CircuitWorkbenchCraftingEntry(ItemStack circuit, ItemStack conduct, ItemStack... parts) {
            ItemCircuitBoard itemCircuitBoard = (ItemCircuitBoard) circuit.getItem();
            int boardColor = itemCircuitBoard.getColorFromItemStack(circuit, 0);
            int conductColor = itemCircuitBoard.getColorFromItemStack(circuit, 1);
            ItemCircuit itemCircuit = (ItemCircuit) CIRCUIT.getItem();
            output = new ItemStack(itemCircuit);
            itemCircuit.setColorForItemStack(output, 0, boardColor);
            itemCircuit.setColorForItemStack(output, 1, conductColor);
            for (ItemStack part : parts) {
                itemCircuit.addElectronicPart(output, part);
            }
        }

        @Override
        public ItemStack[] getInputs() {
            return new ItemStack[0];
        }

        @Override
        public ItemStack[] getOutputs() {
            return new ItemStack[]{output};
        }

        @Override
        public String[] getInputsOreDictionary() {
            return new String[0];
        }

        @Override
        public int getCraftingTime() {
            return 0;
        }

        @Override
        public float[] getOutputChances() {
            return new float[0];
        }
    }
}
