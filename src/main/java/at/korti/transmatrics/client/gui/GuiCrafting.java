package at.korti.transmatrics.client.gui;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.Mod;
import at.korti.transmatrics.tileentity.TileEntityCraftingMachine;
import at.korti.transmatrics.tileentity.container.CraftingContainer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Korti on 30.03.2016.
 */
public abstract class GuiCrafting extends GuiContainer{

    protected IInventory inventory;
    protected ResourceLocation guiTexture;

    public GuiCrafting(CraftingContainer container, IInventory inventory, String guiLocation) {
        super(container);

        this.inventory = inventory;
        this.guiTexture = new ResourceLocation(Mod.MODID, guiLocation);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        List<String> textLines = new LinkedList<>();
        if(inventory instanceof TileEntityCraftingMachine) {
            TileEntityCraftingMachine machine = (TileEntityCraftingMachine) inventory;
            if (isInRect(mouseX, mouseY, 17, 10, 17 + 16, 10 + 64)) {
                textLines.add(String.format("%d/%d TF", machine.getEnergyStored(), machine.getMaxEnergyStored()));
                this.drawHoveringText(textLines, mouseX, mouseY);
                textLines.clear();
            }
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(guiTexture);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
    }

    protected boolean isInRect(int posX, int posY, int startX, int startY, int endX, int endY) {
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        return posX >= i + startX && posX < i + endX && posY >= j + startY && posY < j + endY;
    }

}
