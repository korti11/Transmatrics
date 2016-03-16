package at.korti.transmatrics.api.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

/**
 * Created by Korti on 15.03.2016.
 */
public interface ICraftingRegistry {

    ICraftingRegistry register(ICraftingEntry entry);

    ICraftingEntry get(int index);

    ICraftingEntry get(ItemStack... inputs);

    ICraftingEntry remove(int index);

    boolean remove(ICraftingEntry entry);

    int size();

    int[] getInputSlotsIds();

    int[] getOutputSlotsIds();

    int inventorySize();

    int getStackLimit();

    EnumFacing[] getInputFaces();

    EnumFacing[] getOutputFaces();

    int[] getSlotsForFacing(EnumFacing facing);

    boolean canInsertItem(int slot, ItemStack itemStackIn, EnumFacing facing);

    boolean canExtractItem(int slot, ItemStack itemStackIn, EnumFacing facing);

    interface ICraftingEntry{

        ItemStack[] getInputs();

        ItemStack[] getOutputs();

        String[] getInputsOreDictionary();

        int getXp();

        int getCraftingTime();

    }
}