package at.korti.transmatrics.tileentity.container;

import at.korti.transmatrics.api.crafting.ICraftingRegistry;
import at.korti.transmatrics.tileentity.TileEntityInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fluids.FluidStack;

/**
 * Created by Korti on 12.05.2016.
 */
public abstract class FluidCraftingContainer extends BasicCraftingContainer<FluidStack> {

    public FluidCraftingContainer(InventoryPlayer inventoryPlayer, TileEntityInventory tileEntity, ICraftingRegistry<FluidStack> craftingRegistry) {
        super(inventoryPlayer, tileEntity, craftingRegistry);
    }
}
