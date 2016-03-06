package at.korti.transmatrics.tileentity;

import at.korti.transmatrics.api.energy.EnergyStorage;
import at.korti.transmatrics.api.energy.IEnergyHandler;
import at.korti.transmatrics.api.energy.IEnergyInfo;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Korti on 06.03.2016.
 */
public abstract class TileEntityEnergyNode extends TileEntityNetworkNode implements IEnergyHandler, IEnergyInfo{

    protected EnergyStorage energyStorage;

    public TileEntityEnergyNode(int capacity, int maxReceive, int maxExtract) {
        energyStorage = new EnergyStorage(capacity, maxReceive, maxExtract);
    }

    public TileEntityEnergyNode(int capacity, int transfer) {
        this(capacity, transfer, transfer);
    }

    public TileEntityEnergyNode(int maxEnergy) {
        this(maxEnergy, maxEnergy);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        energyStorage.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        energyStorage.readFromNBT(compound);
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
