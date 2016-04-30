package at.korti.transmatrics.block;

import at.korti.transmatrics.Transmatrics;
import at.korti.transmatrics.api.Constants;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

/**
 * Created by Korti on 24.02.2016.
 */
public class ModBlock extends Block {

    public ModBlock(Material material, MapColor mapColor, String name) {
        super(material, mapColor);

        setCreativeTab(Transmatrics.creativeTab);
        setUnlocalizedName(Constants.Mod.MODID + "." + name);
        setRegistryName(Constants.Mod.MODID.toLowerCase(), name);
    }

    public ModBlock(Material materialIn, String name) {
        this(materialIn, materialIn.getMaterialMapColor(), name);
    }

}
