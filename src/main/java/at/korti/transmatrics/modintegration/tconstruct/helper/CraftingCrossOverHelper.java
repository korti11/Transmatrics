package at.korti.transmatrics.modintegration.tconstruct.helper;

import at.korti.transmatrics.registry.crafting.MagneticSmelteryCraftingRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.smeltery.MeltingRecipe;

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
            if(inputs.size() == 1) {
                MagneticSmelteryCraftingRegistry.getInstance().register(inputs.get(0).copy(), output.copy(), 200);
            }
        }
    }

}
