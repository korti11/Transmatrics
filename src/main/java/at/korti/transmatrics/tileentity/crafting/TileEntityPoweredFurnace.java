package at.korti.transmatrics.tileentity.crafting;

import at.korti.transmatrics.api.Constants.Energy;
import at.korti.transmatrics.api.Constants.TransmatricsTileEntity;
import at.korti.transmatrics.api.crafting.FurnaceCraftingRegistry;
import at.korti.transmatrics.tileentity.TileEntityCraftingMachine;

/**
 * Created by Korti on 29.03.2016.
 */
public class TileEntityPoweredFurnace extends TileEntityCraftingMachine {

    public TileEntityPoweredFurnace() {
        super(Energy.POWERED_FURNACE_CAPACITY, Energy.POWERED_FURNACE_RECEIVE, Energy.POWERED_FURNACE_ENERGY_USE,
                TransmatricsTileEntity.POWERED_FURNACE.getRegName(), FurnaceCraftingRegistry.getInstance());
    }

}
