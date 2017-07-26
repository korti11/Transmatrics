package at.korti.transmatrics.tileentity.container;

import at.korti.transmatrics.api.crafting.ICraftingRegistry.ICraftingEntry;
import at.korti.transmatrics.api.crafting.IFluidItemCraftingRegistry;
import at.korti.transmatrics.tileentity.TileEntityInventory;
import at.korti.transmatrics.tileentity.TileEntityItemStackFluidItemCraftingMachine;
import at.korti.transmatrics.util.helper.InventoryHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

/**
 * Created by Korti on 14.04.2016.
 */
public abstract class FluidItemCraftingContainer extends BasicCraftingContainer<FluidStack> {

    public FluidItemCraftingContainer(InventoryPlayer inventoryPlayer, TileEntityInventory tileEntity, IFluidItemCraftingRegistry registry) {
        super(inventoryPlayer, tileEntity, registry);
    }

    private IFluidItemCraftingRegistry getRegistry() {
        return (IFluidItemCraftingRegistry) craftingRegistry;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot =  this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack tempStack = slot.getStack();
            itemStack = tempStack.copy();

            if (InventoryHelper.isOutputSlot(getRegistry(), index)) {
                if (!this.mergeItemStack(tempStack, playerMinIndex, playerMaxIndex, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(tempStack, itemStack);
            } else if (!InventoryHelper.isInputSlot(getRegistry(), index)) {
                if(tileEntity instanceof TileEntityItemStackFluidItemCraftingMachine) {
                    TileEntityItemStackFluidItemCraftingMachine machine = (TileEntityItemStackFluidItemCraftingMachine) tileEntity;
                    FluidStack[] fluidStacks = machine.getInputs();
                    ICraftingEntry entry = getRegistry().get(fluidStacks, new ItemStack[]{tempStack});
                    if (checkCraftingEntry(entry, tempStack, index)) {
                        return ItemStack.EMPTY;
                    }
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
