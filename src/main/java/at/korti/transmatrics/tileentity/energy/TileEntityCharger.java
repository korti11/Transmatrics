package at.korti.transmatrics.tileentity.energy;

import at.korti.transmatrics.api.Constants.Energy;
import at.korti.transmatrics.api.Constants.NBT;
import at.korti.transmatrics.api.Constants.TransmatricsTileEntity;
import at.korti.transmatrics.api.energy.IChargeable;
import at.korti.transmatrics.block.ActiveMachineBlock;
import at.korti.transmatrics.tileentity.TileEntityInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Korti on 16.05.2016.
 */
public class TileEntityCharger extends TileEntityInventory {

    private int energyUse;
    private int storedEnergy;
    private int maxStoreEnergy;

    public TileEntityCharger() {
        super(Energy.CHARGER_CAPACITY, Energy.CHARGER_RECEIVE, 2, 1, TransmatricsTileEntity.CHARGER.getRegName());
        energyUse = Energy.CHARGER_ENERGY_USE;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger(NBT.ENERGY, storedEnergy);
        compound.setInteger(NBT.CAPACITY, maxStoreEnergy);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        super.setInventorySlotContents(index, stack);
        if (stack != null && stack.getItem() instanceof IChargeable) {
            IChargeable item = (IChargeable) stack.getItem();
            this.storedEnergy = item.getEnergy(stack);
            this.maxStoreEnergy = item.getCapacity();
        } else if (stack == null) {
            this.storedEnergy = 0;
            this.maxStoreEnergy = 0;
        }
    }

    @Override
    public void update() {
        super.update();

        if (!canProvideEnergy()) {
            return;
        }

        boolean markDirty = false;
        boolean isCharging = false;

        if (!worldObj.isRemote) {
            if (energyStorage.getEnergyStored() - energyUse >= 0 && getStackInSlot(0) != null) {
                if(canCharge()) {
                    ItemStack stack = getStackInSlot(0);
                    if (stack.getItem() instanceof IChargeable) {
                        IChargeable item = (IChargeable) stack.getItem();
                        item.charge(stack, energyUse, false);
                        this.storedEnergy = item.getEnergy(stack);
                        markDirty = true;
                        isCharging = true;
                    }
                }
            }
            if (isCharging && !ActiveMachineBlock.isActive(worldObj, pos)) {
                markDirty = true;
                ActiveMachineBlock.setState(true, worldObj, pos);
            } else if (!isCharging && ActiveMachineBlock.isActive(worldObj, pos)) {
                markDirty = true;
                ActiveMachineBlock.setState(false, worldObj, pos);
            }
        }

        if (markDirty) {
            this.markDirty();
        }
    }

    private boolean canCharge() {
        ItemStack stack = getStackInSlot(0);
        if (stack == null) {
            return false;
        } else if (stack.getItem() instanceof IChargeable &&
                getStackInSlot(1) == null) {
            return true;
        }
        return false;
    }

    @Override
    public int getField(int id) {
        switch (id) {
            case 0:
                return storedEnergy;
            case 1:
                return maxStoreEnergy;
            case 2:
                return energyStorage.getEnergyStored();
            case 3:
                return energyStorage.getCapacity();
            default:
                return 0;
        }
    }

    @Override
    public void setField(int id, int value) {
        switch (id) {
            case 0:
                storedEnergy = value;
                break;
            case 1:
                maxStoreEnergy = value;
                break;
            case 2:
                energyStorage.setEnergyStored(value);
                break;
            case 3:
                energyStorage.setCapacity(value);
        }
    }

    @Override
    public int getFieldCount() {
        return 4;
    }
}
