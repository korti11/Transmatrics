package at.korti.transmatrics.modintegration.jei.liquidcaste;

import at.korti.transmatrics.registry.crafting.LiquidCasterCraftingRegistry.LiquidCasterCraftingEntry;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

/**
 * Created by Korti on 14.04.2016.
 */
public class LiquidCasterRecipeJEI extends BlankRecipeWrapper {

    private final FluidStack fluidInput;
    private final ItemStack itemInput;

    private final ItemStack output;

    public LiquidCasterRecipeJEI(LiquidCasterCraftingEntry entry) {
        this.fluidInput = entry.getInputs()[0];
        this.itemInput = entry.getSecondInputs()[0];
        this.output = entry.getOutputs()[0];
    }

    @Nonnull
    @Override
    public List<FluidStack> getFluidInputs() {
        return Collections.singletonList(fluidInput);
    }

    @Nonnull
    @Override
    public List getInputs() {
        return Collections.singletonList(itemInput);
    }

    @Nonnull
    @Override
    public List getOutputs() {
        return Collections.singletonList(output);
    }
}
