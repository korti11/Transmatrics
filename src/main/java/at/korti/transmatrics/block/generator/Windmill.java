package at.korti.transmatrics.block.generator;

import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.block.MachineBlock;
import at.korti.transmatrics.tileentity.generator.TileEntityWindmill;
import net.minecraft.block.material.Material;

/**
 * Created by Korti on 06.03.2016.
 */
public class Windmill extends MachineBlock {

    public Windmill() {
        super(Material.IRON, TransmatricsBlock.WINDMILL.getRegName(), TileEntityWindmill.class);
    }

}
