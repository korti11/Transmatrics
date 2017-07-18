package at.korti.transmatrics.tileentity.container.slot;

import cofh.redstoneflux.api.IEnergyContainerItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Korti on 19.05.2016.
 */
public class CapacitorSlot extends Slot {

    public CapacitorSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack == null || stack.getItem() instanceof IEnergyContainerItem;
    }
}
