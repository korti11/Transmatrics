package at.korti.transmatrics.tileentity;

import at.korti.transmatrics.api.energy.EnergyStorage;
import at.korti.transmatrics.api.energy.IEnergyProducer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

/**
 * Created by Korti on 25.02.2016.
 */
public abstract class TileEntityGenerator extends TileEntity implements IEnergyProducer, ITickable {

    private int energyPerTick;
    private EnergyStorage energyStorage;

    public TileEntityGenerator(int energyPerTick, int capacity, int maxReceive, int maxExtract) {
        this.energyPerTick = energyPerTick;
        this.energyStorage = new EnergyStorage(capacity, maxReceive, maxExtract);
    }

    public TileEntityGenerator(int energyPerTick, int capacity, int transfer) {
        this(energyPerTick, capacity, transfer, transfer);
    }

    public TileEntityGenerator(int energyPerTick, int maxValue) {
        this(energyPerTick, maxValue, maxValue);
    }

    @Override
    public void update() {
        if (canProduceEnergy()) {
            energyStorage.receiveEnergy(energyPerTick);
        }
    }

    @Override
    public int getEnergyPerTick() {
        return energyPerTick;
    }

    @Override
    public int getEnergyStored() {
        return energyStorage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored() {
        return energyStorage.getMaxEnergyStored();
    }

    @Override
    public boolean canProvideEnergy() {
        return energyStorage.getEnergyStored() > 0;
    }
}
