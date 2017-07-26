package at.korti.transmatrics.modintegration.jei.magneticsmeltery;

import at.korti.transmatrics.registry.crafting.MagneticSmelteryCraftingRegistry.MagneticSmelteryCraftingEntry;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;

/**
 * Created by Korti on 25.07.2017.
 */
public class MagneticSmelteryRecipeFacotry implements IRecipeWrapperFactory<MagneticSmelteryCraftingEntry> {
    @Override
    public IRecipeWrapper getRecipeWrapper(MagneticSmelteryCraftingEntry entry) {
        return new MagneticSmelteryRecipeWrapper(entry);
    }
}
