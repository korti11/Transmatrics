package at.korti.transmatrics.block;

import at.korti.transmatrics.tileentity.TileEntitySolarPanel;
import net.minecraft.block.material.Material;

/**
 * Created by Korti on 24.02.2016.
 */
public class SolarPanel extends ModBlockContainer{

    public SolarPanel() {
        super(Material.iron, "SolarPanel", TileEntitySolarPanel.class);
    }
}
