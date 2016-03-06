package at.korti.transmatrics.registry;

import at.korti.transmatrics.item.tool.ItemConnector;
import at.korti.transmatrics.item.tool.ItemWrench;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Korti on 29.02.2016.
 */
public final class Items {

    private static ItemWrench wrench;
    private static ItemConnector connector;

    public static void registerItems() {
        GameRegistry.registerItem(wrench = new ItemWrench());
        GameRegistry.registerItem(connector = new ItemConnector());
    }

    public static void registerItemTextures() {
        registerItemTexture(wrench);
        registerItemTexture(connector);
    }

    private static void registerItemTexture(Item item) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

}
