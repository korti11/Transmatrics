package at.korti.transmatrics.modintegration.tconstruct.crafting;

import at.korti.transmatrics.Transmatrics;
import at.korti.transmatrics.api.crafting.ICraftingRegistry;
import at.korti.transmatrics.api.crafting.IFluidCraftingRegistry;
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
 * Created by Korti on 12.05.2016.
 */
public final class AlloyMixerCraftingRegistry implements IFluidCraftingRegistry<FluidStack> {

    private static AlloyMixerCraftingRegistry instance;

    private List<AlloyMixerCraftingEntry> recipes;
    private Logger logger = Transmatrics.logger;

    private AlloyMixerCraftingRegistry() {
        this.recipes = new LinkedList<>();
    }

    public static AlloyMixerCraftingRegistry getInstance() {
        if (instance == null) {
            instance = new AlloyMixerCraftingRegistry();
        }
        return instance;
    }

    @Override
    public EnumFacing[] getFluidInputFaces() {
        return new EnumFacing[]{UP, NORTH, EAST, SOUTH, WEST, DOWN};
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
        return new int[]{10000, 10000, 10000, 10000};
    }

    @Override
    public int[] getFluidInputIds() {
        return new int[]{0, 1, 2};
    }

    @Override
    public int[] getFluidOutputIds() {
        return new int[]{3};
    }

    public ICraftingRegistry register(FluidStack output, FluidStack... inputs) {
        if (inputs.length < 4) {
            return register(new AlloyMixerCraftingEntry(output, inputs));
        }
        return this;
    }

    @Override
    public ICraftingRegistry register(ICraftingEntry entry) {
        try {
            recipes.add((AlloyMixerCraftingEntry) entry);
        } catch (Exception e) {
            logger.error(String.format("Can't register alloy mixer recipe with the inputs=%s.",
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
        for (AlloyMixerCraftingEntry entry : recipes) {
            boolean flag = false;
            for (int i = 0; i < entry.getInputs().length; i++) {
                flag = false;
                FluidStack need = entry.getInputs()[i];
                for (int l = 0; l < inputs.length; l++) {
                    FluidStack get = inputs[l];
                    if (get != null) {
                        if (get.containsFluid(need)) {
                            flag = true;
                            break;
                        }
                    }
                }
                if (!flag) {
                    break;
                }
            }
            if (flag) {
                return entry;
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
        return new int[0];
    }

    @Override
    public int[] getOutputSlotsIds() {
        return new int[0];
    }

    @Override
    public int inventorySize() {
        return 0;
    }

    @Override
    public int getStackLimit() {
        return 0;
    }

    @Override
    public float getChanceForSlot(int slot, FluidStack... inputs) {
        return 0;
    }

    @Override
    public EnumFacing[] getInputFaces() {
        return new EnumFacing[0];
    }

    @Override
    public EnumFacing[] getOutputFaces() {
        return new EnumFacing[0];
    }

    @Override
    public int[] getSlotsForFacing(EnumFacing facing) {
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack itemStackIn, EnumFacing facing) {
        return false;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack itemStackIn, EnumFacing facing) {
        return false;
    }

    @Override
    public boolean decreaseItemForSlot(int slot) {
        return false;
    }

    public static class AlloyMixerCraftingEntry implements ICraftingEntry<FluidStack, FluidStack>{

        private FluidStack[] inputs;
        private FluidStack output;

        public AlloyMixerCraftingEntry(FluidStack output, FluidStack... inputs) {
            this.inputs = inputs;
            this.output = output;
        }

        @Override
        public FluidStack[] getInputs() {
            return inputs;
        }

        @Override
        public FluidStack[] getOutputs() {
            return new FluidStack[]{output};
        }

        @Override
        public String[] getInputsOreDictionary() {
            return new String[0];
        }

        @Override
        public int getCraftingTime() {
            return 0;
        }

        @Override
        public float[] getOutputChances() {
            return new float[0];
        }
    }
}
