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

    EnumFacing getFacing(IBlockState state);

    void rotate(World worldIn, EntityPlayer playerIn, BlockPos posIn, IBlockState stateIn);

}
