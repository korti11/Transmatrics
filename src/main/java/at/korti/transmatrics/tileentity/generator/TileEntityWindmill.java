package at.korti.transmatrics.tileentity.generator;

import at.korti.transmatrics.api.Constants.Energy;
import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.block.MachineBlock;
import at.korti.transmatrics.tileentity.TileEntityHeightGenerator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.AxisDirection;
import net.minecraft.util.math.BlockPos;

/**
 * Created by Korti on 05.03.2016.
 */
public class TileEntityWindmill extends TileEntityHeightGenerator {

    private static final int RANGE = 16;

    public TileEntityWindmill() {
        super(Energy.WINDMILL_MIN_GENERATE, Energy.WINDMILL_MAX_GENERATE, Energy.WINDMILL_CAPACITY,
                Energy.WINDMILL_EXTRACTION, Energy.WINDMILL_MIN_HEIGHT, Energy.WINDMILL_MAX_HEIGHT, AxisDirection.POSITIVE);
    }

    @Override
    public void onLoad() {
        int seaLevel = getWorld().getSeaLevel();
        if (minHeight - seaLevel > 16) {
            int diff = maxHeight - minHeight;
            minHeight = seaLevel + 16;
            maxHeight = Math.min(minHeight + diff, 256);
        }
        super.onLoad();
    }

    @Override
    public boolean canProduceEnergy() {
        return checkWorldAround() && super.canProduceEnergy();
    }

    private boolean checkWorldAround() {
        for (int x = pos.getX() - (RANGE / 2); x < pos.getX() + (RANGE / 2); x++) {
            for (int z = pos.getZ() - (RANGE / 2); z < pos.getZ() + (RANGE / 2); z++) {
                BlockPos blockPos = new BlockPos(x, pos.getY(), z);
                if (!getWorld().canBlockSeeSky(new BlockPos(x, pos.getY() + 1, z))) {
                    return false;
                } else if (!getWorld().isAirBlock(blockPos) &&
                        !getWorld().getBlockState(blockPos).getBlock().equals(TransmatricsBlock.WINDMILL.getBlock())) {
                    return false;
                }
            }
        }

        IBlockState state = getWorld().getBlockState(pos);
        MachineBlock block = (MachineBlock) state.getBlock();
        EnumFacing facing = block.getFacing(state);
        BlockPos blockPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ());

        for (int i = 0; i < RANGE / 2; i++) {
            blockPos = blockPos.offset(facing);
            if (!getWorld().isAirBlock(blockPos)) {
                return false;
            }
        }

        return true;
    }
}
