package at.korti.transmatrics.api;

import net.minecraft.block.Block;

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

    public static class NBT{
        public static final String SLOT = "slot";
        public static final String INVENTORY = "inventory";
    }

    public enum TransmatricsBlock {
        SOLAR_PANEL("SolarPanel");

        public final String regName;

        private TransmatricsBlock(String regName) {
            this.regName = regName;
        }

        public Block getBlock() {
            return TransmatricsApi.getBlock(regName);
        }
    }
}
