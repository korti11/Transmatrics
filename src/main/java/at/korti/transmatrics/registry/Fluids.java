package at.korti.transmatrics.registry;

import at.korti.transmatrics.api.Constants.Mod;
import at.korti.transmatrics.block.ModFluidBlock;
import at.korti.transmatrics.fluid.GasColored;
import at.korti.transmatrics.fluid.MoltenMetal;
import net.minecraft.block.material.MapColor;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static at.korti.transmatrics.api.Constants.TransmatricsFluid.*;

/**
 * Created by Korti on 02.03.2016.
 */
public final class Fluids {

    public static Fluid moltenCopper;
    public static Fluid moltenTin;
    public static Fluid moltenSilver;
    public static Fluid moltenLead;
    public static Fluid moltenNickel;
    public static Fluid moltenInvar;
    public static Fluid moltenElectrum;

    public static void registerFluids() {
        moltenCopper = createMoltenMetal(MOLTEN_COPPER.getRegName(), 0xef7e0c, 600, true);
        moltenTin = createMoltenMetal(MOLTEN_TIN.getRegName(), 0xffe6ff, 600, true);
        moltenSilver = createMoltenMetal(MOLTEN_SILVER.getRegName(), 0xccffff, 600, true);
        moltenLead = createMoltenMetal(MOLTEN_LEAD.getRegName(), 0x30193c, 600, true);
        moltenNickel = createMoltenMetal(MOLTEN_NICKEL.getRegName(), 0xa3a375, 600, true);
        moltenInvar = createMoltenMetal(MOLTEN_INVAR.getRegName(), 0xc2c2a3, 600, true);
        moltenElectrum = createMoltenMetal(MOLTEN_ELECTRUM.getRegName(), 0xffd633, 600, true);

        registerBlock(moltenCopper);
        registerBlock(moltenTin);
        registerBlock(moltenSilver);
        registerBlock(moltenLead);
        registerBlock(moltenNickel);
        registerBlock(moltenInvar);
        registerBlock(moltenElectrum);
    }

    private static Fluid createGas(String name, int color, boolean bucket) {
        GasColored gas = new GasColored(name, color);
        gas = registerFluid(gas);
        if (bucket) {
            FluidRegistry.addBucketForFluid(gas);
        }
        return gas;
    }

    private static Fluid createMoltenMetal(String name, int color, int temperture, boolean bucket) {
        MoltenMetal moltenMetal = new MoltenMetal(name, color, temperture);
        moltenMetal = registerFluid(moltenMetal);
        if (bucket) {
            FluidRegistry.addBucketForFluid(moltenMetal);
        }
        return moltenMetal;
    }

    private static <T extends Fluid> T registerFluid(T fluid) {
        fluid.setUnlocalizedName(Mod.MODID + "." + fluid.getName());
        FluidRegistry.registerFluid(fluid);
        return fluid;
    }

    private static BlockFluidBase registerBlock(Fluid fluid) {
        BlockFluidBase block = new ModFluidBlock(fluid, MapColor.ADOBE);
        block.setRegistryName(Mod.MODID, fluid.getName());
        ItemBlock itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(block.getRegistryName());
        GameRegistry.register(block);
        GameRegistry.register(itemBlock);
        return block;
    }
}
