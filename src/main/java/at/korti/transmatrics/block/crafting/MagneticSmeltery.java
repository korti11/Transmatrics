package at.korti.transmatrics.block.crafting;

import at.korti.transmatrics.api.Constants.GuiIds;
import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.tileentity.crafting.TileEntityMagneticSmeltery;
import net.minecraft.block.material.Material;

/**
 * Created by Korti on 31.03.2016.
 */
public class MagneticSmeltery extends FluidCraftingMachineBlock {

    public MagneticSmeltery() {
        super(Material.iron, TransmatricsBlock.MAGNETIC_SMELTERY.getRegName(), TileEntityMagneticSmeltery.class, GuiIds.MAGNETIC_SMELTERY_GUI_ID);
    }

}
