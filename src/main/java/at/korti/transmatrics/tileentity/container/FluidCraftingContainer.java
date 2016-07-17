package at.korti.transmatrics.tileentity.container;

import at.korti.transmatrics.api.crafting.ICraftingRegistry;
import at.korti.transmatrics.tileentity.TileEntityInventory;
import at.korti.transmatrics.tileentity.container.slot.CapacitorSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fluids.FluidStack;

/**
 * Created by Korti on 12.05.2016.
 */
public abstract class FluidCraftingContainer extends Container {

    private final TileEntityInventory tileEntity;
    private final ICraftingRegistry<FluidStack> craftingRegistry;

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
}
