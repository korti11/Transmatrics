package at.korti.transmatrics.tileentity;

import at.korti.transmatrics.api.crafting.ICraftingRegistry.ICraftingEntry;
import at.korti.transmatrics.api.crafting.IFluidCraftingRegistry;
import at.korti.transmatrics.event.MachineCraftingEvent;
import at.korti.transmatrics.fluid.MultiCraftingTank;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nullable;

import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

/**
 * Created by Korti on 26.07.2017.
 */
public abstract class TileEntityFluidStackCraftingMachine extends TileEntityCraftingMachine<FluidStack> {

    private final MultiCraftingTank craftingTank;

    protected TileEntityFluidStackCraftingMachine(int capacity, int maxReceive, int energyUse, boolean capacitorSlot, String name, IFluidCraftingRegistry<ItemStack> registry) {
        super(capacity, maxReceive, energyUse, capacitorSlot, name, registry);
        this.craftingTank = new MultiCraftingTank(getCraftingRegistry().getFluidCapacities(),
                getCraftingRegistry().getFluidInputIds(), getCraftingRegistry().getFluidOutputIds());
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
    @SuppressWarnings("unchecked")
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return (T) craftingTank;
        }
        return super.getCapability(capability, facing);
    }
    //endregion

    @Override
    protected IFluidCraftingRegistry<ItemStack> getCraftingRegistry() {
        return (IFluidCraftingRegistry<ItemStack>) super.getCraftingRegistry();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void craft() {
        if (this.canCraft()) {
            ICraftingEntry<ItemStack, FluidStack> entry = getCraftingRegistry().get(getInputs());
            FluidStack[] outputs = entry.getOutputs();
            EVENT_BUS.post(new MachineCraftingEvent.Pre<>(entry, getCraftingRegistry(), this, this));
            for (FluidStack stack : outputs) {
                craftingTank.fill(false, stack, true);
            }
            decreaseInputs(entry.getInputs());
            EVENT_BUS.post(new MachineCraftingEvent.Post<>(entry, getCraftingRegistry(), this, this));
        }
    }

    @Override
    protected boolean areOutputsEmpty() {
        return craftingTank.areTanksEmpty(false);
    }

    @Override
    protected boolean checkOutputIsFull(ICraftingEntry<ItemStack, FluidStack> entry) {
        return craftingTank.checkFluidSize(false, entry.getOutputs());
    }

    @Override
    public FluidStack[] getOutputs() {
        return craftingTank.getFluids(false);
    }
}
