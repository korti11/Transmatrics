package at.korti.transmatrics.api.crafting;

import at.korti.transmatrics.Transmatrics;
import at.korti.transmatrics.util.helper.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

import static net.minecraft.util.EnumFacing.*;

/**
 * Created by Korti on 01.04.2016.
 */
public class LiquidCasterCraftingRegistry implements IFluidItemCraftingRegistry {

    private static LiquidCasterCraftingRegistry instance;

    private List<LiquidCasterCraftingEntry> recipes;
    private Logger logger = Transmatrics.logger;

    private LiquidCasterCraftingRegistry() {
        recipes = new LinkedList<>();
    }

    public static LiquidCasterCraftingRegistry getInstance() {
        if (instance == null) {
            instance = new LiquidCasterCraftingRegistry();
        }
        return instance;
    }

    @Override
    public IFluidItemCraftingEntry get(FluidStack[] fluidInputs, ItemStack[] itemInputs) {
        return get(fluidInputs[0], itemInputs[0]);
    }

    public LiquidCasterCraftingEntry get(FluidStack fluidInput, ItemStack itemInput) {
        for (LiquidCasterCraftingEntry entry : recipes) {
            if (entry.getInputs()[0].isFluidEqual(fluidInput) && entry.getSecondInputs()[0].isItemEqual(itemInput)) {
                return entry;
            }
        }
        return null;
    }

    @Override
    public EnumFacing[] getFluidInputFaces() {
        return new EnumFacing[]{UP, NORTH, EAST, SOUTH, WEST, DOWN};
    }

    @Override
    public EnumFacing[] getFluidOutputFaces() {
        return new EnumFacing[0];
    }

    @Override
    public boolean canFill(Fluid fluidIn, EnumFacing facing) {
        return InventoryHelper.canFill(this, facing);
    }

    @Override
    public boolean canDrain(Fluid fluidIn, EnumFacing facing) {
        return InventoryHelper.canDrain(this, facing);
    }

    @Override
    public int[] getFluidCapacities() {
        return new int[]{10000};
    }

    @Override
    public int[] getFluidInputIds() {
        return new int[]{0};
    }

    @Override
    public int[] getFluidOutputIds() {
        return new int[0];
    }

    public LiquidCasterCraftingRegistry register(FluidStack input, ItemStack secondInput, ItemStack output, int craftingTime) {
        return (LiquidCasterCraftingRegistry) register(new LiquidCasterCraftingEntry(input, secondInput, output, craftingTime));
    }

    @Override
    public ICraftingRegistry register(ICraftingEntry entry) {
        try {
            recipes.add((LiquidCasterCraftingEntry) entry);
        } catch (Exception e) {
            logger.error(String.format("Can't register magnetic smeltery recipe with the inputs=%s.",
                    entry.getInputs().toString()), e);
        }
        return this;
    }

    @Override
    public ICraftingEntry get(int index) {
        return recipes.get(index);
    }

    @Override
    public ICraftingEntry get(FluidStack... inputs) {
        throw new UnsupportedOperationException();
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
        return new int[]{1};
    }

    @Override
    public int inventorySize() {
        return getInputSlotsIds().length + getOutputSlotsIds().length;
    }

    @Override
    public int getStackLimit() {
        return 64;
    }

    @Override
    public float getChanceForSlot(int slot, FluidStack... inputs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public float getChanceForSlot(int slot, FluidStack[] fluidInputs, ItemStack[] itemStacks) {
        return get(fluidInputs,itemStacks).getOutputChances()[slot];
    }

    @Override
    public EnumFacing[] getInputFaces() {
        return new EnumFacing[]{UP};
    }

    @Override
    public EnumFacing[] getOutputFaces() {
        return new EnumFacing[]{NORTH, EAST, SOUTH, WEST};
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

    public static class LiquidCasterCraftingEntry implements IFluidItemCraftingEntry<ItemStack> {

        private FluidStack input;
        private ItemStack secondInput;
        private ItemStack output;
        private int craftingTime;

        public LiquidCasterCraftingEntry(FluidStack input, ItemStack secondInput, ItemStack output, int craftingTime) {
            this.input = input;
            this.secondInput = secondInput;
            this.output = output;
            this.craftingTime = craftingTime;
        }

        @Override
        public FluidStack[] getInputs() {
            return new FluidStack[]{input};
        }

        @Override
        public ItemStack[] getOutputs() {
            return new ItemStack[]{output};
        }

        @Override
        public String[] getInputsOreDictionary() {
            return new String[0];
        }

        @Override
        public int getCraftingTime() {
            return craftingTime;
        }

        @Override
        public float[] getOutputChances() {
            return new float[]{0f, 1f};
        }

        @Override
        public ItemStack[] getSecondInputs() {
            return new ItemStack[]{secondInput};
        }

    }
}
