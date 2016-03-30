package at.korti.transmatrics.api.crafting;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

/**
 * Created by Korti on 15.03.2016.
 */
public interface ICraftingRegistry<I> {

    ICraftingRegistry register(ICraftingEntry entry);

    ICraftingEntry get(int index);

    ICraftingEntry get(I... inputs);

    ICraftingEntry remove(int index);

    boolean remove(ICraftingEntry entry);

    int size();

    int[] getInputSlotsIds();

    int[] getOutputSlotsIds();

    int inventorySize();

    int getStackLimit();

    float getChanceForSlot(int slot, I... inputs);

    EnumFacing[] getInputFaces();

    EnumFacing[] getOutputFaces();

    int[] getSlotsForFacing(EnumFacing facing);

    boolean canInsertItem(int slot, ItemStack itemStackIn, EnumFacing facing);

    boolean canExtractItem(int slot, ItemStack itemStackIn, EnumFacing facing);

    interface ICraftingEntry<I, O>{

        I[] getInputs();

        O[] getOutputs();

        String[] getInputsOreDictionary();

        int getCraftingTime();

        float[] getOutputChances();
    }
}