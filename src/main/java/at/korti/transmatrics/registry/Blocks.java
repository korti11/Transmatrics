package at.korti.transmatrics.registry;

import at.korti.transmatrics.block.generator.*;
import at.korti.transmatrics.block.network.Controller;
import at.korti.transmatrics.block.network.LargeSwitch;
import at.korti.transmatrics.block.network.MediumSwitch;
import at.korti.transmatrics.block.network.SmallSwitch;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Korti on 01.03.2016.
 */
public final class Blocks {

    private static SolarPanel solarPanel;
    private static AdvancedSolarPanel advancedSolarPanel;
    private static LavaGenerator lavaGenerator;
    private static ThermalGenerator thermalGenerator;
    private static HydrogenGenerator hydrogenGenerator;
    private static Windmill windmill;
    private static Watermill watermill;
    private static SmallSwitch smallSwitch;
    private static MediumSwitch mediumSwitch;
    private static LargeSwitch largeSwitch;
    private static Controller controller;

    public static void registerBlocks() {
        GameRegistry.registerBlock(solarPanel = new SolarPanel());
        GameRegistry.registerBlock(advancedSolarPanel = new AdvancedSolarPanel());
        GameRegistry.registerBlock(lavaGenerator = new LavaGenerator());
        GameRegistry.registerBlock(thermalGenerator = new ThermalGenerator());
        GameRegistry.registerBlock(hydrogenGenerator = new HydrogenGenerator());
        GameRegistry.registerBlock(windmill = new Windmill());
        GameRegistry.registerBlock(watermill = new Watermill());
        GameRegistry.registerBlock(smallSwitch = new SmallSwitch());
        GameRegistry.registerBlock(mediumSwitch = new MediumSwitch());
        GameRegistry.registerBlock(largeSwitch = new LargeSwitch());
        GameRegistry.registerBlock(controller = new Controller());
    }

    public static void registerBlockTextures() {
        registerBlockTexture(solarPanel);
        registerBlockTexture(advancedSolarPanel);
        registerBlockTexture(lavaGenerator);
        registerBlockTexture(thermalGenerator);
        registerBlockTexture(hydrogenGenerator);
        registerBlockTexture(windmill);
        registerBlockTexture(watermill);
        registerBlockTexture(smallSwitch);
        registerBlockTexture(mediumSwitch);
        registerBlockTexture(largeSwitch);
        registerBlockTexture(controller);
    }

    private static void registerBlockTexture(Block block) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }

}
