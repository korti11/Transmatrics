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
