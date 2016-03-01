package at.korti.transmatrics.block.generator;

import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.block.ActiveMachineBlock;
import at.korti.transmatrics.tileentity.TileEntityThermalGenerator;
import net.minecraft.block.material.Material;

/**
 * Created by Korti on 01.03.2016.
 */
public class ThermalGenerator extends ActiveMachineBlock {
    public ThermalGenerator() {
        super(Material.iron, TransmatricsBlock.THERMAL_GENERATOR.getRegName(), TileEntityThermalGenerator.class);
    }
}
