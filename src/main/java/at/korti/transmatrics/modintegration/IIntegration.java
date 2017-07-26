package at.korti.transmatrics.modintegration;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Korti on 02.03.2016.
 */
public interface IIntegration {

    void preInit(FMLPreInitializationEvent event);

    void init(FMLInitializationEvent event);

    void postInit(FMLPostInitializationEvent event);

    @SideOnly(Side.CLIENT)
    void clientPreInit();

    @SideOnly(Side.CLIENT)
    void clientInit();

    @SideOnly(Side.CLIENT)
    void clientPostInit();
}
