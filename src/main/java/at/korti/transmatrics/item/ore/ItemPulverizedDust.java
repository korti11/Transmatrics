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

    private static final int DUSTS = 2;

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
