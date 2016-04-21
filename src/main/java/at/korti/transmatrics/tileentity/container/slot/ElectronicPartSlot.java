package at.korti.transmatrics.tileentity.container.slot;

import at.korti.transmatrics.item.electronic.ItemElectronicParts;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Korti on 21.04.2016.
 */
public class ElectronicPartSlot extends Slot {

    public ElectronicPartSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.getItem() instanceof ItemElectronicParts;
    }
}
