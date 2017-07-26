package at.korti.transmatrics.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

/**
 * Created by Korti on 28.02.2016.
 */
public abstract class ActiveMachineBlock extends MachineBlock {

    private static final PropertyBool ACTIVE = PropertyBool.create("active");

    protected ActiveMachineBlock(Material material, MapColor mapColor, String name, Class<? extends TileEntity> tileEntityClass) {
        super(material, mapColor, name, tileEntityClass);
        super.getDefaultState().withProperty(ACTIVE, Boolean.FALSE);
    }

    protected ActiveMachineBlock(Material materialIn, String name, Class<? extends TileEntity> tileEntityClass) {
        this(materialIn, materialIn.getMaterialMapColor(), name, tileEntityClass);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        super.onBlockAdded(worldIn, pos, state);
        setState(false, worldIn, pos);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        BlockStateContainer superState = super.createBlockState();
        IProperty[] properties = new IProperty[superState.getProperties().size() + 1];
        properties = superState.getProperties().toArray(properties);
        properties[properties.length - 1] = ACTIVE;
        return new BlockStateContainer(this, properties);
    }

    public static void setState(boolean active, World worldIn, BlockPos posIn) {
        IBlockState state = worldIn.getBlockState(posIn);
        IBlockState defaultState = state.getBlock().getDefaultState();

        worldIn.setBlockState(posIn, defaultState.withProperty(FACING, state.getValue(FACING)).withProperty(ACTIVE, active), 3);
    }

    public static boolean isActive(World worldIn, BlockPos posIn) {
        return worldIn.getBlockState(posIn).getValue(ACTIVE);
    }

    public static boolean isActive(IBlockState blockState) {
        return blockState.getValue(ACTIVE);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = super.getMetaFromState(state);
        if (state.getValue(ACTIVE)) {
            i |= 4;
        }
        return i;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (isActive(worldIn, pos)) {
            double d0 = (double) pos.getX() + 0.5;
            double d1 = (double) pos.getY() + rand.nextDouble() * 6 / 16;
            double d2 = (double) pos.getZ() + 0.5;

            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1 + 1, d2, 0, 0, 0);
        }
    }
}
