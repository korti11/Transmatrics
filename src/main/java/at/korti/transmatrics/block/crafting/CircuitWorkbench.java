package at.korti.transmatrics.block.crafting;

import at.korti.transmatrics.api.Constants.GuiIds;
import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.block.CraftingMachineBlock;
import at.korti.transmatrics.tileentity.crafting.TileEntityCircuitWorkbench;
import net.minecraft.block.material.Material;

/**
 * Created by Korti on 21.04.2016.
 */
public class CircuitWorkbench extends CraftingMachineBlock {

    public CircuitWorkbench() {
        super(Material.iron, TransmatricsBlock.CIRCUIT_WORKBENCH.getRegName(),
                TileEntityCircuitWorkbench.class, GuiIds.CIRCUIT_WORKBENCH_GUI_ID);
    }
}
