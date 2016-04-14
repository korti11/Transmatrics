package at.korti.transmatrics.api;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

/**
 * Created by Korti on 24.02.2016.
 */
public final class Constants {

    public static class Mod{
        public static final String MODID = "Transmatrics";
        public static final String NAME = "Transmatrics";
        public static final String VERSION = "@VERSION@";

        public static final String COMMON_PROXY = "at.korti.transmatrics.proxy.CommonProxy";
        public static final String CLIENT_PROXY = "at.korti.transmatrics.proxy.ClientProxy";

        public static final String CREATIVE_TAB_LABEL = "transmatrics";
    }

    public static class ModIntegrationIds{
        public static final String WAILA = "Waila";
        public static final String JEI = "JEI";
    }

    public static class JEI{
        public static class Categories{
            public static final String PULVERIZER = Mod.MODID + ":Pulverizer";
            public static final String MAGNETIC_SMELTERY = Mod.MODID + ":MagneticSmeltery";
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
        public static final String CONNECTION_PRIORITY = "connection_priority";
        public static final String CONTROLLER_X = "cx";
        public static final String CONTROLLER_Y = "cy";
        public static final String CONTROLLER_Z = "cz";

        //Controller
        public static final String IS_MASTER = "is_master";
        public static final String CONTROLLER_EXTENSIONS = "controller_extensions";
        public static final String EXT_CONTROLLER_X = "ecx";
        public static final String EXT_CONTROLLER_Y = "ecy";
        public static final String EXT_CONTROLLER_Z = "ecz";

        //Crafting machine
        public static final String CRAFTING_TIME = "crafting_time";
        public static final String TOTAL_CRAFTING_TIME = "total_crafting_time";
        public static final String CRAFTING_EFFICIENCY = "crafting_efficiency";
        public static final String CRAFTING_TANKS = "crafting_tanks";
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
    }

    public static class ToolTips{
        //Connector
        public static final String CONNECTION_NAME = "tooltip.connector.connection.name";
        public static final String CONNECTION_POS = "tooltip.connector.connection.pos";
    }

    public static class Energy{
        //Solar Panel
        public static final int SOLAR_PANEL_GENERATE = 1;
        public static final int SOLAR_PANEL_CAPACITY = 1000;
        public static final int SOLAR_PANEL_EXTRACTION = 10;

        //Advanced Solar Panel
        public static final int ADVANCED_SOLAR_PANEL_GENERATE = 10;
        public static final int ADVANCED_SOLAR_PANEL_CAPACITY = 5000;
        public static final int ADVANCED_SOLAR_PANEL_EXTRACTION = 100;

        //Lava Generator
        public static final int LAVA_GENERATOR_GENERATE = 3;
        public static final int LAVA_GENERATOR_FLUID_USE = 10;
        public static final int LAVA_GENERATOR_CAPACITY = 2500;
        public static final int LAVA_GENERATOR_EXTRACTION = 25;

        //Windmill
        public static final int WINDMILL_MIN_GENERATE = 5;
        public static final int WINDMILL_MAX_GENERATE = 20;
        public static final int WINDMILL_MIN_HEIGHT = 80;
        public static final int WINDMILL_MAX_HEIGHT = 256;
        public static final int WINDMILL_CAPACITY = 2000;
        public static final int WINDMILL_EXTRACTION = 20;

        //Watermill
        public static final int WATERMILL_GENERATE = 1;
        public static final int WATERMILL_CAPACITY = 1000;
        public static final int WATERMILL_EXTRACTION = 10;

        //Thermal Generator
        public static final int THERMAL_GENERATOR_MIN_GENERATE = 10;
        public static final int THERMAL_GENERATOR_MAX_GENERATE = 100;
        public static final int THERMAL_GENERATOR_MIN_HEIGHT = 64;
        public static final int THERMAL_GENERATOR_MAX_HEIGHT = 12;
        public static final int THERMAL_GENERATOR_CAPACITY = 10000;
        public static final int THERMAL_GENERATOR_EXTRACTION = 100;

        //Hydrogen Generator
        public static final int HYDROGEN_GENERATOR_GENERATE = 75;
        public static final int HYDROGEN_GENERATOR_FLUID_USE = 100;
        public static final int HYDROGEN_GENERATOR_CAPACITY = 10000;
        public static final int HYDROGEN_GENERATOR_EXTRACTION = 75;

        //Small Switch
        public static final int SMALL_SWITCH_CAPACITY = 1250;
        public static final int SMALL_SWITCH_TRANSFER = 125;

        //Medium Switch
        public static final int MEDIUM_SWITCH_CAPACITY = 2500;
        public static final int MEDIUM_SWITCH_TRANSFER = 250;

        //Large Switch
        public static final int LARGE_SWITCH_CAPACITY = 5000;
        public static final int LARGE_SWITCH_TRANSFER = 500;

        //Controller
        public static final int CONTROLLER_CAPACITY = 20000;
        public static final int CONTROLLER_TRANSFER = 1000;

        //Pulverizer
        public static final int PULVERIZER_CAPACITY = 5000;
        public static final int PULVERIZER_RECEIVE = 500;
        public static final int PULVERIZER_ENERGY_USE = 25;

        //Powered Furnace
        public static final int POWERED_FURNACE_CAPACITY = 5000;
        public static final int POWERED_FURNACE_RECEIVE = 500;
        public static final int POWERED_FURNACE_ENERGY_USE = 25;

        //Magnetic Smeltery
        public static final int MAGNETIC_SMELTERY_CAPACITY = 5000;
        public static final int MAGNETIC_SMELTERY_RECEIVE = 500;
        public static final int MAGNETIC_SMELTERY_ENERGY_USE = 25;

        //Liquid Caster
        public static final int LIQUID_CASTER_CAPACITY = 5000;
        public static final int LIQUID_CASTER_RECEIVE = 500;
        public static final int LIQUID_CASTER_ENERGY_USE = 25;
    }

    public static class Tanks{
        //Lava Generator
        public static final int LAVA_GENERATOR_CAPACITY = 10000;
        public static final Fluid LAVA_GENERATOR_FLUID = FluidRegistry.LAVA;

        //Hydrogen Generator
        public static final int HYDROGEN_GENERATOR_CAPACITY = 10000;
        public static final Fluid HYDROGEN_GENERATOR_FLUID = TransmatricsFluid.HYDROGEN_GAS.getFluid();
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
        public static final int PULVERIZER_GUI_ID = 0;
        public static final int POWERED_FURNACE_GUI_ID = 1;
        public static final int MAGNETIC_SMELTERY_GUI_ID = 2;
        public static final int LIQUID_CASTER_GUI_ID = 3;
    }

    public enum TransmatricsBlock {
        SOLAR_PANEL("SolarPanel"),
        ADVANCED_SOLAR_PANEL("AdvancedSolarPanel"),
        LAVA_GENERATOR("LavaGenerator"),
        THERMAL_GENERATOR("ThermalGenerator"),
        HYDROGEN_GENERATOR("HydrogenGenerator"),
        WINDMILL("Windmill"),
        WATERMILL("Watermill"),
        SMALL_SWITCH("SmallSwitch"),
        MEDIUM_SWITCH("MediumSwitch"),
        LARGE_SWITCH("LargeSwitch"),
        CONTROLLER("Controller"),
        PULVERIZER("Pulverizer"),
        POWERED_FURNACE("PoweredFurnace"),
        MAGNETIC_SMELTERY("MagneticSmeltery"),
        LIQUID_CASTER("LiquidCaster"),
        ORE_BLOCK("Ore");

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
        WRENCH("Wrench"),
        CONNECTOR("Connector"),
        PULVERIZED_DUST("PulverizedDust"),
        INGOT("Ingot"),
        GEAR("Gear"),
        CAST("Cast");

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
        SOLAR_PANEL("TileSolarPanel"),
        ADVANCED_SOLAR_PANEL("TileAdvancedSolarPanel"),
        LAVA_GENERATOR("TileLavaGenerator"),
        THERMAL_GENERATOR("TileThermalGenerator"),
        HYDROGEN_GENERATOR("TileHydrogenGenerator"),
        WINDMILL("TileWindmill"),
        WATERMILL("TileWatermill"),
        SMALL_SWITCH("TileSmallSwitch"),
        MEDIUM_SWITCH("TileMediumSwitch"),
        LARGE_SWITCH("TileLargeSwitch"),
        CONTROLLER("TileController"),
        PULVERIZER("TilePulverizer"),
        POWERED_FURNACE("TilePoweredFurnace"),
        MAGNETIC_SMELTERY("TileMagneticSmeltery"),
        LIQUID_CASTER("TileLiquidCaster");

        private final String regName;

        TransmatricsTileEntity(String regName) {
            this.regName = regName;
        }

        public String getRegName() {
            return regName;
        }
    }

    public enum TransmatricsFluid{
        HYDROGEN_GAS("HydrogenGas"),
        MOLTEN_COPPER("MoltenCopper"),
        MOLTEN_TIN("MoltenTin"),
        MOLTEN_SILVER("MoltenSilver"),
        MOLTEN_LEAD("MoltenLead");

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
