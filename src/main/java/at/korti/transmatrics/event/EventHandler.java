package at.korti.transmatrics.event;

import at.korti.transmatrics.api.Constants.NBT;
import at.korti.transmatrics.block.ActiveMachineBlock;
import at.korti.transmatrics.item.electronic.ItemCircuit;
import at.korti.transmatrics.tileentity.crafting.TileEntityCircuitStamper;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

/**
 * Created by Korti on 20.04.2016.
 */
public final class EventHandler {

    @SubscribeEvent
    public void handleCircuitStamperActive(MachineCraftingEvent.Pre<ItemStack, ItemStack> event) {
        if (event.inventory instanceof TileEntityCircuitStamper) {
            TileEntityCircuitStamper tileEntity = (TileEntityCircuitStamper) event.inventory;
            tileEntity.shouldActive = true;
            ActiveMachineBlock.setState(true, tileEntity.getWorld(), tileEntity.getPos());
        }
    }

    @SubscribeEvent
    public void handleCircuitCrafting(ItemCraftedEvent event) {
        ItemStack circuit = findCircuit(event.craftMatrix);
        if (circuit != null) {
            NBTTagCompound electronicParts = circuit.getSubCompound(NBT.ELECTRONIC_PARTS, false);
            if (electronicParts != null) {
                if (event.crafting.getTagCompound() == null) {
                    event.crafting.setTagCompound(new NBTTagCompound());
                }
                event.crafting.getTagCompound().setTag(NBT.ELECTRONIC_PARTS, electronicParts.copy());
            }
        }
    }

    public ItemStack findCircuit(IInventory craftingMatrix) {

        for (int i = 0; i < craftingMatrix.getSizeInventory(); i++) {
            ItemStack stack = craftingMatrix.getStackInSlot(i);
            if (stack != null && stack.getItem() instanceof ItemCircuit) {
                return stack;
            }
        }
        return null;
    }

}
