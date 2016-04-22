package at.korti.transmatrics.util.helper;

import at.korti.transmatrics.api.electronic.IElectronicPart;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by Korti on 22.04.2016.
 */
public class ElectronicPartsHelper {

    public static int updateEnergyStorage(List<ItemStack> parts, int defaultCapacity, int meta) {
        int newCapacity = defaultCapacity;
        for (ItemStack stack : parts) {
            if (stack.getItem() instanceof IElectronicPart && stack.getItemDamage() == meta) {
                IElectronicPart electronicPart = (IElectronicPart) stack.getItem();
                newCapacity += (electronicPart.getImprovementValue(meta) * stack.stackSize);
            }
        }
        return newCapacity;
    }

    public static int updateMaxEfficiency(List<ItemStack> parts, int defaultMaxEfficiency, int meta) {
        int newEfficiency = defaultMaxEfficiency;
        for (ItemStack stack : parts) {
            if (stack.getItem() instanceof IElectronicPart && stack.getItemDamage() == meta) {
                IElectronicPart electronicPart = (IElectronicPart) stack.getItem();
                newEfficiency += (electronicPart.getImprovementValue(meta) * stack.stackSize);
            }
        }
        return newEfficiency;
    }

    public static int updateReceive(List<ItemStack> parts, int defaultReceive, int meta) {
        int newReceive = defaultReceive;
        for (ItemStack stack : parts) {
            if (stack.getItem() instanceof IElectronicPart && stack.getItemDamage() == meta) {
                IElectronicPart electronicPart = (IElectronicPart) stack.getItem();
                newReceive += (electronicPart.getImprovementValue(meta) * stack.stackSize);
            }
        }
        return newReceive;
    }
}
