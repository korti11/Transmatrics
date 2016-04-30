package at.korti.transmatrics.block.generator;

import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.block.MachineBlock;
import at.korti.transmatrics.tileentity.generator.TileEntityWatermill;
import net.minecraft.block.material.Material;

/**
 * Created by Korti on 06.03.2016.
 */
public class Watermill extends MachineBlock{

    public Watermill() {
        super(Material.IRON, TransmatricsBlock.WATERMILL.getRegName(), TileEntityWatermill.class);
    }

}
