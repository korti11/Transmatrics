package at.korti.transmatrics.block.network;

import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.block.MachineBlock;
import at.korti.transmatrics.tileentity.network.TileEntityMediumSwitch;
import net.minecraft.block.material.Material;

/**
 * Created by Korti on 07.03.2016.
 */
public class MediumSwitch extends MachineBlock {

    public MediumSwitch() {
        super(Material.iron, TransmatricsBlock.MEDIUM_SWITCH.getRegName(), TileEntityMediumSwitch.class);
    }

}
