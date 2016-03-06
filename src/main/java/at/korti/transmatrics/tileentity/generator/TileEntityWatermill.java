package at.korti.transmatrics.tileentity.generator;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.Energy;
import at.korti.transmatrics.block.MachineBlock;
import at.korti.transmatrics.tileentity.TileEntityGenerator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

/**
 * Created by Korti on 06.03.2016.
 */
public class TileEntityWatermill extends TileEntityGenerator{

    public TileEntityWatermill() {
        super(Energy.WATERMILL_GENERATE, Energy.WATERMILL_CAPACITY, Energy.WATERMILL_EXTRACTION);
    }

    @Override
    public boolean canProduceEnergy() {
        return isInWater() && super.canProduceEnergy();
    }

    private boolean isInWater() {
        IBlockState state = worldObj.getBlockState(pos);
        MachineBlock block = (MachineBlock) state.getBlock();
        EnumFacing facing = block.getFacing(state);
        BlockPos blockPos = pos.offset(facing);
        return worldObj.getBlockState(blockPos).getBlock().equals(Blocks.water);
    }
}
