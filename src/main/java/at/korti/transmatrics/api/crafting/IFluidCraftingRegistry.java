package at.korti.transmatrics.api.crafting;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;

/**
 * Created by Korti on 30.03.2016.
 */
public interface IFluidCraftingRegistry<I> extends ICraftingRegistry<I> {

    EnumFacing[] getFluidInputFaces();

    EnumFacing[] getFluidOutputFaces();

    boolean canFill(FluidStack fluidStackIn, EnumFacing facing);

    boolean canDrain(FluidStack fluidStackIn, EnumFacing facing);

}
