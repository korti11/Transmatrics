package at.korti.transmatrics.item.tool;

import at.korti.transmatrics.api.Constants.NBT;
import at.korti.transmatrics.api.Constants.TransmatricsItem;
import at.korti.transmatrics.api.network.INetworkNode;
import at.korti.transmatrics.api.network.INetworkSwitch;
import at.korti.transmatrics.api.network.IStatusMessage;
import at.korti.transmatrics.item.ModItem;
import at.korti.transmatrics.util.helper.MessageHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * Created by Korti on 06.03.2016.
 */
public class ItemConnector extends ModItem {

    public ItemConnector() {
        super(TransmatricsItem.CONNECTOR.getRegName(), EnumChatFormatting.YELLOW);

        setMaxStackSize(1);
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(!world.isRemote) {
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof INetworkNode) {
                INetworkNode networkNode = (INetworkNode) te;
                if (hasNetworkNodeStored(stack)) {
                    NBTTagCompound tagCompound = stack.getTagCompound();
                    int x = tagCompound.getInteger(NBT.NETWORK_X);
                    int y = tagCompound.getInteger(NBT.NETWORK_Y);
                    int z = tagCompound.getInteger(NBT.NETWORK_Z);
                    BlockPos blockPos = new BlockPos(x, y, z);
                    TileEntity savedTile = world.getTileEntity(blockPos);
                    if (savedTile instanceof INetworkNode) {
                        INetworkNode savedNode = (INetworkNode) savedTile;
                        IStatusMessage message;
                        if (savedNode instanceof INetworkSwitch) {
                            message = savedNode.connectToNode(networkNode, false);
                        } else {
                            message = networkNode.connectToNode(savedNode, false);
                        }
                        if (message.isSuccessful()) {
                            stack.setTagCompound(new NBTTagCompound());
                        }
                        MessageHelper.sendMessages(player, message);
                    }
                } else {
                    if (stack.getTagCompound() == null) {
                        stack.setTagCompound(new NBTTagCompound());
                    }
                    networkNode.writeSelfToNBT(stack.getTagCompound());
                    stack.getTagCompound().setBoolean(NBT.CLEAR_STORED_NETWORK, false);
                }
            }
        }
        return super.onItemUseFirst(stack, player, world, pos, side, hitX, hitY, hitZ);
    }

    private boolean hasNetworkNodeStored(ItemStack stack) {
        if (stack.getTagCompound() == null) {
            return false;
        } else {
            return stack.getTagCompound().hasKey(NBT.NETWORK_X);
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
        if (playerIn.isSneaking() && itemStackIn.getTagCompound().getBoolean(NBT.CLEAR_STORED_NETWORK)) {
            itemStackIn.setTagCompound(new NBTTagCompound());
        }
        itemStackIn.getTagCompound().setBoolean(NBT.CLEAR_STORED_NETWORK, true);
        return super.onItemRightClick(itemStackIn, worldIn, playerIn);
    }
}
