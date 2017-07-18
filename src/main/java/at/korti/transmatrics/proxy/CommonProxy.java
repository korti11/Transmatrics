package at.korti.transmatrics.proxy;

import at.korti.transmatrics.Transmatrics;
import at.korti.transmatrics.client.gui.GuiHandler;
import at.korti.transmatrics.event.EventHandler;
import at.korti.transmatrics.modintegration.ModIntegrationManager;
import at.korti.transmatrics.network.TransmatricsPacketHandler;
import at.korti.transmatrics.registry.Crafting;
import at.korti.transmatrics.registry.ModFluids;
import at.korti.transmatrics.registry.TileEntities;
import at.korti.transmatrics.world.OreGeneration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Korti on 24.02.2016.
 */
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        ModIntegrationManager.initManager();
        TileEntities.registerTileEntities();
        GameRegistry.registerWorldGenerator(new OreGeneration(), 1);
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        ModIntegrationManager.preInit(event);
    }

    public void init(FMLInitializationEvent event) {
        Crafting.register();
        ModFluids.registerFluidContainers();
        TransmatricsPacketHandler.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(Transmatrics.instance, new GuiHandler());
        ModIntegrationManager.init(event);
    }

    public void postInit(FMLPostInitializationEvent event) {
        ModIntegrationManager.postInit(event);
    }

}
