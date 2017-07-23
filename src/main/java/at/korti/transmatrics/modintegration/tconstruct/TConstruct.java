package at.korti.transmatrics.modintegration.tconstruct;

import at.korti.transmatrics.api.Constants.TransmatricsTileEntity;
import at.korti.transmatrics.modintegration.IIntegration;
import at.korti.transmatrics.block.crafting.AlloyMixer;
import at.korti.transmatrics.modintegration.tconstruct.config.TConstructConfig;
import at.korti.transmatrics.modintegration.tconstruct.helper.CraftingCrossOverHelper;
import at.korti.transmatrics.tileentity.crafting.TileEntityAlloyMixer;
import at.korti.transmatrics.registry.Crafting;
import at.korti.transmatrics.registry.ModFluids;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.shared.TinkerFluids;

/**
 * Created by Korti on 11.05.2016.
 */
public class TConstruct implements IIntegration {

    private static AlloyMixer alloyMixer;

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
        TinkerRegistry.registerAlloy(new FluidStack(ModFluids.MOLTEN_INVAR, Crafting.FLUID_AMOUNT_PER_INGOT * 3),
                new FluidStack(ModFluids.MOLTEN_NICKEL, Crafting.FLUID_AMOUNT_PER_INGOT),
                new FluidStack(TinkerFluids.iron, Crafting.FLUID_AMOUNT_PER_INGOT * 2));
        TinkerRegistry.registerAlloy(new FluidStack(ModFluids.MOLTEN_ELECTRUM, Crafting.FLUID_AMOUNT_PER_INGOT * 2),
                new FluidStack(ModFluids.MOLTEN_SILVER, Crafting.FLUID_AMOUNT_PER_INGOT),
                new FluidStack(TinkerFluids.gold, Crafting.FLUID_AMOUNT_PER_INGOT));

    }
}
