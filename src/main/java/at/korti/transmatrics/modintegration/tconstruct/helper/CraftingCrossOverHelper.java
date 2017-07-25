package at.korti.transmatrics.modintegration.tconstruct.helper;

import at.korti.transmatrics.registry.crafting.AlloyMixerCraftingRegistry;
import at.korti.transmatrics.registry.crafting.LiquidCasterCraftingRegistry;
import at.korti.transmatrics.registry.crafting.MagneticSmelteryCraftingRegistry;
import at.korti.transmatrics.registry.crafting.MagneticSmelteryCraftingRegistry.MagneticSmelteryCraftingEntry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.smeltery.AlloyRecipe;
import slimeknights.tconstruct.library.smeltery.CastingRecipe;
import slimeknights.tconstruct.library.smeltery.ICastingRecipe;
import slimeknights.tconstruct.library.smeltery.MeltingRecipe;
//import slimeknights.tconstruct.library.TinkerRegistry;
//import slimeknights.tconstruct.library.smeltery.AlloyRecipe;
//import slimeknights.tconstruct.library.smeltery.CastingRecipe;
//import slimeknights.tconstruct.library.smeltery.ICastingRecipe;
//import slimeknights.tconstruct.library.smeltery.MeltingRecipe;

import java.util.List;

/**
 * Created by Korti on 11.05.2016.
 */
public class CraftingCrossOverHelper {

    public static void loadSmelteryCrossOver() {
        List<MeltingRecipe> meltingRecipes = TinkerRegistry.getAllMeltingRecipies();
        for (MeltingRecipe meltingRecipe : meltingRecipes) {
            List<ItemStack> inputs = meltingRecipe.input.getInputs();
            FluidStack output = meltingRecipe.getResult();
            for(ItemStack input : inputs) {
                if(MagneticSmelteryCraftingRegistry.getInstance().get(input) == null) {
                    MagneticSmelteryCraftingRegistry.getInstance().register(input.copy(), output.copy(), 200);
                }
            }
        }
    }

    public static void loadCastingCrossOver() {
        List<ICastingRecipe> castingRecipes = TinkerRegistry.getAllTableCastingRecipes();
        for (ICastingRecipe iCastingRecipe : castingRecipes) {
            if(iCastingRecipe != null && iCastingRecipe instanceof CastingRecipe) {
                CastingRecipe castingRecipe = (CastingRecipe) iCastingRecipe;
                if(castingRecipe.getFluid() != null && castingRecipe.cast != null && castingRecipe.getResult() != null) {
                    FluidStack input = castingRecipe.getFluid();
                    List<ItemStack> casts = castingRecipe.cast.getInputs();
                    ItemStack output = castingRecipe.getResult();
                    int time = castingRecipe.getTime();
                    for (ItemStack cast : casts) {
                        if (input != null && cast != null && output != null) {
                            LiquidCasterCraftingRegistry.getInstance().register(input.copy(), cast.copy(), output.copy(), time);
                        }
                    }
                }
            }
        }
    }

    public static void loadAlloyCrossOver() {
        List<AlloyRecipe> alloyRecipes = TinkerRegistry.getAlloys();
        for (AlloyRecipe alloyRecipe : alloyRecipes) {
            List<FluidStack> inputs = alloyRecipe.getFluids();
            FluidStack output = alloyRecipe.getResult();
            if (inputs.size() < 4) {
                FluidStack[] temp = new FluidStack[inputs.size()];
                temp = inputs.toArray(temp);
                AlloyMixerCraftingRegistry.getInstance().register(output, temp);
            }
        }
    }

}
