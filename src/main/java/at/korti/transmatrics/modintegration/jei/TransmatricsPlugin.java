package at.korti.transmatrics.modintegration.jei;

import at.korti.transmatrics.modintegration.jei.magneticsmeltery.MagneticSmelteryRecipeCategory;
import at.korti.transmatrics.modintegration.jei.magneticsmeltery.MagneticSmelteryRecipeHandler;
import at.korti.transmatrics.modintegration.jei.magneticsmeltery.MagneticSmelteryRecipeMaker;
import at.korti.transmatrics.modintegration.jei.pulverizer.PulverizerRecipeCategory;
import at.korti.transmatrics.modintegration.jei.pulverizer.PulverizerRecipeHandler;
import at.korti.transmatrics.modintegration.jei.pulverizer.PulverizerRecipeMaker;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;

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

        registry.addRecipeCategories(new PulverizerRecipeCategory(), new MagneticSmelteryRecipeCategory());
        registry.addRecipeHandlers(new PulverizerRecipeHandler(), new MagneticSmelteryRecipeHandler());

        registry.addRecipes(PulverizerRecipeMaker.getRecipes());
        registry.addRecipes(MagneticSmelteryRecipeMaker.getRecipes());
    }
}
