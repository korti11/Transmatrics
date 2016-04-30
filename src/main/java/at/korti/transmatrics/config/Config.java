package at.korti.transmatrics.config;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

/**
 * Created by Korti on 26.04.2016.
 */
public class Config {

    public static void loadConfig(File file) {
        Configuration configuration = new Configuration(file);
        configuration.load();

        configuration.save();
    }

}
