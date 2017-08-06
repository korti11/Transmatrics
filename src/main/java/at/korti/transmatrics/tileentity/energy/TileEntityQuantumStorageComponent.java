package at.korti.transmatrics.tileentity.energy;

import at.korti.transmatrics.api.Constants.Energy;
import at.korti.transmatrics.tileentity.TileEntityMultiBlockEnergyNode;

/**
 * Created by Korti on 06.08.2017.
 */
public class TileEntityQuantumStorageComponent extends TileEntityMultiBlockEnergyNode {

    public TileEntityQuantumStorageComponent() {
        super(Energy.QUANTUM_STORAGE_COMPONENT_CAPACITY, Energy.QUANTUM_STORAGE_COMPONENT_TRANSFER);
    }

}
