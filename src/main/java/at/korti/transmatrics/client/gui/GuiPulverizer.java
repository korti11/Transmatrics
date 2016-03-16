package at.korti.transmatrics.client.gui;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.Mod;
import at.korti.transmatrics.tileentity.container.ContainerPulverizer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Korti on 15.03.2016.
 */
@SideOnly(Side.CLIENT)
public class GuiPulverizer extends GuiContainer {

    private IInventory inventory;

    public GuiPulverizer(InventoryPlayer inventoryPlayer, IInventory tilePulverizer) {
        super(new ContainerPulverizer(inventoryPlayer, tilePulverizer));

        this.inventory = tilePulverizer;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        ResourceLocation pulverizerGuiTexture = new ResourceLocation(Mod.MODID, "textures/gui/Pulverizer.png");
        this.mc.getTextureManager().bindTexture(pulverizerGuiTexture);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        int craftingProgress = getCraftingProgress(24);
        this.drawTexturedModalRect(i + 79, j + 34, 176, 14, craftingProgress + 1, 16);

        int energyBar = getEnergyBar(64);
        this.drawTexturedModalRect(i + 17, j + 10 + 64 - energyBar, 176, 31 + 64 - energyBar, 16, energyBar + 1);
    }

    private int getCraftingProgress(int pixels) {
        int craftingTime = inventory.getField(0);
        int totalCraftingTime = inventory.getField(1);
        return totalCraftingTime != 0 && craftingTime != 0 ? craftingTime * pixels / totalCraftingTime : 0;
    }

    private int getEnergyBar(int pixel) {
        int energyStored = inventory.getField(2);
        int capacity = inventory.getField(3);
        return capacity != 0 && energyStored != 0 ? energyStored * pixel / capacity : 0;
    }
}
