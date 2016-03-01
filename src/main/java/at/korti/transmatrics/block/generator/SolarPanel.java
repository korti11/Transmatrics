package at.korti.transmatrics.block.generator;

import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.block.MachineBlock;
import at.korti.transmatrics.block.ModBlockContainer;
import at.korti.transmatrics.tileentity.TileEntitySolarPanel;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Korti on 24.02.2016.
 */
public class SolarPanel extends MachineBlock {

    public SolarPanel() {
        this(Material.iron, TransmatricsBlock.SOLAR_PANEL.getRegName(), TileEntitySolarPanel.class);
    }

    protected SolarPanel(Material materialIn, String nameIn, Class<? extends TileEntity> tileEntity) {
        super(materialIn, nameIn, tileEntity);
        setBlockBounds(0f, 0f, 0f, 1f, 0.25f, 1f);
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
    public boolean isFullCube() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }
}
