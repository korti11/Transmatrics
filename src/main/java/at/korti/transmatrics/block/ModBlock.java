package at.korti.transmatrics.block;

import at.korti.transmatrics.Transmatrics;
import at.korti.transmatrics.api.Constants;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Korti on 24.02.2016.
 */
public class ModBlock extends Block {

    public ModBlock(Material material, MapColor mapColor, String name) {
        super(material, mapColor);

        setCreativeTab(Transmatrics.creativeTab);
        setUnlocalizedName(name);
        setRegistryName(Constants.Mod.MODID, name);
    }

    public ModBlock(Material materialIn, String name) {
        this(materialIn, materialIn.getMaterialMapColor(), name);
    }

    public static void registerBlocks() {
        GameRegistry.registerBlock(new SolarPanel());
    }
}
