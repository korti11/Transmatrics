package at.korti.transmatrics.modintegration.jei.pulverizer;

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
 * Created by Korti on 14.04.2016.
 */
public class PulverizerRecipeCategory implements IRecipeCategory {

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
                new ResourceLocation(Constants.Mod.MODID + ":textures/gui/jei/Pulverizer.png"), 0, 0, 120, 76);
    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {

    }

    @Override
    public void drawAnimations(@Nonnull Minecraft minecraft) {

    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper) {
        recipeLayout.getItemStacks().init(INPUT_SLOT, true, 3, 14);
        recipeLayout.getItemStacks().init(PRIMARY_OUTPUT_SLOT, false, 78, 7);
        recipeLayout.getItemStacks().init(SECONDARY_OUTPUT_SLOT, false, 79, 47);

        if (recipeWrapper instanceof PulverizerRecipeJEI) {
            PulverizerRecipeJEI pulverizerRecipeJEI = (PulverizerRecipeJEI) recipeWrapper;
            recipeLayout.getItemStacks().set(INPUT_SLOT, pulverizerRecipeJEI.getInputs());
            recipeLayout.getItemStacks().set(PRIMARY_OUTPUT_SLOT, (ItemStack) pulverizerRecipeJEI.getOutputs().get(0));
            if(pulverizerRecipeJEI.getOutputs().size() > 1) {
                recipeLayout.getItemStacks().set(SECONDARY_OUTPUT_SLOT, (ItemStack) pulverizerRecipeJEI.getOutputs().get(1));
            }
        }
    }
}
