package at.korti.transmatrics.item.tool;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.TransmatricsItem;
import at.korti.transmatrics.item.ModItem;
import at.korti.transmatrics.util.helper.TextHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
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
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(TextHelper.localize(Constants.ToolTips.HAMMER_USES_LEFT,
                stack.getMaxDamage() - stack.getItemDamage()));
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean hasContainerItem(ItemStack stack) {
        return stack.getItemDamage() < getMaxDamage() - 1;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        ItemStack stack = itemStack.copy();
        stack.setItemDamage(stack.getItemDamage() + 1);
        stack.setCount(1);
        return stack;
    }
}
