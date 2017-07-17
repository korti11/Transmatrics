package at.korti.transmatrics.item;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Korti on 17.07.2017.
 */
public class ModMetaColorItem extends ModMetaItem {

    @SideOnly(Side.CLIENT)
    public ModMetaItemColorHandler colorHandler;

    public ModMetaColorItem(String name, TextFormatting nameColor, Integer[] colors, String... extensions) {
        super(name, nameColor, extensions);
        if(FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            this.colorHandler = new ModMetaItemColorHandler();
            this.colorHandler.addColors(colors);
        }
    }

    public ModMetaColorItem(String name, Integer[] colors, String... extensions) {
        this(name, TextFormatting.WHITE, colors, extensions);
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
