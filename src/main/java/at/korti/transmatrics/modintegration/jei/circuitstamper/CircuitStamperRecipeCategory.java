package at.korti.transmatrics.modintegration.jei.circuitstamper;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.JEI;
import at.korti.transmatrics.modintegration.jei.TransmatricsPlugin;
import at.korti.transmatrics.util.helper.TextHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Created by Korti on 16.04.2016.
 */
public class CircuitStamperRecipeCategory implements IRecipeCategory {

    private final int BOARD_INPUT = 0;
    private final int CONDUCTOR_INPUT = 1;

    private final int OUTPUT = 2;

    @Nonnull
    @Override
    public String getUid() {
        return JEI.Categories.CIRCUIT_STAMPER;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return TextHelper.localize("jei.category.circuitstamper.title");
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return TransmatricsPlugin.jeiHelper.getGuiHelper().createDrawable(
                new ResourceLocation(Constants.Mod.MODID + ":textures/gui/CircuitStamper.png"), 55, 14, 82, 45);
    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {

    }

    @Override
    public void drawAnimations(@Nonnull Minecraft minecraft) {

    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper) {
        recipeLayout.getItemStacks().init(BOARD_INPUT, true, 0, 11);
        recipeLayout.getItemStacks().init(CONDUCTOR_INPUT, true, 26, 0);
        recipeLayout.getItemStacks().init(OUTPUT, false, 60, 20);

        if (recipeWrapper instanceof CircuitStamperRecipeJEI) {
            CircuitStamperRecipeJEI recipe = (CircuitStamperRecipeJEI) recipeWrapper;
            recipeLayout.getItemStacks().set(BOARD_INPUT, (ItemStack) recipe.getInputs().get(0));
            recipeLayout.getItemStacks().set(CONDUCTOR_INPUT, (ItemStack) recipe.getInputs().get(1));
            recipeLayout.getItemStacks().set(OUTPUT, recipe.getOutputs());
        }
    }
}
