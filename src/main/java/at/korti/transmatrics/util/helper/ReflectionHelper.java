package at.korti.transmatrics.util.helper;

/**
 * Created by Korti on 31.05.2016.
 */
public class ReflectionHelper {

    public static boolean hasClass(Object object, Class clazz) {
        return clazz.isInstance(object);
    }

    public static <T> T castTo(Object object, Class<T> clazz) {
        return clazz.cast(object);
    }

}
