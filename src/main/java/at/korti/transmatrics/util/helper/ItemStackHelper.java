package at.korti.transmatrics.util.helper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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

    public static NBTTagCompound getTagCompound(ItemStack stack) {
        NBTTagCompound compound;
        if ((compound = stack.getTagCompound()) == null) {
            stack.setTagCompound(compound = new NBTTagCompound());
        }
        return compound;
    }
}
