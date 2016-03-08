package at.korti.transmatrics.block.network;

import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.block.MachineBlock;
import at.korti.transmatrics.tileentity.network.TileEntityController;
import net.minecraft.block.material.Material;

/**
 * Created by Korti on 08.03.2016.
 */
public class Controller extends MachineBlock{

    public Controller() {
        super(Material.iron, TransmatricsBlock.CONTROLLER.getRegName(), TileEntityController.class);
    }

}
