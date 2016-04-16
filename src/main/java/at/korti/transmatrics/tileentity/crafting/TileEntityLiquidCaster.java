package at.korti.transmatrics.tileentity.crafting;

import at.korti.transmatrics.api.Constants.Energy;
import at.korti.transmatrics.api.Constants.TransmatricsTileEntity;
import at.korti.transmatrics.registry.crafting.LiquidCasterCraftingRegistry;
import at.korti.transmatrics.tileentity.TileEntityFluidItemCraftingMachine;

/**
 * Created by Korti on 14.04.2016.
 */
public class TileEntityLiquidCaster extends TileEntityFluidItemCraftingMachine {

    public TileEntityLiquidCaster() {
        super(Energy.LIQUID_CASTER_CAPACITY, Energy.LIQUID_CASTER_RECEIVE, Energy.LIQUID_CASTER_ENERGY_USE,
                TransmatricsTileEntity.LIQUID_CASTER.getRegName(), LiquidCasterCraftingRegistry.getInstance());
    }

}
