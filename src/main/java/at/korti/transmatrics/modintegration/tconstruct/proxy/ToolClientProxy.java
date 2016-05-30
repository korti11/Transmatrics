package at.korti.transmatrics.modintegration.tconstruct.proxy;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.modintegration.tconstruct.TConstruct;
import at.korti.transmatrics.modintegration.tconstruct.tools.modifier.ModEnergetic;
import at.korti.transmatrics.util.helper.TextHelper;
import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.common.ClientProxy;
import slimeknights.tconstruct.library.modifiers.Modifier;

/**
 * Created by Korti on 17.05.2016.
 */
public class ToolClientProxy extends ClientProxy {

    private static final String LOC_MODIFIER = "models/integration/tconstruct/modifier/%s";

    @Override
    public void registerModels() {
        registerModifierModel(ModEnergetic.getBaseIdentifier(), TConstruct.leadModEnergetic.getIdentifier());
        registerModifierModel(ModEnergetic.getBaseIdentifier(), TConstruct.invarModEnergetic.getIdentifier());
        registerModifierModel(ModEnergetic.getBaseIdentifier(), TConstruct.electrumModEnergetic.getIdentifier());
    }

    private void registerModifierModel(Modifier modifier) {
        modifierLoader.registerModifierFile(modifier.getIdentifier(),
                new ResourceLocation(Constants.Mod.MODID,
                        String.format(LOC_MODIFIER, TextHelper.firstCharUppercase(modifier.getIdentifier()))));
    }

    private void registerModifierModel(String baseIdentifier, String identifier) {
        modifierLoader.registerModifierFile(identifier,
                new ResourceLocation(Constants.Mod.MODID,
                        String.format(LOC_MODIFIER, TextHelper.firstCharUppercase(baseIdentifier))));
    }
}
