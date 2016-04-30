package at.korti.transmatrics.api.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by Korti on 27.02.2016.
 */
public interface IDismantable {

    /**
     * Break the block, drop the inventory if exists and store the current state of the tile entity.
     * @param playerIn The player who want to dismantle the affected block.
     * @param worldIn The world where the affected block is in it.
     * @param pos The position of the affected block.
     * @param state The block state of the affected block.
     */
    void dismantleBlock(EntityPlayer playerIn, World worldIn, BlockPos pos, IBlockState state);

}
