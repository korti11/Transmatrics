package at.korti.transmatrics.item;

import at.korti.transmatrics.Transmatrics;
import at.korti.transmatrics.api.Constants.Mod;
import at.korti.transmatrics.util.helper.TextHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

/**
 * Created by Korti on 29.02.2016.
 */
public class ModItem extends Item {

    private EnumChatFormatting nameColor;

    public ModItem(String name, EnumChatFormatting nameColor) {
        this.nameColor = nameColor;

        setCreativeTab(Transmatrics.creativeTab);
        setUnlocalizedName(Mod.MODID + "." + name);
        setRegistryName(Mod.MODID, name);
    }

    public ModItem(String name) {
        this(name, EnumChatFormatting.WHITE);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        if(nameColor != EnumChatFormatting.WHITE) {
            stack.setStackDisplayName(nameColor + TextHelper.localize(getUnlocalizedName(stack) + ".name") + EnumChatFormatting.RESET);
        }
    }
}
