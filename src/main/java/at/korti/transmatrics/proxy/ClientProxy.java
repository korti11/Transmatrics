package at.korti.transmatrics.proxy;

import at.korti.transmatrics.client.renderer.FluidRenderer;
import at.korti.transmatrics.client.util.ClientEventHandler;
import at.korti.transmatrics.fluid.FluidStateMapper;
import at.korti.transmatrics.modintegration.ModIntegrationManager;
import at.korti.transmatrics.registry.BlockRegistry;
import at.korti.transmatrics.registry.Fluids;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
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

        registerFluidModel(Fluids.moltenCopper);
        registerFluidModel(Fluids.moltenTin);
        registerFluidModel(Fluids.moltenSilver);
        registerFluidModel(Fluids.moltenLead);
        registerFluidModel(Fluids.moltenNickel);
        registerFluidModel(Fluids.moltenInvar);
        registerFluidModel(Fluids.moltenElectrum);

        MinecraftForge.EVENT_BUS.register(FluidRenderer.instance());
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
        ModIntegrationManager.clientPreInit();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        ModIntegrationManager.clientInit();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
        ModIntegrationManager.clientPostInit();
    }

    private void registerFluidModel(Fluid fluid) {
        if (fluid == null) {
            return;
        }

        Block block = fluid.getBlock();
        Item item = Item.getItemFromBlock(block);
        FluidStateMapper mapper = new FluidStateMapper(fluid);
        ModelLoader.registerItemVariants(item);
        ModelLoader.setCustomMeshDefinition(item, mapper);
        ModelLoader.setCustomStateMapper(block, mapper);
    }
}
