package at.korti.transmatrics.api;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Korti on 24.02.2016.
 */
public class TransmatricsApi {

    public static Block getBlock(String name) {
        return GameRegistry.findBlock(Constants.Mod.MODID, name);
    }

    public static Block getBlock(Constants.TransmatricsBlock block) {
        return getBlock(block.regName);
    }

}
