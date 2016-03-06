package at.korti.transmatrics.api.energy;

/**
 * Created by Korti on 06.03.2016.
 */
public interface IEnergyHandler extends IEnergyProvider, IEnergyConsumer{

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
     * @return Current amount that can provided.
     */
    int getEnergyStored();

    /**
     * @return Max amount that can provided.
     */
    int getMaxEnergyStored();

}
