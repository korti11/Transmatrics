package at.korti.transmatrics.api.energy;

/**
 * Created by Korti on 25.02.2016.
 */
public interface IEnergyProvider {

    /**
     * @return Can provide energy at the moment.
     */
    boolean canProvideEnergy();

    /**
     * @return Current amount that can provided.
     */
    int getEnergyStored();

    /**
     * @return Max amount that can provided.
     */
    int getMaxEnergyStored();

}
