package at.korti.transmatrics.api.energy;

/**
 * Created by Korti on 06.03.2016.
 */

/**
 * @deprecated since Transmatrics 1.3.0. Use Redstone Flux API {@link cofh.redstoneflux.api.IEnergyReceiver}
 */
@Deprecated
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
