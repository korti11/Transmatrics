package at.korti.transmatrics;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.TransmatricsItem;
import at.korti.transmatrics.client.gui.GuiHandler;
import at.korti.transmatrics.config.Config;
import at.korti.transmatrics.modintegration.ModIntegrationManager;
import at.korti.transmatrics.network.TransmatricsPacketHandler;
import at.korti.transmatrics.proxy.CommonProxy;
import at.korti.transmatrics.registry.*;
import at.korti.transmatrics.world.OreGeneration;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Logger;

/**
 * Created by Korti on 24.02.2016.
 */
@Mod(modid = Constants.Mod.MODID, name = Constants.Mod.NAME, version = Constants.Mod.VERSION,
        dependencies = "after:" + Constants.ModIntegrationIds.TCONSTRUCT)
public class Transmatrics {

    @Mod.Instance(Constants.Mod.MODID)
    public static Transmatrics instance;

    @SidedProxy(clientSide = Constants.Mod.CLIENT_PROXY, serverSide = Constants.Mod.COMMON_PROXY)
    public static CommonProxy proxy;

    public static Logger logger;

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
        logger = event.getModLog();
        Config.loadConfig(event.getSuggestedConfigurationFile());
        ModIntegrationManager.initManager();
        Fluids.registerFluids();
        TileEntities.registerTileEntities();
        GameRegistry.registerWorldGenerator(new OreGeneration(), 1);
        proxy.preInit(event);
        ModIntegrationManager.preInit(event);
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
        Crafting.register();
        proxy.init(event);
        TransmatricsPacketHandler.init();
        ModIntegrationManager.init(event);
        NetworkRegistry.INSTANCE.registerGuiHandler(Transmatrics.instance, new GuiHandler());
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
        ModIntegrationManager.postInit(event);
    }

}
