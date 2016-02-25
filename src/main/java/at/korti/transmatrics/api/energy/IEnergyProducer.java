package at.korti.transmatrics.api.energy;

/**
 * Created by Korti on 25.02.2016.
 */
public interface IEnergyProducer extends IEnergyProvider {

    boolean canProduceEnergy();

}
