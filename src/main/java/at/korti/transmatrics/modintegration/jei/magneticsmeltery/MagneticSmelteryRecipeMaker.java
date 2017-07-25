package at.korti.transmatrics.modintegration.jei.magneticsmeltery;

import at.korti.transmatrics.registry.crafting.MagneticSmelteryCraftingRegistry;
import at.korti.transmatrics.registry.crafting.MagneticSmelteryCraftingRegistry.MagneticSmelteryCraftingEntry;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Korti on 14.04.2016.
 */
public class MagneticSmelteryRecipeMaker {

    public static List<MagneticSmelteryCraftingEntry> getRecipes() {
        MagneticSmelteryCraftingRegistry registry = MagneticSmelteryCraftingRegistry.getInstance();

        List<MagneticSmelteryCraftingEntry> recipes = new LinkedList<>();

        for (int i = 0; i < registry.size(); i++) {
            recipes.add((MagneticSmelteryCraftingEntry) registry.get(i));
        }

        return recipes;
    }

}
