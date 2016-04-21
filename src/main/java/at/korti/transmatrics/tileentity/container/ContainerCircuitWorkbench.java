package at.korti.transmatrics.tileentity.container;

import at.korti.transmatrics.api.crafting.ICraftingRegistry;
import at.korti.transmatrics.registry.crafting.CircuitWorkbenchCraftingRegistry;
import at.korti.transmatrics.tileentity.container.slot.ElectronicPartSlot;
import at.korti.transmatrics.tileentity.container.slot.OutputSlot;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Korti on 21.04.2016.
 */
public class ContainerCircuitWorkbench extends CraftingContainer {

    public ContainerCircuitWorkbench(InventoryPlayer inventoryPlayer, IInventory tileEntity) {
        super(inventoryPlayer, tileEntity, CircuitWorkbenchCraftingRegistry.getInstance());
    }

    @Override
    public void addTileEntitySlots(IInventory inventory) {
        addSlotToContainer(new Slot(inventory, 0, 37, 24));
        addSlotToContainer(new Slot(inventory, 1, 37, 44));
        addSlotToContainer(new ElectronicPartSlot(inventory, 2, 58, 13));
        addSlotToContainer(new ElectronicPartSlot(inventory, 3, 58, 34));
        addSlotToContainer(new ElectronicPartSlot(inventory, 4, 58, 55));
        addSlotToContainer(new ElectronicPartSlot(inventory, 5, 79, 13));
        addSlotToContainer(new ElectronicPartSlot(inventory, 6, 79, 34));
        addSlotToContainer(new ElectronicPartSlot(inventory, 7, 79, 55));
        addSlotToContainer(new ElectronicPartSlot(inventory, 8, 100, 13));
        addSlotToContainer(new ElectronicPartSlot(inventory, 9, 100, 34));
        addSlotToContainer(new ElectronicPartSlot(inventory, 10, 100, 55));
        addSlotToContainer(new ElectronicPartSlot(inventory, 11, 121, 34));
        addSlotToContainer(new OutputSlot(inventory, 12, 146, 34));
    }
}
