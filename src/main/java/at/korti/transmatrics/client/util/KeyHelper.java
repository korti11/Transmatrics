package at.korti.transmatrics.client.util;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

/**
 * Created by Korti on 21.04.2016.
 */
@SideOnly(Side.CLIENT)
public class KeyHelper {

    @SideOnly(Side.CLIENT)
    public static boolean isShiftKeyDown() {
        return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
    }

}
