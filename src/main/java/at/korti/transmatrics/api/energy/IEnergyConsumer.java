package at.korti.transmatrics.api.energy;

/**
 * Created by Korti on 06.03.2016.
 */
public interface IEnergyConsumer {

    int receiveEnergy(int energy, boolean simulate);

    /**
     * @return Current amount that can provided.
     */
    int getEnergyStored();

    /**
     * @return Max amount that can provided.
     */
    int getMaxEnergyStored();

}
