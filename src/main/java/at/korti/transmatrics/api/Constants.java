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

    public static class NBT {
        //Inventory
        public static final String SLOT = "slot";
        public static final String INVENTORY = "inventory";

        //Energy
        public static final String ENERGY = "energy";
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
    }

    public static class Tanks{
        //Lava Generator
        public static final int LAVA_GENERATOR_CAPACITY = 10000;
        public static final Fluid LAVA_GENERATOR_FLUID = FluidRegistry.LAVA;
    }

    public enum TransmatricsBlock {
        SOLAR_PANEL("SolarPanel"),
        ADVANCED_SOLAR_PANEL("AdvancedSolarPanel"),
        LAVA_GENERATOR("LavaGenerator");

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
        WRENCH("Wrench");

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

    public enum TransmatricsTileEntity{
        SOLAR_PANEL("TileSolarPanel"),
        ADVANCED_SOLAR_PANEL("TileAdvancedSolarPanel"),
        LAVA_GENERATOR("TileLavaGenerator");

        private final String regName;

        TransmatricsTileEntity(String regName) {
            this.regName = regName;
        }

        public String getRegName() {
            return regName;
        }
    }
}
