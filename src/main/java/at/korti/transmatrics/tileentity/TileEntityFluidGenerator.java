package at.korti.transmatrics.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.*;

/**
 * Created by Korti on 26.02.2016.
 */
public abstract class TileEntityFluidGenerator extends TileEntityGenerator implements IFluidHandler {

    private FluidTank internalTank;
    private Fluid takeIn;
    private int fluidUse;

    public TileEntityFluidGenerator(int energyPerTick, int energyCapacity, int maxExtract, int tankCapacity, int fluidUse, Fluid takeIn) {
        super(energyPerTick, energyCapacity, maxExtract);

        internalTank = new FluidTank(tankCapacity);
        this.takeIn = takeIn;
        this.fluidUse = fluidUse;
    }

    public TileEntityFluidGenerator(int energyPerTick, int maxEnergyValue, int tankCapacity, int fluidUse, Fluid takeIn) {
        this(energyPerTick, maxEnergyValue, maxEnergyValue, tankCapacity, fluidUse, takeIn);
    }

    public TileEntityFluidGenerator(int energyPerTick, int maxValue, int fluidUse, Fluid takeIn) {
        this(energyPerTick, maxValue, maxValue, fluidUse, takeIn);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        internalTank.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        internalTank.readFromNBT(compound);
    }

    @Override
    public void update() {
        super.update();
        if (canProduceEnergy()) {
            drain(EnumFacing.UP, fluidUse, true);
        }
    }

    @Override
    public boolean canProduceEnergy() {
        return internalTank.getFluid() != null && internalTank.getFluid().amount > 0 && super.canProduceEnergy();
    }

    @Override
    public int fill(EnumFacing from, FluidStack resource, boolean doFill) {
        return internalTank.fill(resource, doFill);
    }

    @Override
    public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain) {
        return internalTank.drain(resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain) {
        return internalTank.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(EnumFacing from, Fluid fluid) {
        return fluid.equals(takeIn);
    }

    @Override
    public boolean canDrain(EnumFacing from, Fluid fluid) {
        return true;
    }

    @Override
    public FluidTankInfo[] getTankInfo(EnumFacing from) {
        return new FluidTankInfo[]{internalTank.getInfo()};
    }
}
