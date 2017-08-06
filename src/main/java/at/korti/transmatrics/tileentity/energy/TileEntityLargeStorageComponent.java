package at.korti.transmatrics.tileentity.energy;

import at.korti.transmatrics.api.Constants.Energy;
import at.korti.transmatrics.tileentity.TileEntityMultiBlockEnergyNode;

/**
 * Created by Korti on 06.08.2017.
 */
public class TileEntityLargeStorageComponent extends TileEntityMultiBlockEnergyNode {

    public TileEntityLargeStorageComponent() {
        super(Energy.LARGE_STORAGE_COMPONENT_CAPACITY, Energy.LARGE_STORAGE_COMPONENT_TRANSFER);
    }

}
