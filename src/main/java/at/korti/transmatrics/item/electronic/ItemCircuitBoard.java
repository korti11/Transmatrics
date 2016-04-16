package at.korti.transmatrics.item.electronic;

import at.korti.transmatrics.api.Constants.TransmatricsItem;
import at.korti.transmatrics.item.NBTColoredMetaItem;

/**
 * Created by Korti on 16.04.2016.
 */
public class ItemCircuitBoard extends NBTColoredMetaItem {

    public static final String[] extensions = {"redstone", "quartz", "ender"};
    private static final Integer[] boardColors = {0x720000, 0xcdc1b3, 0x0b4d42};
    public static final Integer[] conductionColors = {0xef7e0c, 0xc1dede, 0x8cf4e2};

    public ItemCircuitBoard() {
        super(TransmatricsItem.CIRCUIT_BOARDER.getRegName(), extensions);
        addColors(boardColors, 0);
        addColors(conductionColors, 1);
    }
}
