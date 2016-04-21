package at.korti.transmatrics.client.gui;

import at.korti.transmatrics.tileentity.container.ContainerCircuitWorkbench;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

/**
 * Created by Korti on 21.04.2016.
 */
public class GuiCircuitWorkbench extends GuiCrafting {

    public GuiCircuitWorkbench(InventoryPlayer inventoryPlayer, IInventory inventory) {
        super(new ContainerCircuitWorkbench(inventoryPlayer, inventory), inventory,
                "textures/gui/CircuitWorkbench.png");
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;

        int energyBar = getEnergyBar(64);
        this.drawTexturedModalRect(i + 17, j + 10 + 64 - energyBar, 176, 31 + 64 - energyBar, 16, energyBar + 1);
    }

}
