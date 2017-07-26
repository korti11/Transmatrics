package at.korti.transmatrics.block.crafting;

import at.korti.transmatrics.block.CraftingMachineBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;

/**
 * Created by Korti on 31.03.2016.
 */
public abstract class FluidCraftingMachineBlock extends CraftingMachineBlock{

    protected FluidCraftingMachineBlock(Material materialIn, String name, Class<? extends TileEntity> tileEntityClass, int guiId) {
        super(materialIn, name, tileEntityClass, guiId);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if (tile instanceof IFluidHandler) {
            if (FluidUtil.interactWithFluidHandler(playerIn, hand, worldIn, pos, side)) {
                return true;
            }
        }

        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, side, hitX, hitY, hitZ);
    }
}
