package at.korti.transmatrics.client.model;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.block.OreBlock;
import at.korti.transmatrics.item.crafting.ItemCast;
import at.korti.transmatrics.item.crafting.ItemElectronics;
import at.korti.transmatrics.registry.ModBlocks;
import at.korti.transmatrics.registry.ModFluids;
import at.korti.transmatrics.registry.ModItems;
import at.korti.transmatrics.util.IVariant;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashSet;
import java.util.Set;
import java.util.function.ToIntFunction;

/**
 * Created by Korti on 13.07.2017.
 */
@Mod.EventBusSubscriber(Side.CLIENT)
@SideOnly(Side.CLIENT)
public class ModModelManager {

    public static final ModModelManager INSTANCE = new ModModelManager();

    private static final String FLUID_MODEL_PATH = Constants.Mod.MODID + ":fluid";

    private final StateMapperBase propertyStringMapper = new StateMapperBase() {
        @Override
        protected ModelResourceLocation getModelResourceLocation(IBlockState iBlockState) {
            return new ModelResourceLocation("minecraft:air");
        }
    };

    private final Set<Item> itemsRegistered = new HashSet<>();

    private ModModelManager() {

    }

    @SubscribeEvent
    public static void registerAllModels(final ModelRegistryEvent event) {
        INSTANCE.registerFluidModels();
        INSTANCE.registerBlockModels();
        INSTANCE.registerItemModels();
    }

    private void registerFluidModels() {
        ModFluids.MOD_FLUID_BLOCKS.forEach(this::registerFluidModel);
    }

    private void registerFluidModel(final IFluidBlock fluidBlock) {
        final Item item = Item.getItemFromBlock((Block) fluidBlock);
        assert item != Items.AIR;

        ModelBakery.registerItemVariants(item);

        final ModelResourceLocation modelResourceLocation = new ModelResourceLocation(FLUID_MODEL_PATH,
                fluidBlock.getFluid().getName());

        ModelLoader.setCustomMeshDefinition(item, MeshDefinitionFix.create(stack -> modelResourceLocation));

        ModelLoader.setCustomStateMapper((Block) fluidBlock, new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState iBlockState) {
                return modelResourceLocation;
            }
        });
    }

    private void registerBlockModels() {
        //region Energy Blocks
        registerBlockItemModel(ModBlocks.SOLAR_PANEL.getDefaultState());
        registerBlockItemModel(ModBlocks.ADVANCED_SOLAR_PANEL.getDefaultState());
        registerBlockItemModel(ModBlocks.LAVA_GENERATOR.getDefaultState());
        registerBlockItemModel(ModBlocks.THERMAL_GENERATOR.getDefaultState());
        registerBlockItemModel(ModBlocks.WINDMILL.getDefaultState());
        registerBlockItemModel(ModBlocks.WATERMILL.getDefaultState());
        registerBlockItemModel(ModBlocks.ENERGY_BRIDGE.getDefaultState());
        //endregion
        //region Network Blocks
        registerBlockItemModel(ModBlocks.SMALL_SWITCH.getDefaultState());
        registerBlockItemModel(ModBlocks.MEDIUM_SWITCH.getDefaultState());
        registerBlockItemModel(ModBlocks.LARGE_SWITCH.getDefaultState());
        registerBlockItemModel(ModBlocks.CONTROLLER.getDefaultState());
        registerBlockItemModel(ModBlocks.QUANTUM_BRIDGE.getDefaultState());
        //endregion
        //region Machines
        registerBlockItemModel(ModBlocks.PULVERIZER.getDefaultState());
        registerBlockItemModel(ModBlocks.POWERED_FURNACE.getDefaultState());
        registerBlockItemModel(ModBlocks.MAGNETIC_SMELTERY.getDefaultState());
        registerBlockItemModel(ModBlocks.LIQUID_CASTER.getDefaultState());
        registerBlockItemModel(ModBlocks.CHARGER.getDefaultState());
        //endregion
        registerVariantBlockItemModels(ModBlocks.ORE_BLOCK.getDefaultState(), OreBlock.TYPE);
        registerBlockItemModel(ModBlocks.MACHINE_CASING.getDefaultState());

        ModBlocks.RegistrationHandler.ITEM_BLOCKS.stream().filter(item -> !itemsRegistered.contains(item)).
                forEach(this::registerItemModel);
    }

    private void registerBlockItemModel(final IBlockState state) {
        final Block block = state.getBlock();
        final Item item = Item.getItemFromBlock(block);

        if (item != Items.AIR) {
            registerItemModel(item, new ModelResourceLocation(block.getRegistryName(),
                    propertyStringMapper.getPropertyString(state.getProperties())));
        }
    }

    private void registerBlockItemModelForMeta(final IBlockState state, final int metadata) {
        final Item item = Item.getItemFromBlock(state.getBlock());

        if(item != Items.AIR) {
            registerItemModelForMeta(item, metadata, propertyStringMapper.getPropertyString(state.getProperties()));
        }
    }

    private <T extends Comparable<T>> void registerVariantBlockItemModels(final IBlockState baseState,
                                                                          final IProperty<T> property,
                                                                          final ToIntFunction<T> getMeta) {
        property.getAllowedValues().forEach(value -> registerBlockItemModelForMeta(baseState.withProperty(property, value),
                getMeta.applyAsInt(value)));
    }

    private <T extends IVariant & Comparable<T>> void registerVariantBlockItemModels(final IBlockState baseState,
                                                                                     final IProperty<T> property) {
        registerVariantBlockItemModels(baseState, property, IVariant::getMeta);
    }

    private void registerItemModels() {
        registerItemModelForExtensions(ModItems.ELECTRONICS, ItemElectronics.extensions);
        registerItemModelForExtensions(ModItems.CAST, ItemCast.extensions);

        ModItems.RegistrationHandler.ITEMS.stream().filter(item -> !itemsRegistered.contains(item)).
                forEach(this::registerItemModel);
    }

    private void registerItemModel(final Item item) {
        registerItemModel(item, item.getRegistryName().toString());
    }

    private void registerItemModel(final Item item, final String modelLocation) {
        final ModelResourceLocation fullModelLocation = new ModelResourceLocation(modelLocation, "inventory");
        registerItemModel(item, fullModelLocation);
    }

    private void registerItemModel(final Item item, final ModelResourceLocation fullModelLocation) {
        ModelBakery.registerItemVariants(item, fullModelLocation);
        registerItemModel(item, MeshDefinitionFix.create(stack -> fullModelLocation));
    }

    private void registerItemModel(final Item item, final ItemMeshDefinition itemMeshDefinition) {
        itemsRegistered.add(item);
        ModelLoader.setCustomMeshDefinition(item, itemMeshDefinition);
    }

    private void registerItemModelForExtensions(final Item item, final String[] extensions) {
        for(int i = 0; i < extensions.length; i++) {
            String extension = extensions[i];
            registerItemModelForMeta(item, i,
                    new ModelResourceLocation(String.format("%s_%s", item.getRegistryName(), extension), "inventory"));
        }
    }

    private void registerItemModelForMeta(final Item item, final int metadata, final String variant) {
        registerItemModelForMeta(item, metadata, new ModelResourceLocation(item.getRegistryName(), variant));
    }

    private void registerItemModelForMeta(final Item item, final int metadata,
                                          final ModelResourceLocation modelResourceLocation) {
        itemsRegistered.add(item);
        ModelLoader.setCustomModelResourceLocation(item, metadata, modelResourceLocation);
    }
}
