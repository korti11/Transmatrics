package at.korti.transmatrics.registry;

import at.korti.transmatrics.block.crafting.Pulverizer;
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

import static net.minecraftforge.fml.common.registry.GameRegistry.registerBlock;

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
    private static Pulverizer pulverizer;

    public static void registerBlocks() {
        registerBlock(solarPanel = new SolarPanel());
        registerBlock(advancedSolarPanel = new AdvancedSolarPanel());
        registerBlock(lavaGenerator = new LavaGenerator());
        registerBlock(thermalGenerator = new ThermalGenerator());
        registerBlock(hydrogenGenerator = new HydrogenGenerator());
        registerBlock(windmill = new Windmill());
        registerBlock(watermill = new Watermill());
        registerBlock(smallSwitch = new SmallSwitch());
        registerBlock(mediumSwitch = new MediumSwitch());
        registerBlock(largeSwitch = new LargeSwitch());
        registerBlock(controller = new Controller());
        registerBlock(pulverizer = new Pulverizer());
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
        registerBlockTexture(pulverizer);
    }

    private static void registerBlockTexture(Block block) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }

}
