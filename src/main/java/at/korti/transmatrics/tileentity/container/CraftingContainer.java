package at.korti.transmatrics.tileentity.container;

import at.korti.transmatrics.api.crafting.ICraftingRegistry;
import at.korti.transmatrics.api.crafting.ICraftingRegistry.ICraftingEntry;
import at.korti.transmatrics.tileentity.TileEntityInventory;
import at.korti.transmatrics.util.helper.InventoryHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Korti on 29.03.2016.
 */
public abstract class CraftingContainer extends BasicCraftingContainer<ItemStack> {

    public CraftingContainer(InventoryPlayer inventoryPlayer, TileEntityInventory tileEntity, ICraftingRegistry<ItemStack> registry) {
        super(inventoryPlayer, tileEntity, registry);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot =  this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack tempStack = slot.getStack();
            itemStack = tempStack.copy();

            if (InventoryHelper.isOutputSlot(craftingRegistry, index)) {
                if (!this.mergeItemStack(tempStack, playerMinIndex, playerMaxIndex, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(tempStack, itemStack);
            } else if (!InventoryHelper.isInputSlot(craftingRegistry, index)) {
                ICraftingEntry entry = craftingRegistry.get(tempStack);
                if(checkCraftingEntry(entry, tempStack, index)){
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(tempStack, playerMinIndex, playerMaxIndex, false)) {
                return ItemStack.EMPTY;
            }

            if (tempStack.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (tempStack.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, tempStack);
        }
        return itemStack;
    }
}
