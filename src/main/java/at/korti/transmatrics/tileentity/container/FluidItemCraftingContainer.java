package at.korti.transmatrics.tileentity.container;

import at.korti.transmatrics.api.crafting.ICraftingRegistry.ICraftingEntry;
import at.korti.transmatrics.api.crafting.IFluidItemCraftingRegistry;
import at.korti.transmatrics.tileentity.TileEntityFluidItemCraftingMachine;
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
import net.minecraftforge.fluids.FluidStack;

/**
 * Created by Korti on 14.04.2016.
 */
public abstract class FluidItemCraftingContainer extends Container {

    protected final TileEntityInventory tileEntity;
    protected final IFluidItemCraftingRegistry craftingRegistry;

    protected final int playerMinIndex;
    protected final int playerMaxIndex;

    private int craftingTime;
    private int totalCraftingTime;
    private int energyStored;
    private int maxEnergyStored;
    private int efficiency;
    private int maxEfficiency;

    public FluidItemCraftingContainer(InventoryPlayer inventoryPlayer, TileEntityInventory tileEntity, IFluidItemCraftingRegistry registry) {
        this.tileEntity = tileEntity;
        this.craftingRegistry = registry;
        addTileEntitySlots(tileEntity);
        addCapacitorSlot();
        this.playerMinIndex = this.inventorySlots.size();
        addPlayerSlots(inventoryPlayer);
        this.playerMaxIndex = this.inventorySlots.size();
    }

    public abstract void addTileEntitySlots(IInventory inventory);

    public void addCapacitorSlot() {
        if (tileEntity.hasCapacitorSlot()) {
            addSlotToContainer(new CapacitorSlot(tileEntity, tileEntity.getCapacitorSlot(), 17, 58));
        }
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
    public void addListener(IContainerListener listener) {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, tileEntity);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (int i = 0; i < this.listeners.size(); i++) {
            IContainerListener listener = this.listeners.get(i);

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
                if(tileEntity instanceof TileEntityFluidItemCraftingMachine) {
                    TileEntityFluidItemCraftingMachine machine = (TileEntityFluidItemCraftingMachine) tileEntity;
                    FluidStack[] fluidStacks = machine.getFluidsInput();
                    ICraftingEntry entry = craftingRegistry.get(fluidStacks, new ItemStack[]{tempStack});
                    if (entry != null) {
                        int startIndex = InventoryHelper.getMinIndex(craftingRegistry.getInputSlotsIds());
                        int endIndex = InventoryHelper.getMaxIndex(craftingRegistry.getInputSlotsIds());
                        if (!this.mergeItemStack(tempStack, startIndex, endIndex + 1, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (index >= playerMinIndex && index < playerMaxIndex - 9) {
                        if (!this.mergeItemStack(tempStack, playerMaxIndex - 9, playerMaxIndex, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (index >= playerMaxIndex - 9 && index < playerMaxIndex && !this.mergeItemStack(tempStack, playerMinIndex, playerMaxIndex - 9, false)) {
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
