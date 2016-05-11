package at.korti.transmatrics.modintegration.tconstruct.helper;

import at.korti.transmatrics.registry.crafting.MagneticSmelteryCraftingRegistry;
import at.korti.transmatrics.registry.crafting.MagneticSmelteryCraftingRegistry.MagneticSmelteryCraftingEntry;
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
        for (int i = 0; i < MagneticSmelteryCraftingRegistry.getInstance().size(); i++) {
            MagneticSmelteryCraftingEntry entry = (MagneticSmelteryCraftingEntry)
                    MagneticSmelteryCraftingRegistry.getInstance().get(i);
            if (TinkerRegistry.getMelting(entry.getInputs()[0]) != null) {
                MagneticSmelteryCraftingRegistry.getInstance().remove(entry);
            }
        }

        List<MeltingRecipe> meltingRecipes = TinkerRegistry.getAllMeltingRecipies();
        for (MeltingRecipe meltingRecipe : meltingRecipes) {
            List<ItemStack> inputs = meltingRecipe.input.getInputs();
            FluidStack output = meltingRecipe.getResult();
            for(ItemStack input : inputs) {
                MagneticSmelteryCraftingRegistry.getInstance().register(input.copy(), output.copy(), 200);
            }
        }
    }

}
