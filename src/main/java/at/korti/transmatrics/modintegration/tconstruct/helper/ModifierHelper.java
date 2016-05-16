package at.korti.transmatrics.modintegration.tconstruct.helper;

import at.korti.transmatrics.api.energy.IChargeable;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

import java.util.List;

/**
 * Created by Korti on 16.05.2016.
 */
public class ModifierHelper {

    public static IChargeable getChargeable(ItemStack stack) {
        List<ITrait> traits = ToolHelper.getTraits(stack);
        for (ITrait trait : traits) {
            if (trait instanceof IChargeable) {
                return (IChargeable) trait;
            }
        }
        return null;
    }

}
