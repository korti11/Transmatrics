package at.korti.transmatrics.tileentity.crafting;

import at.korti.transmatrics.api.Constants.Energy;
import at.korti.transmatrics.api.Constants.TransmatricsTileEntity;
import at.korti.transmatrics.api.crafting.MagneticSmelteryCraftingRegistry;
import at.korti.transmatrics.tileentity.TileEntityFluidCraftingMachine;

/**
 * Created by Korti on 31.03.2016.
 */
public class TileEntityMagneticSmeltery extends TileEntityFluidCraftingMachine {

    public TileEntityMagneticSmeltery() {
        super(Energy.MAGNETIC_SMELTERY_CAPACITY, Energy.MAGNETIC_SMELTERY_RECEIVE, Energy.MAGNETIC_SMELTERY_ENERGY_USE,
                TransmatricsTileEntity.MAGNETIC_SMELTERY.getRegName(), MagneticSmelteryCraftingRegistry.getInstance());
    }

    @Override
    protected boolean isFluidInput() {
        return false;
    }
}
