package at.korti.transmatrics.tileentity.container;

import at.korti.transmatrics.registry.crafting.MagneticSmelteryCraftingRegistry;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

/**
 * Created by Korti on 31.03.2016.
 */
public class ContainerMagneticSmeltery extends CraftingContainer {

    public ContainerMagneticSmeltery(InventoryPlayer inventoryPlayer, IInventory tileEntity) {
        super(inventoryPlayer, tileEntity, MagneticSmelteryCraftingRegistry.getInstance());
    }

    @Override
    public void addTileEntitySlots(IInventory inventory) {
        addSlotToContainer(new Slot(inventory, 0, 56, 34));
    }
}
