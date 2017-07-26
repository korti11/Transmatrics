package at.korti.transmatrics.fluid;

import at.korti.transmatrics.api.Constants.NBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;

/**
 * Created by Korti on 26.07.2017.
 */
public class MultiCraftingTank implements IFluidHandler {

    private FluidTank[] tanks;
    private final int[] inputTankIds;
    private final int [] outputTankIds;
    private boolean isDirty = false;

    public MultiCraftingTank(int[] capacities, int[] inputTankIds, int[] outputTankIds) {
        this.init(capacities);
        this.inputTankIds = inputTankIds;
        this.outputTankIds = outputTankIds;
    }

    private void init(int[] capacities) {
        this.tanks = new FluidTank[capacities.length];
        for(int i = 0; i < capacities.length; i++) {
            tanks[i] = new FluidTank(capacities[i]);
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagList tanks = new NBTTagList();
        for (FluidTank tank : this.tanks) {
            NBTTagCompound nbtTank = new NBTTagCompound();
            tank.writeToNBT(nbtTank);
            tanks.appendTag(nbtTank);
        }
        compound.setTag(NBT.CRAFTING_TANKS, tanks);
        return compound;
    }

    public void readFromNBT(NBTTagCompound compound) {
        NBTTagList tanks = compound.getTagList(NBT.CRAFTING_TANKS, 10);
        for (int i = 0; i < tanks.tagCount(); i++) {
            NBTTagCompound nbtTank = tanks.getCompoundTagAt(i);
            this.tanks[i].readFromNBT(nbtTank);
        }
    }

    private FluidTank getTankForFluid(boolean input, FluidStack fluid) {
        int[] tankIds = input ? inputTankIds : outputTankIds;
        for (int tankId : tankIds) {
            FluidTank tank = tanks[tankId];
            if (tank.getFluid() != null && tank.getFluid().isFluidEqual(fluid)) {
                return tank;
            }
        }
        return findEmptyTank(input);
    }

    private FluidTank findEmptyTank(boolean input) {
        int[] tanksIds = input ? inputTankIds : outputTankIds;
        for (int tankId : tanksIds) {
            FluidTank tank = tanks[tankId];
            if (tank.getFluidAmount() == 0) {
                return tank;
            }
        }
        return null;
    }

    private FluidTank firstFilledTank(boolean input) {
        int[] tankIds = input ? inputTankIds : outputTankIds;
        for (int tankId : tankIds) {
            FluidTank tank = tanks[tankId];
            if (tank.getFluidAmount() != 0) {
                return tank;
            }
        }
        return null;
    }

    private void markDirty() {
        this.isDirty = true;
    }

    public void unmarkDirty() {
        this.isDirty = false;
    }

    public boolean isDirty() {
        return isDirty;
    }

    public boolean areTanksEmpty(boolean input) {
        return firstFilledTank(input) == null;
    }

    public boolean checkFluidSize(boolean input, FluidStack[] stacks) {
        for (FluidStack stack : stacks) {
            FluidTank tank = getTankForFluid(input, stack);
            int amount = tank.fill(stack, false);
            if (amount != stack.amount) {
                return false;
            }
        }
        return true;
    }

    public FluidStack[] getFluids(boolean input) {
        int[] tankIds = input ? inputTankIds : outputTankIds;
        FluidStack[] stacks = new FluidStack[tankIds.length];
        for(int i = 0; i < stacks.length; i++) {
            stacks[i] = tanks[tankIds[i]].getFluid();
        }
        return stacks;
    }

    public int getAmountForFluid(boolean input, FluidStack stack) {
        FluidTank tank = getTankForFluid(input, stack);
        if(tank != null) {
            return tank.getFluidAmount();
        }
        return 0;
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        IFluidTankProperties[] properties = new IFluidTankProperties[tanks.length];
        for(int i = 0; i < properties.length; i++) {
            properties[i] = tanks[i].getTankProperties()[0];
        }
        return properties;
    }

    @Override
    public int fill(FluidStack fluidStack, boolean doFill) {
        FluidTank tank = getTankForFluid(true, fluidStack);
        if (tank != null) {
            if(doFill){
                markDirty();
            }
            return tank.fill(fluidStack, doFill);
        }
        return 0;
    }

    public int fill(boolean input, FluidStack fluidStack, boolean doFill){
        FluidTank tank = getTankForFluid(input, fluidStack);
        if(tank != null) {
            if(doFill) {
                markDirty();
            }
            return tank.fill(fluidStack, doFill);
        }
        return 0;
    }

    @Nullable
    @Override
    public FluidStack drain(FluidStack fluidStack, boolean doDrain) {
        FluidTank tank = getTankForFluid(false, fluidStack);
        if (tank != null) {
            if(doDrain){
                markDirty();
            }
            return tank.drain(fluidStack, doDrain);
        }
        return null;
    }

    public FluidStack drain(boolean input, FluidStack fluidStack, boolean doDrain) {
        FluidTank tank = getTankForFluid(input, fluidStack);
        if(tank != null) {
            if (doDrain) {
                markDirty();
            }
            return tank.drain(fluidStack, doDrain);
        }
        return null;
    }

    @Nullable
    @Override
    public FluidStack drain(int amount, boolean doDrain) {
        FluidTank tank = firstFilledTank(false);
        if (tank != null) {
            if(doDrain){
                markDirty();
            }
            return tank.drain(amount, doDrain);
        }
        return null;
    }
}
