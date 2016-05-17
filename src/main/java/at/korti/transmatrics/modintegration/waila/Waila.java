package at.korti.transmatrics.modintegration.waila;

import at.korti.transmatrics.api.Constants.ModIntegrationIds;
import at.korti.transmatrics.api.energy.IEnergyInfo;
import at.korti.transmatrics.api.network.INetworkNodeInfo;
import at.korti.transmatrics.api.network.INetworkSwitchInfo;
import at.korti.transmatrics.modintegration.IIntegration;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by Korti on 02.03.2016.
 */
public class Waila implements IIntegration {
    @Override
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Override
    public void init(FMLInitializationEvent event) {
        FMLInterModComms.sendMessage(ModIntegrationIds.WAILA, "register", getClass().getName() + ".wailaRegister");
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Override
    public void clientPreInit() {

    }

    @Override
    public void clientInit() {

    }

    @Override
    public void clientPostInit() {

    }

    public static void wailaRegister(IWailaRegistrar registrar) {
        registrar.registerBodyProvider(new WailaEnergyInfoHandler(), IEnergyInfo.class);
        registrar.registerNBTProvider(new WailaEnergyInfoHandler(), IEnergyInfo.class);
        registrar.registerBodyProvider(new WailaNetworkInfoHandler(), INetworkSwitchInfo.class);
        registrar.registerNBTProvider(new WailaNetworkInfoHandler(), INetworkSwitchInfo.class);
        registrar.registerBodyProvider(new WailaNetworkInfoHandler(), INetworkNodeInfo.class);
        registrar.registerNBTProvider(new WailaNetworkInfoHandler(), INetworkNodeInfo.class);
    }
}
