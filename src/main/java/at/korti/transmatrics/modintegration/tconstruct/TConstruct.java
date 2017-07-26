package at.korti.transmatrics.modintegration.tconstruct;

import at.korti.transmatrics.api.Constants.OreDictionaryEntry;
import at.korti.transmatrics.modintegration.IIntegration;
import at.korti.transmatrics.modintegration.tconstruct.config.TConstructConfig;
import at.korti.transmatrics.modintegration.tconstruct.helper.CraftingCrossOverHelper;
import at.korti.transmatrics.registry.Crafting;
import at.korti.transmatrics.registry.ModFluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.shared.TinkerFluids;

/**
 * Created by Korti on 11.05.2016.
 */
public class TConstruct implements IIntegration {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        TConstructConfig.loadConfig(event.getSuggestedConfigurationFile());
        Crafting.FLUID_AMOUNT_PER_INGOT = Material.VALUE_Ingot;
    }

    @Override
    public void init(FMLInitializationEvent event) {
        initCrafting();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        if(TConstructConfig.canUseSmelteryRecipes) {
            CraftingCrossOverHelper.loadSmelteryCrossOver();
        }
        if(TConstructConfig.canUseTableCastingRecipes) {
            CraftingCrossOverHelper.loadCastingCrossOver();
        }
        CraftingCrossOverHelper.loadAlloyCrossOver();

    }

    @Override
    public void clientPreInit() {

    }

    @Override
    public void clientInit() {

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void clientPostInit() {

    }

    private void initCrafting() {
        //region Ores
        TinkerRegistry.registerMelting(OreDictionaryEntry.ORE_COPPER, ModFluids.MOLTEN_COPPER, Material.VALUE_Ingot * 2);
        TinkerRegistry.registerMelting(OreDictionaryEntry.ORE_LEAD, ModFluids.MOLTEN_LEAD, Material.VALUE_Ingot * 2);
        TinkerRegistry.registerMelting(OreDictionaryEntry.ORE_NICKEL, ModFluids.MOLTEN_NICKEL, Material.VALUE_Ingot * 2);
        TinkerRegistry.registerMelting(OreDictionaryEntry.ORE_SILVER, ModFluids.MOLTEN_SILVER, Material.VALUE_Ingot * 2);
        TinkerRegistry.registerMelting(OreDictionaryEntry.ORE_TIN, ModFluids.MOLTEN_TIN, Material.VALUE_Ingot * 2);
        //endregion

        //region Ingots
        TinkerRegistry.registerMelting(OreDictionaryEntry.INGOT_COPPER, ModFluids.MOLTEN_COPPER, Material.VALUE_Ingot);
        TinkerRegistry.registerMelting(OreDictionaryEntry.INGOT_LEAD, ModFluids.MOLTEN_LEAD, Material.VALUE_Ingot);
        TinkerRegistry.registerMelting(OreDictionaryEntry.INGOT_NICKEL, ModFluids.MOLTEN_NICKEL, Material.VALUE_Ingot);
        TinkerRegistry.registerMelting(OreDictionaryEntry.INGOT_SILVER, ModFluids.MOLTEN_SILVER, Material.VALUE_Ingot);
        TinkerRegistry.registerMelting(OreDictionaryEntry.INGOT_TIN, ModFluids.MOLTEN_TIN, Material.VALUE_Ingot);
        TinkerRegistry.registerMelting(OreDictionaryEntry.INGOT_INVAR, ModFluids.MOLTEN_INVAR, Material.VALUE_Ingot);
        TinkerRegistry.registerMelting(OreDictionaryEntry.INGOT_ELECTRUM, ModFluids.MOLTEN_ELECTRUM, Material.VALUE_Ingot);
        //endregion

        //region Dusts
        TinkerRegistry.registerMelting(OreDictionaryEntry.DUST_IRON, ModFluids.MOLTEN_IRON, Material.VALUE_Ingot);
        TinkerRegistry.registerMelting(OreDictionaryEntry.DUST_GOLD, ModFluids.MOLTEN_GOLD, Material.VALUE_Ingot);
        TinkerRegistry.registerMelting(OreDictionaryEntry.DUST_COPPER, ModFluids.MOLTEN_COPPER, Material.VALUE_Ingot);
        TinkerRegistry.registerMelting(OreDictionaryEntry.DUST_LEAD, ModFluids.MOLTEN_LEAD, Material.VALUE_Ingot);
        TinkerRegistry.registerMelting(OreDictionaryEntry.DUST_NICKEL, ModFluids.MOLTEN_NICKEL, Material.VALUE_Ingot);
        TinkerRegistry.registerMelting(OreDictionaryEntry.DUST_SILVER, ModFluids.MOLTEN_SILVER, Material.VALUE_Ingot);
        TinkerRegistry.registerMelting(OreDictionaryEntry.DUST_TIN, ModFluids.MOLTEN_TIN, Material.VALUE_Ingot);
        TinkerRegistry.registerMelting(OreDictionaryEntry.DUST_INVAR, ModFluids.MOLTEN_INVAR, Material.VALUE_Ingot);
        TinkerRegistry.registerMelting(OreDictionaryEntry.DUST_ELECTRUM, ModFluids.MOLTEN_ELECTRUM, Material.VALUE_Ingot);
        //endregion

        //region Gears
        TinkerRegistry.registerMelting(OreDictionaryEntry.GEAR_COPPER, ModFluids.MOLTEN_COPPER, Material.VALUE_Ingot * 4);
        TinkerRegistry.registerMelting(OreDictionaryEntry.GEAR_LEAD, ModFluids.MOLTEN_LEAD, Material.VALUE_Ingot * 4);
        TinkerRegistry.registerMelting(OreDictionaryEntry.GEAR_SILVER, ModFluids.MOLTEN_SILVER, Material.VALUE_Ingot * 4);
        TinkerRegistry.registerMelting(OreDictionaryEntry.GEAR_TIN, ModFluids.MOLTEN_TIN, Material.VALUE_Ingot * 4);
        //endregion

        //region Plates
        TinkerRegistry.registerMelting(OreDictionaryEntry.PLATE_IRON, ModFluids.MOLTEN_IRON, Material.VALUE_Ingot);
        TinkerRegistry.registerMelting(OreDictionaryEntry.PLATE_COPPER, ModFluids.MOLTEN_COPPER, Material.VALUE_Ingot);
        TinkerRegistry.registerMelting(OreDictionaryEntry.PLATE_TIN, ModFluids.MOLTEN_TIN, Material.VALUE_Ingot);
        //endregion

        TinkerRegistry.registerAlloy(new FluidStack(ModFluids.MOLTEN_INVAR, Crafting.FLUID_AMOUNT_PER_INGOT * 3),
                new FluidStack(ModFluids.MOLTEN_NICKEL, Crafting.FLUID_AMOUNT_PER_INGOT),
                new FluidStack(TinkerFluids.iron, Crafting.FLUID_AMOUNT_PER_INGOT * 2));
        TinkerRegistry.registerAlloy(new FluidStack(ModFluids.MOLTEN_ELECTRUM, Crafting.FLUID_AMOUNT_PER_INGOT * 2),
                new FluidStack(ModFluids.MOLTEN_SILVER, Crafting.FLUID_AMOUNT_PER_INGOT),
                new FluidStack(TinkerFluids.gold, Crafting.FLUID_AMOUNT_PER_INGOT));

    }
}
