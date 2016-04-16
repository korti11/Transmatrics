package at.korti.transmatrics.client.gui;

import at.korti.transmatrics.tileentity.container.ContainerCircuitStamper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

/**
 * Created by Korti on 16.04.2016.
 */
public class GuiCircuitStamper extends GuiCrafting {

    public GuiCircuitStamper(InventoryPlayer inventoryPlayer, IInventory inventory) {
        super(new ContainerCircuitStamper(inventoryPlayer, inventory),
                inventory, "textures/gui/CircuitStamper.png");
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;

        int craftingProgress = getCraftingProgress(24);
        this.drawTexturedModalRect(i + 79, j + 34, 176, 14, craftingProgress + 1, 16);

        int energyBar = getEnergyBar(64);
        this.drawTexturedModalRect(i + 17, j + 10 + 64 - energyBar, 176, 31 + 64 - energyBar, 16, energyBar + 1);

        int efficiencyBar = getEfficiencyBar(12);
        this.drawTexturedModalRect(i + 59, j + 46 + 12 - efficiencyBar, 176, 12 - efficiencyBar, 12, efficiencyBar);
    }
}
