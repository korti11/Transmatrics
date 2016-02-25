package at.korti.transmatrics.util.helper;

import net.minecraft.util.StatCollector;

/**
 * Created by Korti on 25.02.2016.
 */
public class TextHelper {

    public static String localize(String input, Object... format) {
        return StatCollector.translateToLocalFormatted(input, format);
    }
}
