package at.korti.transmatrics.block.network;

import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.block.MachineBlock;
import at.korti.transmatrics.tileentity.network.TileEntityQuantumBridge;
import net.minecraft.block.material.Material;

/**
 * Created by Korti on 27.06.2016.
 */
public class QuantumBridge extends MachineBlock {

    public QuantumBridge() {
        super(Material.IRON, TransmatricsBlock.QUANTUM_BRIDGE.getRegName(), TileEntityQuantumBridge.class);
    }
}
