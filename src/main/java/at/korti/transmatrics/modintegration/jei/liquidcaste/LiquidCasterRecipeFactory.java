package at.korti.transmatrics.modintegration.jei.liquidcaste;

import at.korti.transmatrics.registry.crafting.LiquidCasterCraftingRegistry.LiquidCasterCraftingEntry;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;

/**
 * Created by Korti on 25.07.2017.
 */
public class LiquidCasterRecipeFactory implements IRecipeWrapperFactory<LiquidCasterCraftingEntry> {
    @Override
    public IRecipeWrapper getRecipeWrapper(LiquidCasterCraftingEntry entry) {
        return new LiquidCasterRecipeWrapper(entry);
    }
}
