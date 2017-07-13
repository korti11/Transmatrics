//package at.korti.transmatrics.modintegration.jei.liquidcaste;
//
//import at.korti.transmatrics.api.Constants;
//import at.korti.transmatrics.api.Constants.JEI;
//import mezz.jei.api.recipe.IRecipeHandler;
//import mezz.jei.api.recipe.IRecipeWrapper;
//
//import javax.annotation.Nonnull;
//
///**
// * Created by Korti on 15.04.2016.
// */
//public class LiquidCasterRecipeHandler implements IRecipeHandler<LiquidCasterRecipeJEI> {
//
//    @Nonnull
//    @Override
//    public Class<LiquidCasterRecipeJEI> getRecipeClass() {
//        return LiquidCasterRecipeJEI.class;
//    }
//
//    @Nonnull
//    @Override
//    public String getRecipeCategoryUid() {
//        return JEI.Categories.LIQUID_CASTER;
//    }
//
//    @Nonnull
//    @Override
//    public IRecipeWrapper getRecipeWrapper(@Nonnull LiquidCasterRecipeJEI recipe) {
//        return recipe;
//    }
//
//    @Override
//    public boolean isRecipeValid(@Nonnull LiquidCasterRecipeJEI recipe) {
//        return recipe.getFluidInputs().size() > 0 && recipe.getInputs().size() > 0 && recipe.getOutputs().size() > 0;
//    }
//
//    @Nonnull
//    @Override
//    public String getRecipeCategoryUid(@Nonnull LiquidCasterRecipeJEI recipe) {
//        return JEI.Categories.LIQUID_CASTER;
//    }
//}
