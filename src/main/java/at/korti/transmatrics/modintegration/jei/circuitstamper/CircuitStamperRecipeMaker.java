package at.korti.transmatrics.modintegration.jei.circuitstamper;

import at.korti.transmatrics.registry.crafting.CircuitStamperCraftingRegistry;
import at.korti.transmatrics.registry.crafting.CircuitStamperCraftingRegistry.CircuitStamperCraftingEntry;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Korti on 17.04.2016.
 */
public class CircuitStamperRecipeMaker {

    public static List<CircuitStamperRecipeJEI> getRecipes() {
        CircuitStamperCraftingRegistry registry = CircuitStamperCraftingRegistry.getInstance();

        List<CircuitStamperRecipeJEI> recipes = new LinkedList<>();

        for (int i = 0; i < registry.size(); i++) {
            recipes.add(new CircuitStamperRecipeJEI((CircuitStamperCraftingEntry) registry.get(i)));
        }

        return recipes;
    }

}
