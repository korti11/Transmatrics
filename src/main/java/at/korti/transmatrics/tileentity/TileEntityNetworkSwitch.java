package at.korti.transmatrics.tileentity;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.NBT;
import at.korti.transmatrics.api.Constants.NetworkMessages;
import at.korti.transmatrics.api.network.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

import static at.korti.transmatrics.util.helper.TextHelper.localize;

/**
 * Created by Korti on 06.03.2016.
 */
public abstract class TileEntityNetworkSwitch extends TileEntity implements INetworkSwitch, INetworkSwitchInfo, ITickable{

    protected List<INetworkNode> networkNodes;
    protected final int maxConnections;
    protected final boolean canConnectToMachines;
    protected final int range;
    private NBTTagCompound tempCompound;
    private boolean isLoaded = false;

    public TileEntityNetworkSwitch(int maxConnections, boolean canConnectToMachines, int range) {
        this.networkNodes = new ArrayList<>();
        this.maxConnections = maxConnections;
        this.canConnectToMachines = canConnectToMachines;
        this.range = range;
    }

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
    public List<INetworkNode> getConnections() {
        return networkNodes;
    }

    @Override
    public IOperationMessage connectToNode(INetworkNode node) {
        if (networkNodes.size() == maxConnections) {
            return new OperationMessage(localize(NetworkMessages.MAX_CONNECTIONS, maxConnections), false);
        } else if (false) {     //TODO: Check if the node is a machine
            return new OperationMessage(localize(NetworkMessages.MACHINES_CAN_NOT_CONNECTED), false);
        } else if (this == node) {
            return new OperationMessage(localize(NetworkMessages.SAME_NODE), false);
        } else if (networkNodes.contains(node)) {
            return new OperationMessage(localize(NetworkMessages.ALREADY_CONNECTED), false);
        }
        if (node instanceof TileEntity) {
            TileEntity te = (TileEntity) node;
            double distance = Math.sqrt(te.getDistanceSq(pos.getX(), pos.getY(), pos.getZ()));
            if (distance > (range / 2)) {
                return new OperationMessage(localize(NetworkMessages.OUT_OF_RANGE), false);
            }
        }
        IOperationMessage message = node.connectToNode(this);
        if (!message.isSuccessful()) {
            return message;
        }
        networkNodes.add(node);
        return new OperationMessage(localize(NetworkMessages.SUCCESSFUL_CONNECTED), true);
    }

    @Override
    public IOperationMessage disconnectFromNode(INetworkNode node) {
        if (!networkNodes.contains(node)) {
            return new OperationMessage(localize(NetworkMessages.NOT_CONNECTED), false);
        }
        IOperationMessage message = node.disconnectFromNode(node);
        if (!message.isSuccessful()) {
            return message;
        }
        networkNodes.remove(node);
        return new OperationMessage(localize(NetworkMessages.SUCCESSFUL_DISCONNECTED), true);
    }

    @Override
    public IOperationMessage disconnectFromNode() {
        throw new UnsupportedOperationException("Method disconnectFromNode is not allowed in class TileEntityNetworkSwitch");
    }

    @Override
    public INetworkNode getConnection() {
        throw new UnsupportedOperationException("Method getConnection is not allowed in class TileEntityNetworkSwitch");
    }

    @Override
    public int getNetworkConnections() {
        return networkNodes.size();
    }

    @Override
    public int getMaxNetworkConnections() {
        return maxConnections;
    }

    @Override
    public void writeNodeToNBT(NBTTagCompound tagCompound) {
        NBTTagList tagList = new NBTTagList();
        for (INetworkNode networkNode : networkNodes) {
            if (networkNode instanceof TileEntity) {
                TileEntity te = (TileEntity) networkNode;
                NBTTagCompound node = new NBTTagCompound();
                node.setInteger(NBT.NETWORK_X, te.getPos().getX());
                node.setInteger(NBT.NETWORK_Y, te.getPos().getY());
                node.setInteger(NBT.NETWORK_Z, te.getPos().getZ());
                tagList.appendTag(node);
            }
        }
        tagCompound.setTag(NBT.NETWORK_NODES, tagList);
    }

    @Override
    public void readNodeFromNBT(NBTTagCompound tagCompound) {
        networkNodes.clear();
        NBTTagList tagList = tagCompound.getTagList(NBT.NETWORK_NODES, 10);
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound node = tagList.getCompoundTagAt(i);
            int x = node.getInteger(NBT.NETWORK_X);
            int y = node.getInteger(NBT.NETWORK_Y);
            int z = node.getInteger(NBT.NETWORK_Z);
            BlockPos blockPos = new BlockPos(x, y, z);
            TileEntity te = worldObj.getTileEntity(blockPos);
            if (te instanceof INetworkNode) {
                networkNodes.add((INetworkNode) te);
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
