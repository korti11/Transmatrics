package at.korti.transmatrics.block.crafting;

import at.korti.transmatrics.api.Constants.GuiIds;
import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.block.CraftingMachineBlock;
import at.korti.transmatrics.tileentity.crafting.TileEntityPoweredFurnace;
import net.minecraft.block.material.Material;

/**
 * Created by Korti on 29.03.2016.
 */
public class PoweredFurnace extends CraftingMachineBlock {

    public PoweredFurnace() {
        super(Material.IRON, TransmatricsBlock.POWERED_FURNACE.getRegName(), TileEntityPoweredFurnace.class, GuiIds.POWERED_FURNACE_GUI_ID);
    }

}
