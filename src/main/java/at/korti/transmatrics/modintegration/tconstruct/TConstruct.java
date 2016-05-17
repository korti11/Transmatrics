package at.korti.transmatrics.modintegration.tconstruct;

import at.korti.transmatrics.api.Constants.TransmatricsItem;
import at.korti.transmatrics.api.Constants.TransmatricsTileEntity;
import at.korti.transmatrics.item.energy.ItemCapacitor;
import at.korti.transmatrics.modintegration.IIntegration;
import at.korti.transmatrics.modintegration.tconstruct.block.AlloyMixer;
import at.korti.transmatrics.modintegration.tconstruct.config.TConstructConfig;
import at.korti.transmatrics.modintegration.tconstruct.helper.CraftingCrossOverHelper;
import at.korti.transmatrics.modintegration.tconstruct.proxy.ToolClientProxy;
import at.korti.transmatrics.modintegration.tconstruct.tileentity.TileEntityAlloyMixer;
import at.korti.transmatrics.modintegration.tconstruct.tools.modifier.ModEnergetic;
import at.korti.transmatrics.registry.Crafting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;

/**
 * Created by Korti on 11.05.2016.
 */
public class TConstruct implements IIntegration {

    private static AlloyMixer alloyMixer;

    public static ModEnergetic modEnergetic;

    // client stuff
    private ToolClientProxy clientProxy;

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        TConstructConfig.loadConfig(event.getSuggestedConfigurationFile());
        Crafting.FLUID_AMOUNT_PER_INGOT = Material.VALUE_Ingot;
        GameRegistry.registerTileEntity(TileEntityAlloyMixer.class, TransmatricsTileEntity.ALLOY_MIXER.getRegName());
        GameRegistry.registerBlock(alloyMixer = new AlloyMixer());
    }

    @Override
    public void init(FMLInitializationEvent event) {

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

        TinkerRegistry.registerModifier(modEnergetic = new ModEnergetic(
                (ItemCapacitor) TransmatricsItem.LEAD_CAPACITOR.getItem()));
    }

    @Override
    public void clientPreInit() {
        clientProxy = new ToolClientProxy();
    }

    @Override
    public void clientInit() {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().
                register(Item.getItemFromBlock(alloyMixer), 0,
                        new ModelResourceLocation(alloyMixer.getRegistryName(), "inventory"));
    }

    @Override
    public void clientPostInit() {
        clientProxy.registerModels();
    }
}
