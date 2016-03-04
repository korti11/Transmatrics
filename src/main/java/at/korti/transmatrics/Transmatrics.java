package at.korti.transmatrics;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.TransmatricsItem;
import at.korti.transmatrics.block.ModBlock;
import at.korti.transmatrics.modintegration.ModIntegrationManager;
import at.korti.transmatrics.proxy.CommonProxy;
import at.korti.transmatrics.registry.Blocks;
import at.korti.transmatrics.registry.Fluids;
import at.korti.transmatrics.registry.Items;
import at.korti.transmatrics.registry.TileEntities;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by Korti on 24.02.2016.
 */
@Mod(modid = Constants.Mod.MODID, name = Constants.Mod.NAME, version = Constants.Mod.VERSION)
public class Transmatrics {

    @Mod.Instance(Constants.Mod.MODID)
    public static Transmatrics instance;

    @SidedProxy(clientSide = Constants.Mod.CLIENT_PROXY, serverSide = Constants.Mod.COMMON_PROXY)
    public static CommonProxy proxy;

    static {
        FluidRegistry.enableUniversalBucket();
    }

    public static CreativeTabs creativeTab = new CreativeTabs(Constants.Mod.CREATIVE_TAB_LABEL) {
        @Override
        public Item getTabIconItem() {
            return TransmatricsItem.WRENCH.getItem();
        }
    };

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        ModIntegrationManager.initManager();
        Items.registerItems();
        Blocks.registerBlocks();
        Fluids.registerFluids();
        TileEntities.registerTileEntities();
        proxy.preInit(event);
        ModIntegrationManager.preInit();
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
        Items.registerItemTextures();
        Blocks.registerBlockTextures();
        proxy.init(event);
        ModIntegrationManager.init();
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
        ModIntegrationManager.postInit();
    }

}
