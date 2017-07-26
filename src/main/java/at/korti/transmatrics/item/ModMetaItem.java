package at.korti.transmatrics.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;

/**
 * Created by Korti on 28.03.2016.
 */
public abstract class ModMetaItem extends ModItem {

    private final String[] extensions;


    public ModMetaItem(String name, TextFormatting nameColor, String... extensions) {
        super(name, nameColor);

        this.setHasSubtypes(true);
        this.extensions = extensions;
    }

    public ModMetaItem(String name, String... extensions) {
        this(name, TextFormatting.WHITE, extensions);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        String extension;
        if (stack.getItemDamage() <= extensions.length) {
            extension = extensions[stack.getItemDamage()];
        } else {
            extension = "unknown";
        }
        return super.getUnlocalizedName(stack) + "." + extension;
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        for (int i = 0; i < extensions.length; i++) {
            items.add(new ItemStack(this, 1, i));
        }
    }
}
