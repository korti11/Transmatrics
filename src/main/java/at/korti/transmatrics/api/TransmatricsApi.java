package at.korti.transmatrics.api;

import at.korti.transmatrics.api.Constants.Mod;
import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.api.Constants.TransmatricsFluid;
import at.korti.transmatrics.api.Constants.TransmatricsItem;
import at.korti.transmatrics.api.energy.IEnergyConsumer;
import at.korti.transmatrics.api.energy.IEnergyProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Korti on 24.02.2016.
 */
public class TransmatricsApi {

    public static Block getBlock(String name) {
        return GameRegistry.findBlock(Mod.MODID, name);
    }

    public static Block getBlock(TransmatricsBlock block) {
        return getBlock(block.getRegName());
    }

    public static Item getItem(String name) {
        return GameRegistry.findItem(Mod.MODID, name);
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

    public static void transferEnergy(IEnergyProvider provider, IEnergyConsumer consumer) {
        int energy = provider.extractEnergy(provider.getEnergyStored(), true);
        energy = consumer.receiveEnergy(energy, false);
        provider.extractEnergy(energy, false);
    }

}
