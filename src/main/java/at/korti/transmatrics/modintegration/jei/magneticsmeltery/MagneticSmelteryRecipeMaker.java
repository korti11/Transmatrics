package at.korti.transmatrics.modintegration.jei.magneticsmeltery;

import at.korti.transmatrics.api.crafting.MagneticSmelteryCraftingRegistry;
import at.korti.transmatrics.api.crafting.MagneticSmelteryCraftingRegistry.MagneticSmelteryCraftingEntry;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Korti on 14.04.2016.
 */
public class MagneticSmelteryRecipeMaker {

    public static List<MagneticSmelteryRecipeJEI> getRecipes() {
        MagneticSmelteryCraftingRegistry registry = MagneticSmelteryCraftingRegistry.getInstance();
        List<MagneticSmelteryRecipeJEI> recipes = new LinkedList<>();

        for (int i = 0; i < registry.size(); i++) {
            recipes.add(new MagneticSmelteryRecipeJEI((MagneticSmelteryCraftingEntry) registry.get(i)));
        }

        return recipes;
    }

}
