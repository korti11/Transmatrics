package at.korti.transmatrics.block.energy;

import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.block.MultiMachineBlock;
import at.korti.transmatrics.tileentity.energy.TileEntityQuantumStorageComponent;
import net.minecraft.block.material.Material;

/**
 * Created by Korti on 06.08.2017.
 */
public class QuantumStorageComponent extends MultiMachineBlock {

    public QuantumStorageComponent() {
        super(Material.IRON, TransmatricsBlock.QUANTUM_STORAGE_COMPONENT.getRegName(),
                TileEntityQuantumStorageComponent.class);
    }

}
