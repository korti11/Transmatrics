package at.korti.transmatrics.tileentity.container;

import at.korti.transmatrics.api.energy.IChargeable;
import at.korti.transmatrics.tileentity.container.slot.OutputSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Korti on 16.05.2016.
 */
public class ContainerCharger extends Container{

    private final IInventory inventory;

    private int chargingProgress;
    private int totalChargingProgress;
    private int energyStored;
    private int maxEnergyStored;

    public ContainerCharger(InventoryPlayer inventoryPlayer, IInventory inventory) {
        this.inventory = inventory;
        addSlotToContainer(new Slot(inventory, 0, 56, 34));
        addSlotToContainer(new OutputSlot(inventory, 1, 116, 35));
        addPlayerSlots(inventoryPlayer);
    }

    public void addPlayerSlots(InventoryPlayer inventoryPlayer) {
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
        return inventory.isUsableByPlayer(playerIn);
    }

    @Override
    public void addListener(IContainerListener listener) {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, inventory);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (int i = 0; i < this.listeners.size(); i++) {
            IContainerListener listener = this.listeners.get(i);

            if (this.chargingProgress != inventory.getField(0)) {
                listener.sendWindowProperty(this, 0, this.chargingProgress = inventory.getField(0));
            }

            if (this.totalChargingProgress != inventory.getField(1)) {
                listener.sendWindowProperty(this, 1, this.totalChargingProgress = inventory.getField(1));
            }

            if (this.energyStored != inventory.getField(2)) {
                listener.sendWindowProperty(this, 2, this.energyStored = inventory.getField(2));
            }

            if (this.maxEnergyStored != inventory.getField(3)) {
                listener.sendWindowProperty(this, 3, this.maxEnergyStored = inventory.getField(3));
            }

        }
    }

    @Override
    public void updateProgressBar(int id, int data) {
        this.inventory.setField(id, data);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemStack = null;
        Slot slot =  this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack tempStack = slot.getStack();
            itemStack = tempStack.copy();

            if (index == 1) {
                if (!this.mergeItemStack(tempStack, 2, 38, true)) {
                    return null;
                }

                slot.onSlotChange(tempStack, itemStack);
            } else if (index != 0) {
                if (tempStack.getItem() instanceof IChargeable) {
                    if (!this.mergeItemStack(tempStack, 0, 1, false)) {
                        return null;
                    }
                } else if (index >= 2 && index < 29) {
                    if (!this.mergeItemStack(tempStack, 29, 38, false)) {
                        return null;
                    }
                } else if (index >= 29 && index < 38 && !this.mergeItemStack(tempStack, 2, 38, false)) {
                    return null;
                }
            } else if (!this.mergeItemStack(tempStack, 2, 38, false)) {
                return null;
            }

            if (tempStack.getCount() == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }

            if (tempStack.getCount() == itemStack.getCount()) {
                return null;
            }

            slot.onTake(playerIn, tempStack);
        }
        return itemStack;
    }
}
