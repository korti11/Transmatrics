package at.korti.transmatrics.modintegration.jei.pulverizer;

import at.korti.transmatrics.registry.crafting.PulverizerCraftingRegistry.PulverizerCraftingEntry;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created by Korti on 25.07.2017.
 */
public class PulverizerRecipeFactory implements IRecipeWrapperFactory<PulverizerCraftingEntry> {
    @Override
    public IRecipeWrapper getRecipeWrapper(PulverizerCraftingEntry entry) {
        PulverizerRecipeWrapper wrapper = new PulverizerRecipeWrapper(entry);
        MinecraftForge.EVENT_BUS.register(wrapper);
        return wrapper;
    }
}
