package at.korti.transmatrics.api.energy;

import net.minecraft.item.ItemStack;

/**
 * Created by Korti on 13.05.2016.
 */

/**
 * @deprecated since Transmatrics 1.3.0. Use Redstone Flux API {@link cofh.redstoneflux.api.IEnergyContainerItem}
 */
@Deprecated
public interface IDischargeable {

    /**
     * Extract energy from a item stack.
     * @param stack Where the energy should extract.
     * @param energy How much should extract.
     * @param simulate Only calculate how much actual could extract.
     * @return Actual extract energy.
     */
    int discharge(ItemStack stack, int energy, boolean simulate);

    /**
     * Get the stored energy of the stack.
     * @param stack Where the energy is stored.
     * @return Stored energy.
     */
    int getEnergy(ItemStack stack);

    /**
     * Get the capacity of the stack.
     * @return Capacity.
     */
    int getCapacity();

    /**
     * Get the capacity of the stack.
     * @param stack Where the capacity is stored.
     * @return Capacity.
     */
    int getCapacity(ItemStack stack);

}
