package at.korti.transmatrics.modintegration.jei.liquidcaste;

import at.korti.transmatrics.registry.crafting.LiquidCasterCraftingRegistry;
import at.korti.transmatrics.registry.crafting.LiquidCasterCraftingRegistry.LiquidCasterCraftingEntry;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Korti on 15.04.2016.
 */
public class LiquidCasterRecipeMaker {

    public static List<LiquidCasterRecipeJEI> getRecipes() {
        LiquidCasterCraftingRegistry registry = LiquidCasterCraftingRegistry.getInstance();

        List<LiquidCasterRecipeJEI> recipes = new LinkedList<>();

        for (int i = 0; i < registry.size(); i++) {
            recipes.add(new LiquidCasterRecipeJEI((LiquidCasterCraftingEntry) registry.get(i)));
        }

        return recipes;
    }

}
