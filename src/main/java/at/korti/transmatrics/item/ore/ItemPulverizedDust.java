package at.korti.transmatrics.item.ore;

import at.korti.transmatrics.api.Constants.TransmatricsItem;
import at.korti.transmatrics.item.ModItem;
import at.korti.transmatrics.item.ModMetaItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by Korti on 16.03.2016.
 */
public class ItemPulverizedDust extends ModMetaItem{

    private static final String[] extensions = {"iron", "gold", "copper", "tin", "silver", "lead"};
    private static final Integer[] colors = {0xd8af93, 0xfcee4b, 0xef7e0c, 0xf3f3f3, 0xc1dede, 0x30193c};

    public ItemPulverizedDust() {
        super(TransmatricsItem.PULVERIZED_DUST.getRegName(), extensions);

        this.addColors(colors);
    }
}
