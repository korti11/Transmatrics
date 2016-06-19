package at.korti.transmatrics.tileentity;

import at.korti.transmatrics.api.TransmatricsApi;
import at.korti.transmatrics.api.energy.*;
import at.korti.transmatrics.api.network.INetworkNode;
import at.korti.transmatrics.api.network.INetworkSwitch;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

/**
 * Created by Korti on 06.03.2016.
 */
public abstract class TileEntityEnergySwitch extends TileEntityNetworkSwitch implements IEnergyHandler, IEnergyInfo {

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
    public void update() {
        super.update();
        if (!worldObj.isRemote && canProvideEnergy()) {
            List<INetworkNode> nodes = getNetworkNodes();
            if(nodes != null) {
                for (INetworkNode node : nodes) {
                    if (node instanceof IEnergyConsumer) {
                        IEnergyConsumer consumer = (IEnergyConsumer) node;
                        if (node.getConnectionPriority() > this.getConnectionPriority() || (node.getConnectionPriority() == 0 && this.getConnectionPriority() == 0 && !(node instanceof INetworkSwitch))) {
//                        if (consumer.getMaxEnergyStored() - consumer.getEnergyStored() <= energyStorage.getMaxEnergyStored() - energyStorage.getEnergyStored()) {
//                            continue;
//                        }
                            EnergyHandler.transferEnergy(this, consumer);
                        }
                    }
                }
            }
        }
    }

    @Override
    public int receiveEnergy(int energy, boolean simulate) {
        worldObj.markBlockForUpdate(pos);
        return energyStorage.receiveEnergy(energy, simulate);
    }

    @Override
    public int extractEnergy(int energy, boolean simulate) {
        worldObj.markBlockForUpdate(pos);
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
