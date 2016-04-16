package at.korti.transmatrics.modintegration.jei.pulverizer;

import at.korti.transmatrics.registry.crafting.PulverizerCraftingRegistry;
import at.korti.transmatrics.registry.crafting.PulverizerCraftingRegistry.PulverizerCraftingEntry;
import net.minecraftforge.common.MinecraftForge;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Korti on 14.04.2016.
 */
public class PulverizerRecipeMaker {

    public static List<PulverizerRecipeJEI> getRecipes() {
        PulverizerCraftingRegistry registry = PulverizerCraftingRegistry.getInstance();

        List<PulverizerRecipeJEI> recipes = new LinkedList<>();

        for (int i = 0; i < registry.size(); i++) {
            PulverizerRecipeJEI recipe = new PulverizerRecipeJEI((PulverizerCraftingEntry) registry.get(i));
            recipes.add(recipe);
            MinecraftForge.EVENT_BUS.register(recipe);
        }

        return recipes;
    }

}
