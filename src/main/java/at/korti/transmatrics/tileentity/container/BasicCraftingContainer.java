package at.korti.transmatrics.tileentity.container;

import at.korti.transmatrics.api.crafting.ICraftingRegistry;
import at.korti.transmatrics.api.crafting.ICraftingRegistry.ICraftingEntry;
import at.korti.transmatrics.tileentity.TileEntityInventory;
import at.korti.transmatrics.tileentity.container.slot.CapacitorSlot;
import at.korti.transmatrics.util.helper.InventoryHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Korti on 25.07.2017.
 */
public abstract class BasicCraftingContainer<T> extends Container {

    protected final TileEntityInventory tileEntity;
    protected final ICraftingRegistry<T> craftingRegistry;

    protected final int playerMinIndex;
    protected final int playerMaxIndex;

    private int craftingTime;
    private int totalCraftingTime;
    private int energyStored;
    private int maxEnergyStored;
    private int efficiency;
    private int maxEfficiency;

    protected BasicCraftingContainer(InventoryPlayer inventoryPlayer, TileEntityInventory tileEntity, ICraftingRegistry<T> registry) {
        this.tileEntity = tileEntity;
        this.craftingRegistry = registry;
        this.addTileEntitySlots(tileEntity);
        this.addCapacitorSlot();
        this.playerMinIndex = this.inventorySlots.size();
        this.addPlayerSlots(inventoryPlayer);
        this.playerMaxIndex = this.inventorySlots.size();
    }

    protected abstract void addTileEntitySlots(IInventory inventory);

    private void addCapacitorSlot() {
        if (tileEntity.hasCapacitorSlot()) {
            addSlotToContainer(new CapacitorSlot(tileEntity, tileEntity.getCapacitorSlot(), 17, 58));
        }
    }

    private void addPlayerSlots(InventoryPlayer inventoryPlayer) {
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
    public void addListener(IContainerListener listener) {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, tileEntity);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (IContainerListener listener : this.listeners) {
            if (this.craftingTime != tileEntity.getField(0)) {
                listener.sendWindowProperty(this, 0, this.craftingTime = tileEntity.getField(0));
            }

            if (this.totalCraftingTime != tileEntity.getField(1)) {
                listener.sendWindowProperty(this, 1, this.totalCraftingTime = tileEntity.getField(1));
            }

            if (this.energyStored != tileEntity.getField(2)) {
                listener.sendWindowProperty(this, 2, this.energyStored = tileEntity.getField(2));
            }

            if (this.maxEnergyStored != tileEntity.getField(3)) {
                listener.sendWindowProperty(this, 3, this.maxEnergyStored = tileEntity.getField(3));
            }

            if (this.efficiency != tileEntity.getField(4)) {
                listener.sendWindowProperty(this, 4, this.efficiency = tileEntity.getField(4));
            }

            if (this.maxEfficiency != tileEntity.getField(5)) {
                listener.sendWindowProperty(this, 5, this.maxEfficiency = tileEntity.getField(5));
            }
        }
    }

    @Override
    public void updateProgressBar(int id, int data) {
        this.tileEntity.setField(id, data);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return tileEntity.isUsableByPlayer(playerIn);
    }

    protected boolean checkCraftingEntry(ICraftingEntry entry, ItemStack tempStack, int index) {
        if (entry != null) {
            int startIndex = InventoryHelper.getMinIndex(craftingRegistry.getInputSlotsIds());
            int endIndex = InventoryHelper.getMaxIndex(craftingRegistry.getInputSlotsIds());
            if (!this.mergeItemStack(tempStack, startIndex, endIndex + 1, false)) {
                return true;
            }
        } else if (index >= playerMinIndex && index < playerMaxIndex - 9) {
            if (!this.mergeItemStack(tempStack, playerMaxIndex - 9, playerMaxIndex, false)) {
                return true;
            }
        } else if (index >= playerMaxIndex - 9 && index < playerMaxIndex && !this.mergeItemStack(tempStack, playerMinIndex, playerMaxIndex - 9, false)) {
            return true;
        }
        return false;
    }
}
