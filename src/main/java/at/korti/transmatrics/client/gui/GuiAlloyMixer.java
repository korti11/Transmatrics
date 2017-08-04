package at.korti.transmatrics.client.gui;

import at.korti.transmatrics.client.util.RenderHelper;
import at.korti.transmatrics.tileentity.TileEntityFluidCraftingMachine;
import at.korti.transmatrics.tileentity.TileEntityInventory;
import at.korti.transmatrics.tileentity.container.ContainerAlloyMixer;
import at.korti.transmatrics.util.helper.TextHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import java.util.List;

/**
 * Created by Korti on 12.05.2016.
 */
public class GuiAlloyMixer extends GuiCrafting {

    public GuiAlloyMixer(InventoryPlayer inventoryPlayer, TileEntityInventory inventory) {
        super(new ContainerAlloyMixer(inventoryPlayer, inventory), inventory, "textures/gui/alloy_mixer.png");
    }

    @Override
    protected void addInformation(int mouseX, int mouseY, List<String> textLines) {
        super.addInformation(mouseX, mouseY, textLines);
        if (!(inventory instanceof TileEntityFluidCraftingMachine)) {
            return;
        }

        TileEntityFluidCraftingMachine machine = (TileEntityFluidCraftingMachine) inventory;
        IFluidHandler fluidHandler = (IFluidHandler) machine.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);

        if (isInRect(mouseX, mouseY, 148, 10, 148 + 16, 10 + 64)) {
            IFluidTankProperties tankInfo = fluidHandler.getTankProperties()[3];
            textLines.add(tankInfo.getContents() != null ? tankInfo.getContents().getLocalizedName() : TextHelper.localize("gui.tank.empty"));
            textLines.add(String.format("%d/%d mB",
                    tankInfo.getContents() != null ? tankInfo.getContents().amount : 0, tankInfo.getCapacity()));
        } else if(isInRect(mouseX, mouseY, 56, 10, 56 + 16, 10 + 64)){
            IFluidTankProperties tankInfo = fluidHandler.getTankProperties()[0];
            textLines.add(tankInfo.getContents() != null ? tankInfo.getContents().getLocalizedName() :
                    TextHelper.localize("gui.tank.empty"));
            textLines.add(String.format("%d/%d mB",
                    tankInfo.getContents() != null ? tankInfo.getContents().amount : 0, tankInfo.getCapacity()));
        } else if(isInRect(mouseX, mouseY, 77, 10, 77 + 16, 10 + 64)) {
            IFluidTankProperties tankInfo = fluidHandler.getTankProperties()[1];
            textLines.add(tankInfo.getContents() != null ? tankInfo.getContents().getLocalizedName() :
                    TextHelper.localize("gui.tank.empty"));
            textLines.add(String.format("%d/%d mB",
                    tankInfo.getContents() != null ? tankInfo.getContents().amount : 0, tankInfo.getCapacity()));
        } else if(isInRect(mouseX, mouseY, 98, 10, 98 + 16, 10 + 64)) {
            IFluidTankProperties tankInfo = fluidHandler.getTankProperties()[2];
            textLines.add(tankInfo.getContents() != null ? tankInfo.getContents().getLocalizedName() :
                    TextHelper.localize("gui.tank.empty"));
            textLines.add(String.format("%d/%d mB",
                    tankInfo.getContents() != null ? tankInfo.getContents().amount : 0, tankInfo.getCapacity()));
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;

        int craftingProgress = getCraftingProgress(24);
        this.drawTexturedModalRect(i + 120, j + 34, 176, 14, craftingProgress + 1, 16);

        int energyBar = getEnergyBar(43);
        this.drawTexturedModalRect(i + 17, j + 10 + 43 - energyBar, 176, 31 + 43 - energyBar, 16, energyBar + 1);

        int efficiencyBar = getEfficiencyBar(14);
        this.drawTexturedModalRect(i + 123, j + 53 + 14 - efficiencyBar, 176, 14 - efficiencyBar, 14, efficiencyBar);

        if (inventory instanceof TileEntityFluidCraftingMachine) {
            TileEntityFluidCraftingMachine fluidCraftingMachine = (TileEntityFluidCraftingMachine) inventory;
            IFluidHandler fluidHandler = (IFluidHandler) fluidCraftingMachine.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
            IFluidTankProperties[] tankInfos = fluidHandler.getTankProperties();
            for (int l = 0; l < tankInfos.length - 1; l++) {
                RenderHelper.drawGuiFluid(tankInfos[l], i + 56 + ((16 + 5) * l), j + 10, zLevel, 16, 64);
            }
            RenderHelper.drawGuiFluid(tankInfos[3], i + 148, j + 10, zLevel, 16, 64);
        }
    }
}
