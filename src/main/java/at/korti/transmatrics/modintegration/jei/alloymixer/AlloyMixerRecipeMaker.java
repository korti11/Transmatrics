package at.korti.transmatrics.modintegration.jei.alloymixer;

import at.korti.transmatrics.registry.crafting.AlloyMixerCraftingRegistry;
import at.korti.transmatrics.registry.crafting.AlloyMixerCraftingRegistry.AlloyMixerCraftingEntry;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Korti on 25.07.2017.
 */
public class AlloyMixerRecipeMaker {

    public static List<AlloyMixerCraftingEntry> getRecipes() {
        AlloyMixerCraftingRegistry registry = AlloyMixerCraftingRegistry.getInstance();

        List<AlloyMixerCraftingEntry> recipes = new LinkedList<>();

        for (int i = 0; i < registry.size(); i++) {
            recipes.add((AlloyMixerCraftingEntry) registry.get(i));
        }

        return recipes;
    }

}
