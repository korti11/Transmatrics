package at.korti.transmatrics.tileentity;

import at.korti.transmatrics.api.Constants.Energy;
import at.korti.transmatrics.api.Constants.NBT;
import at.korti.transmatrics.api.crafting.ICraftingRegistry;
import at.korti.transmatrics.api.crafting.ICraftingRegistry.ICraftingEntry;
import at.korti.transmatrics.block.ActiveMachineBlock;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

/**
 * Created by Korti on 26.07.2017.
 */
public abstract class TileEntityBasicCraftingMachine<I, O> extends TileEntityInventory implements ISidedInventory {

    private ICraftingRegistry<I> craftingRegistry;
    private int craftingTime;
    private int totalCraftingTime;
    private int energyUse;
    private int efficiency;
    private int maxEfficiency;

    protected TileEntityBasicCraftingMachine(int capacity, int maxReceive, int energyUse, boolean capacitorSlot,
                                             String name, ICraftingRegistry<I> registry) {
        super(capacity, maxReceive, registry.inventorySize(), registry.getStackLimit(), capacitorSlot, name);
        this.craftingRegistry = registry;
        this.energyUse = energyUse;
        this.calculateMaxEfficiency();
    }

    protected ICraftingRegistry<I> getCraftingRegistry() {
        return craftingRegistry;
    }

    //region TileEntity
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger(NBT.CRAFTING_TIME, craftingTime);
        compound.setInteger(NBT.TOTAL_CRAFTING_TIME, totalCraftingTime);
        compound.setInteger(NBT.CRAFTING_EFFICIENCY, efficiency);
        return super.writeToNBT(compound);
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
    public int[] getSlotsForFace(EnumFacing enumFacing) {
        return craftingRegistry.getSlotsForFacing(enumFacing);
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStack, EnumFacing enumFacing) {
        if(craftingRegistry.canInsertItem(index, itemStack, enumFacing)) {
            ItemStack stack = this.getStackInSlot(index);
            if(stack.isEmpty()) {
                return true;
            } else if (ItemStack.areItemStacksEqual(stack, itemStack)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack itemStack, EnumFacing enumFacing) {
        return craftingRegistry.canExtractItem(index, itemStack, enumFacing);
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
            default:
                return 0;
        }
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
                break;
        }
    }

    @Override
    public int getFieldCount() {
        return 6;
    }
    //endregion

    //region TileEntityBasicCraftingMachine
    @Override
    public void update() {
        super.update();
        if(!getWorld().isRemote) {
            boolean markDirty = false;
            boolean isCrafting = false;
            if (canOperate()) {
                this.efficiency = calculateEfficiency();
                this.craftingTime += efficiency;
                this.updateStoredEnergy();
                isCrafting = true;
                if (craftingFinished()) {
                    this.craftingTime = 0;
                    this.totalCraftingTime = getCraftingTime();
                    this.craft();
                    markDirty = true;
                }
            } else {
                this.craftingTime = 0;
                this.efficiency = Math.max(efficiency - 1, 0);
            }
            markDirty |= setBlockState(isCrafting);
            if (markDirty) {
                this.markDirty();
            }
        }
    }

    protected abstract void craft();

    protected abstract void decreaseInputs(I... inputs);

    protected int calculateEfficiency() {
        return getEnergyStored() / (getMaxEnergyStored() / maxEfficiency);
    }

    protected boolean canOperate() {
        return hasEnoughEnergy() && !areInputsEmpty() && canCraft();
    }

    protected boolean hasEnoughEnergy() {
        return getEnergyStored() - energyUse >= 0;
    }

    protected abstract boolean areInputsEmpty();

    protected abstract boolean areOutputsEmpty();

    protected boolean areSlotsEmpty(int[] slots) {
        for(int i : slots) {
            if(!getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    protected ItemStack[] getContent(int[] slots) {
        ItemStack[] content = new ItemStack[slots.length];
        for (int i = 0; i < slots.length; i++) {
            content[i] = getStackInSlot(slots[i]);
        }
        return content;
    }

    protected int getSlotForStack(boolean input, ItemStack stack) {
        int[] slots = input ? getCraftingRegistry().getInputSlotsIds() : getCraftingRegistry().getOutputSlotsIds();
        for (int slot : slots) {
            if (getStackInSlot(slot).isItemEqual(stack)) {
                return slot;
            }
        }
        return -1;
    }

    @SuppressWarnings("unchecked")
    protected boolean canCraft() {
        if (!areInputsEmpty()) {
            ICraftingEntry<I, O> entry = craftingRegistry.get(getInputs());
            if(entry == null) {
                return false;
            } else if(areOutputsEmpty()) {
                return true;
            } else if(!equalOutputs(entry)) {
                return false;
            }
            return checkOutputIsFull(entry);
        }
        return false;
    }

    protected abstract boolean checkOutputIsFull(ICraftingEntry<I, O> entry);

    public abstract I[] getInputs();

    public abstract O[] getOutputs();

    protected boolean equalOutputs(ICraftingEntry<I, O> entry) {
        O[] outputContent = getOutputs();
        for(int i = 0; i < outputContent.length && i < entry.getOutputs().length; i++) {
            if (!outputContent[i].equals(entry.getOutputs()[i])) {
                return false;
            }
        }
        return true;
    }

    protected void updateStoredEnergy() {
        if(useEnergyOnUpdate()) {
            this.energyStorage.modifyEnergyStored(-energyUse);
        }
    }

    protected boolean useEnergyOnUpdate() {
        return true;
    }

    protected boolean craftingFinished() {
        return this.craftingTime >= this.totalCraftingTime;
    }

    @SuppressWarnings("unchecked")
    protected int getCraftingTime() {
        ICraftingEntry<I, O> entry = craftingRegistry.get(getInputs());
        if(entry != null) {
            return entry.getCraftingTime();
        }
        return 0;
    }

    protected boolean setBlockState(boolean isCrafting) {
        if (isCrafting && !ActiveMachineBlock.isActive(getWorld(), pos)) {
            ActiveMachineBlock.setState(true, this.getWorld(), this.pos);
            return true;
        } else if(!isCrafting && ActiveMachineBlock.isActive(getWorld(), pos)) {
            ActiveMachineBlock.setState(false, this.getWorld(), this.pos);
            return true;
        }
        return false;
    }

    protected void calculateMaxEfficiency() {
        this.maxEfficiency = getMaxEnergyStored() / Energy.EFFICIENCY_DIVIDER;
    }
    //endregion

}
