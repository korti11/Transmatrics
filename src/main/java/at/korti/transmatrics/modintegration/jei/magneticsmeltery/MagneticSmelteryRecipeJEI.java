package at.korti.transmatrics.modintegration.jei.magneticsmeltery;

import at.korti.transmatrics.registry.crafting.MagneticSmelteryCraftingRegistry.MagneticSmelteryCraftingEntry;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

/**
 * Created by Korti on 14.04.2016.
 */
public class MagneticSmelteryRecipeJEI implements IRecipeWrapper {

    private final ItemStack input;

    private final FluidStack output;

    public MagneticSmelteryRecipeJEI(MagneticSmelteryCraftingEntry entry) {
        this.input = entry.getInputs()[0];
        this.output = entry.getOutputs()[0];
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(ItemStack.class, input);
        ingredients.setOutput(FluidStack.class, output);
    }
}
