package at.korti.transmatrics.modintegration;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
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

    @SideOnly(Side.SERVER)
    @SubscribeEvent
    default void handleBlockRegisterServer(RegistryEvent.Register<Block> event) {

    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    default void handleBlockRegisterClient(RegistryEvent.Register<Block> event) {

    }

    @SideOnly(Side.SERVER)
    @SubscribeEvent
    default void handleItemRegisterServer(RegistryEvent.Register<Item> event) {

    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    default void handleItemRegisterClient(RegistryEvent.Register<Item> event) {

    }
}
