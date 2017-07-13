//package at.korti.transmatrics.modintegration.jei.pulverizer;
//
//import at.korti.transmatrics.api.Constants;
//import at.korti.transmatrics.api.Constants.JEI;
//import mezz.jei.api.recipe.IRecipeHandler;
//import mezz.jei.api.recipe.IRecipeWrapper;
//
//import javax.annotation.Nonnull;
//
///**
// * Created by Korti on 14.04.2016.
// */
//public class PulverizerRecipeHandler implements IRecipeHandler<PulverizerRecipeJEI> {
//
//    @Nonnull
//    @Override
//    public Class<PulverizerRecipeJEI> getRecipeClass() {
//        return PulverizerRecipeJEI.class;
//    }
//
//    @Nonnull
//    @Override
//    public String getRecipeCategoryUid() {
//        return JEI.Categories.PULVERIZER;
//    }
//
//    @Nonnull
//    @Override
//    public IRecipeWrapper getRecipeWrapper(@Nonnull PulverizerRecipeJEI recipe) {
//        return recipe;
//    }
//
//    @Override
//    public boolean isRecipeValid(@Nonnull PulverizerRecipeJEI recipe) {
//        return recipe.getInputs().size() > 0 && recipe.getOutputs().size() > 0;
//    }
//
//    @Nonnull
//    @Override
//    public String getRecipeCategoryUid(@Nonnull PulverizerRecipeJEI recipe) {
//        return JEI.Categories.PULVERIZER;
//    }
//}
