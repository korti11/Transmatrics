package at.korti.transmatrics.modintegration.jei.magneticsmeltery;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.JEI;
import at.korti.transmatrics.api.crafting.MagneticSmelteryCraftingRegistry;
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
public class MagneticSmelteryRecipeCategory implements IRecipeCategory {

    private final int INPUT_SLOT = 0;
    private final int OUTPUT_SLOT = 1;

    @Nonnull
    @Override
    public String getUid() {
        return JEI.Categories.MAGNETIC_SMELTERY;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return TextHelper.localize("jei.category.magneticsmeltery.title");
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return TransmatricsPlugin.jeiHelper.getGuiHelper().createDrawable(
                new ResourceLocation(Constants.Mod.MODID + ":textures/gui/MagneticSmeltery.png"), 55, 9, 82, 66
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
        int tankCapacity = MagneticSmelteryCraftingRegistry.getInstance().getFluidCapacities()[0];
        recipeLayout.getItemStacks().init(INPUT_SLOT, true, 0, 24);
        recipeLayout.getFluidStacks().init(OUTPUT_SLOT, false, 52, 1, 16, 64,
                tankCapacity, true, null);

        if (recipeWrapper instanceof MagneticSmelteryRecipeJEI) {
            MagneticSmelteryRecipeJEI recipe = (MagneticSmelteryRecipeJEI) recipeWrapper;
            recipeLayout.getItemStacks().set(INPUT_SLOT, recipe.getInputs());
            recipeLayout.getFluidStacks().set(OUTPUT_SLOT, recipe.getFluidOutputs());
        }
    }
}
