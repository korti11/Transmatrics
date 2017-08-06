package at.korti.transmatrics.api;

import cofh.redstoneflux.RedstoneFlux;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

/**
 * Created by Korti on 24.02.2016.
 */
public final class Constants {

    public static class Mod{
        public static final String MODID = "transmatrics";
        public static final String NAME = "Transmatrics";
        public static final String VERSION = "@VERSION@";

        public static final String COMMON_PROXY = "at.korti.transmatrics.proxy.CommonProxy";
        public static final String CLIENT_PROXY = "at.korti.transmatrics.proxy.ClientProxy";

        public static final String DEPENDENCIES = RedstoneFlux.VERSION_GROUP + "after:" + ModIntegrationIds.TCONSTRUCT;
    }

    public static class ModIntegrationIds{
        public static final String WAILA = "waila";
        public static final String TCONSTRUCT = "tconstruct";
    }

    public static class JEI{
        public static class Categories{
            public static final String PULVERIZER = Mod.MODID + ":Pulverizer";
            public static final String MAGNETIC_SMELTERY = Mod.MODID + ":MagneticSmeltery";
            public static final String LIQUID_CASTER = Mod.MODID + ":LiquidCaster";
            public static final String ALLOY_MIXER = Mod.MODID + ":AlloyMixer";
        }
    }

    public static class NBT {
        //Inventory
        public static final String SLOT = "slot";
        public static final String INVENTORY = "inventory";

        //Energy
        public static final String ENERGY = "energy";
        public static final String CAPACITY = "capacity";

        //Network
        public static final String NETWORK_CONNECTIONS = "network_connections";
        public static final String MAX_NETWORK_CONNECTIONS = "max_network_connections";
        public static final String NETWORK_X = "nx";
        public static final String NETWORK_Y = "ny";
        public static final String NETWORK_Z = "nz";
        public static final String NETWORK_BLOCK_NAME = "network_block_name";
        public static final String NETWORK_NODES = "network_nodes";
        public static final String CLEAR_STORED_NETWORK = "clear_stored_network";
        public static final String NETWORK_CONNECTED = "network_connected";

        //Controller
        public static final String IS_MASTER = "is_master";
        public static final String CONTROLLER_EXTENSIONS = "controller_extensions";
        public static final String EXT_CONTROLLER_X = "ecx";
        public static final String EXT_CONTROLLER_Y = "ecy";
        public static final String EXT_CONTROLLER_Z = "ecz";

        // Multi block structer
        public static final String IS_MASTER_NODE = "is_master_node";
        public static final String CONNECTED_EXTENSION_NODE = "connected_extension_node";
        public static final String EXTENSION_NODES = "extension_nodes";
        public static final String EXTENSION_NODE_X = "extension_node_x";
        public static final String EXTENSION_NODE_Y = "extension_node_y";
        public static final String EXTENSION_NODE_Z = "extension_node_z";

        //Crafting machine
        public static final String CRAFTING_TIME = "crafting_time";
        public static final String TOTAL_CRAFTING_TIME = "total_crafting_time";
        public static final String CRAFTING_EFFICIENCY = "crafting_efficiency";
        public static final String CRAFTING_TANKS = "crafting_tanks";

        //Colored item
        public static final String COLOR_LAYERS = "color_layers";
        public static final String COLOR_LAYER = "color_layer_%d";

        //Mode
        public static final String MODE_NAME = "mode_name";
        public static final String SELECTED_MODE = "selected_mode";

        //Dimension block pos
        public static final String POS_X = "pos_x";
        public static final String POS_Y = "pos_y";
        public static final String POS_Z = "pos_z";
        public static final String DIM_ID = "dim_id";

        //Quantum Bridge
        public static final String QUANTUM_BRIDGE_ONE = "quantum_bridge_one";
        public static final String QUANTUM_BRIDGE_TWO = "quantum_bridge_two";
        public static final String QUANTUM_BRIDGE_MAP_NAME = "quantum_bridge_map_name";

        //Quantum Bridge Mapper
        public static final String QUANTUM_BRIDGE_MAP_NAMES = "quantum_bridge_map_names";
        public static final String QUANTUM_BRIDGE_COUNT = "quantum_bridge_count";
    }

    public static class NetworkMessages{
        public static final String MAX_CONNECTIONS = "network.max_connections.message";
        public static final String MACHINES_CAN_NOT_CONNECTED = "network.machines_can_not_connected.message";
        public static final String SUCCESSFUL_CONNECTED = "network.successful_connected.message";
        public static final String NOT_CONNECTED = "network.not_connected.message";
        public static final String SUCCESSFUL_DISCONNECTED = "network.successful_disconnected.message";
        public static final String SUCCESSFUL_RECONNECTED = "network.successful_reconnected.message";
        public static final String CAN_NOT_CONNECTED = "network.can_not_connected.message";
        public static final String OUT_OF_RANGE = "network.out_of_range.message";
        public static final String SAME_NODE = "network.same_node.message";
        public static final String ALREADY_CONNECTED = "network.already_connected.message";

        public static final String CAN_NOT_HANDLE_PACKAGE = "network.can_not_handle_package.message";
    }

    public static class ToolTips{
        //EnergyBridge
        public static final String CONNECTION_NAME = "tooltip.connector.connection.name";
        public static final String CONNECTION_POS = "tooltip.connector.connection.pos";

        //Hammer
        public static final String HAMMER_USES_LEFT = "tooltip.hammer.uses.left";

        //Capacitor
        public static final String CAPACITOR_ENERGY_LABEL = "tooltip.capacitor.energy.label";
        public static final String CAPACITOR_ENERGY = "tooltip.capacitor.energy";

        //Mode
        public static final String SELECTED_MODE = "tooltip.selected.mode";
    }

    public static class Energy{
        //Solar Panel
        public static final int SOLAR_PANEL_GENERATE = 10;
        public static final int SOLAR_PANEL_CAPACITY = 10000;
        public static final int SOLAR_PANEL_EXTRACTION = 100;

        //Advanced Solar Panel
        public static final int ADVANCED_SOLAR_PANEL_GENERATE = 40;
        public static final int ADVANCED_SOLAR_PANEL_CAPACITY = 50000;
        public static final int ADVANCED_SOLAR_PANEL_EXTRACTION = 1000;

        //Lava Generator
        public static final int LAVA_GENERATOR_GENERATE = 20;
        public static final int LAVA_GENERATOR_FLUID_USE = 10;
        public static final int LAVA_GENERATOR_CAPACITY = 25000;
        public static final int LAVA_GENERATOR_EXTRACTION = 2500;

        //Windmill
        public static final int WINDMILL_MIN_GENERATE = 10;
        public static final int WINDMILL_MAX_GENERATE = 30;
        public static final int WINDMILL_MIN_HEIGHT = 80;
        public static final int WINDMILL_MAX_HEIGHT = 256;
        public static final int WINDMILL_CAPACITY = 20000;
        public static final int WINDMILL_EXTRACTION = 200;

        //Watermill
        public static final int WATERMILL_GENERATE = 10;
        public static final int WATERMILL_CAPACITY = 10000;
        public static final int WATERMILL_EXTRACTION = 100;

        //Thermal Generator
        public static final int THERMAL_GENERATOR_MIN_GENERATE = 50;
        public static final int THERMAL_GENERATOR_MAX_GENERATE = 500;
        public static final int THERMAL_GENERATOR_MIN_HEIGHT = 64;
        public static final int THERMAL_GENERATOR_MAX_HEIGHT = 12;
        public static final int THERMAL_GENERATOR_CAPACITY = 50000;
        public static final int THERMAL_GENERATOR_EXTRACTION = 1000;

        //Controller
        public static final int CONTROLLER_CAPACITY = 100000;
        public static final int CONTROLLER_TRANSFER = 10000;

        //Quantum Bridge
        public static final int QUANTUM_BRIDGE_CAPACITY = 75000;
        public static final int QUANTUM_BRIDGE_TRANSFER = 7500;
        public static final int QUANTUM_BRIDGE_ENERGY_USE = 25;

        //Pulverizer
        public static final int PULVERIZER_CAPACITY = 50000;
        public static final int PULVERIZER_RECEIVE = 5000;
        public static final int PULVERIZER_ENERGY_USE = 25;

        //Powered Furnace
        public static final int POWERED_FURNACE_CAPACITY = 50000;
        public static final int POWERED_FURNACE_RECEIVE = 5000;
        public static final int POWERED_FURNACE_ENERGY_USE = 25;

        //Magnetic Smeltery
        public static final int MAGNETIC_SMELTERY_CAPACITY = 50000;
        public static final int MAGNETIC_SMELTERY_RECEIVE = 5000;
        public static final int MAGNETIC_SMELTERY_ENERGY_USE = 25;

        //Liquid Caster
        public static final int LIQUID_CASTER_CAPACITY = 50000;
        public static final int LIQUID_CASTER_RECEIVE = 5000;
        public static final int LIQUID_CASTER_ENERGY_USE = 25;

        //Alloy Mixer
        public static final int ALLOY_MIXER_CAPACITY = 50000;
        public static final int ALLOY_MIXER_RECEIVE = 5000;
        public static final int ALLOY_MIXER_ENERGY_USE = 25;

        //Charger
        public static final int CHARGER_CAPACITY = 50000;
        public static final int CHARGER_RECEIVE = 5000;
        public static final int CHARGER_ENERGY_USE = 5;

        //RF EnergyBridge
        public static final int RF_CONVERTER_CAPACITY = 50000;
        public static final int RF_CONVERTER_TRANSFER = 5000;

        //Capacitors
        public static final int DISCHARGE_RATE = 10;
        public static final int LEAD_CAPACITOR_CAPACITY = 12500;
        public static final int INVAR_CAPACITOR_CAPACITY = 25000;
        public static final int ELECTRUM_CAPACITOR_CAPACITY = 50000;

        //Storage Components
        public static final int SMALL_STORAGE_COMPONENT_CAPACITY = 100000;
        public static final int SMALL_STORAGE_COMPONENT_TRANSFER = 5000;
        public static final int MEDIUM_STORAGE_COMPONENT_CAPACITY = 500000;
        public static final int MEDIUM_STORAGE_COMPONENT_TRANSFER = 10000;
        public static final int LARGE_STORAGE_COMPONENT_CAPACITY = (int) (2.5 * Math.pow(10, 6));
        public static final int LARGE_STORAGE_COMPONENT_TRANSFER = 25000;
        public static final int QUANTUM_STORAGE_COMPONENT_CAPACITY = (int) Math.pow(10, 9);
        public static final int QUANTUM_STORAGE_COMPONENT_TRANSFER = 50000;

        //Efficiency
        public static final int EFFICIENCY_DIVIDER = 10000;
    }

    public static class Tanks{
        //Lava Generator
        public static final int LAVA_GENERATOR_CAPACITY = 10000;
        public static final Fluid LAVA_GENERATOR_FLUID = FluidRegistry.LAVA;
    }

    public static class Network{
        //Small Switch
        public static final int SMALL_SWITCH_MAX_CONNECTIONS = 8;
        public static final boolean SMALL_SWITCH_MACHINES_CONNECT = true;
        public static final int SMALL_SWITCH_RANGE = 16;

        //Medium Switch
        public static final int MEDIUM_SWITCH_MAX_CONNECTIONS = 16;
        public static final boolean MEDIUM_SWITCH_MACHINES_CONNECT = true;
        public static final int MEDIUM_SWITCH_RANGE = 32;

        //Large Switch
        public static final int LARGE_SWITCH_MAX_CONNECTIONS = 32;
        public static final boolean LARGE_SWITCH_MACHINES_CONNECT = false;
        public static final int LARGE_SWITCH_RANGE = 64;

        //Controller
        public static final int CONTROLLER_MAX_CONNECTIONS = 32;
        public static final boolean CONTROLLER_MACHINES_CONNECT = false;
        public static final int CONTROLLER_RANGE = 32;
    }

    public static class GuiIds{
        // Transmatrics
        public static final int PULVERIZER_GUI_ID = 0;
        public static final int POWERED_FURNACE_GUI_ID = 1;
        public static final int MAGNETIC_SMELTERY_GUI_ID = 2;
        public static final int LIQUID_CASTER_GUI_ID = 3;
        public static final int LAVA_GENERATOR_GUI_ID = 4;
        public static final int ALLOY_MIXER_GUI_ID = 5;
        public static final int CHARGER_GUI_ID = 6;
    }

    public static class OreDictionaryEntry{
        // ingots
        public static final String INGOT_COPPER = "ingotCopper";
        public static final String INGOT_TIN = "ingotTin";
        public static final String INGOT_SILVER = "ingotSilver";
        public static final String INGOT_LEAD = "ingotLead";
        public static final String INGOT_NICKEL = "ingotNickel";
        public static final String INGOT_INVAR = "ingotInvar";
        public static final String INGOT_ELECTRUM = "ingotElectrum";

        // dusts
        public static final String DUST_IRON = "dustIron";
        public static final String DUST_GOLD = "dustGold";
        public static final String DUST_COPPER = "dustCopper";
        public static final String DUST_TIN = "dustTin";
        public static final String DUST_SILVER = "dustSilver";
        public static final String DUST_LEAD = "dustLead";
        public static final String DUST_NICKEL = "dustNickel";
        public static final String DUST_INVAR = "dustInvar";
        public static final String DUST_ELECTRUM = "dustElectrum";

        // plates
        public static final String PLATE = "plate";
        public static final String PLATE_IRON = "plateIron";
        public static final String PLATE_COPPER = "plateCopper";
        public static final String PLATE_TIN = "plateTin";

        // gears
        public static final String GEAR_COPPER = "gearCopper";
        public static final String GEAR_TIN = "gearTin";
        public static final String GEAR_SILVER = "gearSilver";
        public static final String GEAR_LEAD = "gearLead";

        // ores
        public static final String ORE_COPPER = "oreCopper";
        public static final String ORE_TIN = "oreTin";
        public static final String ORE_SILVER = "oreSilver";
        public static final String ORE_LEAD = "oreLead";
        public static final String ORE_NICKEL = "oreNickel";

        // casts
        public static final String CAST_CRAFTING = "tmCastCrafting";
    }

    public enum TransmatricsBlock {
        SOLAR_PANEL("solar_panel"),
        ADVANCED_SOLAR_PANEL("advanced_solar_panel"),
        LAVA_GENERATOR("lava_generator"),
        THERMAL_GENERATOR("thermal_generator"),
        WINDMILL("windmill"),
        WATERMILL("watermill"),
        SMALL_SWITCH("small_switch"),
        MEDIUM_SWITCH("medium_switch"),
        LARGE_SWITCH("large_switch"),
        CONTROLLER("controller"),
        PULVERIZER("pulverizer"),
        POWERED_FURNACE("powered_furnace"),
        MAGNETIC_SMELTERY("magnetic_smeltery"),
        LIQUID_CASTER("liquid_caster"),
        ALLOY_MIXER("alloy_mixer"),
        CHARGER("charger"),
        ORE_BLOCK("ore"),
        MACHINE_CASING("machine_casing"),
        ENERGY_BRIDGE("energy_bridge"),
        QUANTUM_BRIDGE("quantum_bridge"),
        SMALL_STORAGE_COMPONENT("small_storage_component"),
        MEDIUM_STORAGE_COMPONENT("medium_storage_component"),
        LARGE_STORAGE_COMPONENT("large_storage_component"),
        QUANTUM_STORAGE_COMPONENT("quantum_storage_component");

        private final String regName;

        TransmatricsBlock(String regName) {
            this.regName = regName;
        }

        public Block getBlock() {
            return TransmatricsApi.getBlock(regName);
        }

        public String getRegName() {
            return regName;
        }
    }

    public enum TransmatricsItem {
        WRENCH("wrench"),
        CONNECTOR("connector"),
        PULVERIZED_DUST("pulverized_dust"),
        INGOT("ingot"),
        GEAR("gear"),
        CAST("cast"),
        ELECTRONICS("electronics"),
        PLATE("plate"),
        HAMMER("hammer"),
        LEAD_CAPACITOR("lead_capacitor"),
        INVAR_CAPACITOR("invar_capacitor"),
        ELECTRUM_CAPACITOR("electrum_capacitor");

        private final String regName;

        TransmatricsItem(String regName) {
            this.regName = regName;
        }

        public Item getItem() {
            return TransmatricsApi.getItem(regName);
        }

        public String getRegName() {
            return regName;
        }
    }

    public enum TransmatricsTileEntity {
        SOLAR_PANEL("TMTileSolarPanel"),
        ADVANCED_SOLAR_PANEL("TMTileAdvancedSolarPanel"),
        LAVA_GENERATOR("TMTileLavaGenerator"),
        THERMAL_GENERATOR("TMTileThermalGenerator"),
        WINDMILL("TMTileWindmill"),
        WATERMILL("TMTileWatermill"),
        SMALL_SWITCH("TMTileSmallSwitch"),
        MEDIUM_SWITCH("TMTileMediumSwitch"),
        LARGE_SWITCH("TMTileLargeSwitch"),
        CONTROLLER("TMTileController"),
        PULVERIZER("TMTilePulverizer"),
        POWERED_FURNACE("TMTilePoweredFurnace"),
        MAGNETIC_SMELTERY("TMTileMagneticSmeltery"),
        LIQUID_CASTER("TMTileLiquidCaster"),
        ALLOY_MIXER("TMTileAlloyMixer"),
        CHARGER("TMTileCharger"),
        ENERGY_BRIDGE("TMTileEnergyBridge"),
        QUANTUM_BRIDGE("TMTileQuantumBridge"),
        SMALL_STORAGE_COMPONENT("TMTileSmallStorageComponent"),
        MEDIUM_STORAGE_COMPONENT("TMTileMediumStorageComponent"),
        LARGE_STORAGE_COMPONENT("TMTileLargeStorageComponent"),
        QUANTUM_STORAGE_COMPONENT("TMTileQuantumStorageComponent");

        private final String regName;

        TransmatricsTileEntity(String regName) {
            this.regName = regName;
        }

        public String getRegName() {
            return regName;
        }
    }

    public enum TransmatricsFluid{
        MOLTEN_IRON("iron"),
        MOLTEN_GOLD("gold"),
        MOLTEN_COPPER("copper"),
        MOLTEN_TIN("tin"),
        MOLTEN_SILVER("silver"),
        MOLTEN_LEAD("lead"),
        MOLTEN_NICKEL("nickel"),
        MOLTEN_INVAR("invar"),
        MOLTEN_ELECTRUM("electrum");

        private final String regName;

        TransmatricsFluid(String regName) {
            this.regName = regName;
        }

        public Fluid getFluid() {
            return TransmatricsApi.getFluid(regName);
        }

        public String getRegName() {
            return regName;
        }
    }
}
