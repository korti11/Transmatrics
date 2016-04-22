package at.korti.transmatrics.util.helper;

import at.korti.transmatrics.api.electronic.IElectronicPart;
import at.korti.transmatrics.api.energy.IEnergyStorage;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by Korti on 22.04.2016.
 */
public class ElectronicPartsHelper {

    public static void updateEnergyStorage(IEnergyStorage storage, List<ItemStack> parts, int defaultCapacity, int metaValue) {
        int newCapacity = defaultCapacity;
        for (ItemStack stack : parts) {
            if (stack.getItem() instanceof IElectronicPart && stack.getItemDamage() == metaValue) {
                IElectronicPart electronicPart = (IElectronicPart) stack.getItem();
                newCapacity += (electronicPart.getImprovementValue(metaValue) * stack.stackSize);
            }
        }
        storage.setMaxEnergyStorage(newCapacity);
    }
}
