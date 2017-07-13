package at.korti.transmatrics.registry;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.OreDictionaryEntry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import static at.korti.transmatrics.api.Constants.OreDictionaryEntry.*;
import static at.korti.transmatrics.api.Constants.TransmatricsBlock.ORE_BLOCK;
import static at.korti.transmatrics.api.Constants.TransmatricsItem.*;
import static at.korti.transmatrics.api.Constants.TransmatricsItem.PLATE;

/**
 * Created by Korti on 02.05.2016.
 */
public class OreDicts {

    public static void registerOreDictItems() {
        // ingots
        OreDictionary.registerOre(INGOT_COPPER, new ItemStack(INGOT.getItem(), 1, 0));
        OreDictionary.registerOre(INGOT_TIN, new ItemStack(INGOT.getItem(), 1, 1));
        OreDictionary.registerOre(INGOT_SILVER, new ItemStack(INGOT.getItem(), 1, 2));
        OreDictionary.registerOre(INGOT_LEAD, new ItemStack(INGOT.getItem(), 1, 3));
        OreDictionary.registerOre(INGOT_NICKEL, new ItemStack(INGOT.getItem(), 1, 4));
        OreDictionary.registerOre(INGOT_INVAR, new ItemStack(INGOT.getItem(), 1, 5));
        OreDictionary.registerOre(INGOT_ELECTRUM, new ItemStack(INGOT.getItem(), 1, 6));

        // dusts
        OreDictionary.registerOre(DUST_IRON, new ItemStack(PULVERIZED_DUST.getItem(), 1, 0));
        OreDictionary.registerOre(DUST_GOLD, new ItemStack(PULVERIZED_DUST.getItem(), 1, 1));
        OreDictionary.registerOre(DUST_COPPER, new ItemStack(PULVERIZED_DUST.getItem(), 1, 2));
        OreDictionary.registerOre(DUST_TIN, new ItemStack(PULVERIZED_DUST.getItem(), 1, 3));
        OreDictionary.registerOre(DUST_SILVER, new ItemStack(PULVERIZED_DUST.getItem(), 1, 4));
        OreDictionary.registerOre(DUST_LEAD, new ItemStack(PULVERIZED_DUST.getItem(), 1, 5));
        OreDictionary.registerOre(DUST_NICKEL, new ItemStack(PULVERIZED_DUST.getItem(), 1, 6));
        OreDictionary.registerOre(DUST_INVAR, new ItemStack(PULVERIZED_DUST.getItem(), 1, 7));
        OreDictionary.registerOre(DUST_ELECTRUM, new ItemStack(PULVERIZED_DUST.getItem(), 1, 8));

        // plates
        OreDictionary.registerOre(OreDictionaryEntry.PLATE, new ItemStack(PLATE.getItem(), 1, 0));
        OreDictionary.registerOre(OreDictionaryEntry.PLATE, new ItemStack(PLATE.getItem(), 1, 1));
        OreDictionary.registerOre(OreDictionaryEntry.PLATE, new ItemStack(PLATE.getItem(), 1, 2));
        OreDictionary.registerOre(PLATE_IRON, new ItemStack(PLATE.getItem(), 1, 0));
        OreDictionary.registerOre(PLATE_COPPER, new ItemStack(PLATE.getItem(), 1, 1));
        OreDictionary.registerOre(PLATE_TIN, new ItemStack(PLATE.getItem(), 1, 2));

        // gears
        OreDictionary.registerOre(GEAR_COPPER, new ItemStack(GEAR.getItem(), 1, 0));
        OreDictionary.registerOre(GEAR_TIN, new ItemStack(GEAR.getItem(), 1, 1));
        OreDictionary.registerOre(GEAR_SILVER, new ItemStack(GEAR.getItem(), 1, 2));
        OreDictionary.registerOre(GEAR_LEAD, new ItemStack(GEAR.getItem(), 1, 3));
    }

    public static void registerOreDictBlocks() {
        // ores
        OreDictionary.registerOre(ORE_COPPER, new ItemStack(ORE_BLOCK.getBlock(), 1, 0));
        OreDictionary.registerOre(ORE_TIN, new ItemStack(ORE_BLOCK.getBlock(), 1, 1));
        OreDictionary.registerOre(ORE_SILVER, new ItemStack(ORE_BLOCK.getBlock(), 1, 2));
        OreDictionary.registerOre(ORE_LEAD, new ItemStack(ORE_BLOCK.getBlock(), 1, 3));
        OreDictionary.registerOre(ORE_NICKEL, new ItemStack(ORE_BLOCK.getBlock(), 1, 4));
    }

}
