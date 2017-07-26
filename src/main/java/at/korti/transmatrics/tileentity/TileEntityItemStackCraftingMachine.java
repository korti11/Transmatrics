package at.korti.transmatrics.tileentity;

import at.korti.transmatrics.api.crafting.ICraftingRegistry;
import at.korti.transmatrics.api.crafting.ICraftingRegistry.ICraftingEntry;
import at.korti.transmatrics.event.MachineCraftingEvent;
import at.korti.transmatrics.util.helper.CraftingHelper;
import at.korti.transmatrics.util.helper.InventoryHelper;
import net.minecraft.item.ItemStack;

import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

/**
 * Created by Korti on 26.07.2017.
 */
public abstract class TileEntityItemStackCraftingMachine extends TileEntityCraftingMachine<ItemStack> {

    protected TileEntityItemStackCraftingMachine(int capacity, int maxReceive, int energyUse, boolean capacitorSlot, String name, ICraftingRegistry<ItemStack> registry) {
        super(capacity, maxReceive, energyUse, capacitorSlot, name, registry);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void craft() {
        if (this.canCraft()) {
            ICraftingEntry<ItemStack, ItemStack> entry = getCraftingRegistry().get(getInputs());
            int[] outputSlots = getCraftingRegistry().getOutputSlotsIds();
            ItemStack[] outputs = entry.getOutputs();
            EVENT_BUS.post(new MachineCraftingEvent.Pre<>(entry, getCraftingRegistry(), this, this));
            for (int i = 0; i < outputs.length; i++) {
                craftItem(outputSlots[i], outputs[i]);
            }
            decreaseInputs(entry.getInputs());
            EVENT_BUS.post(new MachineCraftingEvent.Post<>(entry, getCraftingRegistry(), this, this));
        }
    }

    private void craftItem(int slot, ItemStack stack) {
        CraftingHelper.craftItem(slot, stack, getCraftingRegistry(), getInputs(), this);
    }

    @Override
    protected boolean areOutputsEmpty() {
        return areSlotsEmpty(getCraftingRegistry().getOutputSlotsIds());
    }

    @Override
    protected boolean checkOutputIsFull(ICraftingEntry<ItemStack, ItemStack> entry) {
        return InventoryHelper.checkOutputStackSize(entry, getInventoryStackLimit(), getOutputs());
    }

    @Override
    public ItemStack[] getOutputs() {
        return getContent(getCraftingRegistry().getOutputSlotsIds());
    }
}
