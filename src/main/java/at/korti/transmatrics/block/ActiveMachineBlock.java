package at.korti.transmatrics.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**
 * Created by Korti on 28.02.2016.
 */
public abstract class ActiveMachineBlock extends MachineBlock {

    private static final PropertyBool ACTIVE = PropertyBool.create("active");

    protected ActiveMachineBlock(Material material, MapColor mapColor, String name, Class<? extends TileEntity> tileEntityClass) {
        super(material, mapColor, name, tileEntityClass);
        super.getDefaultState().withProperty(ACTIVE, Boolean.valueOf(false));
    }

    protected ActiveMachineBlock(Material materialIn, String name, Class<? extends TileEntity> tileEntityClass) {
        this(materialIn, materialIn.getMaterialMapColor(), name, tileEntityClass);
    }

    @Override
    protected BlockState createBlockState() {
        BlockState superState = super.createBlockState();
        IProperty[] properties = new IProperty[superState.getProperties().size() + 1];
        properties = superState.getProperties().toArray(properties);
        properties[properties.length - 1] = ACTIVE;
        return new BlockState(this, properties);
    }

    public static void setState(boolean active, World worldIn, BlockPos posIn) {
        IBlockState state = worldIn.getBlockState(posIn);
        IBlockState defaultState = state.getBlock().getDefaultState();
        TileEntity tileEntity = worldIn.getTileEntity(posIn);

        worldIn.setBlockState(posIn, defaultState.withProperty(FACING, state.getValue(FACING)).withProperty(ACTIVE, Boolean.valueOf(active)), 3);

        if (tileEntity != null) {
            worldIn.removeTileEntity(posIn);
            tileEntity.validate();
            worldIn.setTileEntity(posIn, tileEntity);
        }
    }

    public static boolean isActive(World worldIn, BlockPos posIn) {
        return worldIn.getBlockState(posIn).getValue(ACTIVE).booleanValue();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = super.getMetaFromState(state);
        if (state.getValue(ACTIVE).booleanValue()) {
            i |= 4;
        }
        return i;
    }
}
