package at.korti.transmatrics.modintegration.jei.liquidcaste;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.JEI;
import at.korti.transmatrics.registry.crafting.LiquidCasterCraftingRegistry;
import at.korti.transmatrics.modintegration.jei.TransmatricsPlugin;
import at.korti.transmatrics.util.helper.TextHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;

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
                new ResourceLocation(Constants.Mod.MODID + ":textures/gui/liquid_caster.png"), 55, 9, 82, 66
        );
    }

    @Override
    public String getModName() {
        return Constants.Mod.NAME;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
        if(!(recipeWrapper instanceof LiquidCasterRecipeJEI)){
            return;
        }

        int capacity = LiquidCasterCraftingRegistry.getInstance().getFluidCapacities()[0];

        recipeLayout.getFluidStacks().init(FLUID_INPUT_SLOT, true, 1, 1, 16, 64, capacity, true, null);

        recipeLayout.getItemStacks().init(ITEM_INPUT_SLOT, true, 26, 5);
        recipeLayout.getItemStacks().init(OUTPUT_SLOT, false, 60, 25);

        recipeLayout.getFluidStacks().set(FLUID_INPUT_SLOT, ingredients.getInputs(FluidStack.class).get(0));
        recipeLayout.getItemStacks().set(ITEM_INPUT_SLOT, ingredients.getInputs(ItemStack.class).get(0));
        recipeLayout.getItemStacks().set(OUTPUT_SLOT, ingredients.getOutputs(ItemStack.class).get(0));
    }
}
