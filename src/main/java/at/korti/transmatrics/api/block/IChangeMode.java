package at.korti.transmatrics.api.block;

/**
 * Created by Korti on 17.06.2016.
 */
public interface IChangeMode<E extends Enum> {

    /**
     * Get a array of all possible modes.
     * @return All possible modes.
     */
    E[] getAllModes();

    /**
     * Get the current selected mode.
     * @return Current mode.
     */
    E getCurrentMode();

    /**
     * Set a new mode.
     * @param mode New mode.
     */
    void setMode(E mode);

    /**
     * Set the next possible mode of the list and return it.
     * @return Next possible mode.
     */
    E cycleThroughMode();

}
