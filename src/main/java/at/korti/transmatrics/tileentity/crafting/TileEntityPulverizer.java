package at.korti.transmatrics.tileentity.crafting;

import at.korti.transmatrics.api.Constants.Energy;
import at.korti.transmatrics.api.Constants.TransmatricsTileEntity;
import at.korti.transmatrics.api.crafting.PulverizerCraftingRegistry;
import at.korti.transmatrics.tileentity.TileEntityCraftingMachine;

/**
 * Created by Korti on 15.03.2016.
 */
public class TileEntityPulverizer extends TileEntityCraftingMachine {

    public TileEntityPulverizer() {
        super(Energy.PULVERIZER_CAPACITY, Energy.PULVERIZER_RECEIVE, TransmatricsTileEntity.PULVERIZER.getRegName(), PulverizerCraftingRegistry.getInstance());
    }

}
