package at.korti.transmatrics.block.network;

import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.block.MachineBlock;
import at.korti.transmatrics.tileentity.network.TileEntityLargeSwitch;
import net.minecraft.block.material.Material;

/**
 * Created by Korti on 07.03.2016.
 */
public class LargeSwitch extends MachineBlock {

    public LargeSwitch() {
        super(Material.iron, TransmatricsBlock.LARGE_SWITCH.getRegName(), TileEntityLargeSwitch.class);
    }

}
