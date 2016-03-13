package at.korti.transmatrics.api.energy;

/**
 * Created by Korti on 10.03.2016.
 */
public final class EnergyHandler {

    public static void transferEnergy(IEnergyProvider provider, IEnergyConsumer consumer) {
        int energy = provider.extractEnergy(provider.getEnergyStored(), true);
        energy = consumer.receiveEnergy(energy, false);
        provider.extractEnergy(energy, false);
    }

}
