package at.korti.transmatrics.item.tool;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.TransmatricsItem;
import at.korti.transmatrics.item.ModItem;

/**
 * Created by Korti on 30.04.2016.
 */
public class ItemHammer extends ModItem {

    public ItemHammer() {
        super(TransmatricsItem.HAMMER.getRegName());

        setMaxStackSize(1);
        setMaxDamage(100);
    }

}
