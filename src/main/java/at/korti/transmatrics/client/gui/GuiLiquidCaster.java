package at.korti.transmatrics.client.gui;

import at.korti.transmatrics.client.util.RenderHelper;
import at.korti.transmatrics.tileentity.TileEntityInventory;
import at.korti.transmatrics.tileentity.container.ContainerLiquidCaster;
import at.korti.transmatrics.util.helper.TextHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import java.util.List;

/**
 * Created by Korti on 14.04.2016.
 */
public class GuiLiquidCaster extends GuiCrafting {

    public GuiLiquidCaster(InventoryPlayer inventoryPlayer, TileEntityInventory inventory) {
        super(new ContainerLiquidCaster(inventoryPlayer, inventory),
                inventory, "textures/gui/LiquidCaster.png");
    }

    @Override
    protected void addInformation(int mouseX, int mouseY, List<String> textLines) {
        super.addInformation(mouseX, mouseY, textLines);

        if (inventory instanceof IFluidHandler &&
                isInRect(mouseX, mouseY, 56, 10, 56 + 16, 10 + 64)) {
            IFluidHandler machine = (IFluidHandler) inventory;
            IFluidTankProperties tankInfo = machine.getTankProperties()[0];
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
        this.drawTexturedModalRect(i + 79, j + 34, 176, 14, craftingProgress + 1, 16);

        int energyBar = getEnergyBar(43);
        this.drawTexturedModalRect(i + 17, j + 10 + 43 - energyBar, 176, 31 + 43 - energyBar, 16, energyBar + 1);

        int efficiencyBar = getEfficiencyBar(14);
        this.drawTexturedModalRect(i + 83, j + 55 + 14 - efficiencyBar, 176, 14 - efficiencyBar, 14, efficiencyBar);

        if (inventory instanceof IFluidHandler) {
            IFluidHandler fluidCraftingMachine = (IFluidHandler) inventory;
            IFluidTankProperties tankInfo = fluidCraftingMachine.getTankProperties()[0];
            RenderHelper.drawGuiFluid(tankInfo.getContents(), i + 56, j + 10, zLevel, 16, 64, tankInfo.getCapacity());
        }
    }
}
