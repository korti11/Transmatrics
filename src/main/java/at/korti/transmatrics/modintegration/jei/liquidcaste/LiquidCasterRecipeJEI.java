package at.korti.transmatrics.modintegration.jei.liquidcaste;

import at.korti.transmatrics.registry.crafting.LiquidCasterCraftingRegistry.LiquidCasterCraftingEntry;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.Collections;

/**
 * Created by Korti on 14.04.2016.
 */
public class LiquidCasterRecipeJEI implements IRecipeWrapper {

    private final FluidStack fluidInput;
    private final ItemStack itemInput;

    private final ItemStack output;

    public LiquidCasterRecipeJEI(LiquidCasterCraftingEntry entry) {
        this.fluidInput = entry.getInputs()[0];
        this.itemInput = entry.getSecondInputs()[0];
        this.output = entry.getOutputs()[0];
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(ItemStack.class, Collections.singletonList(itemInput));
        ingredients.setInputs(FluidStack.class, Collections.singletonList(fluidInput));
        ingredients.setOutput(ItemStack.class, output);
    }
}
