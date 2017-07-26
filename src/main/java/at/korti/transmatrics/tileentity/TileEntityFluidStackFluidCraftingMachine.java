package at.korti.transmatrics.tileentity;

import at.korti.transmatrics.api.crafting.ICraftingRegistry.ICraftingEntry;
import at.korti.transmatrics.api.crafting.IFluidCraftingRegistry;
import at.korti.transmatrics.event.MachineCraftingEvent;
import net.minecraftforge.fluids.FluidStack;

import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

/**
 * Created by Korti on 26.07.2017.
 */
public abstract class TileEntityFluidStackFluidCraftingMachine extends TileEntityFluidCraftingMachine<FluidStack> {

    protected TileEntityFluidStackFluidCraftingMachine(int capacity, int maxReceive, int energyUse, boolean capacitorSlot, String name, IFluidCraftingRegistry<FluidStack> registry) {
        super(capacity, maxReceive, energyUse, capacitorSlot, name, registry);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void craft() {
        if (this.canCraft()) {
            ICraftingEntry<FluidStack, FluidStack> entry = getCraftingRegistry().get(getInputs());
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
    protected boolean checkOutputIsFull(ICraftingEntry<FluidStack, FluidStack> entry) {
        return craftingTank.checkFluidSize(false, entry.getOutputs());
    }

    @Override
    public FluidStack[] getOutputs() {
        return craftingTank.getFluids(false);
    }
}
