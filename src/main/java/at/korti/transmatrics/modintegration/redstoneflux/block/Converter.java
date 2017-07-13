package at.korti.transmatrics.modintegration.redstoneflux.block;

import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.block.MachineBlock;
import at.korti.transmatrics.modintegration.redstoneflux.tileentity.TileEntityEnergyConverter;
import net.minecraft.block.material.Material;

/**
 * Created by Korti on 30.05.2016.
 */
public class Converter extends MachineBlock {

    public Converter() {
        super(Material.IRON, TransmatricsBlock.RF_CONVERTER.getRegName(), TileEntityEnergyConverter.class);
    }

    @Override
    public boolean isRotatable() {
        return false;
    }
}
