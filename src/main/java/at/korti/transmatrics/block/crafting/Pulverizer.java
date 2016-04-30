package at.korti.transmatrics.block.crafting;

import at.korti.transmatrics.api.Constants.GuiIds;
import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.block.CraftingMachineBlock;
import at.korti.transmatrics.tileentity.crafting.TileEntityPulverizer;
import net.minecraft.block.material.Material;

/**
 * Created by Korti on 16.03.2016.
 */
public class Pulverizer extends CraftingMachineBlock {

    public Pulverizer() {
        super(Material.IRON, TransmatricsBlock.PULVERIZER.getRegName(), TileEntityPulverizer.class, GuiIds.PULVERIZER_GUI_ID);
    }

}
