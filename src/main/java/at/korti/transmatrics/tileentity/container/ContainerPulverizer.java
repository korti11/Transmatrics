package at.korti.transmatrics.tileentity.container;

import at.korti.transmatrics.registry.crafting.PulverizerCraftingRegistry;
import at.korti.transmatrics.tileentity.container.slot.OutputSlot;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

/**
 * Created by Korti on 15.03.2016.
 */
public class ContainerPulverizer extends CraftingContainer {

    public ContainerPulverizer(InventoryPlayer inventoryPlayer, IInventory tilePulverizer) {
        super(inventoryPlayer, tilePulverizer, PulverizerCraftingRegistry.getInstance());

    }

    @Override
    public void addTileEntitySlots(IInventory inventory) {
        addSlotToContainer(new Slot(inventory, 0, 56, 26));
        addSlotToContainer(new OutputSlot(inventory, 1, 116, 21));
        addSlotToContainer(new OutputSlot(inventory, 2, 116, 52));
    }
}
