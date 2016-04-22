package at.korti.transmatrics.api.energy;

import at.korti.transmatrics.api.Constants;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Korti on 25.02.2016.
 */
public class EnergyStorage implements IEnergyStorage {

    protected int energy;
    protected int capacity;
    protected int maxReceive;
    protected int maxExtract;

    public EnergyStorage(int capacity, int maxReceive, int maxExtract) {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
    }

    public EnergyStorage(int capacity, int transfer) {
        this(capacity, transfer, transfer);
    }

    public EnergyStorage(int maxValue) {
        this(maxValue, maxValue);
    }

    //region Getter and Setter
    public void setEnergyStored(int energy) {
        this.energy = energy;
        if (this.energy > this.capacity) {
            this.energy = this.capacity;
        } else if (this.energy < 0) {
            this.energy = 0;
        }
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
        if (this.energy > this.capacity) {
            this.energy = this.capacity;
        }
    }

    public void setTransfer(int transfer) {
        this.setMaxExtract(transfer);
        this.setMaxReceive(transfer);
    }

    public int getMaxReceive() {
        return maxReceive;
    }

    public void setMaxReceive(int maxReceive) {
        this.maxReceive = maxReceive;
    }

    public int getMaxExtract() {
        return maxExtract;
    }

    public void setMaxExtract(int maxExtract) {
        this.maxExtract = maxExtract;
    }
    //endregion

    public EnergyStorage readFromNBT(NBTTagCompound tagCompound) {
        this.energy = tagCompound.getInteger(Constants.NBT.ENERGY);
        if (this.energy > this.capacity) {
            this.energy = this.capacity;
        }
        return this;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        if (tagCompound == null) {
            tagCompound = new NBTTagCompound();
        }
        tagCompound.setInteger(Constants.NBT.ENERGY, this.energy);
        return tagCompound;
    }

    public void modifyEnergy(int energy) {
        this.energy += energy;
        if (this.energy > this.capacity) {
            this.energy = this.capacity;
        } else if (this.energy < 0) {
            this.energy = 0;
        }
    }

    //region IEnergyStorage
    @Override
    public int receiveEnergy(int energy, boolean simulate) {
        int toStore = Math.min(Math.min(energy, maxReceive), this.capacity - this.energy);
        if(!simulate) {
            this.energy += toStore;
        }
        return toStore;
    }

    @Override
    public int extractEnergy(int energy, boolean simulate) {
        int toExtract = Math.min(Math.min(energy, maxExtract), this.energy);
        if(!simulate) {
            this.energy -= toExtract;
        }
        return toExtract;
    }

    @Override
    public int getEnergyStored() {
        return energy;
    }

    @Override
    public int getMaxEnergyStored() {
        return capacity;
    }

    @Override
    public void setMaxEnergyStorage(int capacity) {
        setCapacity(capacity);
    }
    //endregion
}
