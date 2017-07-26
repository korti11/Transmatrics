package at.korti.transmatrics.tileentity;

import at.korti.transmatrics.api.crafting.IFluidCraftingRegistry;
import at.korti.transmatrics.fluid.MultiCraftingTank;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nullable;

/**
 * Created by Korti on 30.03.2016.
 */
public abstract class TileEntityFluidCraftingMachine<O> extends TileEntityBasicCraftingMachine<FluidStack, O> {

    protected MultiCraftingTank craftingTank;

    protected TileEntityFluidCraftingMachine(int capacity, int maxReceive, int energyUse, boolean capacitorSlot, String name, IFluidCraftingRegistry<FluidStack> registry) {
        super(capacity, maxReceive, energyUse, capacitorSlot, name, registry);
        this.craftingTank = new MultiCraftingTank(registry.getFluidCapacities(),
                registry.getFluidInputIds(), registry.getFluidOutputIds());
    }

    //region TileEntity
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        craftingTank.writeToNBT(compound);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        craftingTank.readFromNBT(compound);
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
    //endregion

    //region TileEntityFluidCraftingMachine
    @Override
    protected IFluidCraftingRegistry<FluidStack> getCraftingRegistry() {
        return (IFluidCraftingRegistry<FluidStack>) super.getCraftingRegistry();
    }

    @Override
    protected boolean areInputsEmpty() {
        return craftingTank.areTanksEmpty(true);
    }

    @Override
    public FluidStack[] getInputs() {
        return craftingTank.getFluids(true);
    }

    protected void decreaseInputs(FluidStack... stacks) {
        for(FluidStack stack : stacks) {
            if(stack != null) {
                craftingTank.drain(true, stack, true);
            }
        }
    }
    //endregion
}