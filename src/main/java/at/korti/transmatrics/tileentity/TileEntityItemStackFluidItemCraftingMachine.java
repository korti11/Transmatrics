package at.korti.transmatrics.tileentity;

import at.korti.transmatrics.api.crafting.ICraftingRegistry;
import at.korti.transmatrics.api.crafting.ICraftingRegistry.ICraftingEntry;
import at.korti.transmatrics.api.crafting.IFluidItemCraftingRegistry;
import at.korti.transmatrics.api.crafting.IFluidItemCraftingRegistry.IFluidItemCraftingEntry;
import at.korti.transmatrics.event.MachineCraftingEvent;
import at.korti.transmatrics.util.helper.CraftingHelper;
import at.korti.transmatrics.util.helper.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

/**
 * Created by Korti on 26.07.2017.
 */
public abstract class TileEntityItemStackFluidItemCraftingMachine extends TileEntityItemStackFluidCraftingMachine {

    protected TileEntityItemStackFluidItemCraftingMachine(int capacity, int maxReceive, int energyUse, boolean capacitorSlot, String name, IFluidItemCraftingRegistry registry) {
        super(capacity, maxReceive, energyUse, capacitorSlot, name, registry);
    }

    @Override
    protected IFluidItemCraftingRegistry getCraftingRegistry() {
        return (IFluidItemCraftingRegistry) super.getCraftingRegistry();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void craft() {
        IFluidItemCraftingEntry<ItemStack> entry = (IFluidItemCraftingEntry<ItemStack>) getCraftingEntry();
        int[] outputSlots = getCraftingRegistry().getOutputSlotsIds();
        ItemStack[] outputs = entry.getOutputs();
        EVENT_BUS.post(new MachineCraftingEvent.Pre<>(entry, getCraftingRegistry(), this, this));
        for (int i = 0; i < outputs.length; i++) {
            craftItem(outputSlots[i], outputs[i]);
        }
        decreaseInputs(entry.getInputs(), entry.getSecondInputs());
        EVENT_BUS.post(new MachineCraftingEvent.Post<>(entry, getCraftingRegistry(), this, this));
    }

    @Override
    protected void craftItem(int slot, ItemStack stack) {
        CraftingHelper.craftItem(slot, stack, getCraftingRegistry(), getInputs(), getInventoryInputs(), this);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected ICraftingEntry<FluidStack, ItemStack> getCraftingEntry() {
        return getCraftingRegistry().get(getInputs(), getInventoryInputs());
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
