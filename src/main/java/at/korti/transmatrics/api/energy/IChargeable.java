package at.korti.transmatrics.api.energy;

import net.minecraft.item.ItemStack;

/**
 * Created by Korti on 13.05.2016.
 */
public interface IChargeable {

    /**
     * Store energy in a item stack.
     * @param stack Where the energy should store.
     * @param energy How much should store.
     * @param simulate Only calculate how much actual could store.
     * @return Actual stored energy.
     */
    int charge(ItemStack stack, int energy, boolean simulate);

}
