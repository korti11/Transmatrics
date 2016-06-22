package at.korti.transmatrics.modintegration.tconstruct.tileentity;

import at.korti.transmatrics.api.Constants.Energy;
import at.korti.transmatrics.api.Constants.TransmatricsTileEntity;
import at.korti.transmatrics.api.crafting.ICraftingRegistry;
import at.korti.transmatrics.api.crafting.ICraftingRegistry.ICraftingEntry;
import at.korti.transmatrics.api.crafting.IFluidCraftingRegistry;
import at.korti.transmatrics.event.MachineCraftingEvent;
import at.korti.transmatrics.modintegration.tconstruct.crafting.AlloyMixerCraftingRegistry;
import at.korti.transmatrics.tileentity.TileEntityFluidCraftingMachine;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

/**
 * Created by Korti on 12.05.2016.
 */
public class TileEntityAlloyMixer extends TileEntityFluidCraftingMachine {

    public TileEntityAlloyMixer() {
        super(Energy.ALLOY_MIXER_CAPACITY, Energy.ALLOY_MIXER_RECEIVE, Energy.ALLOY_MIXER_ENERGY_USE, true,
                TransmatricsTileEntity.ALLOY_MIXER.getRegName(), AlloyMixerCraftingRegistry.getInstance());
    }

    @Override
    protected boolean isFluidInput() {
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void craft() {
        if (this.canCraft()) {
            ICraftingEntry<FluidStack, FluidStack> entry = craftingRegistry.get(getInputFluids());
            FluidStack output = entry.getOutputs()[0].copy();
            FluidStack[] inputs = new FluidStack[entry.getInputs().length];
            EVENT_BUS.post(new MachineCraftingEvent.Pre<>(entry, craftingRegistry, this, this));
            int i = 0;
            int multi = calcMultiplier(entry);
            for (FluidStack stack : entry.getInputs()) {
                inputs[i] = new FluidStack(stack, stack.amount * multi);
                i++;
            }
            output.amount = output.amount * multi;
            craftFluid(output);
            decreaseInputs(inputs);
            EVENT_BUS.post(new MachineCraftingEvent.Post<>(entry, craftingRegistry, this, this));
        }
    }

    private int calcMultiplier(ICraftingEntry<FluidStack, FluidStack> entry) {
        int multi = Integer.MAX_VALUE;
        for (FluidStack stack : entry.getInputs()) {
            FluidTank tank = getTankForFluid(true, stack.getFluid());
            int rest = tank.getFluidAmount() % stack.amount;
            FluidStack tempStack = new FluidStack(stack, tank.getFluidAmount() - rest);
            if (multi > tempStack.amount / stack.amount) {
                multi = tempStack.amount / stack.amount;
            }
        }
        return multi;
    }
}
