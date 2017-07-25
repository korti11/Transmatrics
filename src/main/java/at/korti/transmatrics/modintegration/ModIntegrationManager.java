package at.korti.transmatrics.modintegration;

import at.korti.transmatrics.api.Constants.ModIntegrationIds;
import at.korti.transmatrics.modintegration.tconstruct.TConstruct;
import at.korti.transmatrics.modintegration.waila.Waila;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Korti on 02.03.2016.
 */
public class ModIntegrationManager {

    private static final List<IIntegration> integrationMods = new ArrayList<>();

    public static void initManager() {
        Map<String, Class<? extends IIntegration>> integrationClasses = new HashMap<>();
        try {
            integrationClasses.put(ModIntegrationIds.WAILA, Waila.class);
            integrationClasses.put(ModIntegrationIds.TCONSTRUCT, TConstruct.class);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        for (Map.Entry<String, Class<? extends IIntegration>> entry : integrationClasses.entrySet()) {
            if (Loader.isModLoaded(entry.getKey())) {
                try {
                    integrationMods.add(entry.getValue().newInstance());
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void preInit(FMLPreInitializationEvent event) {
        for (IIntegration mod : integrationMods) {
            mod.preInit(event);
        }
    }

    public static void init(FMLInitializationEvent event) {
        for (IIntegration mod : integrationMods) {
            mod.init(event);
        }
    }

    public static void postInit(FMLPostInitializationEvent event) {
        for (IIntegration mod : integrationMods) {
            mod.postInit(event);
        }
    }

    public static void clientPreInit() {
        for (IIntegration mod : integrationMods) {
            mod.clientPreInit();
        }
    }

    public static void clientInit() {
        for (IIntegration mod : integrationMods) {
            mod.clientInit();
        }
    }

    public static void clientPostInit() {
        for (IIntegration mod : integrationMods) {
            mod.clientPostInit();
        }
    }

}
