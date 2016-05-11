package at.korti.transmatrics.modintegration.tconstruct;

import at.korti.transmatrics.modintegration.IIntegration;
import at.korti.transmatrics.modintegration.tconstruct.helper.CraftingCrossOverHelper;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by Korti on 11.05.2016.
 */
public class TConstruct implements IIntegration {
    @Override
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Override
    public void init(FMLInitializationEvent event) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        CraftingCrossOverHelper.loadSmelteryCrossOver();
    }

    @Override
    public void clientInit() {

    }
}
