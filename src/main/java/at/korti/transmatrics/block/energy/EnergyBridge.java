package at.korti.transmatrics.block.energy;

import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.block.MachineBlock;
import at.korti.transmatrics.tileentity.energy.TileEntityEnergyBridge;
import net.minecraft.block.material.Material;

/**
 * Created by Korti on 30.05.2016.
 */
public class EnergyBridge extends MachineBlock {

    public EnergyBridge() {
        super(Material.IRON, TransmatricsBlock.ENERGY_BRIDGE.getRegName(), TileEntityEnergyBridge.class);
    }

    @Override
    public boolean isRotatable() {
        return false;
    }
}
