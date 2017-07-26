package at.korti.transmatrics.modintegration.jei.pulverizer;

import at.korti.transmatrics.registry.crafting.PulverizerCraftingRegistry;
import at.korti.transmatrics.registry.crafting.PulverizerCraftingRegistry.PulverizerCraftingEntry;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Korti on 14.04.2016.
 */
public class PulverizerRecipeMaker {

    public static List<PulverizerCraftingEntry> getRecipes() {
        PulverizerCraftingRegistry registry = PulverizerCraftingRegistry.getInstance();

        List<PulverizerCraftingEntry> recipes = new LinkedList<>();

        for (int i = 0; i < registry.size(); i++) {
            recipes.add((PulverizerCraftingEntry) registry.get(i));
        }

        return recipes;
    }

}
