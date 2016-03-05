package at.korti.transmatrics.block.generator;

import at.korti.transmatrics.block.ActiveMachineBlock;
import at.korti.transmatrics.tileentity.TileEntityFluidGenerator;
import at.korti.transmatrics.util.helper.ItemStackHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.IFluidContainerItem;

/**
 * Created by Korti on 04.03.2016.
 */
public abstract class FluidGeneratorBlock extends ActiveMachineBlock {

    protected FluidGeneratorBlock(Material materialIn, String name, Class<? extends TileEntityFluidGenerator> tileEntityClass) {
        super(materialIn, name, tileEntityClass);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (super.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ)) {
            return true;
        }

        ItemStack currentStack = playerIn.getHeldItem();
        if (currentStack != null) {
            TileEntity tile = worldIn.getTileEntity(pos);
            if (tile instanceof TileEntityFluidGenerator) {
                TileEntityFluidGenerator fluidGenerator = (TileEntityFluidGenerator) tile;
                if (FluidUtil.interactWithTank(currentStack, playerIn, fluidGenerator, side)) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}
