package at.korti.transmatrics.registry;

import at.korti.transmatrics.api.crafting.PulverizerCraftingRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 * Created by Korti on 16.03.2016.
 */
public final class Crafting {

    public static void register() {
        registerPulverizerCrafting();
    }

    private static void registerPulverizerCrafting() {
        PulverizerCraftingRegistry.getInstance().register(new ItemStack(Blocks.iron_ore), 200, new ItemStack(Items.iron_ingot), new ItemStack(Items.gold_nugget));
    }

}
