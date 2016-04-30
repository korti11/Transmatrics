package at.korti.transmatrics.block.generator;

import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.tileentity.generator.TileEntityAdvancedSolarPanel;
import net.minecraft.block.material.Material;

/**
 * Created by Korti on 28.02.2016.
 */
public class AdvancedSolarPanel extends SolarPanel {

    public AdvancedSolarPanel() {
        super(Material.IRON, TransmatricsBlock.ADVANCED_SOLAR_PANEL.getRegName(), TileEntityAdvancedSolarPanel.class);
    }
}
