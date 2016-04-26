package at.korti.transmatrics.config;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

/**
 * Created by Korti on 26.04.2016.
 */
public class Config {

    public static boolean useCircuitSystem;

    public static void loadConfig(File file) {
        Configuration configuration = new Configuration(file);
        configuration.load();

        useCircuitSystem = configuration.getBoolean("UseCircuitSystem", "Systems", false,
                "The circuit system can be used to improve the machines over the crafting recipe.");

        configuration.save();
    }

}
