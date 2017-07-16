package at.korti.transmatrics.modintegration.jei;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.modintegration.jei.liquidcaste.LiquidCasterRecipeCategory;
import at.korti.transmatrics.modintegration.jei.liquidcaste.LiquidCasterRecipeMaker;
import at.korti.transmatrics.modintegration.jei.magneticsmeltery.MagneticSmelteryRecipeCategory;
import at.korti.transmatrics.modintegration.jei.magneticsmeltery.MagneticSmelteryRecipeMaker;
import at.korti.transmatrics.modintegration.jei.pulverizer.PulverizerRecipeCategory;
import at.korti.transmatrics.modintegration.jei.pulverizer.PulverizerRecipeMaker;
import mezz.jei.api.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;

import javax.annotation.Nonnull;

/**
 * Created by Korti on 14.04.2016.
 */
@JEIPlugin
public class TransmatricsPlugin implements IModPlugin {

    public static IJeiHelpers jeiHelper;

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new PulverizerRecipeCategory(), new MagneticSmelteryRecipeCategory(),
                new LiquidCasterRecipeCategory());
    }

    @Override
    public void register(@Nonnull IModRegistry registry) {
        jeiHelper = registry.getJeiHelpers();

        registry.addRecipes(PulverizerRecipeMaker.getRecipes(), Constants.JEI.Categories.PULVERIZER);
        registry.addRecipes(MagneticSmelteryRecipeMaker.getRecipes(), Constants.JEI.Categories.MAGNETIC_SMELTERY);
        registry.addRecipes(LiquidCasterRecipeMaker.getRecipes(), Constants.JEI.Categories.LIQUID_CASTER);
    }
}
