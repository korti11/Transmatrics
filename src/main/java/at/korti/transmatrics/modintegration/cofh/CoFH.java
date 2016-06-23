package at.korti.transmatrics.modintegration.cofh;

import at.korti.transmatrics.api.Constants.TransmatricsTileEntity;
import at.korti.transmatrics.modintegration.IIntegration;
import at.korti.transmatrics.modintegration.cofh.block.Converter;
import at.korti.transmatrics.modintegration.cofh.tileentity.TileEntityEnergyConverter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Korti on 30.05.2016.
 */
public class CoFH implements IIntegration {

    private static Converter rfConverter;

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        GameRegistry.registerBlock(rfConverter = new Converter());
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
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().
                register(Item.getItemFromBlock(rfConverter), 0,
                        new ModelResourceLocation(rfConverter.getRegistryName(), "inventory"));
    }

    @Override
    public void clientPostInit() {

    }
}
