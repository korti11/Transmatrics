package at.korti.transmatrics.item.ore;

import at.korti.transmatrics.api.Constants.TransmatricsItem;
import at.korti.transmatrics.item.ModMetaItem;

/**
 * Created by Korti on 28.03.2016.
 */
public class ItemIngot extends ModMetaItem{

    private static final String[] extensions = {"copper", "tin", "silver", "lead"};
    private static final Integer[] colors = {0xef7e0c, 0xf3f3f3, 0xc1dede, 0x30193c};

    public ItemIngot() {
        super(TransmatricsItem.INGOT.getRegName(), extensions);

        this.addColors(colors);
    }

}
