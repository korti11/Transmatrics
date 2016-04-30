package at.korti.transmatrics.block;

import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by Korti on 27.04.2016.
 */
public class MachineCasing extends ModBlock {

    public MachineCasing() {
        super(Material.IRON, TransmatricsBlock.MACHINE_CASING.getRegName());
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return false;
    }
}
