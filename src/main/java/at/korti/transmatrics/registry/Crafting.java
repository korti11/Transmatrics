package at.korti.transmatrics.registry;

import at.korti.transmatrics.registry.crafting.AlloyMixerCraftingRegistry;
import at.korti.transmatrics.registry.crafting.LiquidCasterCraftingRegistry;
import at.korti.transmatrics.registry.crafting.MagneticSmelteryCraftingRegistry;
import at.korti.transmatrics.registry.crafting.PulverizerCraftingRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fluids.FluidStack;

import static at.korti.transmatrics.api.Constants.TransmatricsBlock.ORE_BLOCK;
import static at.korti.transmatrics.api.Constants.TransmatricsItem.*;

/**
 * Created by Korti on 16.03.2016.
 */
public final class Crafting {

    public static int FLUID_AMOUNT_PER_INGOT = 500;

    public static void register() {
        registerPulverizerCrafting();
        registerFurnaceCrafting();
        registerMagneticSmelteryCrafting();
        registerLiquidCasterCrafting();
        registerAlloyMixerCrafting();
    }

    private static void registerPulverizerCrafting() {
        PulverizerCraftingRegistry.getInstance().register(new ItemStack(Blocks.COBBLESTONE), 20 * 6, 0.25f, new ItemStack(Blocks.GRAVEL), new ItemStack(Blocks.GRAVEL));
        PulverizerCraftingRegistry.getInstance().register(new ItemStack(Blocks.GRAVEL), 20 * 6, 0.25f, new ItemStack(Blocks.SAND), new ItemStack(Blocks.SAND));
        PulverizerCraftingRegistry.getInstance().register(new ItemStack(Blocks.DIAMOND_ORE), 20 * 12, 0.05f, new ItemStack(Items.DIAMOND, 2), new ItemStack(Items.DIAMOND, 1));
        PulverizerCraftingRegistry.getInstance().register(new ItemStack(Blocks.REDSTONE_ORE), 20 * 11, 0.15f, new ItemStack(Items.REDSTONE, 8), new ItemStack(Items.REDSTONE, 2));
        PulverizerCraftingRegistry.getInstance().register(new ItemStack(Blocks.LAPIS_ORE), 20 * 10, 0.20f, new ItemStack(Items.DYE, 8, 4), new ItemStack(Items.DYE, 2, 4));

        PulverizerCraftingRegistry.getInstance().register(new ItemStack(Blocks.IRON_ORE), 20 * 7, new ItemStack(PULVERIZED_DUST.getItem(), 2, 0));
        PulverizerCraftingRegistry.getInstance().register(new ItemStack(Blocks.GOLD_ORE), 20 * 9, new ItemStack(PULVERIZED_DUST.getItem(), 2, 1));

        PulverizerCraftingRegistry.getInstance().register(new ItemStack(ORE_BLOCK.getBlock(), 1, 0), 20 * 8, new ItemStack(PULVERIZED_DUST.getItem(), 2, 2));
        PulverizerCraftingRegistry.getInstance().register(new ItemStack(ORE_BLOCK.getBlock(), 1, 1), 20 * 8, new ItemStack(PULVERIZED_DUST.getItem(), 2, 3));
        PulverizerCraftingRegistry.getInstance().register(new ItemStack(ORE_BLOCK.getBlock(), 1, 2), 20 * 10, new ItemStack(PULVERIZED_DUST.getItem(), 2, 4));
        PulverizerCraftingRegistry.getInstance().register(new ItemStack(ORE_BLOCK.getBlock(), 1, 3), 20 * 10, new ItemStack(PULVERIZED_DUST.getItem(), 2, 5));
        PulverizerCraftingRegistry.getInstance().register(new ItemStack(ORE_BLOCK.getBlock(), 1, 4), 20 * 8, new ItemStack(PULVERIZED_DUST.getItem(), 2, 6));

        PulverizerCraftingRegistry.getInstance().register(new ItemStack(Items.IRON_INGOT), 10 * 7, new ItemStack(PULVERIZED_DUST.getItem(), 1, 0));
        PulverizerCraftingRegistry.getInstance().register(new ItemStack(Items.GOLD_INGOT), 10 * 9, new ItemStack(PULVERIZED_DUST.getItem(), 1, 1));

        PulverizerCraftingRegistry.getInstance().register(new ItemStack(INGOT.getItem(), 1, 0), 10 * 8, new ItemStack(PULVERIZED_DUST.getItem(), 1, 2));
        PulverizerCraftingRegistry.getInstance().register(new ItemStack(INGOT.getItem(), 1, 1), 10 * 8, new ItemStack(PULVERIZED_DUST.getItem(), 1, 3));
        PulverizerCraftingRegistry.getInstance().register(new ItemStack(INGOT.getItem(), 1, 2), 10 * 8, new ItemStack(PULVERIZED_DUST.getItem(), 1, 4));
        PulverizerCraftingRegistry.getInstance().register(new ItemStack(INGOT.getItem(), 1, 3), 10 * 10, new ItemStack(PULVERIZED_DUST.getItem(), 1, 5));
        PulverizerCraftingRegistry.getInstance().register(new ItemStack(INGOT.getItem(), 1, 4), 10 * 10, new ItemStack(PULVERIZED_DUST.getItem(), 1, 6));
        PulverizerCraftingRegistry.getInstance().register(new ItemStack(INGOT.getItem(), 1, 5), 10 * 8, new ItemStack(PULVERIZED_DUST.getItem(), 1, 7));
        PulverizerCraftingRegistry.getInstance().register(new ItemStack(INGOT.getItem(), 1, 6), 10 * 8, new ItemStack(PULVERIZED_DUST.getItem(), 1, 8));
    }

    private static void registerFurnaceCrafting() {
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(PULVERIZED_DUST.getItem(), 1, 0), new ItemStack(Items.IRON_INGOT), 0.7F);
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(PULVERIZED_DUST.getItem(), 1, 1), new ItemStack(Items.GOLD_INGOT), 1.0F);
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(PULVERIZED_DUST.getItem(), 1, 2), new ItemStack(INGOT.getItem(), 1, 0), 0.5F);
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(PULVERIZED_DUST.getItem(), 1, 3), new ItemStack(INGOT.getItem(), 1, 1), 0.6F);
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(PULVERIZED_DUST.getItem(), 1, 4), new ItemStack(INGOT.getItem(), 1, 2), 0.9F);
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(PULVERIZED_DUST.getItem(), 1, 5), new ItemStack(INGOT.getItem(), 1, 3), 0.9F);
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(PULVERIZED_DUST.getItem(), 1, 6), new ItemStack(INGOT.getItem(), 1, 4), 0.7F);
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(PULVERIZED_DUST.getItem(), 1, 7), new ItemStack(INGOT.getItem(), 1, 5), 0.9F);
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(PULVERIZED_DUST.getItem(), 1, 8), new ItemStack(INGOT.getItem(), 1, 6), 0.9F);

        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(ORE_BLOCK.getBlock(), 1, 0), new ItemStack(INGOT.getItem(), 1, 0), 0.5F);
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(ORE_BLOCK.getBlock(), 1, 1), new ItemStack(INGOT.getItem(), 1, 1), 0.6F);
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(ORE_BLOCK.getBlock(), 1, 2), new ItemStack(INGOT.getItem(), 1, 2), 0.9F);
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(ORE_BLOCK.getBlock(), 1, 3), new ItemStack(INGOT.getItem(), 1, 3), 0.9F);
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(ORE_BLOCK.getBlock(), 1, 4), new ItemStack(INGOT.getItem(), 1, 4), 0.9F);
    }

    private static void registerMagneticSmelteryCrafting() {
        MagneticSmelteryCraftingRegistry.getInstance().register(new ItemStack(Items.IRON_INGOT), ModFluids.MOLTEN_IRON, FLUID_AMOUNT_PER_INGOT, 20 * 9);
        MagneticSmelteryCraftingRegistry.getInstance().register(new ItemStack(Items.GOLD_INGOT), ModFluids.MOLTEN_GOLD, FLUID_AMOUNT_PER_INGOT, 20 * 10);
        MagneticSmelteryCraftingRegistry.getInstance().register(new ItemStack(INGOT.getItem(), 1, 0), ModFluids.MOLTEN_COPPER, FLUID_AMOUNT_PER_INGOT, 20 * 8);
        MagneticSmelteryCraftingRegistry.getInstance().register(new ItemStack(INGOT.getItem(), 1, 1), ModFluids.MOLTEN_TIN, FLUID_AMOUNT_PER_INGOT, 20 * 8);
        MagneticSmelteryCraftingRegistry.getInstance().register(new ItemStack(INGOT.getItem(), 1, 2), ModFluids.MOLTEN_SILVER, FLUID_AMOUNT_PER_INGOT, 20 * 10);
        MagneticSmelteryCraftingRegistry.getInstance().register(new ItemStack(INGOT.getItem(), 1, 3), ModFluids.MOLTEN_LEAD, FLUID_AMOUNT_PER_INGOT, 20 * 10);
        MagneticSmelteryCraftingRegistry.getInstance().register(new ItemStack(INGOT.getItem(), 1, 4), ModFluids.MOLTEN_NICKEL, FLUID_AMOUNT_PER_INGOT, 20 * 9);
        MagneticSmelteryCraftingRegistry.getInstance().register(new ItemStack(INGOT.getItem(), 1, 5), ModFluids.MOLTEN_INVAR, FLUID_AMOUNT_PER_INGOT, 20 * 9);
        MagneticSmelteryCraftingRegistry.getInstance().register(new ItemStack(INGOT.getItem(), 1, 6), ModFluids.MOLTEN_ELECTRUM, FLUID_AMOUNT_PER_INGOT, 20 * 9);

        MagneticSmelteryCraftingRegistry.getInstance().register(new ItemStack(Blocks.IRON_ORE), ModFluids.MOLTEN_IRON, FLUID_AMOUNT_PER_INGOT * 2, 20 * 18);
        MagneticSmelteryCraftingRegistry.getInstance().register(new ItemStack(Blocks.GOLD_ORE), ModFluids.MOLTEN_GOLD, FLUID_AMOUNT_PER_INGOT * 2, 20 * 20);
        MagneticSmelteryCraftingRegistry.getInstance().register(new ItemStack(ORE_BLOCK.getBlock(), 1, 0), ModFluids.MOLTEN_COPPER, FLUID_AMOUNT_PER_INGOT * 2, 20 * 16);
        MagneticSmelteryCraftingRegistry.getInstance().register(new ItemStack(ORE_BLOCK.getBlock(), 1, 1), ModFluids.MOLTEN_TIN, FLUID_AMOUNT_PER_INGOT * 2, 20 * 16);
        MagneticSmelteryCraftingRegistry.getInstance().register(new ItemStack(ORE_BLOCK.getBlock(), 1, 2), ModFluids.MOLTEN_SILVER, FLUID_AMOUNT_PER_INGOT * 2, 20 * 20);
        MagneticSmelteryCraftingRegistry.getInstance().register(new ItemStack(ORE_BLOCK.getBlock(), 1, 3), ModFluids.MOLTEN_LEAD, FLUID_AMOUNT_PER_INGOT * 2, 20 * 20);
        MagneticSmelteryCraftingRegistry.getInstance().register(new ItemStack(ORE_BLOCK.getBlock(), 1, 4), ModFluids.MOLTEN_NICKEL, FLUID_AMOUNT_PER_INGOT * 2, 20 * 18);
    }

    private static void registerLiquidCasterCrafting() {
        LiquidCasterCraftingRegistry.getInstance().register(
                new FluidStack(ModFluids.MOLTEN_COPPER, FLUID_AMOUNT_PER_INGOT * 4), new ItemStack(CAST.getItem(), 1, 1),
                new ItemStack(GEAR.getItem(), 1, 0), 20 * 8
        );
        LiquidCasterCraftingRegistry.getInstance().register(
                new FluidStack(ModFluids.MOLTEN_TIN, FLUID_AMOUNT_PER_INGOT * 4), new ItemStack(CAST.getItem(), 1, 1),
                new ItemStack(GEAR.getItem(), 1, 1), 20 * 8
        );
        LiquidCasterCraftingRegistry.getInstance().register(
                new FluidStack(ModFluids.MOLTEN_SILVER, FLUID_AMOUNT_PER_INGOT * 4), new ItemStack(CAST.getItem(), 1, 1),
                new ItemStack(GEAR.getItem(), 1, 2), 20 * 8
        );
        LiquidCasterCraftingRegistry.getInstance().register(
                new FluidStack(ModFluids.MOLTEN_LEAD, FLUID_AMOUNT_PER_INGOT * 4), new ItemStack(CAST.getItem(), 1, 1),
                new ItemStack(GEAR.getItem(), 1, 3), 20 * 8
        );

        LiquidCasterCraftingRegistry.getInstance().register(
                new FluidStack(ModFluids.MOLTEN_IRON, FLUID_AMOUNT_PER_INGOT), new ItemStack(CAST.getItem(), 1, 0),
                new ItemStack(Items.IRON_INGOT), 20 * 8
        );
        LiquidCasterCraftingRegistry.getInstance().register(
                new FluidStack(ModFluids.MOLTEN_GOLD, FLUID_AMOUNT_PER_INGOT), new ItemStack(CAST.getItem(), 1, 0),
                new ItemStack(Items.GOLD_INGOT), 20 * 8
        );
        LiquidCasterCraftingRegistry.getInstance().register(
                new FluidStack(ModFluids.MOLTEN_COPPER, FLUID_AMOUNT_PER_INGOT), new ItemStack(CAST.getItem(), 1, 0),
                new ItemStack(INGOT.getItem(), 1, 0), 20 * 8
        );
        LiquidCasterCraftingRegistry.getInstance().register(
                new FluidStack(ModFluids.MOLTEN_TIN, FLUID_AMOUNT_PER_INGOT), new ItemStack(CAST.getItem(), 1, 0),
                new ItemStack(INGOT.getItem(), 1, 1), 20 * 8
        );
        LiquidCasterCraftingRegistry.getInstance().register(
                new FluidStack(ModFluids.MOLTEN_SILVER, FLUID_AMOUNT_PER_INGOT), new ItemStack(CAST.getItem(), 1, 0),
                new ItemStack(INGOT.getItem(), 1, 2), 20 * 8
        );
        LiquidCasterCraftingRegistry.getInstance().register(
                new FluidStack(ModFluids.MOLTEN_LEAD, FLUID_AMOUNT_PER_INGOT), new ItemStack(CAST.getItem(), 1, 0),
                new ItemStack(INGOT.getItem(), 1, 3), 20 * 8
        );
        LiquidCasterCraftingRegistry.getInstance().register(
                new FluidStack(ModFluids.MOLTEN_NICKEL, FLUID_AMOUNT_PER_INGOT), new ItemStack(CAST.getItem(), 1, 0),
                new ItemStack(INGOT.getItem(), 1, 4), 20 * 8
        );
        LiquidCasterCraftingRegistry.getInstance().register(
                new FluidStack(ModFluids.MOLTEN_INVAR, FLUID_AMOUNT_PER_INGOT), new ItemStack(CAST.getItem(), 1, 0),
                new ItemStack(INGOT.getItem(), 1, 5), 20 * 8
        );
        LiquidCasterCraftingRegistry.getInstance().register(
                new FluidStack(ModFluids.MOLTEN_ELECTRUM, FLUID_AMOUNT_PER_INGOT), new ItemStack(CAST.getItem(), 1, 0),
                new ItemStack(INGOT.getItem(), 1, 6), 20 * 8
        );

        LiquidCasterCraftingRegistry.getInstance().register(
                new FluidStack(ModFluids.MOLTEN_IRON, FLUID_AMOUNT_PER_INGOT), new ItemStack(CAST.getItem(), 1, 2),
                new ItemStack(PLATE.getItem(), 1, 0), 20 * 8
        );
        LiquidCasterCraftingRegistry.getInstance().register(
                new FluidStack(ModFluids.MOLTEN_COPPER, FLUID_AMOUNT_PER_INGOT), new ItemStack(CAST.getItem(), 1, 2),
                new ItemStack(PLATE.getItem(), 1, 1), 20 * 8
        );
        LiquidCasterCraftingRegistry.getInstance().register(
                new FluidStack(ModFluids.MOLTEN_TIN, FLUID_AMOUNT_PER_INGOT), new ItemStack(CAST.getItem(), 1, 2),
                new ItemStack(PLATE.getItem(), 1, 2), 20 * 8
        );
    }

    private static void registerAlloyMixerCrafting() {
        AlloyMixerCraftingRegistry.getInstance().register(
                new FluidStack(ModFluids.MOLTEN_INVAR, FLUID_AMOUNT_PER_INGOT * 3),
                new FluidStack(ModFluids.MOLTEN_IRON, FLUID_AMOUNT_PER_INGOT * 2),
                new FluidStack(ModFluids.MOLTEN_NICKEL, FLUID_AMOUNT_PER_INGOT)
        );

        AlloyMixerCraftingRegistry.getInstance().register(
                new FluidStack(ModFluids.MOLTEN_ELECTRUM, FLUID_AMOUNT_PER_INGOT * 2),
                new FluidStack(ModFluids.MOLTEN_GOLD, FLUID_AMOUNT_PER_INGOT),
                new FluidStack(ModFluids.MOLTEN_SILVER, FLUID_AMOUNT_PER_INGOT)
        );
    }
}
