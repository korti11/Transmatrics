package at.korti.transmatrics.api.crafting;

import at.korti.transmatrics.util.helper.InventoryHelper;
import at.korti.transmatrics.util.helper.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.EnumFacing;

import java.util.Map;

import static net.minecraft.util.EnumFacing.*;

/**
 * Created by Korti on 29.03.2016.
 */
public final class FurnaceCraftingRegistry implements ICraftingRegistry<ItemStack>{

    private static FurnaceCraftingRegistry instance;

    private FurnaceCraftingRegistry() {

    }

    public static FurnaceCraftingRegistry getInstance() {
        if (instance == null) {
            instance = new FurnaceCraftingRegistry();
        }
        return instance;
    }

    public FurnaceCraftingRegistry register(ItemStack input, ItemStack output, float xp) {
        return (FurnaceCraftingRegistry) register(new FurnaceCraftingEntry(input, output, xp));
    }

    @Override
    public ICraftingRegistry register(ICraftingEntry entry) {
        float xp = 0f;
        if (entry instanceof FurnaceCraftingEntry) {
            xp = ((FurnaceCraftingEntry) entry).getXp();
        }
        FurnaceRecipes.instance().addSmeltingRecipe((ItemStack) entry.getInputs()[0], (ItemStack) entry.getOutputs()[0], xp);
        return null;
    }

    @Override
    public ICraftingEntry get(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ICraftingEntry get(ItemStack... inputs) {
        if(inputs[0] != null) {
            return new FurnaceCraftingEntry(getInputStack(inputs[0]), FurnaceRecipes.instance().getSmeltingResult(inputs[0]),
                    FurnaceRecipes.instance().getSmeltingExperience(inputs[0]));
        }
        return null;
    }

    private ItemStack getInputStack(ItemStack stack) {
        for (Map.Entry<ItemStack, ItemStack> entry : FurnaceRecipes.instance().getSmeltingList().entrySet()) {
            if (compareItemStacks(stack, entry.getKey())) {
                ItemStack tempStack = entry.getKey().copy();
                if (tempStack.getItem().getRegistryName().split(":")[0].equals("minecraft") && tempStack.getItemDamage() == 32767) {
                    tempStack.setItemDamage(0);
                }
                return tempStack;
            }
        }
        return stack;
    }

    private boolean compareItemStacks(ItemStack stackA, ItemStack stackB)
    {
        return stackB.getItem() == stackA.getItem() && (stackB.getMetadata() == 32767 || stackB.getMetadata() == stackA.getMetadata());
    }

    @Override
    public ICraftingEntry remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(ICraftingEntry entry) {
        return FurnaceRecipes.instance().getSmeltingList().remove(entry.getInputs()[0]).
                getIsItemStackEqual((ItemStack) entry.getOutputs()[0]);
    }

    @Override
    public int size() {
        return FurnaceRecipes.instance().getSmeltingList().size();
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
    public float getChanceForSlot(int slot, ItemStack... inputs) {
        ICraftingEntry entry = get(inputs);
        return entry.getOutputChances()[slot];
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

    public static class FurnaceCraftingEntry implements ICraftingEntry<ItemStack, ItemStack> {

        private ItemStack input;
        private ItemStack output;
        private float xp;

        public FurnaceCraftingEntry(ItemStack input, ItemStack output, float xp) {
            this.input = input;
            this.output = output;
            this.xp = xp;
        }

        @Override
        public ItemStack[] getInputs() {
            return new ItemStack[]{input};
        }

        @Override
        public ItemStack[] getOutputs() {
            return new ItemStack[]{output};
        }

        @Override
        public String[] getInputsOreDictionary() {
            return ItemStackHelper.getOreDictionaryNames(input);
        }

        @Override
        public int getCraftingTime() {
            return 200;
        }

        @Override
        public float[] getOutputChances() {
            return new float[]{0f, 1f};
        }

        public float getXp() {
            return xp;
        }
    }
}
