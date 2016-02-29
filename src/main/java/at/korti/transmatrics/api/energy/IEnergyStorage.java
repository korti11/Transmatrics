package at.korti.transmatrics.api.energy;

/**
 * Created by Korti on 25.02.2016.
 */
public interface IEnergyStorage {

    int receiveEnergy(int energy, boolean simulate);

    int extractEnergy(int energy, boolean simulate);

    int getEnergyStored();

    int getMaxEnergyStored();

}
