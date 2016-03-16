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

    public GuiPulverizer(InventoryPlayer inventoryPlayer, IInventory tilePulverizer) {
        super(new ContainerPulverizer(inventoryPlayer, tilePulverizer));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        ResourceLocation pulverizerGuiTexture = new ResourceLocation(Mod.MODID, "textures/gui/Pulverizer.png");
        this.mc.getTextureManager().bindTexture(pulverizerGuiTexture);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
    }
}
