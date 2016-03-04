package at.korti.transmatrics.registry;

import at.korti.transmatrics.api.Constants.Mod;
import at.korti.transmatrics.api.Constants.TransmatricsFluid;
import at.korti.transmatrics.block.ModFluidBlock;
import at.korti.transmatrics.fluid.GasColored;
import net.minecraft.block.material.MapColor;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Korti on 02.03.2016.
 */
public final class Fluids {

    public static Fluid hydrogenGas;

    public static void registerFluids() {
        hydrogenGas = createGas(TransmatricsFluid.HYDROGEN_GAS.getRegName(), 0xffff66, true);
        registerBlock(hydrogenGas);
    }

    private static Fluid createGas(String name, int color, boolean bucket) {
        GasColored gas = new GasColored(name, color);
        gas = registerFluid(gas);
        if (bucket) {
            FluidRegistry.addBucketForFluid(gas);
        }
        return gas;
    }

    private static <T extends Fluid> T registerFluid(T fluid) {
        fluid.setUnlocalizedName(Mod.MODID + "." + fluid.getName());
        FluidRegistry.registerFluid(fluid);
        return fluid;
    }

    private static BlockFluidBase registerBlock(Fluid fluid) {
        BlockFluidBase block = new ModFluidBlock(fluid, MapColor.adobeColor);
        GameRegistry.registerBlock(block, fluid.getName());
        return block;
    }
}
