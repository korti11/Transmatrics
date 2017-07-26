package at.korti.transmatrics.util.helper;

import at.korti.transmatrics.api.crafting.ICraftingRegistry;
import at.korti.transmatrics.api.crafting.ICraftingRegistry.ICraftingEntry;
import at.korti.transmatrics.api.crafting.IFluidCraftingRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

/**
 * Created by Korti on 15.03.2016.
 */
public class InventoryHelper {

    public static int[] getSlotsForFacing(ICraftingRegistry registry, EnumFacing facing) {
        int[] slots = new int[0];
        EnumFacing[] facings = registry.getInputFaces();
        for (EnumFacing inputFacing : facings) {
            if (inputFacing == facing) {
                slots = ArrayUtils.addAll(slots, registry.getInputSlotsIds());
            }
        }
        facings = registry.getOutputFaces();
        for (EnumFacing outputFacing : facings) {
            if (outputFacing == facing) {
                slots = ArrayUtils.addAll(slots, registry.getOutputSlotsIds());
            }
        }
        return slots;
    }

    public static ItemStack getStackFromHandForItem(EntityPlayer player, Item item) {
        return player.getHeldItemMainhand().getItem() == item ?
                player.getHeldItemMainhand() : player.getHeldItemOffhand().getItem() == item ? player.getHeldItemOffhand()
                : null;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isInInventory(EntityPlayer player, ItemStack stack) {
        InventoryPlayer inventoryPlayer = player.inventory;
        return inventoryPlayer.getSlotFor(stack) != -1;
    }

    public static boolean canFill(IFluidCraftingRegistry registry, EnumFacing facing) {
        return Arrays.asList(registry.getFluidInputFaces()).contains(facing);
    }

    public static boolean canDrain(IFluidCraftingRegistry registry, EnumFacing facing) {
        return Arrays.asList(registry.getFluidOutputFaces()).contains(facing);
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

    public static int getMinIndex(int[] indexes) {
        if (indexes.length == 1) {
            return indexes[0];
        } else if (indexes.length == 0) {
            return 0;
        } else {
            int min = indexes[0];
            for (int i = 1; i < indexes.length; i++) {
                if (indexes[i] < min) {
                    min = indexes[i];
                }
            }
            return min;
        }
    }

    public static int getMaxIndex(int[] indexes) {
        if (indexes.length == 1) {
            return indexes[0];
        } else if (indexes.length == 0) {
            return 0;
        } else {
            int max = indexes[0];
            for (int i = 1; i < indexes.length; i++) {
                if (indexes[i] > max) {
                    max = indexes[i];
                }
            }
            return max;
        }
    }

    public static <I> boolean checkOutputStackSize(ICraftingEntry<I, ItemStack> entry, int inventoryStackLimit,
                                                   ItemStack[] outputContent) {
        for(int i = 0; i < outputContent.length && i < entry.getOutputs().length; i++) {
            if (!outputContent[i].isEmpty()) {
                int result = outputContent[i].getCount() + entry.getOutputs()[i].getCount();
                if (result > inventoryStackLimit || result > outputContent[i].getMaxStackSize()) {
                    return false;
                }
            }
        }
        return true;
    }

    public static <I> void decreaseInputForSlot(int slot, ItemStack stack, ICraftingRegistry<I> registry, IInventory inventory) {
        if (slot != -1 && registry.decreaseItemForSlot(slot)) {
            ItemStack stackInSlot = inventory.getStackInSlot(slot);
            stackInSlot.setCount(stackInSlot.getCount() - stack.getCount());
            if (stackInSlot.getCount() <= 0) {
                inventory.setInventorySlotContents(slot, ItemStack.EMPTY);
            }
        }
    }

}
