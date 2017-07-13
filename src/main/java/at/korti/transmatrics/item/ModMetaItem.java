package at.korti.transmatrics.item;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Korti on 28.03.2016.
 */
public abstract class ModMetaItem extends ModItem {

    private final String[] extensions;
    @SideOnly(Side.CLIENT)
    public ModMetaItemColorHandler colorHandler;

    public ModMetaItem(String name, TextFormatting nameColor, String... extensions) {
        super(name, nameColor);

        this.setHasSubtypes(true);
        this.extensions = extensions;
        if(FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            this.colorHandler = new ModMetaItemColorHandler();
        }
    }

    public ModMetaItem(String name, String... extensions) {
        this(name, TextFormatting.WHITE, extensions);
    }

    @SideOnly(Side.CLIENT)
    protected void addColor(int index, int color) {
        colorHandler.addColor(index, color);
    }

    @SideOnly(Side.CLIENT)
    protected void addColor(int color) {
        colorHandler.addColor(color);
    }

    @SideOnly(Side.CLIENT)
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
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        for (int i = 0; i < extensions.length; i++) {
            items.add(new ItemStack(this, 1, i));
        }
    }

    @SideOnly(Side.CLIENT)
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
