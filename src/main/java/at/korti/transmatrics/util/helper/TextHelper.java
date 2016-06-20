package at.korti.transmatrics.util.helper;

import net.minecraft.util.StatCollector;

/**
 * Created by Korti on 25.02.2016.
 */
public class TextHelper {

    public static String localize(String input, Object... format) {
        return StatCollector.translateToLocalFormatted(input, format);
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

    public static String firstCharOfEachWordUppercase(String input) {
        String[] words = input.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        for (String word : words) {
            word = firstCharUppercase(word);
            stringBuilder.append(word);
            stringBuilder.append(" ");
        }
        return stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();
    }
}
