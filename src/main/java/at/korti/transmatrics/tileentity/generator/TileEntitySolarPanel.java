package at.korti.transmatrics.tileentity.generator;

import at.korti.transmatrics.api.Constants.Energy;
import at.korti.transmatrics.tileentity.TileEntityGenerator;

/**
 * Created by Korti on 25.02.2016.
 */
public class TileEntitySolarPanel extends TileEntityGenerator {

    public TileEntitySolarPanel() {
        this(Energy.SOLAR_PANEL_GENERATE, Energy.SOLAR_PANEL_CAPACITY, Energy.SOLAR_PANEL_EXTRACTION);
    }

    protected TileEntitySolarPanel(int energyPerTick, int capacity, int maxExtract) {
        super(energyPerTick, capacity, maxExtract);
    }

    @Override
    public boolean canProduceEnergy() {
        if (worldObj.provider.isDaytime() && worldObj.canBlockSeeSky(this.getPos()) && super.canProduceEnergy()) {
            return true;
        }
        return false;
    }
}
