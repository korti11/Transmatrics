package at.korti.transmatrics.item.ore;

import at.korti.transmatrics.api.Constants.TransmatricsItem;
import at.korti.transmatrics.item.ModItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by Korti on 16.03.2016.
 */
public class ItemPulverizedDust extends ModItem{

    private static final int DUSTS = 6;

    public ItemPulverizedDust() {
        super(TransmatricsItem.PULVERIZED_DUST.getRegName());

        this.setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        String extension;
        switch (stack.getItemDamage()) {
            case 0:
                extension = "iron";
                break;
            case 1:
                extension = "gold";
                break;
            case 2:
                extension = "copper";
                break;
            case 3:
                extension = "tin";
                break;
            case 4:
                extension = "silver";
                break;
            case 5:
                extension = "lead";
                break;
            default:
                extension = "unknown";
                break;
        }
        return super.getUnlocalizedName(stack) + "." + extension;
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int renderPass) {
        switch (stack.getItemDamage()) {
            case 0:
                return 0xd8af93;
            case 1:
                return 0xfcee4b;
            case 2:
                return 0xef7e0c;
            case 3:
                return 0xf3f3f3;
            case 4:
                return 0xc1dede;
            case 5:
                return 0x30193c;
            default:
                return super.getColorFromItemStack(stack, renderPass);
        }
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        for (int i = 0; i < DUSTS; i++) {
            subItems.add(new ItemStack(itemIn, 1, i));
        }
    }
}
