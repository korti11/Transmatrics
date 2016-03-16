package at.korti.transmatrics.client.gui;

import at.korti.transmatrics.api.Constants.Mod;
import at.korti.transmatrics.tileentity.TileEntityCraftingMachine;
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
public class GuiPulverizer extends GuiContainer {

    private IInventory inventory;

    public GuiPulverizer(InventoryPlayer inventoryPlayer, IInventory tilePulverizer) {
        super(new ContainerPulverizer(inventoryPlayer, tilePulverizer));

        this.inventory = tilePulverizer;
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
        ResourceLocation pulverizerGuiTexture = new ResourceLocation(Mod.MODID, "textures/gui/Pulverizer.png");
        this.mc.getTextureManager().bindTexture(pulverizerGuiTexture);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        int craftingProgress = getCraftingProgress(24);
        this.drawTexturedModalRect(i + 79, j + 34, 176, 14, craftingProgress + 1, 16);

        int energyBar = getEnergyBar(64);
        this.drawTexturedModalRect(i + 17, j + 10 + 64 - energyBar, 176, 31 + 64 - energyBar, 16, energyBar + 1);

        int efficiencyBar = getEfficiencyBar(12);
        this.drawTexturedModalRect(i + 56, j + 46 + 12 - efficiencyBar, 176, 12 - efficiencyBar, 14, efficiencyBar);
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

    private int getEfficiencyBar(int pixel) {
        int efficiency = inventory.getField(4);
        int maxEfficiency = inventory.getField(5);
        return maxEfficiency != 0 && efficiency != 0 ? efficiency * pixel / maxEfficiency : 0;
    }

    private boolean isInRect(int posX, int posY, int startX, int startY, int endX, int endY) {
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        return posX >= i + startX && posX < i + endX && posY >= j + startY && posY < j + endY;
    }
}
