package at.korti.transmatrics.modintegration.redstoneflux;

import at.korti.transmatrics.api.Constants.TransmatricsTileEntity;
import at.korti.transmatrics.modintegration.IIntegration;
import at.korti.transmatrics.modintegration.redstoneflux.block.Converter;
import at.korti.transmatrics.modintegration.redstoneflux.tileentity.TileEntityEnergyConverter;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Created by Korti on 30.05.2016.
 */
public class RedstoneFlux implements IIntegration {

    private static Converter rfConverter;

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        GameRegistry.registerTileEntity(TileEntityEnergyConverter.class,
                TransmatricsTileEntity.RF_CONVERTER.getRegName());
    }

    @Override
    public void init(FMLInitializationEvent event) {

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

    @Override
    @SideOnly(Side.SERVER)
    @SubscribeEvent
    public void handleBlockRegisterServer(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();
        registry.register(rfConverter = new Converter());
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void handleBlockRegisterClient(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();
        registry.register(rfConverter = new Converter());
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().
                register(Item.getItemFromBlock(rfConverter), 0,
                        new ModelResourceLocation(rfConverter.getRegistryName(), "inventory"));
    }
}
