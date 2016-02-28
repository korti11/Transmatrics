package at.korti.transmatrics.api.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**
 * Created by Korti on 27.02.2016.
 */
public interface IDismantable {

    void dismantleBlock(EntityPlayer playerIn, World worldIn, BlockPos pos, IBlockState state);

}
