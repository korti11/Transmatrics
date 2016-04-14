package at.korti.transmatrics.tileentity;

import at.korti.transmatrics.api.Constants.NBT;
import at.korti.transmatrics.api.crafting.IFluidItemCraftingRegistry;
import at.korti.transmatrics.api.crafting.IFluidItemCraftingRegistry.IFluidItemCraftingEntry;
import at.korti.transmatrics.block.ActiveMachineBlock;
import at.korti.transmatrics.util.helper.CraftingHelper;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.*;

/**
 * Created by Korti on 02.04.2016.
 */
public abstract class TileEntityFluidItemCraftingMachine extends TileEntityInventory implements IFluidHandler, ISidedInventory {

    protected IFluidItemCraftingRegistry craftingRegistry;
    protected FluidTank[] tanks;
    private int craftingTime;
    private int totalCraftingTime;
    protected int energyUse;
    protected int efficiency;
    protected int maxEfficiency;

    protected TileEntityFluidItemCraftingMachine(int capacity, int maxReceive, int energyUse, String name, IFluidItemCraftingRegistry craftingRegistry) {
        super(capacity, maxReceive, craftingRegistry.inventorySize(), craftingRegistry.getStackLimit(), name);
        this.craftingRegistry = craftingRegistry;
        initTanks();
        this.energyUse = energyUse;
        this.maxEfficiency = energyStorage.getCapacity() / 1000;
    }

    //region TileEntity
    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger(NBT.CRAFTING_TIME, craftingTime);
        compound.setInteger(NBT.TOTAL_CRAFTING_TIME, totalCraftingTime);
        compound.setInteger(NBT.CRAFTING_EFFICIENCY, efficiency);
        NBTTagList tanks = new NBTTagList();
        for (FluidTank tank : this.tanks) {
            NBTTagCompound nbtTank = new NBTTagCompound();
            tank.writeToNBT(nbtTank);
            tanks.appendTag(nbtTank);
        }
        compound.setTag(NBT.CRAFTING_TANKS, tanks);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.craftingTime = compound.getInteger(NBT.CRAFTING_TIME);
        this.totalCraftingTime = compound.getInteger(NBT.TOTAL_CRAFTING_TIME);
        this.efficiency = compound.getInteger(NBT.CRAFTING_EFFICIENCY);
        NBTTagList tanks = compound.getTagList(NBT.CRAFTING_TANKS, 10);
        for (int i = 0; i < tanks.tagCount(); i++) {
            NBTTagCompound nbtTank = tanks.getCompoundTagAt(i);
            this.tanks[i].readFromNBT(nbtTank);
        }
    }
    //endregion

    //region TileEntityFluidItemCraftingMachine
    private void initTanks() {
        int[] capacities = craftingRegistry.getFluidCapacities();
        tanks = new FluidTank[capacities.length];
        for (int i = 0; i < capacities.length; i++) {
            tanks[i] = new FluidTank(capacities[i]);
        }
    }

    @Override
    public void update() {
        super.update();

        boolean markDirty = false;
        boolean isCrafting = false;

        if (!canProvideEnergy()) {
            return;
        }

        if (!worldObj.isRemote) {
            if (this.energyStorage.getEnergyStored() - energyUse >= 0 && !areTanksEmpty(true) && !areInputSlotsEmpty()) {
                if (canCraft()) {
                    this.efficiency = energyStorage.getEnergyStored() / (energyStorage.getCapacity() / maxEfficiency);
                    this.craftingTime += efficiency;
                    this.energyStorage.modifyEnergy(-energyUse);
                    isCrafting = true;
                    if (this.craftingTime >= this.totalCraftingTime) {
                        this.craftingTime = 0;
                        this.totalCraftingTime = getCraftingTime();
                        this.craft();
                        markDirty = true;
                    }
                }
                else {
                    this.craftingTime = 0;
                    this.efficiency = Math.max(efficiency - 1, 0);
                }
            } else {
                this.craftingTime = 0;
                this.efficiency = Math.max(efficiency - 1, 0);
            }
            if (isCrafting && !ActiveMachineBlock.isActive(worldObj, pos)) {
                markDirty = true;
                ActiveMachineBlock.setState(true, this.worldObj, this.pos);
            } else if (!isCrafting && ActiveMachineBlock.isActive(worldObj, pos)) {
                markDirty = true;
                ActiveMachineBlock.setState(false, this.worldObj, this.pos);
            }
        }

        if (markDirty) {
            this.markDirty();
        }
    }

    private int getCraftingTime() {
        IFluidItemCraftingEntry entry = craftingRegistry.get(getFluidsInput(), getInventoryInputs());
        if (entry != null) {
            return entry.getCraftingTime();
        }
        return 0;
    }

    private boolean areInputSlotsEmpty() {
        int[] inputSlots = craftingRegistry.getInputSlotsIds();
        for (int slot : inputSlots) {
            if (getStackInSlot(slot) != null) {
                return false;
            }
        }
        return true;
    }

    private boolean areOutputSlotsEmpty() {
        int[] inputSlots = craftingRegistry.getOutputSlotsIds();
        for (int slot : inputSlots) {
            if (getStackInSlot(slot) != null) {
                return false;
            }
        }
        return true;
    }

    private boolean areTanksEmpty(boolean input) {
        int[] tankIds = input ? craftingRegistry.getFluidInputIds() : craftingRegistry.getOutputSlotsIds();
        for (int tankId : tankIds) {
            FluidTank tank = tanks[tankId];
            if (tank.getFluidAmount() != 0) {
                return false;
            }
        }
        return true;
    }

    private boolean canCraft() {
        if (areTanksEmpty(true) || areInputSlotsEmpty()) {
            return false;
        } else {
            boolean isFluidOutput = craftingRegistry.getOutputSlotsIds().length == 0;
            if (isFluidOutput) {
                IFluidItemCraftingEntry<FluidStack> entry = craftingRegistry.get(getFluidsInput(), getInventoryInputs());
                if (entry == null) {
                    return false;
                } else if (areTanksEmpty(false)) {
                    return false;
                } else if (equalFluids(entry.getOutputs())) {
                    return false;
                }
                return checkFluidSizes(false, entry.getOutputs());
            } else {
                IFluidItemCraftingEntry<ItemStack> entry = craftingRegistry.get(getFluidsInput(), getInventoryInputs());
                if (entry == null) {
                    return false;
                } else if (areOutputSlotsEmpty()) {
                    return true;
                } else if (!equalInventoryOutputs(entry.getOutputs())) {
                    return false;
                }
                return checkOutputStackSize(entry.getOutputs());
            }
        }
    }

    private ItemStack[] getInventoryContent(int[] slots) {
        ItemStack[] inventoryContent = new ItemStack[slots.length];
        for (int i = 0; i < slots.length; i++) {
            inventoryContent[i] = getStackInSlot(slots[i]);
        }
        return inventoryContent;
    }

    private ItemStack[] getInventoryInputs() {
        return getInventoryContent(craftingRegistry.getInputSlotsIds());
    }

    private ItemStack[] getInventoryOutputs() {
        return getInventoryContent(craftingRegistry.getOutputSlotsIds());
    }

    private FluidStack[] getFluids(int[] tankIds) {
        FluidStack[] stacks = new FluidStack[tankIds.length];
        for (int i = 0; i < tankIds.length; i++) {
            stacks[i] = tanks[tankIds[i]].getFluid();
        }
        return stacks;
    }

    public FluidStack[] getFluidsInput() {
        return getFluids(craftingRegistry.getFluidInputIds());
    }

    private boolean equalInventoryOutputs(ItemStack[] outputs) {
        ItemStack[] outputContent = getInventoryOutputs();
        for (int i = 0; i < outputContent.length && i < outputs.length; i++) {
            if (outputContent[i] != null && !outputContent[i].isItemEqual(outputs[i])) {
                return false;
            }
        }
        return true;
    }

    private boolean equalFluids(FluidStack[] stacks) {
        boolean flag = true;
        for (FluidTank tank : tanks) {
            for (FluidStack stack : stacks) {
                if (tank.getFluid() != null && tank.getFluid().getFluid() == stack.getFluid()) {
                    flag = true;
                    continue;
                } else if (tank.getFluid() != null && tank.getFluid().getFluid() != stack.getFluid()) {
                    flag = false;
                }
            }
            if (!flag) {
                return false;
            }
        }
        return true;
    }

    private boolean checkOutputStackSize(ItemStack[] outputs) {
        ItemStack[] outputContent = getInventoryOutputs();
        for (int i = 0; i < outputContent.length && i < outputs.length; i++) {
            if (outputContent[i] != null) {
                int result = outputContent[i].stackSize + outputs[i].stackSize;
                if (result > getInventoryStackLimit() || result > outputContent[i].getMaxStackSize()) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkFluidSizes(boolean input, FluidStack[] outputs) {
        for (FluidStack stack : outputs) {
            FluidTank tank = getTankForFluid(input, stack.getFluid());
            int amount = tank.fill(stack, false);
            if (amount != stack.amount) {
                return false;
            }
        }
        return true;
    }

    private FluidTank getTankForFluid(boolean input, Fluid fluid) {
        int[] tankIds = input ? craftingRegistry.getFluidInputIds() : craftingRegistry.getFluidOutputIds();
        for (int tankId : tankIds) {
            FluidTank tank = tanks[tankId];
            if (tank.getFluid() == null || tank.getFluid().getFluid() == fluid) {
                return tank;
            }
        }
        return findEmptyTank(input);
    }

    private FluidTank findEmptyTank(boolean input) {
        int[] tankIds = input ? craftingRegistry.getFluidInputIds() : craftingRegistry.getFluidOutputIds();
        for (int tankId : tankIds) {
            FluidTank tank = tanks[tankId];
            if (tank.getFluidAmount() == 0) {
                return tank;
            }
        }
        return null;
    }

    private void craft() {
        if (this.canCraft()) {
            boolean isFluidOutput = craftingRegistry.getOutputSlotsIds().length == 0;
            if (isFluidOutput) {
                IFluidItemCraftingEntry<FluidStack> entry = craftingRegistry.get(getFluidsInput(), getInventoryInputs());
                FluidStack[] outputs = entry.getOutputs();
                for (FluidStack stack : outputs) {
                    craftFluid(stack);
                }
                decreaseInputs(entry.getInputs(), entry.getSecondInputs());
            } else {
                IFluidItemCraftingEntry<ItemStack> entry = craftingRegistry.get(getFluidsInput(), getInventoryInputs());
                int[] outputSlots = craftingRegistry.getOutputSlotsIds();
                ItemStack[] outputs = entry.getOutputs();
                for (int i = 0; i < outputs.length; i++) {
                    craftItem(outputSlots[i], outputs[i]);
                }
                decreaseInputs(entry.getInputs(), entry.getSecondInputs());
            }
        }
    }

    private void craftFluid(FluidStack stack) {
        FluidTank tank = getTankForFluid(false, stack.getFluid());
        tank.fill(stack, true);
    }

    private void craftItem(int outputSlot, ItemStack output) {
        if (CraftingHelper.chanceToCraft(craftingRegistry, outputSlot, getFluidsInput(), getInventoryInputs())) {
            if (getStackInSlot(outputSlot) == null) {
                setInventorySlotContents(outputSlot, output.copy());
            } else if (getStackInSlot(outputSlot).isItemEqual(output)) {
                getStackInSlot(outputSlot).stackSize += output.stackSize;
            }
        }
    }

    private void decreaseInputs(FluidStack[] inputs, ItemStack[] secondInputs) {
        for (FluidStack stack : inputs) {
            getTankForFluid(true, stack.getFluid()).drain(stack.amount, true);
        }
        for (ItemStack stack : secondInputs) {
            int slot = getSlotForStack(true, stack);
            if (slot != -1 && craftingRegistry.decreaseItemForSlot(slot)) {
                getStackInSlot(slot).stackSize -= stack.stackSize;
                if (getStackInSlot(slot).stackSize <= 0) {
                    setInventorySlotContents(slot, null);
                }
            }
        }
    }

    private int getSlotForStack(boolean input, ItemStack stack) {
        int[] slots = input ? craftingRegistry.getInputSlotsIds() : craftingRegistry.getOutputSlotsIds();
        for (int slot : slots) {
            if (getStackInSlot(slot).isItemEqual(stack)) {
                return slot;
            }
        }
        return -1;
    }

    private FluidTank firstFilledTank(boolean input) {
        int[] tankIds = input ? craftingRegistry.getFluidInputIds() : craftingRegistry.getFluidOutputIds();
        for (int tankId : tankIds) {
            FluidTank tank = tanks[tankId];
            if (tank.getFluidAmount() != 0) {
                return tank;
            }
        }
        return null;
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
    //endregion

    //region IFluidHandler
    @Override
    public int fill(EnumFacing from, FluidStack resource, boolean doFill) {
        FluidTank tank = getTankForFluid(true, resource.getFluid());
        int result = tank.fill(resource, doFill);
        syncClient();
        return result;
    }

    @Override
    public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain) {
        FluidTank tank = getTankForFluid(false, resource.getFluid());
        FluidStack stack = tank.drain(resource.amount, doDrain);
        syncClient();
        return stack;
    }

    @Override
    public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain) {
        FluidTank tank = firstFilledTank(false);
        FluidStack stack;
        if (tank != null) {
            stack = tank.drain(maxDrain, doDrain);
            syncClient();
        } else {
            stack = findEmptyTank(false).drain(maxDrain, doDrain);
            syncClient();
        }
        return stack;
    }

    @Override
    public boolean canFill(EnumFacing from, Fluid fluid) {
        return craftingRegistry.canFill(fluid, from);
    }

    @Override
    public boolean canDrain(EnumFacing from, Fluid fluid) {
        return craftingRegistry.canDrain(fluid, from);
    }

    @Override
    public FluidTankInfo[] getTankInfo(EnumFacing from) {
        FluidTankInfo[] tankInfos = new FluidTankInfo[tanks.length];
        for (int i = 0; i < tankInfos.length; i++) {
            tankInfos[i] = tanks[i].getInfo();
        }
        return tankInfos;
    }
    //endregion

    //region ISidedInventory
    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return craftingRegistry.getSlotsForFacing(side);
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return craftingRegistry.canInsertItem(index, itemStackIn, direction);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return craftingRegistry.canExtractItem(index, stack, direction);
    }
    //endregion
}
