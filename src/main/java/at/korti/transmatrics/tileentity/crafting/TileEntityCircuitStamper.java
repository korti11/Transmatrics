package at.korti.transmatrics.tileentity.crafting;

import at.korti.transmatrics.api.Constants.Energy;
import at.korti.transmatrics.api.Constants.TransmatricsTileEntity;
import at.korti.transmatrics.registry.crafting.CircuitStamperCraftingRegistry;
import at.korti.transmatrics.tileentity.TileEntityCraftingMachine;

/**
 * Created by Korti on 16.04.2016.
 */
public class TileEntityCircuitStamper extends TileEntityCraftingMachine {

    public TileEntityCircuitStamper() {
        super(Energy.CIRCUIT_STAMPER_CAPACITY, Energy.CIRCUIT_STAMPER_RECEIVE, Energy.CIRCUIT_STAMPER_ENERGY_USE,
                TransmatricsTileEntity.CIRCUIT_STAMPER.getRegName(), CircuitStamperCraftingRegistry.getInstance());
    }

}
