package at.korti.transmatrics.tileentity.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

/**
 * Created by Korti on 15.03.2016.
 */
public class ContainerPulverizer extends Container {

    private final IInventory tilePulverizer;

    public ContainerPulverizer(InventoryPlayer inventoryPlayer, IInventory tilePulverizer) {
        this.tilePulverizer = tilePulverizer;

        //Pulverizer inventory
        addSlotToContainer(new Slot(tilePulverizer, 0, 56, 26));
        addSlotToContainer(new Slot(tilePulverizer, 1, 116, 21));
        addSlotToContainer(new Slot(tilePulverizer, 2, 116, 52));

        //Player inventory
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
    public boolean canInteractWith(EntityPlayer playerIn) {
        return tilePulverizer.isUseableByPlayer(playerIn);
    }
}
