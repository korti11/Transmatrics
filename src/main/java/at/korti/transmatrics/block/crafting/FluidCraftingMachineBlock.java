package at.korti.transmatrics.block.crafting;

import at.korti.transmatrics.block.CraftingMachineBlock;
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
import net.minecraftforge.fluids.IFluidHandler;

/**
 * Created by Korti on 31.03.2016.
 */
public abstract class FluidCraftingMachineBlock extends CraftingMachineBlock{

    protected FluidCraftingMachineBlock(Material materialIn, String name, Class<? extends TileEntity> tileEntityClass, int guiId) {
        super(materialIn, name, tileEntityClass, guiId);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        ItemStack currentStack = heldItem;
        if (currentStack != null) {
            TileEntity tile = worldIn.getTileEntity(pos);
            if (tile instanceof IFluidHandler) {
                IFluidHandler fluidHandler = (IFluidHandler) tile;
                if (FluidUtil.interactWithTank(currentStack, playerIn, fluidHandler, side)) {
                    return true;
                }
            }
        }

        if (super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ)) {
            return true;
        }
        return false;
    }
}
