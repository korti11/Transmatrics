package at.korti.transmatrics.block.generator;

import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.block.MachineBlock;
import at.korti.transmatrics.tileentity.generator.TileEntitySolarPanel;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 * Created by Korti on 24.02.2016.
 */
public class SolarPanel extends MachineBlock {

    public SolarPanel() {
        this(Material.IRON, TransmatricsBlock.SOLAR_PANEL.getRegName(), TileEntitySolarPanel.class);
    }

    protected SolarPanel(Material materialIn, String nameIn, Class<? extends TileEntity> tileEntity) {
        super(materialIn, nameIn, tileEntity);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(0f, 0f, 0f, 1f, 0.25f, 1f);
    }

    @Override
    public boolean isRotatable() {
        return false;
    }

    @Override
    public boolean shouldSaveBlockNBT() {
        return false;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
}
