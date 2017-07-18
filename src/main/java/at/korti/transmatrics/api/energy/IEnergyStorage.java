package at.korti.transmatrics.api.energy;

/**
 * Created by Korti on 25.02.2016.
 */

/**
 * @deprecated since Transmatrics 1.3.0. Use Redstone Flux API {@link cofh.redstoneflux.api.IEnergyStorage}
 */
@Deprecated
public interface IEnergyStorage {

    /**
     * Store energy.
     * @param energy How much should store.
     * @param simulate Only calculate how much actual could store.
     * @return How much actual stored.
     */
    int receiveEnergy(int energy, boolean simulate);

    /**
     * Extract energy.
     * @param energy How much should extract.
     * @param simulate Only calculate how much actual could extract.
     * @return How much could actual extracted.
     */
    int extractEnergy(int energy, boolean simulate);

    /**
     * @return Current amount of the stored energy.
     */
    int getEnergyStored();

    /**
     * @return Capacity of the energy storage.
     */
    int getMaxEnergyStored();

}
