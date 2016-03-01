package at.korti.transmatrics.registry;

import at.korti.transmatrics.block.generator.AdvancedSolarPanel;
import at.korti.transmatrics.block.generator.LavaGenerator;
import at.korti.transmatrics.block.generator.SolarPanel;
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

    public static void registerBlocks() {
        GameRegistry.registerBlock(solarPanel = new SolarPanel());
        GameRegistry.registerBlock(advancedSolarPanel = new AdvancedSolarPanel());
        GameRegistry.registerBlock(lavaGenerator = new LavaGenerator());
    }

    public static void registerBlockTextures() {
        registerBlockTexture(solarPanel);
        registerBlockTexture(advancedSolarPanel);
        registerBlockTexture(lavaGenerator);
    }

    private static void registerBlockTexture(Block block) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }

}
