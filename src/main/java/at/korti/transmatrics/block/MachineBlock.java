package at.korti.transmatrics.block;

import at.korti.transmatrics.api.block.IDismantable;
import at.korti.transmatrics.api.block.IRotatable;
import at.korti.transmatrics.api.network.INetworkNode;
import at.korti.transmatrics.api.network.INetworkSwitch;
import at.korti.transmatrics.util.helper.WorldHelper;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * Created by Korti on 27.02.2016.
 */
public abstract class MachineBlock extends ModBlockContainer implements IDismantable, IRotatable {

    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    protected MachineBlock(Material material, MapColor mapColor, String name, Class<? extends TileEntity> tileEntityClass) {
        super(material, mapColor, name, tileEntityClass);

        super.setHardness(2.5f);
    }

    protected MachineBlock(Material materialIn, String name, Class<? extends TileEntity> tileEntityClass) {
        this(materialIn, materialIn.getMaterialMapColor(), name, tileEntityClass);
    }

    public boolean isDismantable() {
        return true;
    }

    public boolean isRotatable() {
        return true;
    }

    /**
     * @return If the tile entity nbt should saved on dismantleBlock.
     */
    public boolean shouldSaveBlockNBT() {
        return true;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        if (isRotatable()) {
            return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
        }
        return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        if(isRotatable()) {
            EnumFacing enumFacing = EnumFacing.getFront(meta);

            if (enumFacing.getAxis() == EnumFacing.Axis.Y) {
                enumFacing = EnumFacing.NORTH;
            }

            return this.getDefaultState().withProperty(FACING, enumFacing);
        }
        return super.getStateFromMeta(meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        if(isRotatable()) {
            return state.getValue(FACING).getIndex();
        }
        return super.getMetaFromState(state);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        if(isRotatable()) {
            return new BlockStateContainer(this, FACING);
        }
        return super.createBlockState();
    }

    @Override
    public void dismantleBlock(EntityPlayer playerIn, World worldIn, BlockPos pos, IBlockState state) {
        if(isDismantable() && !worldIn.isRemote) {
            ItemStack stack = new ItemStack(this);
            breakBlock(worldIn, pos, state, stack);
            worldIn.setBlockToAir(pos);
        }
    }

    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
        // disable normal drop
    }

    private void breakBlock(World worldIn, BlockPos pos, IBlockState state, @Nonnull ItemStack stack) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity != null) {
            if (tileEntity instanceof INetworkSwitch) {
                ((INetworkSwitch) tileEntity).disconnectedFromAllNodes();
            } else if (tileEntity instanceof INetworkNode) {
                ((INetworkNode) tileEntity).disconnectFromNode();
            }
            WorldHelper.spawnItem(worldIn, writeToStack(tileEntity, stack), pos);
            super.breakBlock(worldIn, pos, state);
        }
    }

    protected ItemStack writeToStack(TileEntity tileEntity, @Nonnull ItemStack stack){
        return stack;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        breakBlock(worldIn,pos,state, new ItemStack(this));
    }

    @Override
    public EnumFacing getFacing(IBlockState stateIn) {
        return stateIn.getValue(FACING);
    }

    @Override
    public void rotate(World worldIn, EntityPlayer playerIn, BlockPos posIn, IBlockState stateIn) {
        if (isRotatable() && !worldIn.isRemote) {
            EnumFacing facing = getFacing(stateIn);
            EnumFacing oppositeLookFacing = playerIn.getHorizontalFacing().getOpposite();

            if (facing.equals(oppositeLookFacing)) {
                stateIn = stateIn.withProperty(FACING, playerIn.getHorizontalFacing());
            } else {
                stateIn = stateIn.withProperty(FACING, oppositeLookFacing);
            }

            worldIn.setBlockState(posIn, stateIn, 3);
        }
    }
}

