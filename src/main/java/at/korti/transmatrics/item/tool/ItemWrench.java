package at.korti.transmatrics.item.tool;

import at.korti.transmatrics.api.Constants.TransmatricsItem;
import at.korti.transmatrics.api.block.IChangeMode;
import at.korti.transmatrics.api.block.IDismantable;
import at.korti.transmatrics.api.block.IRotatable;
import at.korti.transmatrics.item.ModItem;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

/**
 * Created by Korti on 29.02.2016.
 */
public class ItemWrench extends ModItem {

    public ItemWrench() {
        super(TransmatricsItem.WRENCH.getRegName(), TextFormatting.YELLOW);

        setMaxStackSize(1);
    }

    @Override
    public EnumActionResult onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        IBlockState blockState = world.getBlockState(pos);
        Block affectedBlock = blockState.getBlock();
        TileEntity affectedTileEntity = world.getTileEntity(pos);

        if (player.isSneaking()) {
            if (affectedBlock instanceof IDismantable) {
                ((IDismantable) affectedBlock).dismantleBlock(player, world, pos, blockState);
            }
        } else {
            if (affectedBlock instanceof IRotatable) {
                ((IRotatable) affectedBlock).rotate(world, player, pos, blockState);
            }
            if(affectedTileEntity instanceof IChangeMode){
                ((IChangeMode) affectedTileEntity).cycleThroughMode();
            }
        }

        return super.onItemUseFirst(stack, player, world, pos, side, hitX, hitY, hitZ, hand);
    }
}
