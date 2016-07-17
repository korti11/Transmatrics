package at.korti.transmatrics.util.helper;

import at.korti.transmatrics.api.crafting.ICraftingRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by Korti on 27.02.2016.
 */
public class ItemStackHelper {

    public static String[] getOreDictionaryNames(ItemStack stack) {
        if(stack != null) {
            int[] oreDictIds = OreDictionary.getOreIDs(stack);
            String[] oreDictNames = new String[oreDictIds.length];
            for (int i = 0; i < oreDictIds.length; i++) {
                oreDictNames[i] = OreDictionary.getOreName(oreDictIds[i]);
            }
            return oreDictNames;
        }
        return new String[0];
    }

    public static NBTTagCompound getCompound(ItemStack stack) {
        NBTTagCompound tagCompound;
        if ((tagCompound = stack.getTagCompound()) == null) {
            stack.setTagCompound(tagCompound = new NBTTagCompound());
        }
        return tagCompound;
    }
}
