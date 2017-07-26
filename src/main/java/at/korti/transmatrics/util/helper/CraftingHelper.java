package at.korti.transmatrics.util.helper;

import at.korti.transmatrics.api.crafting.ICraftingRegistry;
import at.korti.transmatrics.api.crafting.IFluidItemCraftingRegistry;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Korti on 29.03.2016.
 */
public class CraftingHelper {

    private final static int CHANCE_POOL = 20;
    private final static Random random = new Random();

    @SuppressWarnings("unchecked")
    public static <I> boolean chanceToCraft(ICraftingRegistry registry, int slot, I... inputs) {
        float chance = registry.getChanceForSlot(slot, inputs);
        return generateCraftingNumbers(chance).contains(random.nextInt(CHANCE_POOL));
    }

    public static boolean chanceToCraft(IFluidItemCraftingRegistry registry, int slot, FluidStack[] fluidInputs, ItemStack[] itemInputs) {
        float chance = registry.getChanceForSlot(slot, fluidInputs, itemInputs);
        return generateCraftingNumbers(chance).contains(random.nextInt(CHANCE_POOL));
    }

    private static List<Integer> generateCraftingNumbers(float chance) {
        int countCraftNumbers = Math.round(CHANCE_POOL * chance);
        List<Integer> craftNumbers = new ArrayList<>();
        for (int i = 0; i < countCraftNumbers; i++) {
            int nextNumber = random.nextInt(CHANCE_POOL);
            if (!craftNumbers.contains(nextNumber)) {
                craftNumbers.add(nextNumber);
            } else {
                i--;
            }
        }
        return craftNumbers;
    }

    public static <I> void craftItem(int slot, ItemStack stack, ICraftingRegistry<I> registry, I[] inputs,
                                        IInventory inventory) {
        if (chanceToCraft(registry, slot, inputs)) {
            ItemStack slotStack = inventory.getStackInSlot(slot);
            if (slotStack.isEmpty()) {
                inventory.setInventorySlotContents(slot, stack.copy());
            } else if (slotStack.isItemEqual(stack)) {
                int stackSize = slotStack.getCount();
                slotStack.setCount(stackSize + stack.getCount());
            }
        }
    }

}
