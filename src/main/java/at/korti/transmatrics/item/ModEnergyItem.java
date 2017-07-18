package at.korti.transmatrics.item;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.NBT;
import cofh.redstoneflux.api.IEnergyContainerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;

/**
 * Created by Korti on 18.07.2017.
 */
public class ModEnergyItem extends ModItem implements IEnergyContainerItem {

    protected int capacity;
    protected int maxReceive;
    protected int maxExtract;

    public ModEnergyItem(String name, TextFormatting nameColor, int capacity, int maxReceive, int maxExtract) {
        super(name, nameColor);

        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;

        this.setMaxDamage(capacity);
    }

    public ModEnergyItem(String name, TextFormatting nameColor, int capacity, int maxTransfer) {
        this(name, nameColor, capacity, maxTransfer, maxTransfer);
    }

    public ModEnergyItem(String name, TextFormatting nameColor, int capacity) {
        this(name, nameColor, capacity, capacity);
    }

    public ModEnergyItem(String name, int capacity) {
        this(name, capacity, capacity);
    }

    public ModEnergyItem(String name, int capacity, int maxTransfer) {
        this(name, capacity, maxTransfer, maxTransfer);
    }

    public ModEnergyItem(String name, int capacity, int maxReceive, int maxExtract) {
        this(name, TextFormatting.WHITE, capacity, maxReceive, maxExtract);
    }

    @Override
    public int getDamage(ItemStack stack) {
        return getMaxEnergyStored(stack) - getEnergyStored(stack);
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        return getMaxEnergyStored(stack) > getEnergyStored(stack);
    }

    public ModEnergyItem setCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public ModEnergyItem setMaxTransfer(int maxTransfer) {
        this.setMaxReceive(maxTransfer);
        this.setMaxExtract(maxTransfer);
        return this;
    }

    public ModEnergyItem setMaxReceive(int maxReceive) {
        this.maxReceive = maxReceive;
        return this;
    }

    public ModEnergyItem setMaxExtract(int maxExtract) {
        this.maxExtract = maxExtract;
        return this;
    }

    protected ModEnergyItem setEnergy(ItemStack container, int energy) {
        if (!container.hasTagCompound()) {
            container.setTagCompound(new NBTTagCompound());
        }
        if (energy > getMaxEnergyStored(container)) {
            energy = getMaxEnergyStored(container);
        }

        container.getTagCompound().setInteger(NBT.ENERGY, energy);
        return this;
    }

    @Override
    public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
        if (!container.hasTagCompound()) {
            container.setTagCompound(new NBTTagCompound());
        }

        int energy = getEnergyStored(container);
        int energyReceived = Math.min(this.capacity - energy, Math.min(this.maxReceive, maxReceive));
        if (!simulate) {
            energy += energyReceived;
            container.getTagCompound().setInteger(NBT.ENERGY, energy);
        }

        return energyReceived;
    }

    @Override
    public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
        if (container.hasTagCompound() && container.getTagCompound().hasKey(NBT.ENERGY)) {
            int energy = getEnergyStored(container);
            int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));
            if (!simulate) {
                energy -= energyExtracted;
                container.getTagCompound().setInteger(NBT.ENERGY, energy);
            }

            return energyExtracted;
        }

        return 0;
    }

    @Override
    public int getEnergyStored(ItemStack container) {
        return container.hasTagCompound() && container.getTagCompound().hasKey(NBT.ENERGY) ?
                container.getTagCompound().getInteger(NBT.ENERGY) : 0;
    }

    @Override
    public int getMaxEnergyStored(ItemStack container) {
        return this.capacity;
    }
}
