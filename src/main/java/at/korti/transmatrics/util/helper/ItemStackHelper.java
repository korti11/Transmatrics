package at.korti.transmatrics.util.helper;

import at.korti.transmatrics.api.crafting.ICraftingRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by Korti on 27.02.2016.
 */
public class ItemStackHelper {

    public static String[] getOreDictionaryNames(ItemStack stack) {
        int[] oreDictIds = OreDictionary.getOreIDs(stack);
        String[] oreDictNames = new String[oreDictIds.length];
        for (int i = 0; i < oreDictIds.length; i++) {
            oreDictNames[i] = OreDictionary.getOreName(oreDictIds[i]);
        }
        return oreDictNames;
    }
}
