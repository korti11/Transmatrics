package at.korti.transmatrics.modintegration.jei;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.JEI;
import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.modintegration.jei.alloymixer.AlloyMixerRecipeCategory;
import at.korti.transmatrics.modintegration.jei.alloymixer.AlloyMixerRecipeFactory;
import at.korti.transmatrics.modintegration.jei.alloymixer.AlloyMixerRecipeMaker;
import at.korti.transmatrics.modintegration.jei.liquidcaste.LiquidCasterRecipeCategory;
import at.korti.transmatrics.modintegration.jei.liquidcaste.LiquidCasterRecipeFactory;
import at.korti.transmatrics.modintegration.jei.liquidcaste.LiquidCasterRecipeMaker;
import at.korti.transmatrics.modintegration.jei.magneticsmeltery.MagneticSmeleryRecipeFacotry;
import at.korti.transmatrics.modintegration.jei.magneticsmeltery.MagneticSmelteryRecipeCategory;
import at.korti.transmatrics.modintegration.jei.magneticsmeltery.MagneticSmelteryRecipeMaker;
import at.korti.transmatrics.modintegration.jei.pulverizer.PulverizerRecipeCategory;
import at.korti.transmatrics.modintegration.jei.pulverizer.PulverizerRecipeFactory;
import at.korti.transmatrics.modintegration.jei.pulverizer.PulverizerRecipeMaker;
import at.korti.transmatrics.registry.crafting.AlloyMixerCraftingRegistry.AlloyMixerCraftingEntry;
import at.korti.transmatrics.registry.crafting.LiquidCasterCraftingRegistry;
import at.korti.transmatrics.registry.crafting.LiquidCasterCraftingRegistry.LiquidCasterCraftingEntry;
import at.korti.transmatrics.registry.crafting.MagneticSmelteryCraftingRegistry;
import at.korti.transmatrics.registry.crafting.MagneticSmelteryCraftingRegistry.MagneticSmelteryCraftingEntry;
import at.korti.transmatrics.registry.crafting.PulverizerCraftingRegistry;
import at.korti.transmatrics.registry.crafting.PulverizerCraftingRegistry.PulverizerCraftingEntry;
import mezz.jei.api.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Created by Korti on 14.04.2016.
 */
@JEIPlugin
public class TransmatricsPlugin implements IModPlugin {

    public static IJeiHelpers jeiHelper;

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new PulverizerRecipeCategory(), new MagneticSmelteryRecipeCategory(),
                new LiquidCasterRecipeCategory(), new AlloyMixerRecipeCategory());
    }

    @Override
    public void register(@Nonnull IModRegistry registry) {
        jeiHelper = registry.getJeiHelpers();

        registry.handleRecipes(PulverizerCraftingEntry.class, new PulverizerRecipeFactory(), JEI.Categories.PULVERIZER);
        registry.handleRecipes(MagneticSmelteryCraftingEntry.class, new MagneticSmeleryRecipeFacotry(), JEI.Categories.MAGNETIC_SMELTERY);
        registry.handleRecipes(LiquidCasterCraftingEntry.class, new LiquidCasterRecipeFactory(), JEI.Categories.LIQUID_CASTER);
        registry.handleRecipes(AlloyMixerCraftingEntry.class, new AlloyMixerRecipeFactory(), JEI.Categories.ALLOY_MIXER);

        registry.addRecipeCatalyst(new ItemStack(TransmatricsBlock.POWERED_FURNACE.getBlock()), VanillaRecipeCategoryUid.SMELTING);
        registry.addRecipeCatalyst(new ItemStack(TransmatricsBlock.PULVERIZER.getBlock()), JEI.Categories.PULVERIZER);
        registry.addRecipeCatalyst(new ItemStack(TransmatricsBlock.MAGNETIC_SMELTERY.getBlock()), JEI.Categories.MAGNETIC_SMELTERY);
        registry.addRecipeCatalyst(new ItemStack(TransmatricsBlock.LIQUID_CASTER.getBlock()), JEI.Categories.LIQUID_CASTER);
        registry.addRecipeCatalyst(new ItemStack(TransmatricsBlock.ALLOY_MIXER.getBlock()), JEI.Categories.ALLOY_MIXER);

        registry.addRecipes(PulverizerRecipeMaker.getRecipes(), JEI.Categories.PULVERIZER);
        registry.addRecipes(MagneticSmelteryRecipeMaker.getRecipes(), JEI.Categories.MAGNETIC_SMELTERY);
        registry.addRecipes(LiquidCasterRecipeMaker.getRecipes(), JEI.Categories.LIQUID_CASTER);
        registry.addRecipes(AlloyMixerRecipeMaker.getRecipes(), JEI.Categories.ALLOY_MIXER);
    }
}
