package at.korti.transmatrics.util.helper;

import net.minecraft.item.ItemStack;

/**
 * Created by Korti on 27.02.2016.
 */
public class ItemStackHelper {

    public static ItemStack consumeStack(ItemStack stack) {
        if (stack.stackSize == 1) {
            if (stack.getItem().hasContainerItem(stack)) {
                return stack.getItem().getContainerItem(stack);
            }
            return null;
        }

        return stack.splitStack(1);
    }

}
