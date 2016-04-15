package at.korti.transmatrics.item.electronic;

import at.korti.transmatrics.api.Constants.TransmatricsItem;
import at.korti.transmatrics.item.ModMetaItem;

/**
 * Created by Korti on 15.04.2016.
 */
public class ItemBlankCircuitBoard extends ModMetaItem {

    public static final String[] extensions = {"redstone", "quartz", "ender"};
    private static final Integer[] colors = {0x720000, 0xcdc1b3, 0x0b4d42};

    public ItemBlankCircuitBoard() {
        super(TransmatricsItem.BLANK_CIRCUIT_BOARD.getRegName(), extensions);
        addColors(colors);
    }
}
