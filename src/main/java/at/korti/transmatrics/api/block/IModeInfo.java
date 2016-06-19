package at.korti.transmatrics.api.block;

import net.minecraft.util.EnumChatFormatting;

/**
 * Created by Korti on 17.06.2016.
 */
public interface IModeInfo<E extends Enum> {

    String getCurrentModeName();

    EnumChatFormatting getColorForMode(E mode);

}
