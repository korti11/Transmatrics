package at.korti.transmatrics.api.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

/**
 * Created by Korti on 01.04.2016.
 */
public interface IFluidItemCraftingRegistry extends IFluidCraftingRegistry<FluidStack> {

    IFluidItemCraftingEntry get(FluidStack[] fluidInputs, ItemStack[] itemInputs);

    float getChanceForSlot(int slot, FluidStack[] fluidInputs, ItemStack[] itemStacks);

    interface IFluidItemCraftingEntry<O> extends ICraftingEntry<FluidStack, O>{

        ItemStack[] getSecondInputs();

    }

}
