package at.korti.transmatrics.modintegration.cofh.block;

import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.block.MachineBlock;
import at.korti.transmatrics.modintegration.cofh.tileentity.TileEntityEnergyConverter;
import net.minecraft.block.material.Material;

/**
 * Created by Korti on 30.05.2016.
 */
public class Converter extends MachineBlock {

    public Converter() {
        super(Material.iron, TransmatricsBlock.RF_CONVERTER.getRegName(), TileEntityEnergyConverter.class);
    }
}
