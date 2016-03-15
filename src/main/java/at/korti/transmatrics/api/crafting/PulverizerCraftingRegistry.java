package at.korti.transmatrics.api.crafting;

import at.korti.transmatrics.Transmatrics;
import at.korti.transmatrics.util.helper.InventoryHelper;
import at.korti.transmatrics.util.helper.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

import static net.minecraft.util.EnumFacing.*;

/**
 * Created by Korti on 15.03.2016.
 */
public final class PulverizerCraftingRegistry implements ICraftingRegistry {

    private static PulverizerCraftingRegistry instance;
    private static Logger logger = Transmatrics.logger;

    private List<PulverizerCraftingEntry> recipes;

    private PulverizerCraftingRegistry() {
        this.recipes = new LinkedList<>();
    }

    public static PulverizerCraftingRegistry getInstance() {
        if (instance == null) {
            instance = new PulverizerCraftingRegistry();
        }
        return instance;
    }

    public PulverizerCraftingRegistry register(ItemStack input, int craftingTime, int xp, ItemStack... outputs) {
        return (PulverizerCraftingRegistry) register(new PulverizerCraftingEntry(input, craftingTime, xp, outputs));
    }

    @Override
    public ICraftingRegistry register(ICraftingEntry entry) {
        try {
            recipes.add((PulverizerCraftingEntry) entry);
        } catch (Exception e) {
            logger.error(String.format("Can't register pulverizer recipe with the inputs=%s.",
                    entry.getInputs().toString()), e);
        }
        return this;
    }

    @Override
    public ICraftingEntry get(int index) {
        return recipes.get(index);
    }

    @Override
    public ICraftingEntry remove(int index) {
        return recipes.remove(index);
    }

    @Override
    public boolean remove(ICraftingEntry entry) {
        return recipes.remove(entry);
    }

    @Override
    public int size() {
        return recipes.size();
    }

    @Override
    public int[] getInputSlotsIds() {
        return new int[]{0};
    }

    @Override
    public int[] getOutputSlotsIds() {
        return new int[]{1, 2};
    }

    @Override
    public EnumFacing[] getInputFaces() {
        return new EnumFacing[]{NORTH, EAST, SOUTH, WEST, UP};
    }

    @Override
    public EnumFacing[] getOutputFaces() {
        return new EnumFacing[]{DOWN};
    }

    @Override
    public int[] getSlotsForFacing(EnumFacing facing) {
        return InventoryHelper.getSlotsForFacing(this, facing);
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack itemStackIn, EnumFacing facing) {
        return InventoryHelper.canInsertItem(this, slot, facing);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack itemStackIn, EnumFacing facing) {
        return InventoryHelper.canExtractItem(this, slot, facing);
    }

    public static class PulverizerCraftingEntry implements ICraftingEntry {

        private ItemStack input;
        private ItemStack[] outputs;
        private int craftingTime;
        private int xp;

        public PulverizerCraftingEntry(ItemStack input, int craftingTime, int xp, ItemStack... outputs) {
            this.input = input;
            this.outputs = outputs;
            this.craftingTime = craftingTime;
            this.xp = xp;
        }

        @Override
        public ItemStack[] getInputs() {
            return new ItemStack[]{input};
        }

        @Override
        public ItemStack[] getOutputs() {
            return outputs;
        }

        @Override
        public String[] getInputsOreDictionary() {
            return ItemStackHelper.getOreDictionaryNames(input);
        }

        @Override
        public int getXp() {
            return xp;
        }

        @Override
        public int getCraftingTime() {
            return craftingTime;
        }
    }
}
