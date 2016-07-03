package at.korti.transmatrics.item.crafting;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.TransmatricsItem;
import at.korti.transmatrics.item.ModMetaItem;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by Korti on 27.04.2016.
 */
public class ItemPlate extends ModMetaItem {

    public static final String[] extensions = {"iron", "copper", "tin"};
    private static final Integer[] colors = {0xd8d8d8, 0xef7e0c, 0xf3f3f3};

    public ItemPlate() {
        super(TransmatricsItem.PLATE.getRegName(), extensions);
        if(FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            this.addColors(colors);
        }
    }
}
