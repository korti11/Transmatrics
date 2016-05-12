package at.korti.transmatrics.tileentity.container;

import at.korti.transmatrics.registry.crafting.LiquidCasterCraftingRegistry;
import at.korti.transmatrics.registry.crafting.LiquidCasterCraftingRegistry.LiquidCasterCraftingEntry;
import at.korti.transmatrics.tileentity.container.slot.CastSlot;
import at.korti.transmatrics.tileentity.container.slot.OutputSlot;
import at.korti.transmatrics.util.helper.InventoryHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Korti on 14.04.2016.
 */
public class ContainerLiquidCaster extends FluidItemCraftingContainer {

    public ContainerLiquidCaster(InventoryPlayer inventoryPlayer, IInventory tileEntity) {
        super(inventoryPlayer, tileEntity, LiquidCasterCraftingRegistry.getInstance());
    }

    @Override
    public void addTileEntitySlots(IInventory inventory) {
        addSlotToContainer(new CastSlot(inventory, 0, 82, 15));
        addSlotToContainer(new OutputSlot(inventory, 1, 116, 35));
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
                LiquidCasterCraftingEntry entry = ((LiquidCasterCraftingRegistry) craftingRegistry).find(tempStack);
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
