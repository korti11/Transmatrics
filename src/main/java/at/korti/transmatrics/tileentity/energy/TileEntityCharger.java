package at.korti.transmatrics.tileentity.energy;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.Energy;
import at.korti.transmatrics.api.Constants.ModIntegrationIds;
import at.korti.transmatrics.api.Constants.NBT;
import at.korti.transmatrics.api.Constants.TransmatricsTileEntity;
import at.korti.transmatrics.api.energy.IChargeable;
import at.korti.transmatrics.block.ActiveMachineBlock;
import at.korti.transmatrics.modintegration.tconstruct.helper.ModifierHelper;
import at.korti.transmatrics.tileentity.TileEntityInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Loader;

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
        compound.setInteger(NBT.CRAFTING_TIME, storedEnergy);
        compound.setInteger(NBT.TOTAL_CRAFTING_TIME, maxStoreEnergy);
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
        } else if (Loader.isModLoaded(ModIntegrationIds.TCONSTRUCT) && ModifierHelper.hasChargeable(stack)) {
            item = ModifierHelper.getChargeable(stack);
        } else if (stack == null && index == 0) {
            this.storedEnergy = 0;
            this.maxStoreEnergy = 0;
            return;
        }
        if (item != null) {
            this.storedEnergy = item.getEnergy(stack);
            this.maxStoreEnergy = item.getCapacity();
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
                    IChargeable chargeable = null;
                    if (stack.getItem() instanceof IChargeable) {
                        chargeable = (IChargeable) stack.getItem();
                    } else if (Loader.isModLoaded(ModIntegrationIds.TCONSTRUCT)) {
                        chargeable = ModifierHelper.getChargeable(stack);
                    }
                    if (chargeable != null) {
                        chargeable.charge(stack, energyUse, false);
                        this.storedEnergy = chargeable.getEnergy(stack);
                        markDirty = true;
                        isCharging = true;
                        if (chargeable.getEnergy(stack) == chargeable.getCapacity()) {
                            setInventorySlotContents(1, stack);
                            setInventorySlotContents(0, null);
                        }
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
            worldObj.markBlockForUpdate(pos);
        }
    }

    private boolean canCharge() {
        ItemStack stack = getStackInSlot(0);
        if (stack == null) {
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
