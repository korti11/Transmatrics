package at.korti.transmatrics.tileentity.container;

import at.korti.transmatrics.registry.crafting.AlloyMixerCraftingRegistry;
import at.korti.transmatrics.tileentity.TileEntityInventory;
import net.minecraft.entity.player.InventoryPlayer;

/**
 * Created by Korti on 12.05.2016.
 */
public class ContainerAlloyMixer extends FluidCraftingContainer {

    public ContainerAlloyMixer(InventoryPlayer inventoryPlayer, TileEntityInventory tileEntity) {
        super(inventoryPlayer, tileEntity, AlloyMixerCraftingRegistry.getInstance());
    }
}
