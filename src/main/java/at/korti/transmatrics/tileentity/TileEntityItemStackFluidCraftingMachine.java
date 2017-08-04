package at.korti.transmatrics.tileentity;

import at.korti.transmatrics.api.crafting.ICraftingRegistry.ICraftingEntry;
import at.korti.transmatrics.api.crafting.IFluidCraftingRegistry;
import at.korti.transmatrics.event.MachineCraftingEvent;
import at.korti.transmatrics.util.helper.CraftingHelper;
import at.korti.transmatrics.util.helper.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

/**
 * Created by Korti on 26.07.2017.
 */
public abstract class TileEntityItemStackFluidCraftingMachine extends TileEntityFluidCraftingMachine<ItemStack> {

    protected TileEntityItemStackFluidCraftingMachine(int capacity, int maxReceive, int energyUse, boolean capacitorSlot, String name, IFluidCraftingRegistry<FluidStack> registry) {
        super(capacity, maxReceive, energyUse, capacitorSlot, name, registry);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void craft() {
        ICraftingEntry<FluidStack, ItemStack> entry = getCraftingEntry();
        int[] outputSlots = getCraftingRegistry().getOutputSlotsIds();
        ItemStack[] outputs = entry.getOutputs();
        EVENT_BUS.post(new MachineCraftingEvent.Pre<>(entry, getCraftingRegistry(), this, this));
        for (int i = 0; i < outputs.length; i++) {
            craftItem(outputSlots[i], outputs[i]);
        }
        decreaseInputs(entry.getInputs());
        EVENT_BUS.post(new MachineCraftingEvent.Post<>(entry, getCraftingRegistry(), this, this));
    }

    protected void craftItem(int slot, ItemStack stack) {
        CraftingHelper.craftItem(slot, stack, getCraftingRegistry(), getInputs(), this);
    }

    @Override
    protected boolean areOutputsEmpty() {
        return this.areSlotsEmpty(getCraftingRegistry().getOutputSlotsIds());
    }

    @Override
    protected boolean checkOutputIsFull(ICraftingEntry<FluidStack, ItemStack> entry) {
        return InventoryHelper.checkOutputStackSize(entry, getInventoryStackLimit(), getOutputs());
    }

    @Override
    public ItemStack[] getOutputs() {
        return this.getContent(getCraftingRegistry().getOutputSlotsIds());
    }

    @Override
    protected boolean equalOutputs(ICraftingEntry<FluidStack, ItemStack> entry) {
        ItemStack[] outputContent = getOutputs();
        for(int i = 0; i < outputContent.length && i < entry.getOutputs().length; i++) {
            if (!outputContent[i].isItemEqual(entry.getOutputs()[i]) && !outputContent[i].isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
