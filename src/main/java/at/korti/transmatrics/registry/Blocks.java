package at.korti.transmatrics.registry;

import at.korti.transmatrics.block.generator.*;
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

    public static void registerBlocks() {
        GameRegistry.registerBlock(solarPanel = new SolarPanel());
        GameRegistry.registerBlock(advancedSolarPanel = new AdvancedSolarPanel());
        GameRegistry.registerBlock(lavaGenerator = new LavaGenerator());
        GameRegistry.registerBlock(thermalGenerator = new ThermalGenerator());
        GameRegistry.registerBlock(hydrogenGenerator = new HydrogenGenerator());
    }

    public static void registerBlockTextures() {
        registerBlockTexture(solarPanel);
        registerBlockTexture(advancedSolarPanel);
        registerBlockTexture(lavaGenerator);
        registerBlockTexture(thermalGenerator);
        registerBlockTexture(hydrogenGenerator);
    }

    private static void registerBlockTexture(Block block) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }

}
