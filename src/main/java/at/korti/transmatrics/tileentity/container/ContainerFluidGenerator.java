package at.korti.transmatrics.tileentity.container;

import at.korti.transmatrics.tileentity.TileEntityFluidGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

/**
 * Created by Korti on 30.04.2016.
 */
public class ContainerFluidGenerator extends Container {

    private TileEntityFluidGenerator fluidGenerator;

    public ContainerFluidGenerator(InventoryPlayer inventoryPlayer, TileEntityFluidGenerator fluidGenerator) {
        this.fluidGenerator = fluidGenerator;
        this.addPlayerSlots(inventoryPlayer);
    }

    private void addPlayerSlots(InventoryPlayer inventoryPlayer) {
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
        return playerIn.getDistanceSq(fluidGenerator.getPos()) <= 64;
    }
}
