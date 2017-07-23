package at.korti.transmatrics.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;

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
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        internalTank.writeToNBT(compound);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        internalTank.readFromNBT(compound);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return (T) this;
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void update() {
        super.update();
        if (canProduceEnergy()) {
            drain(fluidUse, true);
        }
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return internalTank.getTankProperties();
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        return internalTank.fill(resource, doFill);
    }

    @Nullable
    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        return internalTank.drain(resource, doDrain);
    }

    @Nullable
    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        return internalTank.drain(maxDrain, doDrain);
    }
}
