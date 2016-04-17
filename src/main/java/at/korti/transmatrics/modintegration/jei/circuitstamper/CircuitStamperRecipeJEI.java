package at.korti.transmatrics.modintegration.jei.circuitstamper;

import at.korti.transmatrics.registry.crafting.CircuitStamperCraftingRegistry;
import at.korti.transmatrics.registry.crafting.CircuitStamperCraftingRegistry.CircuitStamperCraftingEntry;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Korti on 17.04.2016.
 */
public class CircuitStamperRecipeJEI extends BlankRecipeWrapper{

    private final ItemStack[] blankCircuit;

    private final ItemStack output;

    public CircuitStamperRecipeJEI(CircuitStamperCraftingEntry entry) {
        this.blankCircuit = entry.getInputs();
        this.output = entry.getOutputs()[0];
    }

    @Nonnull
    @Override
    public List getInputs() {
        return Arrays.asList(blankCircuit);
    }

    @Nonnull
    @Override
    public List getOutputs() {
        return Collections.singletonList(output);
    }
}
