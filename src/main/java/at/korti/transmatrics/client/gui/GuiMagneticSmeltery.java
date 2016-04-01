package at.korti.transmatrics.client.gui;

import at.korti.transmatrics.client.util.RenderHelper;
import at.korti.transmatrics.tileentity.TileEntityFluidCraftingMachine;
import at.korti.transmatrics.tileentity.container.ContainerMagneticSmeltery;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidTankInfo;

/**
 * Created by Korti on 31.03.2016.
 */
public class GuiMagneticSmeltery extends GuiCrafting {

    public GuiMagneticSmeltery(InventoryPlayer inventoryPlayer, IInventory inventory) {
        super(new ContainerMagneticSmeltery(inventoryPlayer, inventory), inventory, "textures/gui/MagneticSmeltery.png");
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;

        int craftingProgress = getCraftingProgress(24);
        this.drawTexturedModalRect(i + 79, j + 34, 176, 14, craftingProgress + 1, 16);

        int energyBar = getEnergyBar(64);
        this.drawTexturedModalRect(i + 17, j + 10 + 64 - energyBar, 176, 31 + 64 - energyBar, 16, energyBar + 1);

        int efficiencyBar = getEfficiencyBar(14);
        this.drawTexturedModalRect(i + 56, j + 53 + 14 - efficiencyBar, 176, 14 - efficiencyBar, 14, efficiencyBar);

        if (inventory instanceof TileEntityFluidCraftingMachine) {
            TileEntityFluidCraftingMachine fluidCraftingMachine = (TileEntityFluidCraftingMachine) inventory;
            FluidTankInfo tankInfo = fluidCraftingMachine.getTankInfo(EnumFacing.UP)[0];
            RenderHelper.drawGuiFluid(tankInfo.fluid, i + 107, j + 10, zLevel, 16, 64, tankInfo.capacity);
        }
    }

    private int getFluidBar(int pixels) {
        int fluidAmount = inventory.getField(6);
        int tankCapacity = inventory.getField(7);
        return tankCapacity != 0 && fluidAmount != 0 ? fluidAmount * pixels / tankCapacity : 0;
    }

    private ResourceLocation getFluidTexture() {
        if (inventory instanceof TileEntityFluidCraftingMachine) {
            TileEntityFluidCraftingMachine fluidCraftingMachine = (TileEntityFluidCraftingMachine) inventory;
            FluidTankInfo tankInfo = fluidCraftingMachine.getTankInfo(EnumFacing.UP)[0];
            if(tankInfo.fluid != null) {
                ResourceLocation tempResource = tankInfo.fluid.getFluid().getStill(tankInfo.fluid);
                return new ResourceLocation(tempResource.getResourceDomain(), "textures/" + tempResource.getResourcePath() + ".png");
            }
        }
        return null;
    }

    private int getColor() {
        if (inventory instanceof TileEntityFluidCraftingMachine) {
            TileEntityFluidCraftingMachine fluidCraftingMachine = (TileEntityFluidCraftingMachine) inventory;
            FluidTankInfo tankInfo = fluidCraftingMachine.getTankInfo(EnumFacing.UP)[0];
            if (tankInfo.fluid != null) {
                return tankInfo.fluid.getFluid().getColor();
            }
        }
        return 0xffffff;
    }
}
