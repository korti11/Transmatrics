package at.korti.transmatrics.item.tool;

import at.korti.transmatrics.api.Constants.NBT;
import at.korti.transmatrics.api.Constants.ToolTips;
import at.korti.transmatrics.api.Constants.TransmatricsItem;
import at.korti.transmatrics.api.item.IChangeMode;
import at.korti.transmatrics.api.network.INetworkNode;
import at.korti.transmatrics.api.network.INetworkSwitch;
import at.korti.transmatrics.api.network.IStatusMessage;
import at.korti.transmatrics.item.ModItem;
import at.korti.transmatrics.util.helper.MessageHelper;
import at.korti.transmatrics.util.helper.TextHelper;
import at.korti.transmatrics.util.helper.WorldHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Korti on 06.03.2016.
 */
public class ItemConnector extends ModItem implements IChangeMode<ItemConnector.ConnectorMode> {

    public ItemConnector() {
        super(TransmatricsItem.CONNECTOR.getRegName(), EnumChatFormatting.YELLOW);

        setMaxStackSize(1);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, playerIn, tooltip, advanced);

        NBTTagCompound compound = stack.getTagCompound();
        if(compound != null && compound.hasKey(NBT.NETWORK_BLOCK_NAME)) {
            BlockPos blockPos = new BlockPos(compound.getInteger(NBT.NETWORK_X), compound.getInteger(NBT.NETWORK_Y),
                    compound.getInteger(NBT.NETWORK_Z));
            String localizedName = TextHelper.localize(compound.getString(NBT.NETWORK_BLOCK_NAME));
            tooltip.add(TextHelper.localize(ToolTips.CONNECTION_NAME, localizedName));
            tooltip.add(TextHelper.localize(ToolTips.CONNECTION_POS, blockPos.getX(), blockPos.getY(), blockPos.getZ()));
        }

        tooltip.add(TextHelper.localize(ToolTips.SELECTED_MODE, getModeName(stack)));
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
                            message = savedNode.connectToNode(networkNode, false, false);
                        } else {
                            message = networkNode.connectToNode(savedNode, false, false);
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
                    stack.getTagCompound().setString(NBT.NETWORK_BLOCK_NAME, WorldHelper.getBlock(world, pos).getUnlocalizedName() + ".name");
                    stack.getTagCompound().setBoolean(NBT.CLEAR_STORED_NETWORK, false);
                }
            }
        }
        return super.onItemUseFirst(stack, player, world, pos, side, hitX, hitY, hitZ);
    }

    public boolean hasNetworkNodeStored(ItemStack stack) {
        if (stack.getTagCompound() == null) {
            return false;
        } else {
            return stack.getTagCompound().hasKey(NBT.NETWORK_X);
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
        if(!worldIn.isRemote) {
            NBTTagCompound tagCompound = itemStackIn.getTagCompound();
            if (playerIn.isSneaking() && tagCompound.hasKey(NBT.CLEAR_STORED_NETWORK) && tagCompound.getBoolean(NBT.CLEAR_STORED_NETWORK)) {
                tagCompound.removeTag(NBT.NETWORK_BLOCK_NAME);
                tagCompound.removeTag(NBT.NETWORK_X);
                tagCompound.removeTag(NBT.NETWORK_Y);
                tagCompound.removeTag(NBT.NETWORK_Z);
                tagCompound.removeTag(NBT.CLEAR_STORED_NETWORK);
            } else if(tagCompound.hasKey(NBT.CLEAR_STORED_NETWORK) && !tagCompound.getBoolean(NBT.CLEAR_STORED_NETWORK)) {
                tagCompound.setBoolean(NBT.CLEAR_STORED_NETWORK, true);
            } else if (playerIn.isSneaking()) {
                cycleThroughMode(itemStackIn);
            }
        }
        return super.onItemRightClick(itemStackIn, worldIn, playerIn);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return super.getItemStackDisplayName(stack) + "(" + getModeName(stack) + ")";
    }

    @Override
    public ConnectorMode[] getAllModes() {
        return ConnectorMode.values();
    }

    @Override
    public ConnectorMode getCurrentMode(ItemStack stack) {
        NBTTagCompound tagCompound;
        if ((tagCompound = stack.getTagCompound()) != null) {
            int mode = tagCompound.getInteger(NBT.SELECTED_MODE);
            return getAllModes()[mode];
        } else {
            tagCompound = new NBTTagCompound();
            stack.setTagCompound(tagCompound);
            ConnectorMode mode = ConnectorMode.CONNECT;
            tagCompound.setInteger(NBT.SELECTED_MODE, mode.ordinal());
            return mode;
        }
    }

    @Override
    public void setMode(ConnectorMode mode, ItemStack stack) {
        NBTTagCompound tagCompound;
        if ((tagCompound = stack.getTagCompound()) == null) {
            tagCompound = new NBTTagCompound();
            stack.setTagCompound(tagCompound);
        }
        tagCompound.setInteger(NBT.SELECTED_MODE, mode.ordinal());
    }

    @Override
    public ConnectorMode cycleThroughMode(ItemStack stack) {
        ConnectorMode mode = getCurrentMode(stack);
        if (mode.ordinal() + 1 < getAllModes().length) {
            mode = getAllModes()[mode.ordinal() + 1];
        } else {
            mode = getAllModes()[0];
        }
        setMode(mode, stack);
        return mode;
    }

    private String getModeName(ItemStack stack) {
        ConnectorMode mode = getCurrentMode(stack);
        String modeName = mode.name().replace("_", " ").toLowerCase();
        return getColorForMode(mode) + TextHelper.firstCharOfEachWordUppercase(modeName) + EnumChatFormatting.RESET;
    }

    private EnumChatFormatting getColorForMode(ConnectorMode connectorMode) {
        switch (connectorMode) {
            case CONNECT:
                return EnumChatFormatting.GREEN;
            case DISCONNECT:
                return EnumChatFormatting.RED;
            case SHOW_CONNECTION:
                return EnumChatFormatting.YELLOW;
            default:
                return EnumChatFormatting.WHITE;
        }
    }

    public enum ConnectorMode{
        CONNECT,
        DISCONNECT,
        SHOW_CONNECTION
    }

}
