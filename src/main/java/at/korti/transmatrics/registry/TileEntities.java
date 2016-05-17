package at.korti.transmatrics.registry;

import at.korti.transmatrics.config.Config;
import at.korti.transmatrics.tileentity.crafting.*;
import at.korti.transmatrics.tileentity.energy.TileEntityCharger;
import at.korti.transmatrics.tileentity.generator.*;
import at.korti.transmatrics.tileentity.network.TileEntityController;
import at.korti.transmatrics.tileentity.network.TileEntityLargeSwitch;
import at.korti.transmatrics.tileentity.network.TileEntityMediumSwitch;
import at.korti.transmatrics.tileentity.network.TileEntitySmallSwitch;

import static at.korti.transmatrics.api.Constants.TransmatricsTileEntity.*;
import static net.minecraftforge.fml.common.registry.GameRegistry.registerTileEntity;

/**
 * Created by Korti on 27.02.2016.
 */
public final class TileEntities {

    public static void registerTileEntities() {
        registerTileEntity(TileEntitySolarPanel.class, SOLAR_PANEL.getRegName());
        registerTileEntity(TileEntityAdvancedSolarPanel.class, ADVANCED_SOLAR_PANEL.getRegName());
        registerTileEntity(TileEntityLavaGenerator.class, LAVA_GENERATOR.getRegName());
        registerTileEntity(TileEntityThermalGenerator.class, THERMAL_GENERATOR.getRegName());
        registerTileEntity(TileEntityWindmill.class, WINDMILL.getRegName());
        registerTileEntity(TileEntityWatermill.class, WATERMILL.getRegName());
        registerTileEntity(TileEntitySmallSwitch.class, SMALL_SWITCH.getRegName());
        registerTileEntity(TileEntityMediumSwitch.class, MEDIUM_SWITCH.getRegName());
        registerTileEntity(TileEntityLargeSwitch.class, LARGE_SWITCH.getRegName());
        registerTileEntity(TileEntityController.class, CONTROLLER.getRegName());
        registerTileEntity(TileEntityPulverizer.class, PULVERIZER.getRegName());
        registerTileEntity(TileEntityPoweredFurnace.class, POWERED_FURNACE.getRegName());
        registerTileEntity(TileEntityMagneticSmeltery.class, MAGNETIC_SMELTERY.getRegName());
        registerTileEntity(TileEntityLiquidCaster.class, LIQUID_CASTER.getRegName());
        registerTileEntity(TileEntityCharger.class, CHARGER.getRegName());
    }

}
