package at.korti.transmatrics.item.tool;

import at.korti.transmatrics.api.Constants;
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
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Korti on 06.03.2016.
 */
public class ItemConnector extends ModItem implements IChangeMode<ItemConnector.ConnectorMode> {

    public ItemConnector() {
        super(TransmatricsItem.CONNECTOR.getRegName(), TextFormatting.YELLOW);

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
    public EnumActionResult onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
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
                        IStatusMessage message = null;
                        if (savedNode instanceof INetworkSwitch) {
                            if(getCurrentMode(stack) == ConnectorMode.CONNECT) {
                                message = savedNode.connectToNode(networkNode, false, false);
                            } else if (getCurrentMode(stack) == ConnectorMode.DISCONNECT) {
                                message = savedNode.disconnectFromNode(networkNode, false, false);
                            }
                        } else {
                            if(getCurrentMode(stack) == ConnectorMode.CONNECT) {
                                message = networkNode.connectToNode(savedNode, false, false);
                            } else if(getCurrentMode(stack) == ConnectorMode.DISCONNECT) {
                                message = networkNode.disconnectFromNode(savedNode, false, false);
                            }
                        }
                        if (message != null && message.isSuccessful()) {
                            clearStoreNetworkNode(stack);
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
        return super.onItemUseFirst(stack, player, world, pos, side, hitX, hitY, hitZ, hand);
    }

    public boolean hasNetworkNodeStored(ItemStack stack) {
        return stack.getTagCompound() != null && stack.getTagCompound().hasKey(NBT.NETWORK_X);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        if(!worldIn.isRemote) {
            NBTTagCompound tagCompound = itemStackIn.getTagCompound();
            if (playerIn.isSneaking() && tagCompound != null && tagCompound.hasKey(NBT.CLEAR_STORED_NETWORK) && tagCompound.getBoolean(NBT.CLEAR_STORED_NETWORK)) {
                clearStoreNetworkNode(itemStackIn);
            } else if(tagCompound != null && tagCompound.hasKey(NBT.CLEAR_STORED_NETWORK) && !tagCompound.getBoolean(NBT.CLEAR_STORED_NETWORK)) {
                tagCompound.setBoolean(NBT.CLEAR_STORED_NETWORK, true);
            } else if (playerIn.isSneaking()) {
                cycleThroughMode(itemStackIn);
            }
        }
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return super.getItemStackDisplayName(stack) + "(" + getModeName(stack) + ")";
    }

    private void clearStoreNetworkNode(ItemStack stack) {
        NBTTagCompound tagCompound = stack.getTagCompound();
        if(tagCompound != null) {
            tagCompound.removeTag(NBT.NETWORK_BLOCK_NAME);
            tagCompound.removeTag(NBT.NETWORK_X);
            tagCompound.removeTag(NBT.NETWORK_Y);
            tagCompound.removeTag(NBT.NETWORK_Z);
            tagCompound.removeTag(NBT.CLEAR_STORED_NETWORK);
        }
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
        return getColorForMode(mode) + TextHelper.firstCharOfEachWordUppercase(modeName) + TextFormatting.RESET;
    }

    private TextFormatting getColorForMode(ConnectorMode connectorMode) {
        switch (connectorMode) {
            case CONNECT:
                return TextFormatting.GREEN;
            case DISCONNECT:
                return TextFormatting.RED;
            case SHOW_CONNECTION:
                return TextFormatting.YELLOW;
            default:
                return TextFormatting.WHITE;
        }
    }

    public enum ConnectorMode{
        CONNECT,
        DISCONNECT,
        SHOW_CONNECTION
    }

}
