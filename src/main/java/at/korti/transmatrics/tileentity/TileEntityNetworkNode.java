package at.korti.transmatrics.tileentity;

import at.korti.transmatrics.api.Constants.NBT;
import at.korti.transmatrics.api.Constants.NetworkMessages;
import at.korti.transmatrics.api.network.INetworkNode;
import at.korti.transmatrics.api.network.IStatusMessage;
import at.korti.transmatrics.api.network.StatusMessage;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;

import static at.korti.transmatrics.util.helper.TextHelper.localize;

/**
 * Created by Korti on 06.03.2016.
 */
public abstract class TileEntityNetworkNode extends TileEntity implements INetworkNode, ITickable {

    protected INetworkNode networkNode;
    private NBTTagCompound tempCompound;
    private boolean isLoaded = false;

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        writeNodeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        tempCompound = compound;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return newState.getBlock() == Blocks.air;
    }

    @Override
    public void update() {
        if (tempCompound != null && !isLoaded && !worldObj.isRemote) {
            readNodeFromNBT(tempCompound);
            isLoaded = true;
            tempCompound = null;
        }
    }

    @Override
    public IStatusMessage connectToNode(INetworkNode node, boolean isSecond) {
        if (networkNode != null) {
            disconnectFromNode();
            networkNode = node;
            return new StatusMessage(localize(NetworkMessages.SUCCESSFUL_RECONNECTED), true);
        } else if (node == null) {
            return new StatusMessage(localize(NetworkMessages.CAN_NOT_CONNECTED), false);
        } else if (this == node) {
            return new StatusMessage(localize(NetworkMessages.SAME_NODE), false);
        }
        if (!isSecond) {
            IStatusMessage message = node.connectToNode(this, true);
            if (!message.isSuccessful()) {
                return message;
            }
        }
        networkNode = node;
        return new StatusMessage(localize(NetworkMessages.SUCCESSFUL_CONNECTED), true);
    }

    @Override
    public IStatusMessage disconnectFromNode(INetworkNode node, boolean isSecond) {
        if (networkNode != node) {
            return new StatusMessage(localize(NetworkMessages.NOT_CONNECTED), false);
        }
        if (!isSecond) {
            IStatusMessage message = node.disconnectFromNode(this, true);
            if (!message.isSuccessful()) {
                return message;
            }
        }
        networkNode = null;
        return new StatusMessage(localize(NetworkMessages.SUCCESSFUL_DISCONNECTED), true);
    }

    @Override
    public IStatusMessage disconnectFromNode() {
        return disconnectFromNode(networkNode, false);
    }

    @Override
    public INetworkNode getConnection() {
        return networkNode;
    }

    @Override
    public void writeNodeToNBT(NBTTagCompound tagCompound) {
        if (networkNode instanceof TileEntity) {
            TileEntity te = (TileEntity) networkNode;
            tagCompound.setInteger(NBT.NETWORK_X, te.getPos().getX());
            tagCompound.setInteger(NBT.NETWORK_Y, te.getPos().getY());
            tagCompound.setInteger(NBT.NETWORK_Z, te.getPos().getZ());
        }
    }

    @Override
    public void readNodeFromNBT(NBTTagCompound tagCompound) {
        if (tagCompound.hasKey(NBT.NETWORK_X)) {
            int x = tagCompound.getInteger(NBT.NETWORK_X);
            int y = tagCompound.getInteger(NBT.NETWORK_Y);
            int z = tagCompound.getInteger(NBT.NETWORK_Z);
            BlockPos blockPos = new BlockPos(x, y, z);
            TileEntity te = worldObj.getTileEntity(blockPos);
            if (te instanceof INetworkNode) {
                networkNode = (INetworkNode) te;
            }
        }
    }

    @Override
    public void writeSelfToNBT(NBTTagCompound tagCompound) {
        tagCompound.setInteger(NBT.NETWORK_X, pos.getX());
        tagCompound.setInteger(NBT.NETWORK_Y, pos.getY());
        tagCompound.setInteger(NBT.NETWORK_Z, pos.getZ());
    }
}
