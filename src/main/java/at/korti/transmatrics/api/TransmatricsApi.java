package at.korti.transmatrics.api;

import at.korti.transmatrics.api.Constants.Mod;
import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.api.Constants.TransmatricsFluid;
import at.korti.transmatrics.api.Constants.TransmatricsItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Korti on 24.02.2016.
 */
public final class TransmatricsApi {

    public static Block getBlock(String name) {
        return Block.REGISTRY.getObject(new ResourceLocation(Mod.MODID, name));
    }

    public static Block getBlock(TransmatricsBlock block) {
        return getBlock(block.getRegName());
    }

    public static Item getItem(String name) {
        return Item.REGISTRY.getObject(new ResourceLocation(Mod.MODID, name));
    }

    public static Item getItem(TransmatricsItem item) {
        return getItem(item.getRegName());
    }

    public static Fluid getFluid(String name) {
        return FluidRegistry.getFluid(name);
    }

    public static Fluid getFluid(TransmatricsFluid fluid) {
        return getFluid(fluid.getRegName());
    }

}
