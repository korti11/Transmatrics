package at.korti.transmatrics.block.energy;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.GuiIds;
import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.block.CraftingMachineBlock;
import at.korti.transmatrics.tileentity.energy.TileEntityCharger;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Korti on 16.05.2016.
 */
public class Charger extends CraftingMachineBlock {

    public Charger() {
        super(Material.IRON, TransmatricsBlock.CHARGER.getRegName(), TileEntityCharger.class, GuiIds.CHARGER_GUI_ID);
    }
}
