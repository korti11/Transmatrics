package at.korti.transmatrics.tileentity;

import at.korti.transmatrics.api.crafting.ICraftingRegistry;
import at.korti.transmatrics.api.crafting.ICraftingRegistry.ICraftingEntry;
import net.minecraft.item.ItemStack;

/**
 * Created by Korti on 15.03.2016.
 */
public abstract class TileEntityCraftingMachine<O> extends TileEntityBasicCraftingMachine<ItemStack, O> {

    protected TileEntityCraftingMachine(int capacity, int maxReceive, int energyUse, boolean capacitorSlot, String name, ICraftingRegistry<ItemStack> registry) {
        super(capacity, maxReceive, energyUse, capacitorSlot, name, registry);
    }

    //region TileEntityCraftingMachine
    @Override
    protected boolean areInputsEmpty() {
        return areSlotsEmpty(getCraftingRegistry().getInputSlotsIds());
    }

    @Override
    public ItemStack[] getInputs() {
        return getContent(getCraftingRegistry().getInputSlotsIds());
    }

    protected void decreaseInputs(ItemStack... stacks) {
        for (ItemStack stack : stacks) {
            int slot = getSlotForStack(true, stack);
            if(slot != -1 && getCraftingRegistry().decreaseItemForSlot(slot)) {
                int stackSize = getStackInSlot(slot).getCount();
                getStackInSlot(slot).setCount(stackSize - stack.getCount());
                if (getStackInSlot(slot).getCount() <= 0) {
                    setInventorySlotContents(slot, ItemStack.EMPTY);
                }
            }
        }
    }

    private boolean containsSlot(int[] slots, int searchSlot) {
        for (int slot : slots) {
            if (slot == searchSlot) {
                return true;
            }
        }
        return false;
    }

    private boolean isInputSlot(int slot) {
        return containsSlot(getCraftingRegistry().getInputSlotsIds(), slot);
    }
    //endregion

    //region IInventory
    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        boolean isSameItem = !stack.isEmpty() && stack.isItemEqual(getStackInSlot(index)) && ItemStack.areItemStackTagsEqual(stack, getStackInSlot(index));
        super.setInventorySlotContents(index, stack);
        ICraftingEntry entry = getCraftingRegistry().get(getInputs());
        if (isInputSlot(index) && entry != null && !isSameItem) {
            this.setField(0, 0);
            this.setField(1, entry.getCraftingTime());
            this.markDirty();
        } else if (isInputSlot(index) && stack.isEmpty()) {
            this.setField(0, 0);
            this.setField(1, 0);
            this.setField(4, 0);
        }
    }
    //endregion
}
