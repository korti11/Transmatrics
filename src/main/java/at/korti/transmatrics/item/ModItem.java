package at.korti.transmatrics.item;

import at.korti.transmatrics.Transmatrics;
import at.korti.transmatrics.api.Constants.Mod;
import at.korti.transmatrics.util.helper.TextHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;


import java.util.List;

/**
 * Created by Korti on 29.02.2016.
 */
public class ModItem extends Item {

    private TextFormatting nameColor;

    public ModItem(String name, TextFormatting nameColor) {
        this.nameColor = nameColor;

        setCreativeTab(Transmatrics.creativeTab);
        setUnlocalizedName(Mod.MODID + "." + name);
        setRegistryName(Mod.MODID.toLowerCase(), name);
    }

    public ModItem(String name) {
        this(name, TextFormatting.WHITE);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        if(nameColor != TextFormatting.WHITE) {
            stack.setStackDisplayName(nameColor + TextHelper.localize(getUnlocalizedName(stack) + ".name") + TextFormatting.RESET);
        }
    }
}
