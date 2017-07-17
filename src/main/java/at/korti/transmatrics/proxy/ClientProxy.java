package at.korti.transmatrics.proxy;

import at.korti.transmatrics.client.renderer.FluidRenderer;
import at.korti.transmatrics.client.util.ClientEventHandler;
import at.korti.transmatrics.modintegration.ModIntegrationManager;
import at.korti.transmatrics.registry.ModItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Korti on 24.02.2016.
 */
@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        MinecraftForge.EVENT_BUS.register(FluidRenderer.instance());
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
        ModIntegrationManager.clientPreInit();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        ModIntegrationManager.clientInit();
        ModItems.RegistrationHandler.registerItemColors();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
        ModIntegrationManager.clientPostInit();
    }
}
