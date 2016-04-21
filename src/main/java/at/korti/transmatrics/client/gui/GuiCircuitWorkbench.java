package at.korti.transmatrics.client.gui;

import at.korti.transmatrics.tileentity.container.ContainerCircuitWorkbench;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;

/**
 * Created by Korti on 21.04.2016.
 */
public class GuiCircuitWorkbench extends GuiCrafting {

    public GuiCircuitWorkbench(InventoryPlayer inventoryPlayer, IInventory inventory) {
        super(new ContainerCircuitWorkbench(inventoryPlayer, inventory), inventory,
                "textures/gui/CircuitWorkbench.png");
    }

}
