package at.korti.transmatrics.modintegration.jei.liquidcaste;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.JEI;
import at.korti.transmatrics.registry.crafting.LiquidCasterCraftingRegistry;
import at.korti.transmatrics.modintegration.jei.TransmatricsPlugin;
import at.korti.transmatrics.util.helper.TextHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Created by Korti on 14.04.2016.
 */
public class LiquidCasterRecipeCategory implements IRecipeCategory {

    private final int FLUID_INPUT_SLOT = 0;
    private final int ITEM_INPUT_SLOT = 1;

    private final int OUTPUT_SLOT = 3;

    @Nonnull
    @Override
    public String getUid() {
        return JEI.Categories.LIQUID_CASTER;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return TextHelper.localize("jei.category.liquidcaster.title");
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return TransmatricsPlugin.jeiHelper.getGuiHelper().createDrawable(
                new ResourceLocation(Constants.Mod.MODID + ":textures/gui/LiquidCaster.png"), 55, 9, 82, 66
        );
    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {

    }

    @Override
    public void drawAnimations(@Nonnull Minecraft minecraft) {

    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper) {
        int capacity = LiquidCasterCraftingRegistry.getInstance().getFluidCapacities()[0];
        recipeLayout.getFluidStacks().init(FLUID_INPUT_SLOT, true, 1, 1, 16, 64, capacity, true, null);
        recipeLayout.getItemStacks().init(ITEM_INPUT_SLOT, true, 26, 5);
        recipeLayout.getItemStacks().init(OUTPUT_SLOT, false, 60, 25);

        if (recipeWrapper instanceof LiquidCasterRecipeJEI) {
            LiquidCasterRecipeJEI recipe = (LiquidCasterRecipeJEI) recipeWrapper;
            recipeLayout.getFluidStacks().set(FLUID_INPUT_SLOT, recipe.getFluidInputs());
            recipeLayout.getItemStacks().set(ITEM_INPUT_SLOT, recipe.getInputs());
            recipeLayout.getItemStacks().set(OUTPUT_SLOT, recipe.getOutputs());
        }
    }
}
