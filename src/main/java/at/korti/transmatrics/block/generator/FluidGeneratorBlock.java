package at.korti.transmatrics.block.generator;

import at.korti.transmatrics.Transmatrics;
import at.korti.transmatrics.block.ActiveMachineBlock;
import at.korti.transmatrics.item.tool.ItemConnector;
import at.korti.transmatrics.item.tool.ItemWrench;
import at.korti.transmatrics.tileentity.TileEntityFluidGenerator;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;

/**
 * Created by Korti on 04.03.2016.
 */
public abstract class FluidGeneratorBlock extends ActiveMachineBlock {

    private int guiId;

    protected FluidGeneratorBlock(Material materialIn, String name, int guiId, Class<? extends TileEntityFluidGenerator> tileEntityClass) {
        super(materialIn, name, tileEntityClass);
        this.guiId = guiId;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ)) {
            return true;
        }

        ItemStack currentStack = heldItem;
        if (currentStack != null) {
            TileEntity tile = worldIn.getTileEntity(pos);
            if (tile instanceof TileEntityFluidGenerator) {
                TileEntityFluidGenerator fluidGenerator = (TileEntityFluidGenerator) tile;
                if (FluidUtil.interactWithFluidHandler(currentStack, fluidGenerator, playerIn)) {
                    return true;
                }
            }
        }

        ItemStack currentItem = heldItem;
        if (currentItem != null && (currentItem.getItem() instanceof ItemConnector || currentItem.getItem() instanceof ItemWrench)) {
            return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
        }
        if (worldIn.getTileEntity(pos).getClass().equals(tileEntityClass) && !playerIn.isSneaking()) {
            playerIn.openGui(Transmatrics.instance, guiId, worldIn, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }

        return false;
    }
}
