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
        super(Energy.CHARGER_CAPACITY, Energy.CHARGER_RECEIVE, 2, 1, false, TransmatricsTileEntity.CHARGER.getRegName());
        energyUse = Energy.CHARGER_ENERGY_USE;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        compound.setInteger(NBT.CRAFTING_TIME, storedEnergy);
        compound.setInteger(NBT.TOTAL_CRAFTING_TIME, maxStoreEnergy);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.storedEnergy = compound.getInteger(NBT.CRAFTING_TIME);
        this.maxStoreEnergy = compound.getInteger(NBT.TOTAL_CRAFTING_TIME);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        super.setInventorySlotContents(index, stack);
        IChargeable item = null;
        if (stack != null && stack.getItem() instanceof IChargeable && index == 0) {
            item = (IChargeable) stack.getItem();
        } else if (stack == null && index == 0) {
            this.storedEnergy = 0;
            this.maxStoreEnergy = 0;
            return;
        }
        if (item != null) {
            this.storedEnergy = item.getEnergy(stack);
            this.maxStoreEnergy = item.getCapacity(stack);
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

        if (!getWorld().isRemote) {
            if (energyStorage.getEnergyStored() - energyUse >= 0 && !getStackInSlot(0).isEmpty()) {
                if(canCharge()) {
                    ItemStack stack = getStackInSlot(0);
                    IChargeable chargeable = null;
                    if (stack.getItem() instanceof IChargeable) {
                        chargeable = (IChargeable) stack.getItem();
                    }
                    if (chargeable != null) {
                        chargeable.charge(stack, energyUse, false);
                        this.storedEnergy = chargeable.getEnergy(stack);
                        markDirty = true;
                        isCharging = true;
                        if (chargeable.getEnergy(stack) == chargeable.getCapacity(stack)) {
                            setInventorySlotContents(1, stack);
                            setInventorySlotContents(0, ItemStack.EMPTY);
                        }
                    }
                }
            }
            if (isCharging && !ActiveMachineBlock.isActive(getWorld(), pos)) {
                markDirty = true;
                ActiveMachineBlock.setState(true, getWorld(), pos);
            } else if (!isCharging && ActiveMachineBlock.isActive(getWorld(), pos)) {
                markDirty = true;
                ActiveMachineBlock.setState(false, getWorld(), pos);
            }
        }

        if (markDirty) {
            this.markDirty();
            syncClient();
        }
    }

    private boolean canCharge() {
        ItemStack stack = getStackInSlot(0);
        if (stack.isEmpty()) {
            return false;
        }
        return true;
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
