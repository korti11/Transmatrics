package at.korti.transmatrics.tileentity.container;

import at.korti.transmatrics.api.crafting.ICraftingRegistry;
import at.korti.transmatrics.api.crafting.ICraftingRegistry.ICraftingEntry;
import at.korti.transmatrics.api.crafting.PulverizerCraftingRegistry;
import at.korti.transmatrics.util.helper.InventoryHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Korti on 29.03.2016.
 */
public abstract class CraftingContainer extends Container {

    private final IInventory tileEntity;
    private final ICraftingRegistry<ItemStack> craftingRegistry;

    private final int playerMinIndex;
    private final int playerMaxIndex;

    private int craftingTime;
    private int totalCraftingTime;
    private int energyStored;
    private int maxEnergyStored;
    private int efficiency;
    private int maxEfficiency;

    public CraftingContainer(InventoryPlayer inventoryPlayer, IInventory tileEntity, ICraftingRegistry<ItemStack> registry) {
        this.tileEntity = tileEntity;
        this.craftingRegistry = registry;
        addTileEntitySlots(tileEntity);
        this.playerMinIndex = this.inventorySlots.size();
        addPlayerSlots(inventoryPlayer);
        this.playerMaxIndex = this.inventorySlots.size();
    }

    public abstract void addTileEntitySlots(IInventory inventory);

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
    public void onCraftGuiOpened(ICrafting listener) {
        super.onCraftGuiOpened(listener);
        listener.sendAllWindowProperties(this, tileEntity);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); i++) {
            ICrafting iCrafting = this.crafters.get(i);

            if (this.craftingTime != tileEntity.getField(0)) {
                iCrafting.sendProgressBarUpdate(this, 0, this.craftingTime = tileEntity.getField(0));
            }

            if (this.totalCraftingTime != tileEntity.getField(1)) {
                iCrafting.sendProgressBarUpdate(this, 1, this.totalCraftingTime = tileEntity.getField(1));
            }

            if (this.energyStored != tileEntity.getField(2)) {
                iCrafting.sendProgressBarUpdate(this, 2, this.energyStored = tileEntity.getField(2));
            }

            if (this.maxEnergyStored != tileEntity.getField(3)) {
                iCrafting.sendProgressBarUpdate(this, 3, this.maxEnergyStored = tileEntity.getField(3));
            }

            if (this.efficiency != tileEntity.getField(4)) {
                iCrafting.sendProgressBarUpdate(this, 4, this.efficiency = tileEntity.getField(4));
            }

            if (this.maxEfficiency != tileEntity.getField(5)) {
                iCrafting.sendProgressBarUpdate(this, 5, this.maxEfficiency = tileEntity.getField(5));
            }
        }
    }

    @Override
    public void updateProgressBar(int id, int data) {
        this.tileEntity.setField(id, data);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return tileEntity.isUseableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemStack = null;
        Slot slot =  this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack tempStack = slot.getStack();
            itemStack = tempStack.copy();

            if (InventoryHelper.isOutputSlot(craftingRegistry, index)) {
                if (!this.mergeItemStack(tempStack, playerMinIndex, playerMaxIndex, true)) {
                    return null;
                }

                slot.onSlotChange(tempStack, itemStack);
            } else if (!InventoryHelper.isInputSlot(craftingRegistry, index)) {
                ICraftingEntry entry = craftingRegistry.get(tempStack);
                if (entry != null) {
                    int startIndex = InventoryHelper.getMinIndex(craftingRegistry.getInputSlotsIds());
                    int endIndex = InventoryHelper.getMaxIndex(craftingRegistry.getInputSlotsIds());
                    if (!this.mergeItemStack(tempStack, startIndex, endIndex + 1, false)) {
                        return null;
                    }
                } else if (index >= playerMinIndex && index < playerMaxIndex - 9) {
                    if (!this.mergeItemStack(tempStack, playerMaxIndex - 9, playerMaxIndex, false)) {
                        return null;
                    }
                } else if (index >= playerMaxIndex - 9 && index < playerMaxIndex && !this.mergeItemStack(tempStack, playerMinIndex, playerMaxIndex - 9, false)) {
                    return null;
                }
            } else if (!this.mergeItemStack(tempStack, playerMinIndex, playerMaxIndex, false)) {
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
