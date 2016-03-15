package at.korti.transmatrics.util.helper;

import at.korti.transmatrics.api.crafting.ICraftingRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

/**
 * Created by Korti on 15.03.2016.
 */
public class InventoryHelper {

    public static int[] getSlotsForFacing(ICraftingRegistry registry, EnumFacing facing) {
        EnumFacing[] facings = registry.getInputFaces();
        for (EnumFacing inputFacing : facings) {
            if (inputFacing == facing) {
                return registry.getInputSlotsIds();
            }
        }
        facings = registry.getOutputFaces();
        for (EnumFacing outputFacing : facings) {
            if (outputFacing == facing) {
                return registry.getOutputSlotsIds();
            }
        }
        return new int[0];
    }

    public static boolean isInputSlot(ICraftingRegistry registry, int slot) {
        return containsSlot(registry.getInputSlotsIds(), slot);
    }

    public static boolean isOutputSlot(ICraftingRegistry registry, int slot) {
        return containsSlot(registry.getOutputSlotsIds(), slot);
    }

    public static boolean containsSlot(int[] slots, int slot) {
        for (int i : slots) {
            if (i == slot) {
                return true;
            }
        }
        return false;
    }

    public static boolean canInsertItem(ICraftingRegistry registry, int slot, EnumFacing facing) {
        if (isInputSlot(registry, slot)) {
            int[] slotsForFace = getSlotsForFacing(registry, facing);
            for (int i : slotsForFace) {
                if (i == slot) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean canExtractItem(ICraftingRegistry registry, int slot, EnumFacing facing) {
        if (isOutputSlot(registry, slot)) {
            int[] slotsForFace = getSlotsForFacing(registry, facing);
            for (int i : slotsForFace) {
                if (i == slot) {
                    return true;
                }
            }
        }
        return false;
    }

}
