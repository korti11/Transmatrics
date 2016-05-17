package at.korti.transmatrics.client.gui;

import at.korti.transmatrics.tileentity.container.ContainerCharger;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

/**
 * Created by Korti on 16.05.2016.
 */
public class GuiCharger extends GuiCrafting {

    public GuiCharger(InventoryPlayer inventoryPlayer, IInventory inventory) {
        super(new ContainerCharger(inventoryPlayer, inventory), inventory, "textures/gui/Charger.png");

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;

        int craftingProgress = getCraftingProgress(15);
        this.drawTexturedModalRect(i + 57, j + 52 + 15 - craftingProgress,
                176, 15 - craftingProgress, 15, craftingProgress);

        int energyBar = getEnergyBar(64);
        this.drawTexturedModalRect(i + 17, j + 10 + 64 - energyBar, 176, 32 + 64 - energyBar, 16, energyBar + 1);
    }
}
