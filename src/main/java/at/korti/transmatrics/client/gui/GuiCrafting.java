package at.korti.transmatrics.client.gui;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.Mod;
import at.korti.transmatrics.api.energy.IEnergyHandler;
import at.korti.transmatrics.tileentity.TileEntityCraftingMachine;
import at.korti.transmatrics.tileentity.container.CraftingContainer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
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

    public GuiCrafting(Container container, IInventory inventory, String guiLocation) {
        super(container);

        this.inventory = inventory;
        this.guiTexture = new ResourceLocation(Mod.MODID, guiLocation);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        List<String> textLines = new LinkedList<>();
        this.addInformation(mouseX, mouseY, textLines);
        this.drawHoveringText(textLines, mouseX, mouseY);
    }

    protected void addInformation(int mouseX, int mouseY, List<String> textLines) {
        if (inventory instanceof IEnergyHandler && isInRect(mouseX, mouseY, 17, 10, 17 + 16, 10 + 43)) {
            IEnergyHandler machine = (IEnergyHandler) inventory;
            textLines.add(String.format("%d/%d TF", machine.getEnergyStored(), machine.getMaxEnergyStored()));
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

    protected int getCraftingProgress(int pixels) {
        int craftingTime = inventory.getField(0);
        int totalCraftingTime = inventory.getField(1);
        return totalCraftingTime != 0 && craftingTime != 0 ? craftingTime * pixels / totalCraftingTime : 0;
    }

    protected int getEnergyBar(int pixel) {
        int energyStored = inventory.getField(2);
        int capacity = inventory.getField(3);
        return capacity != 0 && energyStored != 0 ? energyStored * pixel / capacity : 0;
    }

    protected int getEfficiencyBar(int pixel) {
        int efficiency = inventory.getField(4);
        int maxEfficiency = inventory.getField(5);
        return maxEfficiency != 0 && efficiency != 0 ? efficiency * pixel / maxEfficiency : 0;
    }

    protected boolean isInRect(int posX, int posY, int startX, int startY, int endX, int endY) {
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        return posX >= i + startX && posX < i + endX && posY >= j + startY && posY < j + endY;
    }

}
