package at.korti.transmatrics.block;

import at.korti.transmatrics.Transmatrics;
import at.korti.transmatrics.item.tool.ItemConnector;
import at.korti.transmatrics.item.tool.ItemWrench;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by Korti on 16.03.2016.
 */
public abstract class CraftingMachineBlock extends ActiveMachineBlock{

    private final int guiId;

    protected CraftingMachineBlock(Material materialIn, String name, Class<? extends TileEntity> tileEntityClass, int guiId) {
        super(materialIn, name, tileEntityClass);
        this.guiId = guiId;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        ItemStack currentItem = playerIn.getHeldItem(hand);
        if (currentItem.getItem() instanceof ItemConnector || currentItem.getItem() instanceof ItemWrench) {
            return true;
        }
        if (worldIn.getTileEntity(pos).getClass().equals(tileEntityClass) && !playerIn.isSneaking()) {
            playerIn.openGui(Transmatrics.instance, guiId, worldIn, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, side, hitX, hitY, hitZ);
    }

}
