package at.korti.transmatrics.tileentity;

import at.korti.transmatrics.api.energy.EnergyStorage;
import at.korti.transmatrics.api.energy.IEnergyHandler;
import at.korti.transmatrics.api.energy.IEnergyInfo;

/**
 * Created by Korti on 06.03.2016.
 */
public abstract class TileEntityEnergySwitch extends TileEntityNetworkSwitch implements IEnergyHandler, IEnergyInfo {

    protected EnergyStorage energyStorage;

    public TileEntityEnergySwitch(int maxConnections, boolean canConnectToMachines, int capacity, int maxReceive, int maxExtract) {
        super(maxConnections, canConnectToMachines);
        energyStorage = new EnergyStorage(capacity, maxReceive, maxExtract);
    }

    public TileEntityEnergySwitch(int maxConnections, boolean canConnectToMachines, int capacity, int transfer) {
        this(maxConnections, canConnectToMachines, capacity, transfer, transfer);
    }

    public TileEntityEnergySwitch(int maxConnections, boolean canConnectToMachines, int maxEnergy) {
        this(maxConnections, canConnectToMachines, maxEnergy, maxEnergy);
    }

    @Override
    public int receiveEnergy(int energy, boolean simulate) {
        return energyStorage.receiveEnergy(energy, simulate);
    }

    @Override
    public int extractEnergy(int energy, boolean simulate) {
        return energyStorage.extractEnergy(energy, simulate);
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
