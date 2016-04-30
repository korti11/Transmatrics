package at.korti.transmatrics.item;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.FMLClientHandler;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Korti on 28.03.2016.
 */
public abstract class ModMetaItem extends ModItem {

    private final String[] extensions;
    public ModMetaItemColorHandler colorHandler;

    public ModMetaItem(String name, TextFormatting nameColor, String... extensions) {
        super(name, nameColor);

        this.setHasSubtypes(true);
        this.extensions = extensions;
        this.colorHandler = new ModMetaItemColorHandler();
    }

    public ModMetaItem(String name, String... extensions) {
        this(name, TextFormatting.WHITE, extensions);
    }

    protected void addColor(int index, int color) {
        colorHandler.addColor(index, color);
    }

    protected void addColor(int color) {
        colorHandler.addColor(color);
    }

    protected void addColors(Integer[] colors) {
        this.colorHandler.addColors(colors);
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
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        for (int i = 0; i < extensions.length; i++) {
            subItems.add(new ItemStack(itemIn, 1, i));
        }
    }

    public static class ModMetaItemColorHandler implements IItemColor {

        private List<Integer> colors;

        public ModMetaItemColorHandler() {
            colors = new LinkedList<>();
        }

        public void addColor(int index, int color) {
            colors.add(index, color);
        }

        public void addColor(int color) {
            colors.add(color);
        }

        public void addColors(Integer[] colors) {
            this.colors.addAll(Arrays.asList(colors));
        }

        @Override
        public int getColorFromItemstack(ItemStack stack, int tintIndex) {
            if (stack.getItemDamage() < colors.size()) {
                return colors.get(stack.getItemDamage());
            }
            return 0;
        }
    }
}
