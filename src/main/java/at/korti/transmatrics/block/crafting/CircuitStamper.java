package at.korti.transmatrics.block.crafting;

import at.korti.transmatrics.api.Constants.GuiIds;
import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.block.CraftingMachineBlock;
import at.korti.transmatrics.tileentity.crafting.TileEntityCircuitStamper;
import net.minecraft.block.material.Material;

/**
 * Created by Korti on 16.04.2016.
 */
public class CircuitStamper extends CraftingMachineBlock {

    public CircuitStamper() {
        super(Material.iron, TransmatricsBlock.CIRCUIT_STAMPER.getRegName(), TileEntityCircuitStamper.class,
                GuiIds.CIRCUIT_STAMPER_GUI_ID);
    }

}
