package at.korti.transmatrics.modintegration.jei.alloymixer;

import at.korti.transmatrics.registry.crafting.AlloyMixerCraftingRegistry.AlloyMixerCraftingEntry;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraftforge.fluids.FluidStack;
import scala.actors.threadpool.Arrays;

/**
 * Created by Korti on 25.07.2017.
 */
public class AlloyMixerRecipeWrapper implements IRecipeWrapper {

    private final FluidStack[] inputs;
    private final FluidStack output;

    public AlloyMixerRecipeWrapper(AlloyMixerCraftingEntry entry) {
        this.inputs = entry.getInputs();
        this.output = entry.getOutputs()[0];
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(FluidStack.class, Arrays.asList(inputs));
        ingredients.setOutput(FluidStack.class, output);
    }

}
