package at.korti.transmatrics.tileentity.container.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

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

}
