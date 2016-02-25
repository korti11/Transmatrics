package at.korti.transmatrics.api.energy;

/**
 * Created by Korti on 25.02.2016.
 */
public interface IEnergyProvider {

    int getEnergyPerTick();

    boolean canProvideEnergy();

    int getEnergyStored();

    int getMaxEnergyStored();

}
