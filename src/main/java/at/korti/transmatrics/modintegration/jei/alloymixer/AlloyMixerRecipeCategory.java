package at.korti.transmatrics.modintegration.jei.alloymixer;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.JEI;
import at.korti.transmatrics.modintegration.jei.TransmatricsPlugin;
import at.korti.transmatrics.registry.crafting.AlloyMixerCraftingRegistry;
import at.korti.transmatrics.util.helper.TextHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

/**
 * Created by Korti on 25.07.2017.
 */
public class AlloyMixerRecipeCategory implements IRecipeCategory<AlloyMixerRecipeWrapper> {

    private final int FLUID_INPUT_SLOT_ONE = 0;
    private final int FLUID_INPUT_SLOT_TWO = 1;
    private final int FLUID_INPUT_SLOT_THREE = 2;

    private final int FLUID_OUTPUT_SLOT = 3;

    @Override
    public String getUid() {
        return JEI.Categories.ALLOY_MIXER;
    }

    @Override
    public String getTitle() {
        return TextHelper.localize("jei.category.alloymixer.title");
    }

    @Override
    public String getModName() {
        return Constants.Mod.NAME;
    }

    @Override
    public IDrawable getBackground() {
        return TransmatricsPlugin.jeiHelper.getGuiHelper().createDrawable(
                new ResourceLocation(Constants.Mod.MODID, "textures/gui/alloy_mixer.png"), 55, 9, 110, 66
        );
    }

    @Nullable
    @Override
    public IDrawable getIcon() {
        return null;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, AlloyMixerRecipeWrapper alloyMixerRecipeWrapper, IIngredients ingredients) {
        int inputTank1 = AlloyMixerCraftingRegistry.getInstance().getFluidCapacities()[0] / 10;
        int inputTank2 = AlloyMixerCraftingRegistry.getInstance().getFluidCapacities()[1] / 10;
        int inputTank3 = AlloyMixerCraftingRegistry.getInstance().getFluidCapacities()[2] / 10;

        int outputTank = AlloyMixerCraftingRegistry.getInstance().getFluidCapacities()[3] / 10;

        recipeLayout.getFluidStacks().init(FLUID_INPUT_SLOT_ONE, true, 1, 1, 16, 64, inputTank1, true, null);
        recipeLayout.getFluidStacks().init(FLUID_INPUT_SLOT_TWO, true, 22, 1, 16, 64, inputTank2, true, null);
        recipeLayout.getFluidStacks().init(FLUID_INPUT_SLOT_THREE, true, 43, 1, 16, 64, inputTank3, true, null);

        recipeLayout.getFluidStacks().init(FLUID_OUTPUT_SLOT, false, 93, 1, 16, 64, outputTank, true, null);

        recipeLayout.getFluidStacks().set(ingredients);
    }

}
