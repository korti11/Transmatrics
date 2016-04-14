package at.korti.transmatrics.modintegration.jei;

import at.korti.transmatrics.modintegration.jei.pulverizer.PulverizerRecipeCategory;
import at.korti.transmatrics.modintegration.jei.pulverizer.PulverizerRecipeHandler;
import at.korti.transmatrics.modintegration.jei.pulverizer.PulverizerRecipeMaker;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nonnull;

/**
 * Created by Korti on 14.04.2016.
 */
@JEIPlugin
public class TransmatricsPlugin extends BlankModPlugin {

    public static IJeiHelpers jeiHelper;

    @Override
    public void register(@Nonnull IModRegistry registry) {
        jeiHelper = registry.getJeiHelpers();

        registry.addRecipeCategories(new PulverizerRecipeCategory());
        registry.addRecipeHandlers(new PulverizerRecipeHandler());

        registry.addRecipes(PulverizerRecipeMaker.getRecipes());
    }
}
