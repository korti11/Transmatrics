package at.korti.transmatrics.item.ore;

import at.korti.transmatrics.api.Constants.TransmatricsItem;
import at.korti.transmatrics.item.ModItem;
import at.korti.transmatrics.item.ModMetaItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import java.util.List;

/**
 * Created by Korti on 16.03.2016.
 */
public class ItemPulverizedDust extends ModMetaItem{

    private static final String[] extensions = {"iron", "gold", "copper", "tin", "silver", "lead", "nickel", "invar", "electrum"};
    private static final Integer[] colors = {0xd8af93, 0xfcee4b, 0xef7e0c, 0xf3f3f3, 0xc1dede, 0x30193c, 0xa3a375, 0xc2c2a3, 0xffd633};

    public ItemPulverizedDust() {
        super(TransmatricsItem.PULVERIZED_DUST.getRegName(), extensions);

        if(FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            this.addColors(colors);
        }
    }
}
