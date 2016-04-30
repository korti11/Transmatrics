package at.korti.transmatrics.proxy;

import at.korti.transmatrics.client.renderer.FluidRenderer;
import at.korti.transmatrics.fluid.FluidStateMapper;
import at.korti.transmatrics.registry.Blocks;
import at.korti.transmatrics.registry.Fluids;
import at.korti.transmatrics.registry.Items;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by Korti on 24.02.2016.
 */
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        Items.registerItemsClient();

        registerFluidModel(Fluids.moltenCopper);
        registerFluidModel(Fluids.moltenTin);
        registerFluidModel(Fluids.moltenSilver);
        registerFluidModel(Fluids.moltenLead);

        MinecraftForge.EVENT_BUS.register(FluidRenderer.instance());
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        Items.registerItemTextures();
        Items.registerColorHandler();
        Blocks.registerBlockTextures();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
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
