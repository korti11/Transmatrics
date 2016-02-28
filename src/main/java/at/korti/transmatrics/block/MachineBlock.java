package at.korti.transmatrics.block;

import at.korti.transmatrics.api.block.IDismantable;
import at.korti.transmatrics.api.block.IRotatable;
import at.korti.transmatrics.util.helper.WorldHelper;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * Created by Korti on 27.02.2016.
 */
public abstract class MachineBlock extends ModBlockContainer implements IDismantable, IRotatable {

    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    protected MachineBlock(Material material, MapColor mapColor, String name, Class<? extends TileEntity> tileEntityClass) {
        super(material, mapColor, name, tileEntityClass);
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

    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        if(isRotatable()) {
            return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
        }
        return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
    }

    @Override
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
    protected BlockState createBlockState() {
        if(isRotatable()) {
            return new BlockState(this, new IProperty[]{FACING});
        }
        return super.createBlockState();
    }

    @Override
    public void dismantleBlock(EntityPlayer playerIn, World worldIn, BlockPos pos, IBlockState state) {
        if(isDismantable()) {
            ItemStack stack = new ItemStack(this, 1);
            WorldHelper.spawnItem(worldIn, stack, pos);
            breakBlock(worldIn, pos, state);
        }
    }

    @Override
    public EnumFacing getFacing(IBlockState stateIn) {
        return stateIn.getValue(FACING);
    }

    @Override
    public void rotate(World worldIn, EntityPlayer playerIn, BlockPos posIn, IBlockState stateIn) {
        if (isRotatable()) {
            IBlockState defaultState = stateIn.getBlock().getDefaultState();
            TileEntity tileEntity = worldIn.getTileEntity(posIn);

            if (playerIn.isSneaking()) {
               stateIn = defaultState.withProperty(FACING, playerIn.getHorizontalFacing());
            } else {
               stateIn = defaultState.withProperty(FACING, playerIn.getHorizontalFacing().getOpposite());
            }

            worldIn.setBlockState(posIn, stateIn, 3);

            if (tileEntity != null) {
                tileEntity.validate();
                worldIn.setTileEntity(posIn, tileEntity);
            }
        }
    }
}

