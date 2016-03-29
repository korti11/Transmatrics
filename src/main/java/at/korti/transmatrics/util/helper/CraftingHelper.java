package at.korti.transmatrics.util.helper;

import at.korti.transmatrics.api.crafting.ICraftingRegistry;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Korti on 29.03.2016.
 */
public class CraftingHelper {

    private final static int CHANCE_POOL = 10;
    private final static Random random = new Random();

    public static boolean chanceToCraft(ICraftingRegistry registry, int slot, ItemStack... inputs) {
        float chance = registry.getChanceForSlot(slot, inputs);
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
        return craftNumbers.contains(random.nextInt(CHANCE_POOL));
    }

}
