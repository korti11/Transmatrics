package at.korti.transmatrics.tileentity.crafting;

import at.korti.transmatrics.api.Constants.Energy;
import at.korti.transmatrics.api.Constants.TransmatricsTileEntity;
import at.korti.transmatrics.registry.crafting.MagneticSmelteryCraftingRegistry;
import at.korti.transmatrics.tileentity.TileEntityFluidStackCraftingMachine;

/**
 * Created by Korti on 31.03.2016.
 */
public class TileEntityMagneticSmeltery extends TileEntityFluidStackCraftingMachine {

    public TileEntityMagneticSmeltery() {
        super(Energy.MAGNETIC_SMELTERY_CAPACITY, Energy.MAGNETIC_SMELTERY_RECEIVE, Energy.MAGNETIC_SMELTERY_ENERGY_USE,
                true, TransmatricsTileEntity.MAGNETIC_SMELTERY.getRegName(), MagneticSmelteryCraftingRegistry.getInstance());
    }
}
