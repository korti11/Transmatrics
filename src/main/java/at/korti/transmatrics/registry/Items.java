package at.korti.transmatrics.registry;

import at.korti.transmatrics.api.Constants.Energy;
import at.korti.transmatrics.item.crafting.ItemCast;
import at.korti.transmatrics.item.crafting.ItemElectronics;
import at.korti.transmatrics.item.crafting.ItemGear;
import at.korti.transmatrics.item.crafting.ItemPlate;
import at.korti.transmatrics.item.energy.ItemCapacitor;
import at.korti.transmatrics.item.ore.ItemIngot;
import at.korti.transmatrics.item.ore.ItemPulverizedDust;
import at.korti.transmatrics.item.tool.ItemConnector;
import at.korti.transmatrics.item.tool.ItemHammer;
import at.korti.transmatrics.item.tool.ItemWrench;
import at.korti.transmatrics.util.helper.TextHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.LinkedList;
import java.util.List;

import static at.korti.transmatrics.api.Constants.TransmatricsItem.ELECTRUM_CAPACITOR;
import static at.korti.transmatrics.api.Constants.TransmatricsItem.INVAR_CAPACITOR;
import static at.korti.transmatrics.api.Constants.TransmatricsItem.LEAD_CAPACITOR;
import static net.minecraftforge.fml.common.registry.GameRegistry.registerItem;

/**
 * Created by Korti on 29.02.2016.
 */
public final class Items {

    private static ItemWrench wrench;
    private static ItemConnector connector;
    private static ItemHammer hammer;
    private static ItemPulverizedDust pulverizedDust;
    private static ItemIngot itemIngot;
    private static ItemGear itemGear;
    private static ItemCast itemCast;
    private static ItemElectronics itemElectronics;
    private static ItemPlate itemPlate;
    private static ItemCapacitor itemLeadCapacitor;
    private static ItemCapacitor itemInvarCapacitor;
    private static ItemCapacitor itemElectrumCapacitor;

    public static void registerItemsCommon() {
        registerItem(wrench = new ItemWrench());
        registerItem(connector = new ItemConnector());
        registerItem(hammer = new ItemHammer());
        registerItem(pulverizedDust = new ItemPulverizedDust());
        registerItem(itemIngot = new ItemIngot());
        registerItem(itemGear = new ItemGear());
        registerItem(itemCast = new ItemCast());
        registerItem(itemElectronics = new ItemElectronics());
        registerItem(itemPlate = new ItemPlate());
        registerItem(itemLeadCapacitor = new ItemCapacitor(LEAD_CAPACITOR.getRegName(), Energy.LEAD_CAPACITOR_CAPACITY));
        registerItem(itemInvarCapacitor = new ItemCapacitor(INVAR_CAPACITOR.getRegName(), Energy.INVAR_CAPACITOR_CAPACITY));
        registerItem(itemElectrumCapacitor = new ItemCapacitor(ELECTRUM_CAPACITOR.getRegName(), Energy.ELECTRUM_CAPACITOR_CAPACITY));
    }

    public static void registerItemsClient() {
        addItemVariants(itemCast, itemCast.extensions);
        addItemVariants(itemElectronics, itemElectronics.extensions);
    }

    public static void registerItemTextures() {
        registerItemTexture(wrench);
        registerItemTexture(connector);
        registerItemTexture(hammer);
        registerItemTexture(itemLeadCapacitor);
        registerItemTexture(itemInvarCapacitor);
        registerItemTexture(itemElectrumCapacitor);

        registerMetaItemTexture(pulverizedDust);
        registerMetaItemTexture(itemIngot);
        registerMetaItemTexture(itemGear);
        registerMetaItemTexture(itemCast, itemCast.extensions);
        registerMetaItemTexture(itemElectronics, itemElectronics.extensions);
        registerMetaItemTexture(itemPlate);
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

    private static void registerMetaItemTexture(Item item, String[] extensions) {
        List<ItemStack> subItems = new LinkedList<>();
        item.getSubItems(item, null, subItems);
        for (int i = 0; i < subItems.size(); i++) {
            String extension = TextHelper.firstCharUppercase(extensions[i]);
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().
                    register(item, i, new ModelResourceLocation(item.getRegistryName() + extension, "inventory"));
        }
    }

    private static void addItemVariants(Item item, String[] extensions) {
        List<ItemStack> subItems = new LinkedList<>();
        item.getSubItems(item, null, subItems);
        for(int i = 0; i < subItems.size(); i++) {
            String extension = TextHelper.firstCharUppercase(extensions[i]);
            ModelBakery.registerItemVariants(item,
                    new ResourceLocation(subItems.get(i).getItem().getRegistryName() + extension));
        }
    }

}
