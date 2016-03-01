package at.korti.transmatrics.registry;

import at.korti.transmatrics.api.Constants.TransmatricsTileEntity;
import at.korti.transmatrics.tileentity.TileEntityAdvancedSolarPanel;
import at.korti.transmatrics.tileentity.TileEntityLavaGenerator;
import at.korti.transmatrics.tileentity.TileEntitySolarPanel;
import at.korti.transmatrics.tileentity.TileEntityThermalGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Korti on 27.02.2016.
 */
public final class TileEntities {

    public static void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntitySolarPanel.class, TransmatricsTileEntity.SOLAR_PANEL.getRegName());
        GameRegistry.registerTileEntity(TileEntityAdvancedSolarPanel.class, TransmatricsTileEntity.ADVANCED_SOLAR_PANEL.getRegName());
        GameRegistry.registerTileEntity(TileEntityLavaGenerator.class, TransmatricsTileEntity.LAVA_GENERATOR.getRegName());
        GameRegistry.registerTileEntity(TileEntityThermalGenerator.class, TransmatricsTileEntity.THERMAL_GENERATOR.getRegName());
    }

}
