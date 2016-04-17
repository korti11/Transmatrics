package at.korti.transmatrics.modintegration.jei.circuitstamper;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.JEI;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

import javax.annotation.Nonnull;

/**
 * Created by Korti on 17.04.2016.
 */
public class CircuitStamperRecipeHandler implements IRecipeHandler<CircuitStamperRecipeJEI> {
    @Nonnull
    @Override
    public Class<CircuitStamperRecipeJEI> getRecipeClass() {
        return CircuitStamperRecipeJEI.class;
    }

    @Nonnull
    @Override
    public String getRecipeCategoryUid() {
        return JEI.Categories.CIRCUIT_STAMPER;
    }

    @Nonnull
    @Override
    public IRecipeWrapper getRecipeWrapper(@Nonnull CircuitStamperRecipeJEI recipe) {
        return recipe;
    }

    @Override
    public boolean isRecipeValid(@Nonnull CircuitStamperRecipeJEI recipe) {
        return recipe.getInputs().size() > 0 && recipe.getOutputs().size() > 0;
    }
}
