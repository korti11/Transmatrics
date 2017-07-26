package at.korti.transmatrics.tileentity;

import at.korti.transmatrics.api.crafting.IFluidItemCraftingRegistry;
import at.korti.transmatrics.api.crafting.IFluidItemCraftingRegistry.IFluidItemCraftingEntry;
import at.korti.transmatrics.event.MachineCraftingEvent;
import at.korti.transmatrics.util.helper.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

/**
 * Created by Korti on 26.07.2017.
 */
public abstract class TileEntityFluidStackFluidItemCraftingMachine extends TileEntityFluidStackFluidCraftingMachine {

    protected TileEntityFluidStackFluidItemCraftingMachine(int capacity, int maxReceive, int energyUse, boolean capacitorSlot, String name, IFluidItemCraftingRegistry registry) {
        super(capacity, maxReceive, energyUse, capacitorSlot, name, registry);
    }

    @Override
    protected IFluidItemCraftingRegistry getCraftingRegistry() {
        return (IFluidItemCraftingRegistry) super.getCraftingRegistry();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void craft() {
        IFluidItemCraftingEntry<FluidStack> entry = getCraftingRegistry().get(getInputs(), getInventoryInputs());
        FluidStack[] outputs = entry.getOutputs();
        EVENT_BUS.post(new MachineCraftingEvent.Pre<>(entry, getCraftingRegistry(), this, this));
        for (FluidStack stack : outputs) {
            craftingTank.fill(true, stack, true);
        }
        decreaseInputs(entry.getInputs(), entry.getSecondInputs());
        EVENT_BUS.post(new MachineCraftingEvent.Post<>(entry, getCraftingRegistry(), this, this));
    }

    @Override
    protected boolean areInputsEmpty() {
        return super.areInputsEmpty() || areSlotsEmpty(getCraftingRegistry().getInputSlotsIds());
    }

    private ItemStack[] getInventoryInputs() {
        return getContent(getCraftingRegistry().getInputSlotsIds());
    }

    protected void decreaseInputs(FluidStack[] inputs, ItemStack[] secondInputs) {
        for (FluidStack stack : inputs) {
            craftingTank.drain(true, stack, true);
        }
        for (ItemStack stack : secondInputs) {
            int slot = getSlotForStack(true, stack);
            InventoryHelper.decreaseInputForSlot(slot, stack, getCraftingRegistry(), this);
        }
    }
}
