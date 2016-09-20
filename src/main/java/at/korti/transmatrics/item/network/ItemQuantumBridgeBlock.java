package at.korti.transmatrics.item.network;

import at.korti.transmatrics.api.Constants.NBT;
import at.korti.transmatrics.item.crafting.ItemMachineBlock;
import at.korti.transmatrics.network.TransmatricsPacketHandler;
import at.korti.transmatrics.network.message.CreateQuantumIdMessage;
import at.korti.transmatrics.util.helper.InventoryHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.List;

/**
 * Created by Korti on 27.06.2016.
 */
public class ItemQuantumBridgeBlock extends ItemMachineBlock {

    public ItemQuantumBridgeBlock(Block block) {
        super(block);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, playerIn, tooltip, advanced);
        NBTTagCompound tagCompound;
        if ((tagCompound = stack.getTagCompound()) == null) {
            stack.setTagCompound(tagCompound = new NBTTagCompound());
        }
        if(InventoryHelper.isInInventory(playerIn, stack) && playerIn.worldObj.isRemote) {
            if (!tagCompound.hasKey(NBT.QUANTUM_BRIDGE_MAP_NAME)) {
//                tagCompound.setString(NBT.QUANTUM_BRIDGE_MAP_NAME, QuantumBridgeHandler.createQuantumBridge());
//                TransmatricsPacketHandler.sendToServer(new PacketUpdateItemStackMessage(stack,
//                        playerIn.inventory.getSlotFor(stack)));
                TransmatricsPacketHandler.sendToServer(new CreateQuantumIdMessage(playerIn.inventory.getSlotFor(stack)));
            }
            tooltip.add("ID: " + tagCompound.getString(NBT.QUANTUM_BRIDGE_MAP_NAME));
        }
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {

        return super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState);
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        super.onCreated(stack, worldIn, playerIn);
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
//        if(QuantumBridgeHandler.canBeUsed()) {
//            NBTTagCompound tagCompound;
//            if ((tagCompound = stack.getTagCompound()) == null) {
//                stack.setTagCompound(tagCompound = new NBTTagCompound());
//            }
//            tagCompound.setString(NBT.QUANTUM_BRIDGE_MAP_NAME, QuantumBridgeHandler.createQuantumBridge());
//        }
        return super.initCapabilities(stack, nbt);
    }
}
