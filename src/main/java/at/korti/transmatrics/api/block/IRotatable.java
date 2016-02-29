package at.korti.transmatrics.api.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * Created by Korti on 27.02.2016.
 */
public interface IRotatable {

    /**
     * @param state The block state of the block that is affected.
     * @return Get the facing where the front is.
     */
    EnumFacing getFacing(IBlockState state);

    /**
     * Rotate the block in a specific direction.
     * @param worldIn The world where the affected block is in it.
     * @param playerIn The player who want to rotate the affected block.
     * @param posIn The position of the affected block.
     * @param stateIn The block state of the affected block.
     */
    void rotate(World worldIn, EntityPlayer playerIn, BlockPos posIn, IBlockState stateIn);

}
