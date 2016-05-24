package at.korti.transmatrics.item.ore;

import at.korti.transmatrics.api.Constants.TransmatricsItem;
import at.korti.transmatrics.item.ModMetaItem;

/**
 * Created by Korti on 28.03.2016.
 */
public class ItemIngot extends ModMetaItem{

    private static final String[] extensions = {"copper", "tin", "silver", "lead", "nickel", "invar", "electrum"};
    private static final Integer[] colors = {0xef7e0c, 0xdcf3f3, 0xc1dede, 0x30193c, 0xa3a375, 0xc2c2a3, 0xffd633};

    public ItemIngot() {
        super(TransmatricsItem.INGOT.getRegName(), extensions);

        this.addColors(colors);
    }

}
