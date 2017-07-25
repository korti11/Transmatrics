package at.korti.transmatrics.modintegration.jei.alloymixer;

import at.korti.transmatrics.registry.crafting.AlloyMixerCraftingRegistry.AlloyMixerCraftingEntry;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;

/**
 * Created by Korti on 25.07.2017.
 */
public class AlloyMixerRecipeFactory implements IRecipeWrapperFactory<AlloyMixerCraftingEntry> {
    @Override
    public IRecipeWrapper getRecipeWrapper(AlloyMixerCraftingEntry entry) {
        return new AlloyMixerRecipeWrapper(entry);
    }
}
