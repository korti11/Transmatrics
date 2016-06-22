package at.korti.transmatrics.registry;

import at.korti.transmatrics.item.crafting.ItemCast;
import at.korti.transmatrics.item.crafting.ItemPlate;
import at.korti.transmatrics.registry.crafting.LiquidCasterCraftingRegistry;
import at.korti.transmatrics.registry.crafting.MagneticSmelteryCraftingRegistry;
import at.korti.transmatrics.registry.crafting.PulverizerCraftingRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import static at.korti.transmatrics.api.Constants.TransmatricsBlock.*;
import static at.korti.transmatrics.api.Constants.TransmatricsItem.*;

/**
 * Created by Korti on 16.03.2016.
 */
public final class Crafting {

    public static int FLUID_AMOUNT_PER_INGOT = 500;

    public static void register() {
        registerCrafting();
        registerPulverizerCrafting();
        registerFurnaceCrafting();
        registerMagneticSmelteryCrafting();
        registerLiquidCasterCrafting();
    }

    private static void registerCrafting() {
        // solar panel
        registerShapedOreRecipe(new ItemStack(SOLAR_PANEL.getBlock()),
                "   ",
                "DDD",
                "ICI", 'D', new ItemStack(Blocks.DAYLIGHT_DETECTOR), 'C', new ItemStack(ELECTRONICS.getItem()), 'I',
                new ItemStack(Items.IRON_INGOT)
        );
        // advanced solar panel
        registerShapedOreRecipe(new ItemStack(ADVANCED_SOLAR_PANEL.getBlock()),
                " S ",
                "SCS",
                " S ", 'S', new ItemStack(SOLAR_PANEL.getBlock()), 'C', new ItemStack(ELECTRONICS.getItem(), 1, 1)
        );
        // lava generator
        registerShapedOreRecipe(new ItemStack(LAVA_GENERATOR.getBlock()),
                "IBI",
                "BMB",
                "ICI", 'B', new ItemStack(Items.BUCKET), 'M', new ItemStack(MACHINE_CASING.getBlock()), 'I',
                new ItemStack(Items.IRON_INGOT), 'C', new ItemStack(ELECTRONICS.getItem())
        );
        // watermill
        registerShapedOreRecipe(new ItemStack(WATERMILL.getBlock()),
                "IWI",
                "GMG",
                "ICI", 'W', new ItemStack(Items.WATER_BUCKET), 'M', new ItemStack(MACHINE_CASING.getBlock()), 'I',
                new ItemStack(Items.IRON_INGOT), 'C', new ItemStack(ELECTRONICS.getItem()), 'G',
                new ItemStack(GEAR.getItem(), 1, 1)
        );
        // windmill
        registerShapedOreRecipe(new ItemStack(WINDMILL.getBlock()),
                "III",
                "GMG",
                "ICI", 'M', new ItemStack(MACHINE_CASING.getBlock()), 'I', new ItemStack(Items.IRON_INGOT),
                'C', new ItemStack(ELECTRONICS.getItem()), 'G', new ItemStack(GEAR.getItem(), 1, 1)
        );
        // thermal generator
        registerShapedOreRecipe(new ItemStack(THERMAL_GENERATOR.getBlock()),
                "III",
                "LML",
                "ICI", 'M', new ItemStack(MACHINE_CASING.getBlock()), 'I', new ItemStack(Items.IRON_INGOT),
                'C', new ItemStack(ELECTRONICS.getItem(), 1, 1), 'L', new ItemStack(Items.LAVA_BUCKET)
        );
        // controller
        registerShapedOreRecipe(new ItemStack(CONTROLLER.getBlock()),
                "ICI",
                "CMC",
                "ITI", 'T', new ItemStack(ELECTRONICS.getItem(), 1, 4), 'M', new ItemStack(MACHINE_CASING.getBlock()), 'I',
                new ItemStack(Items.IRON_INGOT), 'C', new ItemStack(ELECTRONICS.getItem(), 1, 1)
        );
        // small switch
        registerShapedOreRecipe(new ItemStack(SMALL_SWITCH.getBlock()),
                "ICI",
                "CMC",
                "ITI", 'T', new ItemStack(ELECTRONICS.getItem(), 1, 2), 'M', new ItemStack(MACHINE_CASING.getBlock()), 'I',
                new ItemStack(Items.IRON_INGOT), 'C', new ItemStack(ELECTRONICS.getItem())
        );
        // medium switch
        registerShapedOreRecipe(new ItemStack(MEDIUM_SWITCH.getBlock()),
                "ICI",
                "CMC",
                "ITI", 'T', new ItemStack(ELECTRONICS.getItem(), 1, 3), 'M', new ItemStack(MACHINE_CASING.getBlock()), 'I',
                new ItemStack(Items.IRON_INGOT), 'C', new ItemStack(ELECTRONICS.getItem())
        );
        // large switch
        registerShapedOreRecipe(new ItemStack(LARGE_SWITCH.getBlock()),
                "ICI",
                "CMC",
                "ITI", 'T', new ItemStack(ELECTRONICS.getItem(), 1, 4), 'M', new ItemStack(MACHINE_CASING.getBlock()), 'I',
                new ItemStack(Items.IRON_INGOT), 'C', new ItemStack(ELECTRONICS.getItem())
        );
        // pulverizer
        registerShapedOreRecipe(new ItemStack(PULVERIZER.getBlock()),
                "FFF",
                "IMI",
                "ICI", 'M', new ItemStack(MACHINE_CASING.getBlock()), 'I', new ItemStack(Items.IRON_INGOT),
                'C', new ItemStack(ELECTRONICS.getItem()), 'F', new ItemStack(Items.FLINT)
        );
        // powered furnace
        registerShapedOreRecipe(new ItemStack(POWERED_FURNACE.getBlock()),
                "IFI",
                "IMI",
                "ICI", 'M', new ItemStack(MACHINE_CASING.getBlock()), 'I', new ItemStack(Items.IRON_INGOT),
                'C', new ItemStack(ELECTRONICS.getItem()), 'F', new ItemStack(Blocks.FURNACE)
        );
        // magnetic smeltery
        registerShapedOreRecipe(new ItemStack(MAGNETIC_SMELTERY.getBlock()),
                "ILI",
                "IMI",
                "ICI", 'M', new ItemStack(MACHINE_CASING.getBlock()), 'I', new ItemStack(Items.IRON_INGOT),
                'C', new ItemStack(ELECTRONICS.getItem()), 'L', new ItemStack(Items.LAVA_BUCKET)
        );
        // liquid caster
        for (int i = 0; i < ItemCast.extensions.length; i++) {
            registerShapedOreRecipe(new ItemStack(LIQUID_CASTER.getBlock()),
                    "IAI",
                    "IMI",
                    "ICI", 'M', new ItemStack(MACHINE_CASING.getBlock()), 'I', new ItemStack(Items.IRON_INGOT),
                    'C', new ItemStack(ELECTRONICS.getItem()), 'A', new ItemStack(CAST.getItem(), 1, i)
            );
        }
        // machine casing
        registerShapedOreRecipe(new ItemStack(MACHINE_CASING.getBlock()),
                "BPB",
                "P P",
                "BPB", 'B', new ItemStack(Blocks.IRON_BARS), 'P', new ItemStack(PLATE.getItem(), 1, 0)
        );
        // basic circuit
        registerShapedOreRecipe(new ItemStack(ELECTRONICS.getItem()),
                "TRT",
                "RPR",
                "TRT", 'R', new ItemStack(Items.REDSTONE), 'P', new ItemStack(PLATE.getItem(), 1, 1), 'T',
                new ItemStack(PLATE.getItem(), 1, 2)
        );
        // advanced circuit
        registerShapedOreRecipe(new ItemStack(ELECTRONICS.getItem(), 1, 1),
                "SLS",
                "LCL",
                "SLS", 'L', new ItemStack(Items.DYE, 1, 4), 'S', new ItemStack(INGOT.getItem(), 1, 2), 'C',
                new ItemStack(ELECTRONICS.getItem())
        );
        // short transmitter
        registerShapedOreRecipe(new ItemStack(ELECTRONICS.getItem(), 1, 2),
                "P",
                "I", 'P', new ItemStack(Items.ENDER_PEARL), 'I', new ItemStack(Items.IRON_INGOT)
        );
        // transmitter
        registerShapedOreRecipe(new ItemStack(ELECTRONICS.getItem(), 1, 3),
                "P",
                "T",
                "P", 'P', new ItemStack(Items.ENDER_PEARL), 'T', new ItemStack(ELECTRONICS.getItem(), 1, 2)
        );
        registerShapedOreRecipe(new ItemStack(ELECTRONICS.getItem(), 1, 3),
                "   ",
                "PTP",
                "   ", 'P', new ItemStack(Items.ENDER_PEARL), 'T', new ItemStack(ELECTRONICS.getItem(), 1, 2)
        );
        // long transmitter
        registerShapedOreRecipe(new ItemStack(ELECTRONICS.getItem(), 1, 4),
                " P ",
                "PTP",
                " P ", 'P', new ItemStack(Items.ENDER_PEARL), 'T', new ItemStack(ELECTRONICS.getItem(), 1, 3)
        );
        // ingot casting
        registerShapedOreRecipe(new ItemStack(CAST.getItem()),
                "PPP",
                "PIP",
                "PPP", 'P', new ItemStack(PLATE.getItem()), 'I', new ItemStack(Items.IRON_INGOT)
        );
        // gear casting
        registerShapedOreRecipe(new ItemStack(CAST.getItem(), 1, 1),
                "IPI",
                "PPP",
                "IPI", 'I', new ItemStack(Items.IRON_INGOT), 'P', new ItemStack(PLATE.getItem())
        );
        // plate casting
        for (int i = 0; i < ItemPlate.extensions.length; i++) {
            registerShapedOreRecipe(new ItemStack(CAST.getItem(), 1, 2),
                    "III",
                    "IPI",
                    "III", 'I', new ItemStack(Items.IRON_INGOT), 'P', new ItemStack(PLATE.getItem(), 1, i)
            );
        }
        // wrench
        registerShapedOreRecipe(new ItemStack(WRENCH.getItem()),
                "P P",
                " I ",
                " I ", 'I', new ItemStack(Items.IRON_INGOT), 'P', new ItemStack(PLATE.getItem(), 1, 2)
        );
        // connector
        registerShapedOreRecipe(new ItemStack(CONNECTOR.getItem()),
                "   ",
                "T  ",
                "PCP", 'T', new ItemStack(ELECTRONICS.getItem(), 1, 3), 'P', new ItemStack(PLATE.getItem()), 'C',
                new ItemStack(ELECTRONICS.getItem())
        );
        // hammer
        registerShapedOreRecipe(new ItemStack(HAMMER.getItem()),
                " I ",
                " SI",
                "S  ", 'I', new ItemStack(Items.IRON_INGOT), 'S', new ItemStack(Items.STICK)
        );
        // lead capacitor
        registerShapedOreRecipe(new ItemStack(LEAD_CAPACITOR.getItem(), 1, LEAD_CAPACITOR.getItem().getMaxDamage()),
                "LLL",
                "CRC",
                "LLL", 'L', new ItemStack(INGOT.getItem(), 1, 3), 'C', new ItemStack(INGOT.getItem()), 'R',
                new ItemStack(Items.REDSTONE)
        );
        // invar capacitor
        registerShapedOreRecipe(new ItemStack(INVAR_CAPACITOR.getItem(), 1, INVAR_CAPACITOR.getItem().getMaxDamage()),
                " I ",
                "ICI",
                " I ", 'I', new ItemStack(INGOT.getItem(), 1, 5), 'C',
                new ItemStack(LEAD_CAPACITOR.getItem(), 1, OreDictionary.WILDCARD_VALUE)
        );
        // electrum capacitor
        registerShapedOreRecipe(new ItemStack(ELECTRUM_CAPACITOR.getItem(), 1,
                        ELECTRUM_CAPACITOR.getItem().getMaxDamage()),
                " I ",
                "ICI",
                " I ", 'I', new ItemStack(INGOT.getItem(), 1, 6), 'C',
                new ItemStack(INVAR_CAPACITOR.getItem(), 1, OreDictionary.WILDCARD_VALUE)
        );
        // pulverized invar
        registerShapelessOreRecipe(new ItemStack(PULVERIZED_DUST.getItem(), 3, 7),
                new ItemStack(PULVERIZED_DUST.getItem(), 1, 6), new ItemStack(PULVERIZED_DUST.getItem()),
                new ItemStack(PULVERIZED_DUST.getItem())
        );
        // pulverized electrum
        registerShapelessOreRecipe(new ItemStack(PULVERIZED_DUST.getItem(), 2, 8),
                new ItemStack(PULVERIZED_DUST.getItem(), 1, 1), new ItemStack(PULVERIZED_DUST.getItem(), 1, 4)
        );
        // iron plate
        registerShapelessOreRecipe(new ItemStack(PLATE.getItem()),
                new ItemStack(HAMMER.getItem(), 1, OreDictionary.WILDCARD_VALUE),
                new ItemStack(Items.IRON_INGOT)
        );
        for (int i = 1; i < ItemPlate.extensions.length; i++) {
            registerShapelessOreRecipe(new ItemStack(PLATE.getItem(), 1, i),
                    new ItemStack(HAMMER.getItem(), 1, OreDictionary.WILDCARD_VALUE),
                    new ItemStack(INGOT.getItem(), 1, i - 1)
            );
        }

    }

    private static void registerShapedOreRecipe(ItemStack output, Object... recipe) {
        GameRegistry.addRecipe(new ShapedOreRecipe(output, recipe));
    }

    private static void registerShapelessOreRecipe(ItemStack output, Object... recipe) {
        GameRegistry.addRecipe(new ShapelessOreRecipe(output, recipe));
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

        PulverizerCraftingRegistry.getInstance().register(new ItemStack(Items.iron_ingot), 10 * 7, new ItemStack(PULVERIZED_DUST.getItem(), 1, 0));
        PulverizerCraftingRegistry.getInstance().register(new ItemStack(Items.gold_ingot), 10 * 9, new ItemStack(PULVERIZED_DUST.getItem(), 1, 1));

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
    }

    private static void registerMagneticSmelteryCrafting() {
        MagneticSmelteryCraftingRegistry.getInstance().register(new ItemStack(INGOT.getItem(), 1, 0), Fluids.moltenCopper, FLUID_AMOUNT_PER_INGOT, 20 * 8);
        MagneticSmelteryCraftingRegistry.getInstance().register(new ItemStack(INGOT.getItem(), 1, 1), Fluids.moltenTin, FLUID_AMOUNT_PER_INGOT, 20 * 8);
        MagneticSmelteryCraftingRegistry.getInstance().register(new ItemStack(INGOT.getItem(), 1, 2), Fluids.moltenSilver, FLUID_AMOUNT_PER_INGOT, 20 * 10);
        MagneticSmelteryCraftingRegistry.getInstance().register(new ItemStack(INGOT.getItem(), 1, 3), Fluids.moltenLead, FLUID_AMOUNT_PER_INGOT, 20 * 10);
        MagneticSmelteryCraftingRegistry.getInstance().register(new ItemStack(INGOT.getItem(), 1, 4), Fluids.moltenNickel, FLUID_AMOUNT_PER_INGOT, 20 * 9);
        MagneticSmelteryCraftingRegistry.getInstance().register(new ItemStack(INGOT.getItem(), 1, 5), Fluids.moltenInvar, FLUID_AMOUNT_PER_INGOT, 20 * 9);
        MagneticSmelteryCraftingRegistry.getInstance().register(new ItemStack(INGOT.getItem(), 1, 6), Fluids.moltenElectrum, FLUID_AMOUNT_PER_INGOT, 20 * 9);

        MagneticSmelteryCraftingRegistry.getInstance().register(new ItemStack(ORE_BLOCK.getBlock(), 1, 0), Fluids.moltenCopper, FLUID_AMOUNT_PER_INGOT * 2, 20 * 16);
        MagneticSmelteryCraftingRegistry.getInstance().register(new ItemStack(ORE_BLOCK.getBlock(), 1, 1), Fluids.moltenTin, FLUID_AMOUNT_PER_INGOT * 2, 20 * 16);
        MagneticSmelteryCraftingRegistry.getInstance().register(new ItemStack(ORE_BLOCK.getBlock(), 1, 2), Fluids.moltenSilver, FLUID_AMOUNT_PER_INGOT * 2, 20 * 20);
        MagneticSmelteryCraftingRegistry.getInstance().register(new ItemStack(ORE_BLOCK.getBlock(), 1, 3), Fluids.moltenLead, FLUID_AMOUNT_PER_INGOT * 2, 20 * 20);
        MagneticSmelteryCraftingRegistry.getInstance().register(new ItemStack(ORE_BLOCK.getBlock(), 1, 4), Fluids.moltenNickel, FLUID_AMOUNT_PER_INGOT * 2, 20 * 18);
    }

    private static void registerLiquidCasterCrafting() {
        LiquidCasterCraftingRegistry.getInstance().register(
                new FluidStack(Fluids.moltenCopper, FLUID_AMOUNT_PER_INGOT * 4), new ItemStack(CAST.getItem(), 1, 1),
                new ItemStack(GEAR.getItem(), 1, 0), 20 * 8
        );
        LiquidCasterCraftingRegistry.getInstance().register(
                new FluidStack(Fluids.moltenTin, FLUID_AMOUNT_PER_INGOT * 4), new ItemStack(CAST.getItem(), 1, 1),
                new ItemStack(GEAR.getItem(), 1, 1), 20 * 8
        );
        LiquidCasterCraftingRegistry.getInstance().register(
                new FluidStack(Fluids.moltenSilver, FLUID_AMOUNT_PER_INGOT * 4), new ItemStack(CAST.getItem(), 1, 1),
                new ItemStack(GEAR.getItem(), 1, 2), 20 * 8
        );
        LiquidCasterCraftingRegistry.getInstance().register(
                new FluidStack(Fluids.moltenLead, FLUID_AMOUNT_PER_INGOT * 4), new ItemStack(CAST.getItem(), 1, 1),
                new ItemStack(GEAR.getItem(), 1, 3), 20 * 8
        );

        LiquidCasterCraftingRegistry.getInstance().register(
                new FluidStack(Fluids.moltenCopper, FLUID_AMOUNT_PER_INGOT), new ItemStack(CAST.getItem(), 1, 0),
                new ItemStack(INGOT.getItem(), 1, 0), 20 * 8
        );
        LiquidCasterCraftingRegistry.getInstance().register(
                new FluidStack(Fluids.moltenTin, FLUID_AMOUNT_PER_INGOT), new ItemStack(CAST.getItem(), 1, 0),
                new ItemStack(INGOT.getItem(), 1, 1), 20 * 8
        );
        LiquidCasterCraftingRegistry.getInstance().register(
                new FluidStack(Fluids.moltenSilver, FLUID_AMOUNT_PER_INGOT), new ItemStack(CAST.getItem(), 1, 0),
                new ItemStack(INGOT.getItem(), 1, 2), 20 * 8
        );
        LiquidCasterCraftingRegistry.getInstance().register(
                new FluidStack(Fluids.moltenLead, FLUID_AMOUNT_PER_INGOT), new ItemStack(CAST.getItem(), 1, 0),
                new ItemStack(INGOT.getItem(), 1, 3), 20 * 8
        );
        LiquidCasterCraftingRegistry.getInstance().register(
                new FluidStack(Fluids.moltenNickel, FLUID_AMOUNT_PER_INGOT), new ItemStack(CAST.getItem(), 1, 0),
                new ItemStack(INGOT.getItem(), 1, 4), 20 * 8
        );
        LiquidCasterCraftingRegistry.getInstance().register(
                new FluidStack(Fluids.moltenInvar, FLUID_AMOUNT_PER_INGOT), new ItemStack(CAST.getItem(), 1, 0),
                new ItemStack(INGOT.getItem(), 1, 5), 20 * 8
        );
        LiquidCasterCraftingRegistry.getInstance().register(
                new FluidStack(Fluids.moltenElectrum, FLUID_AMOUNT_PER_INGOT), new ItemStack(CAST.getItem(), 1, 0),
                new ItemStack(INGOT.getItem(), 1, 6), 20 * 8
        );

        LiquidCasterCraftingRegistry.getInstance().register(
                new FluidStack(Fluids.moltenCopper, FLUID_AMOUNT_PER_INGOT), new ItemStack(CAST.getItem(), 1, 2),
                new ItemStack(PLATE.getItem(), 1, 1), 20 * 8
        );
        LiquidCasterCraftingRegistry.getInstance().register(
                new FluidStack(Fluids.moltenTin, FLUID_AMOUNT_PER_INGOT), new ItemStack(CAST.getItem(), 1, 2),
                new ItemStack(PLATE.getItem(), 1, 2), 20 * 8
        );
    }
}
