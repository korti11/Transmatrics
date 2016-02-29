package at.korti.transmatrics.api.energy;

/**
 * Created by Korti on 25.02.2016.
 */
public interface IEnergyProducer extends IEnergyProvider {

    /**
     * @return Amount of energy can generated per tick.
     */
    int getEnergyPerTick();

    /**
     * @return Can energy produced.
     */
    boolean canProduceEnergy();

}
