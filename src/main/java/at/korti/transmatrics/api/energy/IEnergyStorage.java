package at.korti.transmatrics.api.energy;

/**
 * Created by Korti on 25.02.2016.
 */
public interface IEnergyStorage {

    int receiveEnergy(int energy);

    int extractEnergy(int energy);

    int getEnergyStored();

    int getMaxEnergyStored();

}
