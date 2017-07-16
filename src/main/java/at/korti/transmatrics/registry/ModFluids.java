package at.korti.transmatrics.registry;

import at.korti.transmatrics.Transmatrics;
import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.TransmatricsFluid;
import at.korti.transmatrics.fluid.MoltenMetal;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by Korti on 15.07.2017.
 */
public class ModFluids {

    /**
     * The fluids registered by this mod. Includes fluids that were already registered by another mod.
     */
    public static final Set<Fluid> FLUIDS = new HashSet<>();

    /**
     * The fluid blocks from this mod only.
     * Doesn't include blocks for fluids that were already registered by another mod.
     */
    public static final Set<IFluidBlock> MOD_FLUID_BLOCKS = new HashSet<>();

    public static final Fluid MOLTEN_COPPER = createMoltenMetal(TransmatricsFluid.MOLTEN_COPPER.getRegName(),
            0xef7e0c, 600, fluid -> {}, fluid -> new BlockFluidClassic(fluid, new MaterialLiquid(MapColor.ADOBE)));

    public static final Fluid MOLTEN_TIN = createMoltenMetal(TransmatricsFluid.MOLTEN_TIN.getRegName(),
            0xffe6ff, 600, fluid -> {}, fluid -> new BlockFluidClassic(fluid, new MaterialLiquid(MapColor.ADOBE)));

    public static final Fluid MOLTEN_SILVER = createMoltenMetal(TransmatricsFluid.MOLTEN_SILVER.getRegName(),
            0xccffff, 600, fluid -> {}, fluid -> new BlockFluidClassic(fluid, new MaterialLiquid(MapColor.ADOBE)));

    public static final Fluid MOLTEN_LEAD = createMoltenMetal(TransmatricsFluid.MOLTEN_LEAD.getRegName(),
            0x30193c, 600, fluid -> {}, fluid -> new BlockFluidClassic(fluid, new MaterialLiquid(MapColor.ADOBE)));

    public static final Fluid MOLTEN_NICKEL = createMoltenMetal(TransmatricsFluid.MOLTEN_NICKEL.getRegName(),
            0xa3a375, 600, fluid -> {}, fluid -> new BlockFluidClassic(fluid, new MaterialLiquid(MapColor.ADOBE)));

    public static final Fluid MOLTEN_INVAR = createMoltenMetal(TransmatricsFluid.MOLTEN_INVAR.getRegName(),
            0xc2c2a3, 600, fluid -> {}, fluid -> new BlockFluidClassic(fluid, new MaterialLiquid(MapColor.ADOBE)));

    public static final Fluid MOLTEN_ELECTRUM = createMoltenMetal(TransmatricsFluid.MOLTEN_ELECTRUM.getRegName(),
            0xffd633, 600, fluid -> {}, fluid -> new BlockFluidClassic(fluid, new MaterialLiquid(MapColor.ADOBE)));

    private static <T extends Block & IFluidBlock> Fluid createMoltenMetal(final String name,
                                                                                 final int color, final int temperatur,
                                                                                 final Consumer<Fluid> fluidPropertyApplier,
                                                                                 final Function<Fluid, T> blockFactory) {
        Fluid fluid = new MoltenMetal(name, color, temperatur);
        final boolean useOwnFluid = FluidRegistry.registerFluid(fluid);

        if (useOwnFluid) {
            fluidPropertyApplier.accept(fluid);
            MOD_FLUID_BLOCKS.add(blockFactory.apply(fluid));
        } else {
            fluid = FluidRegistry.getFluid(name);
        }

        FLUIDS.add(fluid);

        return fluid;
    }

    private static <T extends Block & IFluidBlock> Fluid createFluid(final String name, final boolean hasFlowIcon,
                                                                     final Consumer<Fluid> fluidPropertyApplier,
                                                                     final Function<Fluid, T> blockFactory) {
        return createFluid(name, name, hasFlowIcon, fluidPropertyApplier, blockFactory);
    }

    private static <T extends Block & IFluidBlock> Fluid createFluid(final String name, final String textureName,
                                                                     final boolean hasFlowIcon,
                                                                     final Consumer<Fluid> fluidPropertyApplier,
                                                                     final Function<Fluid, T> blockFactory) {
        final String texturePrefix = Constants.Mod.MODID + ":blocks/fluid_";

        final ResourceLocation still = new ResourceLocation(texturePrefix + textureName + "_still");
        final ResourceLocation flowing = hasFlowIcon ?
                new ResourceLocation(texturePrefix + textureName + "_flowing") : still;

        Fluid fluid = new Fluid(name, still, flowing);
        final boolean useOwnFluid = FluidRegistry.registerFluid(fluid);

        if (useOwnFluid) {
            fluidPropertyApplier.accept(fluid);
            MOD_FLUID_BLOCKS.add(blockFactory.apply(fluid));
        } else {
            fluid = FluidRegistry.getFluid(name);
        }

        FLUIDS.add(fluid);

        return fluid;
    }

    @Mod.EventBusSubscriber
    public static class RegistrationHandler {

        @SubscribeEvent
        public static void registerBlocks(final RegistryEvent.Register<Block> event) {
            final IForgeRegistry<Block> registry = event.getRegistry();

            for (final IFluidBlock fluidBlock : MOD_FLUID_BLOCKS) {
                final Block block = (Block) fluidBlock;
                block.setRegistryName(Constants.Mod.MODID, "fluid." + fluidBlock.getFluid().getName());
                block.setUnlocalizedName(Constants.Mod.MODID + "." + fluidBlock.getFluid().getName());
                block.setCreativeTab(Transmatrics.creativeTab);
                registry.register(block);
            }
        }

        @SubscribeEvent
        public static void registerItems(final RegistryEvent.Register<Item> event) {
            final IForgeRegistry<Item> registry = event.getRegistry();

            for (final IFluidBlock fluidBlock : MOD_FLUID_BLOCKS) {
                final Block block = (Block) fluidBlock;
                final ItemBlock itemBlock = new ItemBlock(block);
                itemBlock.setRegistryName(block.getRegistryName());
                registry.register(itemBlock);
            }
        }

    }

    public static void registerFluidContainers() {
        for (final Fluid fluid : FLUIDS) {
            FluidRegistry.addBucketForFluid(fluid);
        }
    }
}
