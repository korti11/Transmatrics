package at.korti.transmatrics.api.energy;

import cofh.redstoneflux.api.IEnergyProvider;
import cofh.redstoneflux.api.IEnergyReceiver;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

/**
 * Created by Korti on 10.03.2016.
 */
public final class EnergyHandler {

    public static void transferEnergy(IEnergyProvider provider, IEnergyReceiver consumer) {
        int energy = provider.extractEnergy(EnumFacing.NORTH, provider.getEnergyStored(EnumFacing.NORTH), true);
        energy = consumer.receiveEnergy(EnumFacing.NORTH, energy, false);
        provider.extractEnergy(EnumFacing.NORTH, energy, false);
    }

    public static int extractEnergy(ItemStack stack, int energy, boolean simulate) {
        int toExtract = Math.min(energy, stack.getMaxDamage() - stack.getItemDamage());
        if (!simulate) {
            stack.setItemDamage(stack.getItemDamage() + toExtract);
        }
        return toExtract;
    }

    public static int storeEnergy(ItemStack stack, int energy, boolean simulate) {
        int toStore = Math.min(energy, stack.getItemDamage());
        if (!simulate) {
            stack.setItemDamage(stack.getItemDamage() - toStore);
        }
        return toStore;
    }

    public static void modifyEnergy(ItemStack stack, int energy, boolean simulate) {
        stack.setItemDamage(stack.getItemDamage() + energy);
        if (stack.getItemDamage() > stack.getMaxDamage()) {
            stack.setItemDamage(stack.getMaxDamage());
        } else if (stack.getItemDamage() < 0) {
            stack.setItemDamage(0);
        }
    }

}
