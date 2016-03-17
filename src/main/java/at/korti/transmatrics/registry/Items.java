package at.korti.transmatrics.registry;

import at.korti.transmatrics.item.ore.ItemPulverizedDust;
import at.korti.transmatrics.item.tool.ItemConnector;
import at.korti.transmatrics.item.tool.ItemWrench;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.LinkedList;
import java.util.List;

import static net.minecraftforge.fml.common.registry.GameRegistry.registerItem;

/**
 * Created by Korti on 29.02.2016.
 */
public final class Items {

    private static ItemWrench wrench;
    private static ItemConnector connector;
    private static ItemPulverizedDust pulverizedDust;

    public static void registerItems() {
        registerItem(wrench = new ItemWrench());
        registerItem(connector = new ItemConnector());
        registerItem(pulverizedDust = new ItemPulverizedDust());
    }

    public static void registerItemTextures() {
        registerItemTexture(wrench);
        registerItemTexture(connector);
        registerMetaItemTexture(pulverizedDust);
    }

    private static void registerItemTexture(Item item) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    private static void registerMetaItemTexture(Item item) {
        List<ItemStack> subItems = new LinkedList<>();
        item.getSubItems(item, null, subItems);
        for (int i = 0; i < subItems.size(); i++) {
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, i, new ModelResourceLocation(item.getRegistryName(), "inventory"));
        }
    }

}
