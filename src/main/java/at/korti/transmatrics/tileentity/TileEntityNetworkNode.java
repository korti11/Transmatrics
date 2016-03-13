package at.korti.transmatrics.tileentity;

import at.korti.transmatrics.api.Constants.NBT;
import at.korti.transmatrics.api.Constants.NetworkMessages;
import at.korti.transmatrics.api.network.*;
import at.korti.transmatrics.tileentity.network.TileEntityController;
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
public abstract class TileEntityNetworkNode extends TileEntity implements INetworkNode, ITickable, INetworkNodeInfo {

    protected INetworkNode networkNode;
    private NBTTagCompound tempCompound;
    private boolean isLoaded = false;
    private BlockPos controller;
    protected int connectionPriority;

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        writeNodeToNBT(compound);
        compound.setInteger(NBT.CONNECTION_PRIORITY, connectionPriority);
        if(controller != null) {
            compound.setInteger(NBT.CONTROLLER_X, controller.getX());
            compound.setInteger(NBT.CONTROLLER_Y, controller.getY());
            compound.setInteger(NBT.CONTROLLER_Z, controller.getZ());
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        tempCompound = compound;
        connectionPriority = compound.getInteger(NBT.CONNECTION_PRIORITY);
        if(compound.hasKey(NBT.CONTROLLER_X)) {
            int x = compound.getInteger(NBT.CONTROLLER_X);
            int y = compound.getInteger(NBT.CONTROLLER_Y);
            int z = compound.getInteger(NBT.CONTROLLER_Z);
            controller = new BlockPos(x, y, z);
        }
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
    public IStatusMessage connectToNode(INetworkNode node, boolean isSecond, boolean simulate) {
        if (networkNode != null) {
            disconnectFromNode();
            networkNode = node;
            return new StatusMessage(true, NetworkMessages.SUCCESSFUL_RECONNECTED);
        } else if (node == null) {
            return new StatusMessage(false, NetworkMessages.CAN_NOT_CONNECTED);
        } else if (this == node) {
            return new StatusMessage(false, NetworkMessages.SAME_NODE);
        }
        if (!isSecond) {
            IStatusMessage message = node.connectToNode(this, true, simulate);
            if (!message.isSuccessful()) {
                return message;
            }
        }
        if(!simulate) {
            networkNode = node;
            if (node.getController() != null && this.controller == null) {
                controller = node.getController().getPos();
                connectionPriority = node.getConnectionPriority() + 1;
            }
        }
        return new StatusMessage(true, NetworkMessages.SUCCESSFUL_CONNECTED);
    }

    @Override
    public IStatusMessage disconnectFromNode(INetworkNode node, boolean isSecond, boolean simulate) {
        if (node == null || networkNode == null || networkNode != node) {
            return new StatusMessage(false, NetworkMessages.NOT_CONNECTED);
        }

        if (!isSecond) {
            IStatusMessage message = node.disconnectFromNode(this, true, simulate);
            if (!message.isSuccessful()) {
                return message;
            }
        }
        if(!simulate) {
            networkNode = null;
        }
        return new StatusMessage(true, NetworkMessages.SUCCESSFUL_DISCONNECTED);
    }

    @Override
    public IStatusMessage disconnectFromNode() {
        return disconnectFromNode(networkNode, false, false);
    }

    @Override
    public INetworkNode getConnection() {
        return networkNode;
    }

    @Override
    public TileEntityController getController() {
        return NetworkHandler.getController(worldObj, controller);
    }

    @Override
    public int getConnectionPriority() {
        return connectionPriority;
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

    @Override
    public boolean isConnected() {
        return networkNode != null;
    }
}
