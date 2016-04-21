package at.korti.transmatrics.tileentity.crafting;

import at.korti.transmatrics.api.Constants.Energy;
import at.korti.transmatrics.api.Constants.TransmatricsTileEntity;
import at.korti.transmatrics.registry.crafting.CircuitWorkbenchCraftingRegistry;
import at.korti.transmatrics.tileentity.TileEntityCraftingMachine;

/**
 * Created by Korti on 21.04.2016.
 */
public class TileEntityCircuitWorkbench extends TileEntityCraftingMachine {

    public TileEntityCircuitWorkbench() {
        super(Energy.CIRCUIT_WORKBENCH_CAPACITY, Energy.CIRCUIT_WORKBENCH_RECEIVE, Energy.CIRCUIT_WORKBENCH_ENERGY_USE,
                TransmatricsTileEntity.CIRCUIT_WORKBENCH.getRegName(), CircuitWorkbenchCraftingRegistry.getInstance());
    }

}
