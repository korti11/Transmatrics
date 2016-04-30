package at.korti.transmatrics.util.helper;


import net.minecraft.util.text.translation.I18n;

/**
 * Created by Korti on 25.02.2016.
 */
public class TextHelper {

    public static String localize(String input, Object... format) {
        return I18n.translateToLocalFormatted(input, format);
    }

    public static String firstCharUppercase(String input) {
        if (input.length() < 2) {
            return input.toUpperCase();
        }
        String result;
        Character firstLetter = input.charAt(0);
        result = firstLetter.toString().toUpperCase() + input.substring(1);
        return result;
    }
}
