package at.korti.transmatrics.item.crafting;

import at.korti.transmatrics.api.Constants.TransmatricsItem;
import at.korti.transmatrics.item.ModMetaItem;

/**
 * Created by Korti on 27.04.2016.
 */
public class ItemElectronics extends ModMetaItem {

    public static final String[] extensions = {"basic_circuit", "advanced_circuit", "short_transmitter", "transmitter", "long_transmitter"};

    public ItemElectronics() {
        super(TransmatricsItem.ELECTRONICS.getRegName(), extensions);
    }

}
