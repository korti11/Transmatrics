package at.korti.transmatrics.proxy;

import at.korti.transmatrics.modintegration.ModIntegrationManager;
import at.korti.transmatrics.registry.Blocks;
import at.korti.transmatrics.registry.Items;
import at.korti.transmatrics.registry.OreDicts;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by Korti on 24.02.2016.
 */
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        Items.registerItemsCommon();
        Blocks.registerBlocksCommon();
        OreDicts.registerOreDict();
        ModIntegrationManager.preInit(event);
    }

    public void init(FMLInitializationEvent event) {
        ModIntegrationManager.init(event);
    }

    public void postInit(FMLPostInitializationEvent event) {
        ModIntegrationManager.postInit(event);
    }

}
