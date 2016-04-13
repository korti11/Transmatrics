package at.korti.transmatrics.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import scala.actors.threadpool.Arrays;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Korti on 28.03.2016.
 */
public abstract class ModMetaItem extends ModItem {

    private final String[] extensions;
    private List<Integer> colors;

    public ModMetaItem(String name, EnumChatFormatting nameColor, String... extensions) {
        super(name, nameColor);

        this.setHasSubtypes(true);
        this.extensions = extensions;
        this.colors = new LinkedList<>();
    }

    public ModMetaItem(String name, String... extensions) {
        this(name, EnumChatFormatting.WHITE, extensions);
    }

    protected void addColor(int index, int color) {
        colors.add(index, color);
    }

    protected void addColor(int color) {
        colors.add(color);
    }

    protected void addColors(Integer[] colors) {
        this.colors.addAll(Arrays.asList(colors));
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
    public int getColorFromItemStack(ItemStack stack, int renderPass) {
        if (stack.getItemDamage() < colors.size()) {
            return colors.get(stack.getItemDamage());
        }
        return super.getColorFromItemStack(stack, renderPass);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        for (int i = 0; i < extensions.length; i++) {
            subItems.add(new ItemStack(itemIn, 1, i));
        }
    }
}
