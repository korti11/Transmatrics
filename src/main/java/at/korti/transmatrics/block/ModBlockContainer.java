package at.korti.transmatrics.block;

import at.korti.transmatrics.Transmatrics;
import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.tileentity.TileEntityInventory;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**
 * Created by Korti on 25.02.2016.
 */
public abstract class ModBlockContainer extends BlockContainer {

    private Class<? extends TileEntity> tileEntityClass;

    protected ModBlockContainer(Material material, MapColor mapColor, String name, Class<? extends TileEntity> tileEntityClass) {
        super(material, mapColor);

        this.tileEntityClass = tileEntityClass;

        setCreativeTab(Transmatrics.creativeTab);
        setUnlocalizedName(Constants.Mod.MODID + "." + name);
        setRegistryName(Constants.Mod.MODID, name);
    }

    protected ModBlockContainer(Material materialIn, String name, Class<? extends TileEntity> tileEntityClass) {
        this(materialIn, materialIn.getMaterialMapColor(), name, tileEntityClass);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        try {
            return tileEntityClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileEntityInventory) {
            ((TileEntityInventory) te).dropItems();
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public int getRenderType() {
        return 3;
    }
}
