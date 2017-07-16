package at.korti.transmatrics.registry.crafting;

import at.korti.transmatrics.Transmatrics;
import at.korti.transmatrics.api.crafting.ICraftingRegistry;
import at.korti.transmatrics.api.crafting.IFluidCraftingRegistry;
import at.korti.transmatrics.util.helper.InventoryHelper;
import at.korti.transmatrics.util.helper.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

import static net.minecraft.util.EnumFacing.*;

/**
 * Created by Korti on 30.03.2016.
 */
public final class MagneticSmelteryCraftingRegistry implements IFluidCraftingRegistry<ItemStack> {

    private static MagneticSmelteryCraftingRegistry instance;

    private List<MagneticSmelteryCraftingEntry> recipes;
    private Logger logger = Transmatrics.logger;

    private MagneticSmelteryCraftingRegistry() {
        recipes = new LinkedList<>();
    }

    public static MagneticSmelteryCraftingRegistry getInstance() {
        if (instance == null) {
            instance = new MagneticSmelteryCraftingRegistry();
        }

        return instance;
    }

    @Override
    public EnumFacing[] getFluidInputFaces() {
        return new EnumFacing[0];
    }

    @Override
    public EnumFacing[] getFluidOutputFaces() {
        return new EnumFacing[]{UP, NORTH, EAST, SOUTH, WEST, DOWN};
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
        return new int[0];
    }

    @Override
    public int[] getFluidOutputIds() {
        return new int[]{0};
    }

    public ICraftingRegistry register(ItemStack input, Fluid outFluid, int fluidAmount, int craftingTime) {
        return register(input, new FluidStack(outFluid, fluidAmount), craftingTime);
    }

    public ICraftingRegistry register(ItemStack input, FluidStack output, int craftingTime) {
        return register(new MagneticSmelteryCraftingEntry(input, output, craftingTime));
    }

    @Override
    public ICraftingRegistry register(ICraftingEntry entry) {
        try {
            recipes.add((MagneticSmelteryCraftingEntry) entry);
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
    public ICraftingEntry get(ItemStack... inputs) {
        return get(inputs[0]);
    }

    public MagneticSmelteryCraftingEntry get(ItemStack stack) {
        if(!stack.isEmpty()) {
            String[] oreDicts = ItemStackHelper.getOreDictionaryNames(stack);
            for (ICraftingEntry<ItemStack, FluidStack> entry : recipes) {
                if (stack != null && stack.getItem().equals(entry.getInputs()[0].getItem()) &&
                        stack.getItemDamage() == entry.getInputs()[0].getItemDamage()) {
                    return (MagneticSmelteryCraftingEntry) entry;
                }
                for (String oreDictRecipe : entry.getInputsOreDictionary()) {
                    for (String oreDictEntry : oreDicts) {
                        if (oreDictRecipe.equals(oreDictEntry)) {
                            return (MagneticSmelteryCraftingEntry) entry;
                        }
                    }
                }
            }
        }

        return null;
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
        return new int[0];
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
    public float getChanceForSlot(int slot, ItemStack... inputs) {
        return get(inputs).getOutputChances()[slot];
    }

    @Override
    public EnumFacing[] getInputFaces() {
        return new EnumFacing[]{UP, NORTH, EAST, SOUTH, WEST, DOWN};
    }

    @Override
    public EnumFacing[] getOutputFaces() {
        return new EnumFacing[0];
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
        return false;
    }

    @Override
    public boolean decreaseItemForSlot(int slot) {
        return true;
    }

    public static class MagneticSmelteryCraftingEntry implements ICraftingEntry<ItemStack, FluidStack> {

        private ItemStack input;
        private FluidStack output;
        private int craftingTime;

        public MagneticSmelteryCraftingEntry(ItemStack input, FluidStack output, int craftingTime) {
            this.input = input;
            this.output = output;
            this.craftingTime = craftingTime;
        }

        @Override
        public ItemStack[] getInputs() {
            return new ItemStack[]{input};
        }

        @Override
        public FluidStack[] getOutputs() {
            return new FluidStack[]{output};
        }

        @Override
        public String[] getInputsOreDictionary() {
            return ItemStackHelper.getOreDictionaryNames(input);
        }

        @Override
        public int getCraftingTime() {
            return craftingTime;
        }

        @Override
        public float[] getOutputChances() {
            return new float[]{0f};
        }
    }
}
