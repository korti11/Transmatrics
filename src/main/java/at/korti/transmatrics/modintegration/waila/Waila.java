package at.korti.transmatrics.modintegration.waila;

import at.korti.transmatrics.api.Constants.ModIntegrationIds;
import at.korti.transmatrics.api.energy.IEnergyInfo;
import at.korti.transmatrics.api.network.INetworkNodeInfo;
import at.korti.transmatrics.api.network.INetworkSwitchInfo;
import at.korti.transmatrics.modintegration.IIntegration;
//import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraftforge.fml.common.event.FMLInterModComms;

/**
 * Created by Korti on 02.03.2016.
 */
public class Waila implements IIntegration {
    @Override
    public void preInit() {

    }

    @Override
    public void init() {
        FMLInterModComms.sendMessage(ModIntegrationIds.WAILA, "register", getClass().getName() + ".wailaRegister");
    }

    @Override
    public void postInit() {

    }

    @Override
    public void clientInit() {

    }

//    public static void wailaRegister(IWailaRegistrar registrar) {
//        registrar.registerBodyProvider(new WailaEnergyInfoHandler(), IEnergyInfo.class);
//        registrar.registerNBTProvider(new WailaEnergyInfoHandler(), IEnergyInfo.class);
//        registrar.registerBodyProvider(new WailaNetworkInfoHandler(), INetworkSwitchInfo.class);
//        registrar.registerNBTProvider(new WailaNetworkInfoHandler(), INetworkSwitchInfo.class);
//        registrar.registerBodyProvider(new WailaNetworkInfoHandler(), INetworkNodeInfo.class);
//        registrar.registerNBTProvider(new WailaNetworkInfoHandler(), INetworkNodeInfo.class);
//    }
}
