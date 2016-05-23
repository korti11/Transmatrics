package at.korti.transmatrics.tileentity.container;

import at.korti.transmatrics.api.crafting.ICraftingRegistry;
import at.korti.transmatrics.tileentity.TileEntityInventory;
import at.korti.transmatrics.tileentity.container.slot.CapacitorSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;

/**
 * Created by Korti on 12.05.2016.
 */
public abstract class FluidCraftingContainer extends Container {

    private final TileEntityInventory tileEntity;
    private final ICraftingRegistry<FluidStack> craftingRegistry;

    private int craftingTime;
    private int totalCraftingTime;
    private int energyStored;
    private int maxEnergyStored;
    private int efficiency;
    private int maxEfficiency;

    public FluidCraftingContainer(InventoryPlayer player, TileEntityInventory tileEntity, ICraftingRegistry<FluidStack> craftingRegistry) {
        this.tileEntity = tileEntity;
        this.craftingRegistry = craftingRegistry;
        addCapacitorSlot();
        addPlayerSlots(player);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return tileEntity.isUseableByPlayer(playerIn);
    }

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
}
