package at.korti.transmatrics.modintegration.tconstruct.tools.modifier;

import at.korti.transmatrics.api.Constants.Mod;
import at.korti.transmatrics.api.Constants.TConstructModifiers;
import at.korti.transmatrics.util.helper.TextHelper;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;

/**
 * Created by Korti on 16.05.2016.
 */
public class ModEnergetic extends ModifierTrait {

    public ModEnergetic() {
        super(TConstructModifiers.ENERGETIC_IDENTIFIER, TConstructModifiers.ENERGETIC_COLOR);
    }

    @Override
    public String getLocalizedName() {
        String key = String.format("modifier.%s.%s", Mod.MODID, getIdentifier());
        return TextHelper.localize(key);
    }
}
