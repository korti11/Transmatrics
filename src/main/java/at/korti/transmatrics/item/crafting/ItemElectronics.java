package at.korti.transmatrics.item.crafting;

import at.korti.transmatrics.api.Constants.TransmatricsItem;
import at.korti.transmatrics.item.ModMetaItem;

/**
 * Created by Korti on 27.04.2016.
 */
public class ItemElectronics extends ModMetaItem {

    public static final String[] extensions = {"basicCircuit", "advancedCircuit", "shortTransmitter", "transmitter", "longTransmitter"};

    public ItemElectronics() {
        super(TransmatricsItem.ELECTRONICS.getRegName(), extensions);
    }

}
