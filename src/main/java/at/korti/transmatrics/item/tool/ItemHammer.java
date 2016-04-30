package at.korti.transmatrics.item.tool;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.TransmatricsItem;
import at.korti.transmatrics.item.ModItem;
import at.korti.transmatrics.util.helper.TextHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by Korti on 30.04.2016.
 */
public class ItemHammer extends ModItem {

    public ItemHammer() {
        super(TransmatricsItem.HAMMER.getRegName());

        setMaxStackSize(1);
        setMaxDamage(100);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, playerIn, tooltip, advanced);
        tooltip.add(TextHelper.localize(Constants.ToolTips.HAMMER_USES_LEFT,
                stack.getMaxDamage() - stack.getItemDamage()));
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return stack.getItemDamage() < getMaxDamage() - 1;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        ItemStack stack = itemStack.copy();
        stack.setItemDamage(stack.getItemDamage() + 1);
        stack.stackSize = 1;
        return stack;
    }
}
