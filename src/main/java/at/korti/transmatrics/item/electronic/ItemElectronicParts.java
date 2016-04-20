package at.korti.transmatrics.item.electronic;

import at.korti.transmatrics.api.Constants.TransmatricsItem;
import at.korti.transmatrics.item.ModMetaItem;

/**
 * Created by Korti on 20.04.2016.
 */
public class ItemElectronicParts extends ModMetaItem {

    public static final String[] extensions = {"inductance", "capacitance", "heatSink"};

    public ItemElectronicParts() {
        super(TransmatricsItem.ELECTRONIC_PARTS.getRegName(), extensions);
    }
}
