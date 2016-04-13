package at.korti.transmatrics.item.crafting;

import at.korti.transmatrics.api.Constants.TransmatricsItem;
import at.korti.transmatrics.item.ModMetaItem;

/**
 * Created by Korti on 10.04.2016.
 */
public class ItemCast extends ModMetaItem {

    public static final String[] extensions = {"ingot", "gear"};

    public ItemCast() {
        super(TransmatricsItem.CAST.getRegName(), extensions);
    }
}
