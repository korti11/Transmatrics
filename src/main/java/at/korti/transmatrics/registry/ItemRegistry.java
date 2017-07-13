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
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import static at.korti.transmatrics.api.Constants.TransmatricsItem.ELECTRUM_CAPACITOR;
import static at.korti.transmatrics.api.Constants.TransmatricsItem.INVAR_CAPACITOR;
import static at.korti.transmatrics.api.Constants.TransmatricsItem.LEAD_CAPACITOR;

/**
 * Created by Korti on 29.02.2016.
 */
public final class ItemRegistry {

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

    private IForgeRegistry<Item> registry;

    @SideOnly(Side.SERVER)
    @SubscribeEvent
    public void handleItemRegisterServer(RegistryEvent.Register<Item> event) {
        this.registry = event.getRegistry();
        this.registerItemsCommon();
        OreDicts.registerOreDictItems();
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void handleItemRegisterClient(RegistryEvent.Register<Item> event) {
        this.registry = event.getRegistry();
        this.registerItemsCommon();
        this.registerItemsClient();
        OreDicts.registerOreDictItems();
    }

    private void registerItemsCommon() {
        registry.register(wrench = new ItemWrench());
        registry.register(connector = new ItemConnector());
        registry.register(hammer = new ItemHammer());
        registry.register(pulverizedDust = new ItemPulverizedDust());
        registry.register(itemIngot = new ItemIngot());
        registry.register(itemGear = new ItemGear());
        registry.register(itemCast = new ItemCast());
        registry.register(itemElectronics = new ItemElectronics());
        registry.register(itemPlate = new ItemPlate());
        registry.register(itemLeadCapacitor = new ItemCapacitor(LEAD_CAPACITOR.getRegName(), Energy.LEAD_CAPACITOR_CAPACITY));
        registry.register(itemInvarCapacitor = new ItemCapacitor(INVAR_CAPACITOR.getRegName(), Energy.INVAR_CAPACITOR_CAPACITY));
        registry.register(itemElectrumCapacitor = new ItemCapacitor(ELECTRUM_CAPACITOR.getRegName(), Energy.ELECTRUM_CAPACITOR_CAPACITY));
    }

    @SideOnly(Side.CLIENT)
    private void registerItemsClient() {
        addItemVariants(itemCast, ItemCast.extensions);
        addItemVariants(itemElectronics, ItemElectronics.extensions);
        registerItemTextures();
        registerColorHandler();
    }

    @SideOnly(Side.CLIENT)
    private void registerItemTextures() {
        registerItemTexture(wrench);
        registerItemTexture(connector);
        registerItemTexture(hammer);
        registerItemTexture(itemLeadCapacitor);
        registerItemTexture(itemInvarCapacitor);
        registerItemTexture(itemElectrumCapacitor);

        registerMetaItemTexture(pulverizedDust);
        registerMetaItemTexture(itemIngot);
        registerMetaItemTexture(itemGear);
        registerMetaItemTexture(itemCast, ItemCast.extensions);
        registerMetaItemTexture(itemElectronics, ItemElectronics.extensions);
        registerMetaItemTexture(itemPlate);
    }

    @SideOnly(Side.CLIENT)
    private void registerColorHandler() {
        FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler(pulverizedDust.colorHandler, pulverizedDust);
        FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler(itemIngot.colorHandler, itemIngot);
        FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler(itemGear.colorHandler, itemGear);
        FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler(itemPlate.colorHandler, itemPlate);
    }

    @SideOnly(Side.CLIENT)
    private void registerItemTexture(Item item) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    @SideOnly(Side.CLIENT)
    private void registerMetaItemTexture(Item item) {
        NonNullList<ItemStack> subItems = NonNullList.create();
        item.getSubItems(null, subItems);
        for (int i = 0; i < subItems.size(); i++) {
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, i, new ModelResourceLocation(item.getRegistryName(), "inventory"));
        }
    }

    @SideOnly(Side.CLIENT)
    private void registerMetaItemTexture(Item item, String[] extensions) {
        NonNullList<ItemStack> subItems = NonNullList.create();
        item.getSubItems(null, subItems);
        for (int i = 0; i < subItems.size(); i++) {
            String extension = TextHelper.firstCharUppercase(extensions[i]);
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().
                    register(item, i, new ModelResourceLocation(item.getRegistryName() + extension, "inventory"));
        }
    }

    @SideOnly(Side.CLIENT)
    private void addItemVariants(Item item, String[] extensions) {
        NonNullList<ItemStack> subItems = NonNullList.create();
        item.getSubItems(null, subItems);
        for(int i = 0; i < subItems.size(); i++) {
            String extension = TextHelper.firstCharUppercase(extensions[i]);
            ModelBakery.registerItemVariants(item,
                    new ResourceLocation(subItems.get(i).getItem().getRegistryName() + extension));
        }
    }

}
