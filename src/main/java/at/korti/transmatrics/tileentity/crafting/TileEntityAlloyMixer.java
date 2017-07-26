package at.korti.transmatrics.tileentity.crafting;

import at.korti.transmatrics.api.Constants.Energy;
import at.korti.transmatrics.api.Constants.TransmatricsTileEntity;
import at.korti.transmatrics.api.crafting.ICraftingRegistry.ICraftingEntry;
import at.korti.transmatrics.event.MachineCraftingEvent;
import at.korti.transmatrics.registry.crafting.AlloyMixerCraftingRegistry;
import at.korti.transmatrics.tileentity.TileEntityFluidStackFluidCraftingMachine;
import net.minecraftforge.fluids.FluidStack;

import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

/**
 * Created by Korti on 12.05.2016.
 */
public class TileEntityAlloyMixer extends TileEntityFluidStackFluidCraftingMachine {

    public TileEntityAlloyMixer() {
        super(Energy.ALLOY_MIXER_CAPACITY, Energy.ALLOY_MIXER_RECEIVE, Energy.ALLOY_MIXER_ENERGY_USE, true,
                TransmatricsTileEntity.ALLOY_MIXER.getRegName(), AlloyMixerCraftingRegistry.getInstance());
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void craft() {
        if (this.canCraft()) {
            ICraftingEntry<FluidStack, FluidStack> entry = getCraftingRegistry().get(getInputs());
            FluidStack output = entry.getOutputs()[0].copy();
            FluidStack[] inputs = new FluidStack[entry.getInputs().length];
            EVENT_BUS.post(new MachineCraftingEvent.Pre<>(entry, getCraftingRegistry(), this, this));
            int i = 0;
            int multi = calcMultiplier(entry);
            for (FluidStack stack : entry.getInputs()) {
                inputs[i] = new FluidStack(stack, stack.amount * multi);
                i++;
            }
            output.amount = output.amount * multi;
            craftingTank.fill(true, output, true);
            decreaseInputs(inputs);
            EVENT_BUS.post(new MachineCraftingEvent.Post<>(entry, getCraftingRegistry(), this, this));
        }
    }

    private int calcMultiplier(ICraftingEntry<FluidStack, FluidStack> entry) {
        int multi = Integer.MAX_VALUE;
        for (FluidStack stack : entry.getInputs()) {
            int fluidAmount = craftingTank.getAmountForFluid(true, stack);
            int rest = fluidAmount % stack.amount;
            FluidStack tempStack = new FluidStack(stack, fluidAmount - rest);
            if (multi > tempStack.amount / stack.amount) {
                multi = tempStack.amount / stack.amount;
            }
        }
        return multi;
    }
}
