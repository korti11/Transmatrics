package at.korti.transmatrics.tileentity.container;

import at.korti.transmatrics.api.crafting.FurnaceCraftingRegistry;
import at.korti.transmatrics.tileentity.container.slot.OutputSlot;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

/**
 * Created by Korti on 30.03.2016.
 */
public class ContainerPoweredFurnace extends CraftingContainer {

    public ContainerPoweredFurnace(InventoryPlayer inventoryPlayer, IInventory tileEntity) {
        super(inventoryPlayer, tileEntity, FurnaceCraftingRegistry.getInstance());
    }

    @Override
    public void addTileEntitySlots(IInventory inventory) {
        addSlotToContainer(new Slot(inventory, 0, 56, 34));
        addSlotToContainer(new OutputSlot(inventory, 1, 116, 35));
    }
}
