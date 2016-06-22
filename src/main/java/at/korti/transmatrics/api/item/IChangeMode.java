package at.korti.transmatrics.api.item;

import net.minecraft.item.ItemStack;

/**
 * Created by Korti on 20.06.2016.
 */
public interface IChangeMode<E extends Enum<E>> {

    /**
     * Get a array of all possible modes.
     * @return All possible modes.
     */
    E[] getAllModes();

    /**
     * Get the current selected mode.
     * @return Current mode.
     */
    E getCurrentMode(ItemStack stack);

    /**
     * Set a new mode.
     * @param mode New mode.
     */
    void setMode(E mode, ItemStack stack);

    /**
     * Set the next possible mode of the list and return it.
     * @return Next possible mode.
     */
    E cycleThroughMode(ItemStack stack);

}
