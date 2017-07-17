package at.korti.transmatrics.item.crafting;

import at.korti.transmatrics.api.Constants.TransmatricsItem;
import at.korti.transmatrics.item.ModMetaColorItem;
import at.korti.transmatrics.item.ModMetaItem;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by Korti on 01.04.2016.
 */
public class ItemGear extends ModMetaColorItem {

    private static final String[] extensions = {"copper", "tin", "silver", "lead"};
    private static final Integer[] colors = {0xef7e0c, 0xf3f3f3, 0xc1dede, 0x30193c};

    public ItemGear() {
        super(TransmatricsItem.GEAR.getRegName(), colors, extensions);
    }

}
