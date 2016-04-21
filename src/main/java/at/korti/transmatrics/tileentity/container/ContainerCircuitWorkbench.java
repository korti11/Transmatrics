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
        addSlotToContainer(new Slot(inventory, 0, 25, 20));
        addSlotToContainer(new Slot(inventory, 1, 25, 40));
        addSlotToContainer(new ElectronicPartSlot(inventory, 2, 45, 10));
        addSlotToContainer(new ElectronicPartSlot(inventory, 3, 65, 10));
        addSlotToContainer(new ElectronicPartSlot(inventory, 4, 85, 10));
        addSlotToContainer(new ElectronicPartSlot(inventory, 5, 105, 10));
        addSlotToContainer(new ElectronicPartSlot(inventory, 6, 45, 30));
        addSlotToContainer(new ElectronicPartSlot(inventory, 7, 65, 30));
        addSlotToContainer(new ElectronicPartSlot(inventory, 8, 85, 30));
        addSlotToContainer(new ElectronicPartSlot(inventory, 9, 105, 30));
        addSlotToContainer(new ElectronicPartSlot(inventory, 10, 45, 50));
        addSlotToContainer(new ElectronicPartSlot(inventory, 11, 65, 50));
        addSlotToContainer(new OutputSlot(inventory, 12, 125, 30));
    }
}
