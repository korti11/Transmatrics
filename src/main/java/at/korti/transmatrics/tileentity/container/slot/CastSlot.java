package at.korti.transmatrics.tileentity.container.slot;

import at.korti.transmatrics.api.crafting.ICasting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Korti on 14.04.2016.
 */
public class CastSlot extends Slot {

    public CastSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.getItem() instanceof ICasting;
    }
}
