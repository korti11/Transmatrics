package at.korti.transmatrics.api.crafting;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;

/**
 * Created by Korti on 30.03.2016.
 */
public interface IFluidCraftingRegistry<I> extends ICraftingRegistry<I> {

    EnumFacing[] getFluidInputFaces();

    EnumFacing[] getFluidOutputFaces();

    boolean canFill(Fluid fluidIn, EnumFacing facing);

    boolean canDrain(Fluid fluidIn, EnumFacing facing);

    int[] getFluidCapacities();

    int[] getFluidInputIds();

    int[] getFluidOutputIds();

}
