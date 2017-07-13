//package at.korti.transmatrics.modintegration.jei.magneticsmeltery;
//
//import at.korti.transmatrics.api.Constants;
//import mezz.jei.api.recipe.IRecipeHandler;
//import mezz.jei.api.recipe.IRecipeWrapper;
//
//import javax.annotation.Nonnull;
//
///**
// * Created by Korti on 14.04.2016.
// */
//public class MagneticSmelteryRecipeHandler implements IRecipeHandler<MagneticSmelteryRecipeJEI> {
//    @Nonnull
//    @Override
//    public Class<MagneticSmelteryRecipeJEI> getRecipeClass() {
//        return MagneticSmelteryRecipeJEI.class;
//    }
//
//    @Nonnull
//    @Override
//    public String getRecipeCategoryUid() {
//        return Constants.JEI.Categories.MAGNETIC_SMELTERY;
//    }
//
//    @Nonnull
//    @Override
//    public IRecipeWrapper getRecipeWrapper(@Nonnull MagneticSmelteryRecipeJEI recipe) {
//        return recipe;
//    }
//
//    @Override
//    public boolean isRecipeValid(@Nonnull MagneticSmelteryRecipeJEI recipe) {
//        return recipe.getFluidOutputs().size() > 0 && recipe.getInputs().size() > 0;
//    }
//
//    @Nonnull
//    @Override
//    public String getRecipeCategoryUid(@Nonnull MagneticSmelteryRecipeJEI recipe) {
//        return Constants.JEI.Categories.MAGNETIC_SMELTERY;
//    }
//}
