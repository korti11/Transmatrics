package at.korti.transmatrics.item;

import at.korti.transmatrics.Transmatrics;
import at.korti.transmatrics.api.Constants.Mod;
import net.minecraft.item.Item;

/**
 * Created by Korti on 29.02.2016.
 */
public class ModItem extends Item {

    public ModItem(String name) {
        setCreativeTab(Transmatrics.creativeTab);
        setUnlocalizedName(Mod.MODID + "." + name);
        setRegistryName(Mod.MODID, name);
    }
}
