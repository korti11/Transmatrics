package at.korti.transmatrics.registry;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.block.MachineCasing;
import at.korti.transmatrics.block.OreBlock;
import at.korti.transmatrics.block.crafting.*;
import at.korti.transmatrics.block.energy.Charger;
import at.korti.transmatrics.block.energy.EnergyBridge;
import at.korti.transmatrics.block.generator.*;
import at.korti.transmatrics.block.network.*;
import at.korti.transmatrics.item.network.ItemQuantumBridgeBlock;
import com.google.common.base.Preconditions;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Korti on 13.07.2017.
 */
@SuppressWarnings("WeakerAccess")
@GameRegistry.ObjectHolder(Constants.Mod.MODID)
public class ModBlocks {

    //region Energy Blocks
    public static SolarPanel SOLAR_PANEL = new SolarPanel();

    public static AdvancedSolarPanel ADVANCED_SOLAR_PANEL = new AdvancedSolarPanel();

    public static LavaGenerator LAVA_GENERATOR = new LavaGenerator();

    public static ThermalGenerator THERMAL_GENERATOR = new ThermalGenerator();

    public static Windmill WINDMILL = new Windmill();

    public static Watermill WATERMILL = new Watermill();

    public static EnergyBridge ENERGY_BRIDGE = new EnergyBridge();
    //endregion

    //region Network Blocks
    public static SmallSwitch SMALL_SWITCH = new SmallSwitch();

    public static MediumSwitch MEDIUM_SWITCH = new MediumSwitch();

    public static LargeSwitch LARGE_SWITCH = new LargeSwitch();

    public static Controller CONTROLLER = new Controller();

    public static QuantumBridge QUANTUM_BRIDGE = new QuantumBridge();
    //endregion

    //region Machines
    public static Pulverizer PULVERIZER = new Pulverizer();

    public static PoweredFurnace POWERED_FURNACE = new PoweredFurnace();

    public static MagneticSmeltery MAGNETIC_SMELTERY = new MagneticSmeltery();

    public static LiquidCaster LIQUID_CASTER = new LiquidCaster();

    public static Charger CHARGER = new Charger();

    public static AlloyMixer ALLOY_MIXER = new AlloyMixer();
    //endregion

    public static OreBlock ORE_BLOCK = new OreBlock();

    public static MachineCasing MACHINE_CASING = new MachineCasing();

    @Mod.EventBusSubscriber
    public static class RegistrationHandler {
        public static final Set<ItemBlock> ITEM_BLOCKS = new HashSet<>();

        @SubscribeEvent
        public static void registerBlocks(final RegistryEvent.Register<Block> event) {
            final IForgeRegistry<Block> registry = event.getRegistry();

            final Block[] blocks = {
                    SOLAR_PANEL,
                    ADVANCED_SOLAR_PANEL,
                    LAVA_GENERATOR,
                    THERMAL_GENERATOR,
                    WINDMILL,
                    WATERMILL,
                    ENERGY_BRIDGE,
                    SMALL_SWITCH,
                    MEDIUM_SWITCH,
                    LARGE_SWITCH,
                    CONTROLLER,
                    QUANTUM_BRIDGE,
                    PULVERIZER,
                    POWERED_FURNACE,
                    MAGNETIC_SMELTERY,
                    LIQUID_CASTER,
                    CHARGER,
                    ALLOY_MIXER,
                    ORE_BLOCK,
                    MACHINE_CASING
            };

            registry.registerAll(blocks);

            OreDicts.registerOreDictBlocks();
        }

        @SubscribeEvent
        public static void registerItemBlocks(final RegistryEvent.Register<Item> event) {
            final ItemBlock[] items = {
                    new ItemBlock(SOLAR_PANEL),
                    new ItemBlock(ADVANCED_SOLAR_PANEL),
                    new ItemBlock(LAVA_GENERATOR),
                    new ItemBlock(THERMAL_GENERATOR),
                    new ItemBlock(WINDMILL),
                    new ItemBlock(WATERMILL),
                    new ItemBlock(ENERGY_BRIDGE),
                    new ItemBlock(SMALL_SWITCH),
                    new ItemBlock(MEDIUM_SWITCH),
                    new ItemBlock(LARGE_SWITCH),
                    new ItemBlock(CONTROLLER),
                    new ItemQuantumBridgeBlock(QUANTUM_BRIDGE),
                    new ItemBlock(PULVERIZER),
                    new ItemBlock(POWERED_FURNACE),
                    new ItemBlock(MAGNETIC_SMELTERY),
                    new ItemBlock(LIQUID_CASTER),
                    new ItemBlock(CHARGER),
                    new ItemBlock(ALLOY_MIXER),
                    new ItemMultiTexture(ORE_BLOCK, ORE_BLOCK, ORE_BLOCK::getMetaName),
                    new ItemBlock(MACHINE_CASING)
            };

            final IForgeRegistry<Item> registry = event.getRegistry();

            for(final ItemBlock item : items) {
                final Block block = item.getBlock();
                final ResourceLocation registryName = Preconditions.checkNotNull(block.getRegistryName(),
                        "Block %s has null registry name", block);
                registry.register(item.setRegistryName(registryName));
                ITEM_BLOCKS.add(item);
            }


        }
    }
}
