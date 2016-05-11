package at.korti.transmatrics.modintegration.tconstruct.config;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

/**
 * Created by Korti on 12.05.2016.
 */
public class TConstructConfig {

    public static boolean canUseSmelteryRecipes;

    public static void loadConfig(File file) {
        Configuration configuration = new Configuration(file);
        configuration.load();
        canUseSmelteryRecipes = configuration.getBoolean("canUseSmelteryRecipes", "Tinkers' Construct", true,
                "If true the Magnetic Smeltery can use the smelting recipes from the TConstruct Smeltery.");
        configuration.save();
    }

}
