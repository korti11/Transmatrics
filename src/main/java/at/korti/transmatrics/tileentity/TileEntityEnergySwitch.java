package at.korti.transmatrics.tileentity;

import at.korti.transmatrics.api.energy.IEnergyInfo;
import cofh.redstoneflux.api.IEnergyHandler;
import cofh.redstoneflux.api.IEnergyProvider;
import cofh.redstoneflux.api.IEnergyReceiver;
import cofh.redstoneflux.impl.EnergyStorage;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

/**
 * Created by Korti on 06.03.2016.
 */
public abstract class TileEntityEnergySwitch extends TileEntityNetworkSwitch implements IEnergyHandler, IEnergyProvider,
        IEnergyReceiver, IEnergyInfo {

    protected EnergyStorage energyStorage;

    public TileEntityEnergySwitch(int maxConnections, boolean canConnectToMachines, int range, int capacity, int maxReceive, int maxExtract) {
        super(maxConnections, canConnectToMachines, range);
        energyStorage = new EnergyStorage(capacity, maxReceive, maxExtract);
    }

    public TileEntityEnergySwitch(int maxConnections, boolean canConnectToMachines, int range, int capacity, int transfer) {
        this(maxConnections, canConnectToMachines, range, capacity, transfer, transfer);
    }

    public TileEntityEnergySwitch(int maxConnections, boolean canConnectToMachines, int range, int maxEnergy) {
        this(maxConnections, canConnectToMachines, range, maxEnergy, maxEnergy);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        energyStorage.writeToNBT(compound);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        energyStorage.readFromNBT(compound);
    }

    @Override
    public int extractEnergy(EnumFacing enumFacing, int energy, boolean simulate) {
        IBlockState state = getWorld().getBlockState(pos);
        getWorld().notifyBlockUpdate(pos, state, state, 3);
        this.markDirty();
        return energyStorage.extractEnergy(energy, simulate);
    }

    @Override
    public int receiveEnergy(EnumFacing enumFacing, int energy, boolean simulate) {
        IBlockState state = getWorld().getBlockState(pos);
        getWorld().notifyBlockUpdate(pos, state, state, 3);
        this.markDirty();
        return energyStorage.receiveEnergy(energy, simulate);
    }

    @Override
    public int getEnergyStored(EnumFacing enumFacing) {
        return energyStorage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(EnumFacing enumFacing) {
        return energyStorage.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(EnumFacing enumFacing) {
        return false;
    }

    @Override
    public int getEnergyStored() {
        return energyStorage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored() {
        return energyStorage.getMaxEnergyStored();
    }

}
