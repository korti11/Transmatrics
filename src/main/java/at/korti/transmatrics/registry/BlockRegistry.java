package at.korti.transmatrics.registry;

import at.korti.transmatrics.block.MachineCasing;
import at.korti.transmatrics.block.OreBlock;
import at.korti.transmatrics.block.crafting.*;
import at.korti.transmatrics.block.energy.Charger;
import at.korti.transmatrics.block.generator.*;
import at.korti.transmatrics.block.network.*;
import at.korti.transmatrics.item.crafting.ItemMachineBlock;
import at.korti.transmatrics.item.network.ItemQuantumBridgeBlock;
import at.korti.transmatrics.item.ore.ItemOreBlock;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Created by Korti on 01.03.2016.
 */
public final class BlockRegistry {

    private static SolarPanel solarPanel;
    private static AdvancedSolarPanel advancedSolarPanel;
    private static LavaGenerator lavaGenerator;
    private static ThermalGenerator thermalGenerator;
    private static Windmill windmill;
    private static Watermill watermill;
    private static SmallSwitch smallSwitch;
    private static MediumSwitch mediumSwitch;
    private static LargeSwitch largeSwitch;
    private static Controller controller;
    private static Pulverizer pulverizer;
    private static PoweredFurnace poweredFurnace;
    private static MagneticSmeltery magneticSmeltery;
    private static LiquidCaster liquidCaster;
    private static Charger charger;
    private static OreBlock oreBlock;
    private static MachineCasing machineCasing;
    private static QuantumBridge quantumBridge;

    private IForgeRegistry<Block> registry;

    @SideOnly(Side.SERVER)
    public void handleBlockRegisterServer(RegistryEvent.Register<Block> event) {
        this.registry = event.getRegistry();
        this.registerBlocksCommon();
        OreDicts.registerOreDictBlocks();
    }

    @SideOnly(Side.CLIENT)
    public void handleBlockRegisterClient(RegistryEvent.Register<Block> event) {
        this.registry = event.getRegistry();
        this.registerBlocksCommon();
        this.registerBlockTextures();
        OreDicts.registerOreDictBlocks();
    }

    private void registerBlocksCommon() {
        registerBlock(solarPanel = new SolarPanel());
        registerBlock(advancedSolarPanel = new AdvancedSolarPanel());
        registerBlock(lavaGenerator = new LavaGenerator());
        registerBlock(thermalGenerator = new ThermalGenerator());
        registerBlock(windmill = new Windmill());
        registerBlock(watermill = new Watermill());
        registerBlock(smallSwitch = new SmallSwitch());
        registerBlock(mediumSwitch = new MediumSwitch());
        registerBlock(largeSwitch = new LargeSwitch());
        registerBlock(controller = new Controller());
        registerBlock(pulverizer = new Pulverizer(), new ItemMachineBlock(pulverizer));
        registerBlock(poweredFurnace = new PoweredFurnace(), new ItemMachineBlock(poweredFurnace));
        registerBlock(magneticSmeltery = new MagneticSmeltery(), new ItemMachineBlock(magneticSmeltery));
        registerBlock(liquidCaster = new LiquidCaster(), new ItemMachineBlock(liquidCaster));
        registerBlock(charger = new Charger(), new ItemMachineBlock(charger));
        registerBlock(oreBlock = new OreBlock(), new ItemOreBlock(oreBlock));
        registerBlock(machineCasing = new MachineCasing());
        registerBlock(quantumBridge = new QuantumBridge(), new ItemQuantumBridgeBlock(quantumBridge));
    }

    private void registerBlock(Block block) {
        registry.register(block);
        //registry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
    }

    private void registerBlock(Block block, ItemBlock itemBlock) {
        registry.register(block);
        //registry.register(itemBlock);
    }

    @SideOnly(Side.CLIENT)
    private void registerBlockTextures() {
        registerBlockTexture(solarPanel);
        registerBlockTexture(advancedSolarPanel);
        registerBlockTexture(lavaGenerator);
        registerBlockTexture(thermalGenerator);
        registerBlockTexture(windmill);
        registerBlockTexture(watermill);
        registerBlockTexture(smallSwitch);
        registerBlockTexture(mediumSwitch);
        registerBlockTexture(largeSwitch);
        registerBlockTexture(controller);
        registerBlockTexture(pulverizer);
        registerBlockTexture(poweredFurnace);
        registerBlockTexture(magneticSmeltery);
        registerBlockTexture(liquidCaster);
        registerBlockTexture(charger);
        registerMetaBlockTextures(oreBlock, OreBlock.OreType.values());
        registerBlockTexture(machineCasing);
    }

    @SideOnly(Side.CLIENT)
    private void registerBlockTexture(Block block) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }

    @SideOnly(Side.CLIENT)
    private <E extends Enum<E>> void registerMetaBlockTextures(Block block, Enum<E>... variants) {
        Item blockItem = Item.getItemFromBlock(block);
        NonNullList<ItemStack> subItems = NonNullList.create();
        block.getSubBlocks(null, subItems);
        for (int i = 0; i < subItems.size(); i++) {
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(blockItem, subItems.get(i).getMetadata(),
                    new ModelResourceLocation(block.getRegistryName(), "type=" + variants[i].toString()));
        }
    }

}
