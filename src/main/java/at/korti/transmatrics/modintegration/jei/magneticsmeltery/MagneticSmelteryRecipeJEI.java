package at.korti.transmatrics.modintegration.jei.magneticsmeltery;

import at.korti.transmatrics.api.crafting.MagneticSmelteryCraftingRegistry;
import at.korti.transmatrics.api.crafting.MagneticSmelteryCraftingRegistry.MagneticSmelteryCraftingEntry;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

/**
 * Created by Korti on 14.04.2016.
 */
public class MagneticSmelteryRecipeJEI extends BlankRecipeWrapper {

    private final ItemStack input;

    private final FluidStack output;

    public MagneticSmelteryRecipeJEI(MagneticSmelteryCraftingEntry entry) {
        this.input = entry.getInputs()[0];
        this.output = entry.getOutputs()[0];
    }

    @Nonnull
    @Override
    public List getInputs() {
        return Collections.singletonList(input);
    }

    @Nonnull
    @Override
    public List<FluidStack> getFluidOutputs() {
        return Collections.singletonList(output);
    }
}
