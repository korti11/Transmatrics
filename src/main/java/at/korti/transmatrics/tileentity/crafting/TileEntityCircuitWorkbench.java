package at.korti.transmatrics.tileentity.crafting;

import at.korti.transmatrics.api.Constants.Energy;
import at.korti.transmatrics.api.Constants.TransmatricsTileEntity;
import at.korti.transmatrics.api.crafting.ICraftingRegistry.ICraftingEntry;
import at.korti.transmatrics.event.MachineCraftingEvent;
import at.korti.transmatrics.registry.crafting.CircuitWorkbenchCraftingRegistry;
import at.korti.transmatrics.tileentity.TileEntityCraftingMachine;
import at.korti.transmatrics.util.helper.InventoryHelper;
import net.minecraft.item.ItemStack;

import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

/**
 * Created by Korti on 21.04.2016.
 */
public class TileEntityCircuitWorkbench extends TileEntityCraftingMachine {

    public TileEntityCircuitWorkbench() {
        super(Energy.CIRCUIT_WORKBENCH_CAPACITY, Energy.CIRCUIT_WORKBENCH_RECEIVE, Energy.CIRCUIT_WORKBENCH_ENERGY_USE,
                TransmatricsTileEntity.CIRCUIT_WORKBENCH.getRegName(), CircuitWorkbenchCraftingRegistry.getInstance());
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        if(InventoryHelper.isInputSlot(craftingRegistry, index) || (InventoryHelper.isOutputSlot(craftingRegistry,index) && stack != null)) {
            super.setInventorySlotContents(index, stack);
            if (InventoryHelper.isInputSlot(craftingRegistry, index) && stack == null) {
                ICraftingEntry entry = craftingRegistry.get(getInputs());
                if(entry.getOutputs()[0] == null) {
                    super.setInventorySlotContents(craftingRegistry.getOutputSlotsIds()[0], null);
                }
            }
        } else if (InventoryHelper.isOutputSlot(craftingRegistry, index) && stack == null) {
            decreaseInputs(getInputs());
            super.setInventorySlotContents(index, stack);
        }
    }

    @Override
    protected void craftItem() {
        if (this.canCraft()) {
            ICraftingEntry<ItemStack, ItemStack> entry = craftingRegistry.get(getInputs());
            int outputId = craftingRegistry.getOutputSlotsIds()[0];
            ItemStack output = entry.getOutputs()[0];
            EVENT_BUS.post(new MachineCraftingEvent.Pre<>(entry, craftingRegistry, this, this));
            craftItem(outputId, output);
            EVENT_BUS.post(new MachineCraftingEvent.Post<>(entry, craftingRegistry, this, this));
        }
    }

    @Override
    protected void craftItem(int slot, ItemStack stack) {
        if(stack != null) {
            ItemStack copyStack = stack.copy();
            copyStack.stackSize = getInputs()[0].stackSize;
            setInventorySlotContents(slot, copyStack);
        }
    }

    @Override
    protected boolean canCraft() {
        ItemStack[] inputs = getInputs();
        if(inputs[0] == null) {
            return false;
        }
        int stackSize = inputs[0].stackSize;
        for (ItemStack stack : inputs) {
            if (stack != null && stack.stackSize < stackSize) {
                return false;
            }
        }
        return super.canCraft();
    }

    @Override
    protected void decreaseInputs(ItemStack... stacks) {
        if(stacks[0] != null) {
            int stackSize = stacks[0].stackSize;
            for (ItemStack stack : stacks) {
                int slot = getSlotForStack(true, stack);
                if (slot != -1) {
                    getStackInSlot(slot).stackSize -= stackSize;
                    if (getStackInSlot(slot).stackSize <= 0) {
                        setInventorySlotContents(slot, null);
                    }
                }
            }
        }
    }
}
