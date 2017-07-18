package at.korti.transmatrics.api.energy;

/**
 * Created by Korti on 25.02.2016.
 */

/**
 * @deprecated since Transmatrics 1.3.0. Use Redstone Flux API {@link cofh.redstoneflux.api.IEnergyProvider}
 */
@Deprecated
public interface IEnergyProvider {

    /**
     * Extract energy.
     * @param energy How much should extract.
     * @param simulate Only calculate how much actual could extract.
     * @return How much could actual extracted.
     */
    int extractEnergy(int energy, boolean simulate);

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
