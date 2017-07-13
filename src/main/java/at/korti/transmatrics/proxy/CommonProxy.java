package at.korti.transmatrics.proxy;

import at.korti.transmatrics.event.EventHandler;
import at.korti.transmatrics.modintegration.ModIntegrationManager;
import at.korti.transmatrics.registry.BlockRegistry;
import at.korti.transmatrics.registry.ItemRegistry;
import at.korti.transmatrics.registry.OreDicts;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by Korti on 24.02.2016.
 */
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        MinecraftForge.EVENT_BUS.register(new ItemRegistry());
        MinecraftForge.EVENT_BUS.register(new BlockRegistry());
        ModIntegrationManager.preInit(event);
    }

    public void init(FMLInitializationEvent event) {
        ModIntegrationManager.init(event);
    }

    public void postInit(FMLPostInitializationEvent event) {
        ModIntegrationManager.postInit(event);
    }

}
