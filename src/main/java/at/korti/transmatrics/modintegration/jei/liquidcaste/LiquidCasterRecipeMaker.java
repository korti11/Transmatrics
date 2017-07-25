package at.korti.transmatrics.modintegration.jei.liquidcaste;

import at.korti.transmatrics.registry.crafting.LiquidCasterCraftingRegistry;
import at.korti.transmatrics.registry.crafting.LiquidCasterCraftingRegistry.LiquidCasterCraftingEntry;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Korti on 15.04.2016.
 */
public class LiquidCasterRecipeMaker {

    public static List<LiquidCasterCraftingEntry> getRecipes() {
        LiquidCasterCraftingRegistry registry = LiquidCasterCraftingRegistry.getInstance();

        List<LiquidCasterCraftingEntry> recipes = new LinkedList<>();

        for (int i = 0; i < registry.size(); i++) {
            recipes.add((LiquidCasterCraftingEntry) registry.get(i));
        }

        return recipes;
    }

}
