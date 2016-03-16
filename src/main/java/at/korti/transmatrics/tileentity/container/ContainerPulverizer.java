package at.korti.transmatrics.tileentity.container;

import at.korti.transmatrics.api.crafting.ICraftingRegistry.ICraftingEntry;
import at.korti.transmatrics.api.crafting.PulverizerCraftingRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Korti on 15.03.2016.
 */
public class ContainerPulverizer extends Container {

    private final IInventory tilePulverizer;

    public ContainerPulverizer(InventoryPlayer inventoryPlayer, IInventory tilePulverizer) {
        this.tilePulverizer = tilePulverizer;

        //Pulverizer inventory
        addSlotToContainer(new Slot(tilePulverizer, 0, 56, 26));
        addSlotToContainer(new Slot(tilePulverizer, 1, 116, 21));
        addSlotToContainer(new Slot(tilePulverizer, 2, 116, 52));

        //Player inventory
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return tilePulverizer.isUseableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemStack = null;
        Slot slot =  this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack tempStack = slot.getStack();
            itemStack = tempStack.copy();

            if (index == 1 || index == 2) {
                if (!this.mergeItemStack(tempStack, 3, 39, true)) {
                    return null;
                }

                slot.onSlotChange(tempStack, itemStack);
            } else if (index != 0) {
                ICraftingEntry entry = PulverizerCraftingRegistry.getInstance().get(tempStack);
                if (entry != null) {
                    if (!this.mergeItemStack(tempStack, 0, 1, false)) {
                        return null;
                    }
                } else if (index >= 3 && index < 30) {
                    if (!this.mergeItemStack(tempStack, 30, 39, false)) {
                        return null;
                    }
                } else if (index >= 30 && index < 39 && !this.mergeItemStack(tempStack, 3, 30, false)) {
                    return null;
                }
            } else if (!this.mergeItemStack(tempStack, 3, 39, false)) {
                return null;
            }

            if (tempStack.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }

            if (tempStack.stackSize == itemStack.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(playerIn, tempStack);
        }
        return itemStack;
    }
}
