package at.korti.transmatrics.registry;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import static at.korti.transmatrics.api.Constants.OreDictionaryEntry.*;
import static at.korti.transmatrics.api.Constants.TransmatricsBlock.ORE_BLOCK;
import static at.korti.transmatrics.api.Constants.TransmatricsItem.GEAR;
import static at.korti.transmatrics.api.Constants.TransmatricsItem.INGOT;
import static at.korti.transmatrics.api.Constants.TransmatricsItem.PLATE;

/**
 * Created by Korti on 02.05.2016.
 */
public class OreDicts {

    public static void registerOreDict() {
        // ingots
        OreDictionary.registerOre(INGOT_COPPER, new ItemStack(INGOT.getItem(), 1, 0));
        OreDictionary.registerOre(INGOT_TIN, new ItemStack(INGOT.getItem(), 1, 1));
        OreDictionary.registerOre(INGOT_SILVER, new ItemStack(INGOT.getItem(), 1, 2));
        OreDictionary.registerOre(INGOT_LEAD, new ItemStack(INGOT.getItem(), 1, 3));

        // plates
        OreDictionary.registerOre(PLATE_IRON, new ItemStack(PLATE.getItem(), 1, 0));
        OreDictionary.registerOre(PLATE_COPPER, new ItemStack(PLATE.getItem(), 1, 1));
        OreDictionary.registerOre(PLATE_TIN, new ItemStack(PLATE.getItem(), 1, 2));

        // gears
        OreDictionary.registerOre(GEAR_COPPER, new ItemStack(GEAR.getItem(), 1, 0));
        OreDictionary.registerOre(GEAR_TIN, new ItemStack(GEAR.getItem(), 1, 1));
        OreDictionary.registerOre(GEAR_SILVER, new ItemStack(GEAR.getItem(), 1, 2));
        OreDictionary.registerOre(GEAR_LEAD, new ItemStack(GEAR.getItem(), 1, 3));

        // ores
        OreDictionary.registerOre(ORE_COPPER, new ItemStack(ORE_BLOCK.getBlock(), 1, 0));
        OreDictionary.registerOre(ORE_TIN, new ItemStack(ORE_BLOCK.getBlock(), 1, 1));
        OreDictionary.registerOre(ORE_SILVER, new ItemStack(ORE_BLOCK.getBlock(), 1, 2));
        OreDictionary.registerOre(ORE_LEAD, new ItemStack(ORE_BLOCK.getBlock(), 1, 3));
    }

}
