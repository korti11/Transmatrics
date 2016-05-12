package at.korti.transmatrics.modintegration.tconstruct.tileentity;

import at.korti.transmatrics.api.Constants.Energy;
import at.korti.transmatrics.api.Constants.TransmatricsTileEntity;
import at.korti.transmatrics.api.crafting.IFluidCraftingRegistry;
import at.korti.transmatrics.modintegration.tconstruct.crafting.AlloyMixerCraftingRegistry;
import at.korti.transmatrics.tileentity.TileEntityFluidCraftingMachine;

/**
 * Created by Korti on 12.05.2016.
 */
public class TileEntityAlloyMixer extends TileEntityFluidCraftingMachine {

    public TileEntityAlloyMixer() {
        super(Energy.ALLOY_MIXER_CAPACITY, Energy.ALLOY_MIXER_RECEIVE, Energy.ALLOY_MIXER_ENERGY_USE,
                TransmatricsTileEntity.ALLOY_MIXER.getRegName(), AlloyMixerCraftingRegistry.getInstance());
    }

    @Override
    protected boolean isFluidInput() {
        return true;
    }
}
