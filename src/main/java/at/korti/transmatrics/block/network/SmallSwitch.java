package at.korti.transmatrics.block.network;

import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.block.MachineBlock;
import at.korti.transmatrics.tileentity.network.TileEntitySmallSwitch;
import net.minecraft.block.material.Material;

/**
 * Created by Korti on 06.03.2016.
 */
public class SmallSwitch extends MachineBlock {

    public SmallSwitch() {
        super(Material.IRON, TransmatricsBlock.SMALL_SWITCH.getRegName(), TileEntitySmallSwitch.class);
    }

}
