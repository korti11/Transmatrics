package at.korti.transmatrics.registry;

import at.korti.transmatrics.block.MachineCasing;
import at.korti.transmatrics.block.ModBlock;
import at.korti.transmatrics.block.OreBlock;
import at.korti.transmatrics.block.crafting.*;
import at.korti.transmatrics.block.generator.*;
import at.korti.transmatrics.block.network.Controller;
import at.korti.transmatrics.block.network.LargeSwitch;
import at.korti.transmatrics.block.network.MediumSwitch;
import at.korti.transmatrics.block.network.SmallSwitch;
import at.korti.transmatrics.config.Config;
import at.korti.transmatrics.item.crafting.ItemMachineBlock;
import at.korti.transmatrics.item.ore.ItemOreBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

import static net.minecraftforge.fml.common.registry.GameRegistry.registerBlock;

/**
 * Created by Korti on 01.03.2016.
 */
public final class Blocks {

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
    private static OreBlock oreBlock;
    private static MachineCasing machineCasing;

    public static void registerBlocks() {
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
        registerBlock(pulverizer = new Pulverizer(), ItemMachineBlock.class);
        registerBlock(poweredFurnace = new PoweredFurnace(), ItemMachineBlock.class);
        registerBlock(magneticSmeltery = new MagneticSmeltery(), ItemMachineBlock.class);
        registerBlock(liquidCaster = new LiquidCaster(), ItemMachineBlock.class);
        registerBlock(oreBlock = new OreBlock(), ItemOreBlock.class);
        registerBlock(machineCasing = new MachineCasing());
    }

    public static void registerBlockTextures() {
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
        registerMetaBlockTextures(oreBlock, OreBlock.OreType.values());
        registerBlockTexture(machineCasing);
    }

    private static void registerBlockTexture(Block block) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }

    private static <E extends Enum<E>> void registerMetaBlockTextures(Block block, Enum<E>... variants) {
        Item blockItem = Item.getItemFromBlock(block);
        List<ItemStack> subItems = new ArrayList<>();
        block.getSubBlocks(blockItem, null, subItems);
        for (int i = 0; i < subItems.size(); i++) {
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(blockItem, subItems.get(i).getMetadata(),
                    new ModelResourceLocation(block.getRegistryName(), "type=" + variants[i].toString()));
        }
    }

}
