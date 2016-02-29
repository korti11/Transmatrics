package at.korti.transmatrics.api;

import at.korti.transmatrics.api.Constants.Mod;
import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.api.Constants.TransmatricsItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Korti on 24.02.2016.
 */
public class TransmatricsApi {

    public static Block getBlock(String name) {
        return GameRegistry.findBlock(Mod.MODID, name);
    }

    public static Block getBlock(TransmatricsBlock block) {
        return getBlock(block.getRegName());
    }

    public static Item getItem(String name) {
        return GameRegistry.findItem(Mod.MODID, name);
    }

    public static Item getItem(TransmatricsItem item) {
        return getItem(item.getRegName());
    }

}
