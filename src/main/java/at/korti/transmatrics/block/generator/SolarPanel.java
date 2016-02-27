package at.korti.transmatrics.block.generator;

import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.block.ModBlockContainer;
import at.korti.transmatrics.tileentity.TileEntitySolarPanel;
import net.minecraft.block.material.Material;

/**
 * Created by Korti on 24.02.2016.
 */
public class SolarPanel extends ModBlockContainer {

    public SolarPanel() {
        super(Material.iron, TransmatricsBlock.SOLAR_PANEL.getRegName(), TileEntitySolarPanel.class);

        setBlockBounds(0f, 0f, 0f, 1f, 0.25f, 1f);
    }
}