package at.korti.transmatrics.client.gui;

import at.korti.transmatrics.api.Constants.Mod;
import at.korti.transmatrics.tileentity.TileEntityCraftingMachine;
import at.korti.transmatrics.tileentity.TileEntityInventory;
import at.korti.transmatrics.tileentity.container.ContainerPulverizer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Korti on 15.03.2016.
 */
@SideOnly(Side.CLIENT)
public class GuiPulverizer extends GuiCrafting {

    public GuiPulverizer(InventoryPlayer inventoryPlayer, TileEntityInventory tilePulverizer) {
        super(new ContainerPulverizer(inventoryPlayer, tilePulverizer), tilePulverizer, "textures/gui/pulverizer.png");
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;

        int craftingProgress = getCraftingProgress(24);
        this.drawTexturedModalRect(i + 79, j + 34, 176, 14, craftingProgress + 1, 16);

        int energyBar = getEnergyBar(43);
        this.drawTexturedModalRect(i + 17, j + 10 + 43 - energyBar, 176, 31 + 43 - energyBar, 16, energyBar + 1);

        int efficiencyBar = getEfficiencyBar(12);
        this.drawTexturedModalRect(i + 56, j + 46 + 12 - efficiencyBar, 176, 12 - efficiencyBar, 14, efficiencyBar);
    }
}
