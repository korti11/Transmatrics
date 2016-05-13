package at.korti.transmatrics.api.energy;

import net.minecraft.item.ItemStack;

/**
 * Created by Korti on 13.05.2016.
 */
public interface IDischargeable {

    /**
     * Extract energy from a item stack.
     * @param stack Where the energy should extract.
     * @param energy How much should extract.
     * @param simulate Only calculate how much actual could extract.
     * @return Actual extract energy.
     */
    int discharge(ItemStack stack, int energy, boolean simulate);

}
