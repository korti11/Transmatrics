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

}
