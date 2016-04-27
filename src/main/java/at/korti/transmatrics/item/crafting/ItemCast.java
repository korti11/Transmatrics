package at.korti.transmatrics.item.crafting;

import at.korti.transmatrics.api.Constants.TransmatricsItem;
import at.korti.transmatrics.api.crafting.ICasting;
import at.korti.transmatrics.item.ModMetaItem;

/**
 * Created by Korti on 10.04.2016.
 */
public class ItemCast extends ModMetaItem implements ICasting{

    public static final String[] extensions = {"ingot", "gear", "plate"};

    public ItemCast() {
        super(TransmatricsItem.CAST.getRegName(), extensions);
    }
}
