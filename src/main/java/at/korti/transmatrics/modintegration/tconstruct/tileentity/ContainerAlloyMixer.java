package at.korti.transmatrics.modintegration.tconstruct.tileentity;

import at.korti.transmatrics.api.crafting.ICraftingRegistry;
import at.korti.transmatrics.modintegration.tconstruct.crafting.AlloyMixerCraftingRegistry;
import at.korti.transmatrics.tileentity.TileEntityInventory;
import at.korti.transmatrics.tileentity.container.FluidCraftingContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/**
 * Created by Korti on 12.05.2016.
 */
public class ContainerAlloyMixer extends FluidCraftingContainer {

    public ContainerAlloyMixer(InventoryPlayer inventoryPlayer, TileEntityInventory tileEntity) {
        super(inventoryPlayer, tileEntity, AlloyMixerCraftingRegistry.getInstance());
    }
}
