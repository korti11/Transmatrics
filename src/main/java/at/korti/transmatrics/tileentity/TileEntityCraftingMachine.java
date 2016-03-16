package at.korti.transmatrics.tileentity;

import at.korti.transmatrics.api.crafting.ICraftingRegistry;
import at.korti.transmatrics.api.crafting.ICraftingRegistry.ICraftingEntry;
import at.korti.transmatrics.block.ActiveMachineBlock;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

/**
 * Created by Korti on 15.03.2016.
 */
public abstract class TileEntityCraftingMachine extends TileEntityInventory implements ISidedInventory{

    protected ICraftingRegistry craftingRegistry;
    private int craftingTime;
    private int totalCraftingTime;

    protected TileEntityCraftingMachine(int capacity, int maxReceive, String name, ICraftingRegistry registry) {
        super(capacity, maxReceive, registry.inventorySize(), registry.getStackLimit(), name);
        this.craftingRegistry = registry;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        boolean isSameItem = stack != null && stack.isItemEqual(getStackInSlot(index)) && ItemStack.areItemStackTagsEqual(stack, getStackInSlot(index));
        super.setInventorySlotContents(index, stack);
        ICraftingEntry entry = craftingRegistry.get(getInputs());
        if (isInputSlot(index) && entry != null && !isSameItem) {
            this.totalCraftingTime = entry.getCraftingTime();
            craftingTime = 0;
            this.markDirty();
        }
    }

    //region ISidedInventory
    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return craftingRegistry.getSlotsForFacing(side);
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        if (craftingRegistry.canInsertItem(index, itemStackIn, direction)) {
            ItemStack inventoryStack = this.getStackInSlot(index);
            if (inventoryStack != null) {
                if (inventoryStack.isItemEqual(itemStackIn)) {
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return craftingRegistry.canExtractItem(index, stack, direction);
    }
    //endregion

    //region TileEntityCraftingMachine
    @Override
    public void update() {
        super.update();

        boolean markDirty = false;
        boolean isCrafting = false;

        if (!canProvideEnergy()) {
            return;
        }

        if (!this.worldObj.isRemote) {
            if (canProvideEnergy() && !areInputSlotsEmpty()) {
                if (canCraft()) {
                    this.craftingTime++;
                    isCrafting = true;
                    if (this.craftingTime == this.totalCraftingTime) {
                        this.craftingTime = 0;
                        this.totalCraftingTime = getCraftingTime();
                        this.craftItem();
                        markDirty = true;
                    }
                } else {
                    this.craftingTime = 0;
                }

                if (isCrafting) {
                    markDirty = true;
                    ActiveMachineBlock.setState(true, this.worldObj, this.pos);
                } else {
                    markDirty = true;
                    ActiveMachineBlock.setState(false, this.worldObj, this.pos);
                }
            }
        }

        if (markDirty) {
            this.markDirty();
        }
    }

    private boolean areInputSlotsEmpty() {
        int[] inputSlots = craftingRegistry.getInputSlotsIds();
        for (int i : inputSlots) {
            if (getStackInSlot(i) != null) {
                return false;
            }
        }
        return true;
    }

    private boolean areOutputSlotsEmpty() {
        int[] outputSlots = craftingRegistry.getOutputSlotsIds();
        for (int i : outputSlots) {
            if (getStackInSlot(i) != null) {
                return false;
            }
        }
        return true;
    }

    private ItemStack[] getContent(int[] slots) {
        ItemStack[] inputs = new ItemStack[slots.length];
        for (int i = 0; i < slots.length; i++) {
            inputs[i] = getStackInSlot(slots[i]);
        }
        return inputs;
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
        return containsSlot(craftingRegistry.getInputSlotsIds(), slot);
    }

    private boolean isOutputSlot(int slot) {
        return containsSlot(craftingRegistry.getOutputSlotsIds(), slot);
    }

    private ItemStack[] getInputs() {
        return getContent(craftingRegistry.getInputSlotsIds());
    }

    private ItemStack[] getOutputs() {
        return getContent(craftingRegistry.getOutputSlotsIds());
    }

    private boolean equalOutputs(ICraftingEntry entry) {
        ItemStack[] outputContent = getOutputs();
        for (int i = 0; i < outputContent.length; i++) {
            if (outputContent[i] != null && !outputContent[i].isItemEqual(entry.getOutputs()[i])) {
                return false;
            }
        }
        return true;
    }

    private boolean checkOutputStackSize(ICraftingEntry entry) {
        ItemStack[] outputContent = getOutputs();
        for (int i = 0; i < outputContent.length; i++) {
            if(outputContent[i] != null) {
                int result = outputContent[i].stackSize + entry.getOutputs()[i].stackSize;
                if (result > getInventoryStackLimit() || result > outputContent[i].getMaxStackSize()) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean canCraft() {
        if (areInputSlotsEmpty()) {
            return false;
        } else {
            ICraftingEntry entry = craftingRegistry.get(getInputs());
            if (entry == null) {
                return false;
            } else if (areOutputSlotsEmpty()) {
                return true;
            } else if (!equalOutputs(entry)) {
                return false;
            }
            return checkOutputStackSize(entry);
        }
    }

    private int getCraftingTime() {
        ICraftingEntry entry = craftingRegistry.get(getInputs());
        if (entry != null) {
            return entry.getCraftingTime();
        }
        return 0;
    }

    private void craftItem() {
        if (this.canCraft()) {
            ICraftingEntry entry = craftingRegistry.get(getInputs());
            int[] outputSlots = craftingRegistry.getOutputSlotsIds();
            for (int i = 0; i < outputSlots.length; i++) {
                craftItem(outputSlots[i], entry.getOutputs()[i]);
            }
            decreaseInputs();
        }
    }

    private void craftItem(int slot, ItemStack stack) {
        if (getStackInSlot(slot) == null) {
            setInventorySlotContents(slot, stack.copy());
        } else if (getStackInSlot(slot).isItemEqual(stack)) {
            getStackInSlot(slot).stackSize += stack.stackSize;
        }
    }

    private void decreaseInputs() {
        int[] inputs = craftingRegistry.getInputSlotsIds();
        for (int slot : inputs) {
            getStackInSlot(slot).stackSize--;
            if (getStackInSlot(slot).stackSize <= 0) {
                setInventorySlotContents(slot, null);
            }
        }
    }
    //endregion
}
