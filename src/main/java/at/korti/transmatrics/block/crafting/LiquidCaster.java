package at.korti.transmatrics.block.crafting;

import at.korti.transmatrics.api.Constants.GuiIds;
import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.tileentity.crafting.TileEntityLiquidCaster;
import net.minecraft.block.material.Material;

/**
 * Created by Korti on 14.04.2016.
 */
public class LiquidCaster extends FluidCraftingMachineBlock {

    public LiquidCaster() {
        super(Material.iron, TransmatricsBlock.LIQUID_CASTER.getRegName(), TileEntityLiquidCaster.class, GuiIds.LIQUID_CASTER_GUI_ID);
    }

}
