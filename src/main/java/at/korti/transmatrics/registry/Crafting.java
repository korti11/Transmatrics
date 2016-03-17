package at.korti.transmatrics.registry;

import at.korti.transmatrics.api.crafting.PulverizerCraftingRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import static at.korti.transmatrics.api.Constants.TransmatricsItem.PULVERIZED_DUST;

/**
 * Created by Korti on 16.03.2016.
 */
public final class Crafting {

    public static void register() {
        registerPulverizerCrafting();
        registerFurnaceCrafting();
    }

    private static void registerPulverizerCrafting() {
        PulverizerCraftingRegistry.getInstance().register(new ItemStack(Blocks.iron_ore), 20 * 7, new ItemStack(PULVERIZED_DUST.getItem(), 2, 0));
        PulverizerCraftingRegistry.getInstance().register(new ItemStack(Blocks.gold_ore), 20 * 9, new ItemStack(PULVERIZED_DUST.getItem(), 2, 1));
    }

    private static void registerFurnaceCrafting() {
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(PULVERIZED_DUST.getItem(), 1, 0), new ItemStack(Items.iron_ingot), 0.7F);
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(PULVERIZED_DUST.getItem(), 1, 1), new ItemStack(Items.gold_ingot), 1.0F);
    }

}
