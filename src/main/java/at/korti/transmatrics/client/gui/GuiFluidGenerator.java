package at.korti.transmatrics.client.gui;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.Mod;
import at.korti.transmatrics.api.energy.IEnergyHandler;
import at.korti.transmatrics.api.energy.IEnergyProducer;
import at.korti.transmatrics.client.util.RenderHelper;
import at.korti.transmatrics.tileentity.TileEntityFluidGenerator;
import at.korti.transmatrics.tileentity.container.ContainerFluidGenerator;
import at.korti.transmatrics.util.helper.TextHelper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Korti on 30.04.2016.
 */
public class GuiFluidGenerator extends GuiContainer {

    private TileEntityFluidGenerator fluidGenerator;
    private String guiTexture = "textures/gui/FluidGenerator.png";

    public GuiFluidGenerator(InventoryPlayer inventoryPlayer, TileEntityFluidGenerator fluidGenerator) {
        super(new ContainerFluidGenerator(inventoryPlayer, fluidGenerator));
        this.fluidGenerator = fluidGenerator;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        List<String> textLines = new LinkedList<>();
        this.addInformation(mouseX, mouseY, textLines);
        this.drawHoveringText(textLines, mouseX, mouseY);
    }

    protected void addInformation(int mouseX, int mouseY, List<String> textLines) {
        if (fluidGenerator instanceof IEnergyProducer && isInRect(mouseX, mouseY, 17, 10, 17 + 16, 10 + 64)) {
            IEnergyProducer machine = fluidGenerator;
            textLines.add(String.format("%d/%d TF", machine.getEnergyStored(), machine.getMaxEnergyStored()));
        }

        if (fluidGenerator instanceof IFluidHandler &&
                isInRect(mouseX, mouseY, 143, 10, 143 + 16, 10 + 64)) {
            IFluidHandler machine = fluidGenerator;
            IFluidTankProperties tankInfo = machine.getTankProperties()[0];
            textLines.add(tankInfo.getContents() != null ? tankInfo.getContents().getLocalizedName() :
                    TextHelper.localize("gui.tank.empty"));
            textLines.add(String.format("%d/%d mB",
                    tankInfo.getContents() != null ? tankInfo.getContents().amount : 0, tankInfo.getCapacity()));
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(new ResourceLocation(Mod.MODID, guiTexture));
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        int energyBar = getEnergyBar(64);
        this.drawTexturedModalRect(i + 17, j + 10 + 64 - energyBar, 176, 31 + 64 - energyBar, 16, energyBar + 1);

        if (fluidGenerator instanceof IFluidHandler) {
            IFluidHandler fluidCraftingMachine = fluidGenerator;
            IFluidTankProperties tankInfo = fluidCraftingMachine.getTankProperties()[0];
            RenderHelper.drawGuiFluid(tankInfo.getContents(), i + 143, j + 10, zLevel, 16, 64, tankInfo.getCapacity());
        }
    }

    protected boolean isInRect(int posX, int posY, int startX, int startY, int endX, int endY) {
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        return posX >= i + startX && posX < i + endX && posY >= j + startY && posY < j + endY;
    }

    protected int getEnergyBar(int pixel) {
        int energyStored = fluidGenerator.getEnergyStored();
        int capacity = fluidGenerator.getMaxEnergyStored();
        return capacity != 0 && energyStored != 0 ? energyStored * pixel / capacity : 0;
    }

}
