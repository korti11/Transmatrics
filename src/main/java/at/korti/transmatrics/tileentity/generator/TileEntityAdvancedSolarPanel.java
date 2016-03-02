package at.korti.transmatrics.tileentity.generator;

import at.korti.transmatrics.api.Constants.Energy;

/**
 * Created by Korti on 28.02.2016.
 */
public class TileEntityAdvancedSolarPanel extends TileEntitySolarPanel {

    public TileEntityAdvancedSolarPanel() {
        super(Energy.ADVANCED_SOLAR_PANEL_GENERATE, Energy.ADVANCED_SOLAR_PANEL_CAPACITY, Energy.ADVANCED_SOLAR_PANEL_EXTRACTION);
    }
}
