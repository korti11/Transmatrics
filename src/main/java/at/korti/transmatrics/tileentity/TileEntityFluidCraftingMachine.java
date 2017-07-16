package at.korti.transmatrics.tileentity;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.NBT;
import at.korti.transmatrics.api.crafting.ICraftingRegistry;
import at.korti.transmatrics.api.crafting.ICraftingRegistry.ICraftingEntry;
import at.korti.transmatrics.api.crafting.IFluidCraftingRegistry;
import at.korti.transmatrics.block.ActiveMachineBlock;
import at.korti.transmatrics.event.MachineCraftingEvent;
import at.korti.transmatrics.util.helper.CraftingHelper;
import at.korti.transmatrics.util.helper.InventoryHelper;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

/**
 * Created by Korti on 30.03.2016.
 */
public abstract class TileEntityFluidCraftingMachine extends TileEntityInventory implements ISidedInventory, IFluidHandler {

    protected IFluidCraftingRegistry craftingRegistry;
    protected FluidTank[] tanks;
    private int craftingTime;
    private int totalCraftingTime;
    protected int energyUse;
    protected int efficiency;
    protected int maxEfficiency;

    protected TileEntityFluidCraftingMachine(int capacity, int maxReceive, int energyUse, boolean capacitorSlot, String name, IFluidCraftingRegistry registry) {
        super(capacity, maxReceive, registry.inventorySize(), registry.getStackLimit(), capacitorSlot, name);
        this.craftingRegistry = registry;
        this.initTanks();
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
        NBTTagList tanks = new NBTTagList();
        for (FluidTank tank : this.tanks) {
            NBTTagCompound nbtTank = new NBTTagCompound();
            tank.writeToNBT(nbtTank);
            tanks.appendTag(nbtTank);
        }
        compound.setTag(NBT.CRAFTING_TANKS, tanks);
        return compound;
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

    //region TileEntityFluidCraftingMachine
    protected abstract boolean isFluidInput();

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

        boolean isFluidInput = isFluidInput();
        boolean markDirty = false;
        boolean isCrafting = false;

        if (!canProvideEnergy()) {
            return;
        }

        if (!getWorld().isRemote) {
            if (this.energyStorage.getEnergyStored() - energyUse >= 0 && isFluidInput ? !areTanksEmpty(true) : !areInputSlotsEmpty()) {
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
                } else {
                    this.craftingTime = 0;
                    this.efficiency = Math.max(efficiency - 1, 0);
                }
            }
            if (isCrafting && !ActiveMachineBlock.isActive(getWorld(), pos)) {
                markDirty = true;
                ActiveMachineBlock.setState(true, this.getWorld(), this.pos);
            } else if (!isCrafting && ActiveMachineBlock.isActive(getWorld(), pos)) {
                markDirty = true;
                ActiveMachineBlock.setState(false, this.getWorld(), this.pos);
            }
        }

        if (markDirty) {
            this.markDirty();
        }
    }

    protected boolean areInputSlotsEmpty() {
        int[] inputSlots = craftingRegistry.getInputSlotsIds();
        for (int i : inputSlots) {
            if (!getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    protected boolean areOutputSlotsEmpty() {
        int[] outputSlots = craftingRegistry.getOutputSlotsIds();
        for (int i : outputSlots) {
            if (!getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    protected boolean areTanksEmpty(boolean input) {
        int[] tankIds = input ? craftingRegistry.getFluidInputIds() : craftingRegistry.getFluidOutputIds();
        for (int tankId : tankIds) {
            FluidTank tank = tanks[tankId];
            if (tank.getFluidAmount() != 0) {
                return false;
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    protected boolean canCraft() {
        if (isFluidInput() ? areTanksEmpty(true) : areInputSlotsEmpty()) {
            return false;
        } else if(!isFluidInput()) {
            ICraftingEntry<ItemStack, FluidStack> entry = craftingRegistry.get(getInventoryInputs());
            if (entry == null) {
                return false;
            } else if (areTanksEmpty(false)) {
                return true;
            } else if (!equalFluids(false, entry.getOutputs())) {
                return false;
            }
            return checkFluidSize(false, entry.getOutputs());
        } else {
            boolean isFluidOutput = craftingRegistry.getOutputSlotsIds().length == 0;
            if (isFluidOutput) {
                ICraftingEntry<FluidStack, FluidStack> entry = craftingRegistry.get(getInputFluids());
                if (entry == null) {
                    return false;
                } else if (areTanksEmpty(false)) {
                    return true;
                } else if (!equalFluids(false, entry.getOutputs())) {
                    return false;
                }
                return checkFluidSize(false, entry.getOutputs());
            } else {
                ICraftingEntry<FluidStack, ItemStack> entry = craftingRegistry.get(getInputFluids());
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

    protected ItemStack[] getInventoryContent(int[] slots) {
        ItemStack[] inventoryContent = new ItemStack[slots.length];
        for (int i = 0; i < slots.length; i++) {
            inventoryContent[i] = getStackInSlot(slots[i]);
        }
        return inventoryContent;
    }

    protected ItemStack[] getInventoryInputs() {
        return getInventoryContent(craftingRegistry.getInputSlotsIds());
    }

    protected ItemStack[] getInventoryOutputs() {
        return getInventoryContent(craftingRegistry.getOutputSlotsIds());
    }

    protected int getSlotForStack(boolean input, ItemStack stack) {
        int[] slots = input ? craftingRegistry.getInputSlotsIds() : craftingRegistry.getOutputSlotsIds();
        for (int slot : slots) {
            if (getStackInSlot(slot).isItemEqual(stack)) {
                return slot;
            }
        }
        return -1;
    }

    protected boolean equalInventoryOutputs(ItemStack[] outputs) {
        ItemStack[] outputContent = getInventoryOutputs();
        for (int i = 0; i < outputContent.length && i < outputs.length; i++) {
            if (!outputContent[i].isItemEqual(outputs[i])) {
                return false;
            }
        }
        return true;
    }

    protected boolean checkOutputStackSize(ItemStack[] outputs) {
        ItemStack[] outputContent = getInventoryOutputs();
        for (int i = 0; i < outputContent.length && i < outputs.length; i++) {
            if(!outputContent[i].isEmpty()) {
                int result = outputContent[i].getCount() + outputs[i].getCount();
                if (result > getInventoryStackLimit() || result > outputContent[i].getMaxStackSize()) {
                    return false;
                }
            }
        }
        return true;
    }

    protected boolean isSlot(boolean input, int slot) {
        int[] slots = input ? craftingRegistry.getInputSlotsIds() : craftingRegistry.getOutputSlotsIds();
        for (int i : slots) {
            if (slot == i) {
                return true;
            }
        }
        return false;
    }

    protected boolean equalFluids(boolean input, FluidStack[] stacks) {
        boolean flag = true;
        int[] tankIds = input ? craftingRegistry.getFluidInputIds() : craftingRegistry.getFluidOutputIds();
        for (int tankId : tankIds) {
            FluidTank tank = tanks[tankId];
            for (FluidStack stack : stacks) {
                if (tank.getFluid() != null && tank.getFluid().getFluid() == stack.getFluid()) {
                    flag = true;
                } else if (tank.getFluid() != null) {
                    flag = false;
                }
            }
            if (!flag) {
                return false;
            }
        }
        return true;
    }

    protected boolean checkFluidSize(boolean input, FluidStack[] stacks) {
        for (FluidStack stack : stacks) {
            FluidTank tank = getTankForFluid(input, stack.getFluid());
            int amount = tank.fill(stack, false);
            if (amount != stack.amount) {
                return false;
            }
        }
        return true;
    }

    protected FluidTank getTankForFluid(boolean input, Fluid fluid) {
        int[] tankIds = input ? craftingRegistry.getFluidInputIds() : craftingRegistry.getFluidOutputIds();
        for (int tankId : tankIds) {
            FluidTank tank = tanks[tankId];
            if (tank.getFluid() != null && tank.getFluid().getFluid() == fluid) {
                return tank;
            }
        }
        return findEmptyTank(input);
    }

    protected FluidTank findEmptyTank(boolean input) {
        int[] tankIds = input ? craftingRegistry.getFluidInputIds() : craftingRegistry.getFluidOutputIds();
        for (int tankId : tankIds) {
            FluidTank tank = tanks[tankId];
            if (tank.getFluidAmount() == 0) {
                return tank;
            }
        }
        return null;
    }

    protected FluidTank firstFilledTank(boolean input) {
        int[] tankIds = input ? craftingRegistry.getFluidInputIds() : craftingRegistry.getFluidOutputIds();
        for (int tankId : tankIds) {
            FluidTank tank = tanks[tankId];
            if (tank.getFluidAmount() != 0) {
                return tank;
            }
        }
        return null;
    }

    protected FluidStack[] getFluids(int[] tankIds) {
        FluidStack[] stacks = new FluidStack[tankIds.length];
        for (int i = 0; i < stacks.length; i++) {
            stacks[i] = tanks[tankIds[i]].getFluid();
        }
        return stacks;
    }

    protected FluidStack[] getInputFluids() {
        return getFluids(craftingRegistry.getFluidInputIds());
    }

    @SuppressWarnings("unchecked")
    protected int getCraftingTime() {
        ICraftingEntry entry = craftingRegistry.get(isFluidInput() ? getInputFluids() : getInventoryInputs());
        if (entry != null) {
            return entry.getCraftingTime();
        }
        return 0;
    }

    @SuppressWarnings("unchecked")
    protected void craft() {
        if (this.canCraft()) {
            if(!isFluidInput()) {
                ICraftingEntry<ItemStack, FluidStack> entry = craftingRegistry.get(getInventoryInputs());
                FluidStack[] outputs = entry.getOutputs();
                EVENT_BUS.post(new MachineCraftingEvent.Pre<>(entry, craftingRegistry, this, this));
                for (FluidStack stack : outputs) {
                    craftFluid(stack);
                }
                decreaseInputs(entry.getInputs());
                EVENT_BUS.post(new MachineCraftingEvent.Post<>(entry, craftingRegistry, this, this));
            } else {
                boolean isFluidOutput = craftingRegistry.getOutputSlotsIds().length == 0;
                if (isFluidOutput) {
                    ICraftingEntry<FluidStack, FluidStack> entry = craftingRegistry.get(getInputFluids());
                    FluidStack[] outputs = entry.getOutputs();
                    EVENT_BUS.post(new MachineCraftingEvent.Pre<>(entry, craftingRegistry, this, this));
                    for (FluidStack stack : outputs) {
                        craftFluid(stack);
                    }
                    decreaseInputs(entry.getInputs());
                    EVENT_BUS.post(new MachineCraftingEvent.Post<>(entry, craftingRegistry, this, this));
                } else {
                    ICraftingEntry<FluidStack, ItemStack> entry = craftingRegistry.get(getInputFluids());
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
        }
    }

    protected void craftFluid(FluidStack stack) {
        FluidTank tank = getTankForFluid(false, stack.getFluid());
        tank.fill(stack, true);
    }

    protected void craftItem(int slot, ItemStack stack) {
        if (CraftingHelper.chanceToCraft(craftingRegistry, slot, getInventoryInputs())) {
            if (getStackInSlot(slot).isEmpty()) {
                setInventorySlotContents(slot, stack.copy());
            } else if (getStackInSlot(slot).isItemEqual(stack)) {
                int stackSize = getStackInSlot(slot).getCount();
                getStackInSlot(slot).setCount(stackSize + stack.getCount());
            }
        }
    }

    protected void decreaseInputs(FluidStack... stacks) {
        for(FluidStack stack : stacks) {
            if(stack != null) {
                getTankForFluid(true, stack.getFluid()).drain(stack.amount, true);
            }
        }
    }

    protected void decreaseInputs(ItemStack... stacks) {
        for (ItemStack stack : stacks) {
            int slot = getSlotForStack(true, stack);
            if(slot != -1 && craftingRegistry.decreaseItemForSlot(slot)) {
                int stackSize = getStackInSlot(slot).getCount();
                getStackInSlot(slot).setCount(stackSize - stack.getCount());
                if (getStackInSlot(slot).getCount() <= 0) {
                    setInventorySlotContents(slot, ItemStack.EMPTY);
                }
            }
        }
    }

    protected void calculateMaxEfficiency() {
        this.maxEfficiency = energyStorage.getCapacity() / Constants.Energy.EFFICIENCY_DIVIDER;
    }
    //endregion

    //region IFluidHandler
    @Override
    public IFluidTankProperties[] getTankProperties() {
        IFluidTankProperties[] properties = new IFluidTankProperties[tanks.length];
        for (int i = 0; i < properties.length; i++) {
            properties[i] = tanks[i].getTankProperties()[0];
        }
        return properties;
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        FluidTank tank = getTankForFluid(true, resource.getFluid());
        int result = tank.fill(resource, doFill);
        syncClient();
        return result;
    }

    @Nullable
    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        FluidTank tank = getTankForFluid(false, resource.getFluid());
        FluidStack stack = tank.drain(resource, doDrain);
        syncClient();
        return stack;
    }

    @Nullable
    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
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
            if (!inventoryStack.isEmpty()) {
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

    //region IInventory
    @Override
    @SuppressWarnings("unchecked")
    public void setInventorySlotContents(int index, ItemStack stack) {
        boolean isSameItem = !stack.isEmpty() && stack.isItemEqual(getStackInSlot(index)) && ItemStack.areItemStackTagsEqual(stack, getStackInSlot(index));
        super.setInventorySlotContents(index, stack);
        ICraftingRegistry.ICraftingEntry entry = null;
        if(!isFluidInput()) {
            entry = craftingRegistry.get(getInventoryInputs());
        }
        if (InventoryHelper.isInputSlot(craftingRegistry, index) && entry != null && !isSameItem) {
            this.totalCraftingTime = entry.getCraftingTime();
            craftingTime = 0;
            this.markDirty();
        } else if (InventoryHelper.isInputSlot(craftingRegistry, index) && stack.isEmpty()) {
            this.totalCraftingTime = 0;
            this.craftingTime = 0;
            this.efficiency = 0;
        }
    }

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
}