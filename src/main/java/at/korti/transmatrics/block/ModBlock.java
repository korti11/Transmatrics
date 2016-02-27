package at.korti.transmatrics.block;

import at.korti.transmatrics.Transmatrics;
import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.block.generator.LavaGenerator;
import at.korti.transmatrics.block.generator.SolarPanel;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Korti on 24.02.2016.
 */
public class ModBlock extends Block {

    public ModBlock(Material material, MapColor mapColor, String name) {
        super(material, mapColor);

        setCreativeTab(Transmatrics.creativeTab);
        setUnlocalizedName(Constants.Mod.MODID + "." + name);
        setRegistryName(Constants.Mod.MODID, name);
    }

    public ModBlock(Material materialIn, String name) {
        this(materialIn, materialIn.getMaterialMapColor(), name);
    }

    public static void registerBlocks() {
        GameRegistry.registerBlock(new SolarPanel());
        GameRegistry.registerBlock(new LavaGenerator());
    }

    public static void registerBlockTextures() {
        registerBlockTexture(TransmatricsBlock.SOLAR_PANEL.getBlock());
        registerBlockTexture(TransmatricsBlock.LAVA_GENERATOR.getBlock());
    }

    private static void registerBlockTexture(Block block) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }
}
