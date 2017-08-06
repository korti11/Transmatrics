package at.korti.transmatrics.tileentity.energy;

import at.korti.transmatrics.tileentity.TileEntityMultiBlockEnergyNode;

import static at.korti.transmatrics.api.Constants.Energy.SMALL_STORAGE_COMPONENT_CAPACITY;
import static at.korti.transmatrics.api.Constants.Energy.SMALL_STORAGE_COMPONENT_TRANSFER;

/**
 * Created by Korti on 05.08.2017.
 */
public class TileEntitySmallStorageComponent extends TileEntityMultiBlockEnergyNode {

    public TileEntitySmallStorageComponent() {
        super(SMALL_STORAGE_COMPONENT_CAPACITY, SMALL_STORAGE_COMPONENT_TRANSFER);
    }
}
