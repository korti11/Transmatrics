package at.korti.transmatrics.modintegration.jei.pulverizer;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.JEI;
import at.korti.transmatrics.modintegration.jei.TransmatricsPlugin;
import at.korti.transmatrics.util.helper.TextHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Created by Korti on 14.04.2016.
 */
public class PulverizerRecipeCategory implements IRecipeCategory<PulverizerRecipeWrapper> {

    private static final int INPUT_SLOT = 0;
    private static final int PRIMARY_OUTPUT_SLOT = 1;
    private static final int SECONDARY_OUTPUT_SLOT = 2;

    @Nonnull
    @Override
    public String getUid() {
        return JEI.Categories.PULVERIZER;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return TextHelper.localize("jei.category.pulverizer.title");
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return TransmatricsPlugin.jeiHelper.getGuiHelper().createDrawable(
                new ResourceLocation(Constants.Mod.MODID + ":textures/gui/pulverizer.png"), 55, 16, 82, 54);
    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {

    }

    @Override
    public String getModName() {
        return Constants.Mod.NAME;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, PulverizerRecipeWrapper recipeWrapper, IIngredients ingredients) {
        recipeLayout.getItemStacks().init(INPUT_SLOT, true, 0, 9);
        recipeLayout.getItemStacks().init(PRIMARY_OUTPUT_SLOT, false, 60, 4);
        recipeLayout.getItemStacks().init(SECONDARY_OUTPUT_SLOT, false, 60, 35);

        recipeLayout.getItemStacks().set(ingredients);
    }
}
