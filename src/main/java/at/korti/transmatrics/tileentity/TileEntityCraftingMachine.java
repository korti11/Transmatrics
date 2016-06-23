package at.korti.transmatrics.tileentity;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.NBT;
import at.korti.transmatrics.api.crafting.ICraftingRegistry;
import at.korti.transmatrics.api.crafting.ICraftingRegistry.ICraftingEntry;
import at.korti.transmatrics.block.ActiveMachineBlock;
import at.korti.transmatrics.event.MachineCraftingEvent;
import at.korti.transmatrics.util.helper.CraftingHelper;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

/**
 * Created by Korti on 15.03.2016.
 */
public abstract class TileEntityCraftingMachine extends TileEntityInventory implements ISidedInventory {

    protected ICraftingRegistry<ItemStack> craftingRegistry;
    private int craftingTime;
    private int totalCraftingTime;
    protected int energyUse;
    protected int efficiency;
    protected int maxEfficiency;

    protected TileEntityCraftingMachine(int capacity, int maxReceive, int energyUse, boolean capacitorSlot, String name, ICraftingRegistry<ItemStack> registry) {
        super(capacity, maxReceive, registry.inventorySize(), registry.getStackLimit(), capacitorSlot, name);
        this.craftingRegistry = registry;
        this.energyUse = energyUse;
        this.calculateMaxEfficiency();
    }

    //region TileEntity
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        compound.setInteger(NBT.CRAFTING_TIME, craftingTime);
        compound.setInteger(NBT.TOTAL_CRAFTING_TIME, totalCraftingTime);
        compound.setInteger(NBT.CRAFTING_EFFICIENCY, efficiency);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.craftingTime = compound.getInteger(NBT.CRAFTING_TIME);
        this.totalCraftingTime = compound.getInteger(NBT.TOTAL_CRAFTING_TIME);
        this.efficiency = compound.getInteger(NBT.CRAFTING_EFFICIENCY);
    }
    //endregion

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
            if (this.energyStorage.getEnergyStored() - energyUse >= 0 && !areInputSlotsEmpty()) {
                if (canCraft()) {
                    this.efficiency = energyStorage.getEnergyStored() / (energyStorage.getCapacity() / maxEfficiency);
                    this.craftingTime += efficiency;
                    if(useEnergyOnUpdate()) {
                        this.energyStorage.modifyEnergy(-energyUse);
                    }
                    isCrafting = true;
                    if (this.craftingTime >= this.totalCraftingTime) {
                        this.craftingTime = 0;
                        this.totalCraftingTime = getCraftingTime();
                        this.craftItem();
                        markDirty = true;
                    }
                } else {
                    this.craftingTime = 0;
                    this.efficiency = Math.max(efficiency - 1, 0);
                }
            }
            if (isCrafting && !ActiveMachineBlock.isActive(worldObj, pos)) {
                markDirty = true;
                ActiveMachineBlock.setState(true, this.worldObj, this.pos);
            } else if(!isCrafting && ActiveMachineBlock.isActive(worldObj, pos)) {
                markDirty = true;
                ActiveMachineBlock.setState(false, this.worldObj, this.pos);
            }
        }

        if (markDirty) {
            this.markDirty();
        }
    }

    protected boolean areInputSlotsEmpty() {
        int[] inputSlots = craftingRegistry.getInputSlotsIds();
        for (int i : inputSlots) {
            if (getStackInSlot(i) != null) {
                return false;
            }
        }
        return true;
    }

    protected boolean areOutputSlotsEmpty() {
        int[] outputSlots = craftingRegistry.getOutputSlotsIds();
        for (int i : outputSlots) {
            if (getStackInSlot(i) != null) {
                return false;
            }
        }
        return true;
    }

    protected int getSlotForStack(boolean input, ItemStack stack) {
        int[] slots = input ? craftingRegistry.getInputSlotsIds() : craftingRegistry.getOutputSlotsIds();
        for (int slot : slots) {
            if (getStackInSlot(slot) != null && getStackInSlot(slot).isItemEqual(stack)) {
                return slot;
            }
        }
        return -1;
    }

    protected ItemStack[] getContent(int[] slots) {
        ItemStack[] inputs = new ItemStack[slots.length];
        for (int i = 0; i < slots.length; i++) {
            inputs[i] = getStackInSlot(slots[i]);
        }
        return inputs;
    }

    protected boolean containsSlot(int[] slots, int searchSlot) {
        for (int slot : slots) {
            if (slot == searchSlot) {
                return true;
            }
        }
        return false;
    }

    protected boolean isInputSlot(int slot) {
        return containsSlot(craftingRegistry.getInputSlotsIds(), slot);
    }

    protected boolean isOutputSlot(int slot) {
        return containsSlot(craftingRegistry.getOutputSlotsIds(), slot);
    }

    protected ItemStack[] getInputs() {
        return getContent(craftingRegistry.getInputSlotsIds());
    }

    protected ItemStack[] getOutputs() {
        return getContent(craftingRegistry.getOutputSlotsIds());
    }

    protected boolean equalOutputs(ICraftingEntry<ItemStack, ItemStack> entry) {
        ItemStack[] outputContent = getOutputs();
        for (int i = 0; i < outputContent.length && i < entry.getOutputs().length; i++) {
            if (outputContent[i] != null && !outputContent[i].isItemEqual(entry.getOutputs()[i])) {
                return false;
            }
        }
        return true;
    }

    protected boolean checkOutputStackSize(ICraftingEntry<ItemStack, ItemStack> entry) {
        ItemStack[] outputContent = getOutputs();
        for (int i = 0; i < outputContent.length && i < entry.getOutputs().length; i++) {
            if(outputContent[i] != null) {
                int result = outputContent[i].stackSize + entry.getOutputs()[i].stackSize;
                if (result > getInventoryStackLimit() || result > outputContent[i].getMaxStackSize()) {
                    return false;
                }
            }
        }
        return true;
    }

    protected boolean canCraft() {
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

    protected int getCraftingTime() {
        ICraftingEntry entry = craftingRegistry.get(getInputs());
        if (entry != null) {
            return entry.getCraftingTime();
        }
        return 0;
    }

    protected void craftItem() {
        if (this.canCraft()) {
            ICraftingEntry<ItemStack, ItemStack> entry = craftingRegistry.get(getInputs());
            int[] outputSlots = craftingRegistry.getOutputSlotsIds();
            ItemStack[] outputs = entry.getOutputs();
            EVENT_BUS.post(new MachineCraftingEvent.Pre<>(entry, craftingRegistry, this, this));
            for (int i = 0; i < outputs.length; i++) {
                craftItem(outputSlots[i], outputs[i]);
            }
            decreaseInputs(entry.getInputs());
            EVENT_BUS.post(new MachineCraftingEvent.Post<>(entry, craftingRegistry, this, this));
        }
    }

    protected void craftItem(int slot, ItemStack stack) {
        if(CraftingHelper.chanceToCraft(craftingRegistry, slot, getInputs())) {
            if (getStackInSlot(slot) == null) {
                setInventorySlotContents(slot, stack.copy());
            } else if (getStackInSlot(slot).isItemEqual(stack)) {
                getStackInSlot(slot).stackSize += stack.stackSize;
            }
        }
    }

    protected void decreaseInputs(ItemStack... stacks) {
        for (ItemStack stack : stacks) {
            int slot = getSlotForStack(true, stack);
            if(slot != -1 && craftingRegistry.decreaseItemForSlot(slot)) {
                getStackInSlot(slot).stackSize -= stack.stackSize;
                if (getStackInSlot(slot).stackSize <= 0) {
                    setInventorySlotContents(slot, null);
                }
            }
        }
    }

    protected void calculateMaxEfficiency() {
        this.maxEfficiency = energyStorage.getCapacity() / Constants.Energy.EFFICIENCY_DIVIDER;
    }

    protected boolean useEnergyOnUpdate() {
        return true;
    }
    //endregion

    //region IInventory
    @Override
    public int getField(int id) {
        switch (id) {
            case 0:
                return craftingTime;
            case 1:
                return totalCraftingTime;
            case 2:
                return getEnergyStored();
            case 3:
                return getMaxEnergyStored();
            case 4:
                return efficiency;
            case 5:
                return maxEfficiency;
        }
        return 0;
    }

    @Override
    public void setField(int id, int value) {
        switch (id) {
            case 0:
                craftingTime = value;
                break;
            case 1:
                totalCraftingTime = value;
                break;
            case 2:
                energyStorage.setEnergyStored(value);
                break;
            case 3:
                energyStorage.setCapacity(value);
                break;
            case 4:
                efficiency = value;
                break;
            case 5:
                maxEfficiency = value;
        }
    }

    @Override
    public int getFieldCount() {
        return 6;
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
        } else if (isInputSlot(index) && stack == null) {
            this.totalCraftingTime = 0;
            this.craftingTime = 0;
            this.efficiency = 0;
        }
    }
    //endregion
}
