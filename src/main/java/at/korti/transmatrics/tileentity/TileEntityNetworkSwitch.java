package at.korti.transmatrics.tileentity;

import at.korti.transmatrics.api.Constants.NBT;
import at.korti.transmatrics.api.Constants.NetworkMessages;
import at.korti.transmatrics.api.network.*;
import at.korti.transmatrics.event.ConnectNetworkNodesEvent;
import at.korti.transmatrics.event.DisconnectNetworkNodesEvent;
import at.korti.transmatrics.tileentity.network.TileEntityController;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

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
    private BlockPos controller;
    protected int connectionPriority;

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
        compound.setInteger(NBT.CONNECTION_PRIORITY, getConnectionPriority());
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
        return newState.getBlock() == Blocks.AIR;
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
    public IStatusMessage connectToNode(INetworkNode node, boolean isSecond, boolean simulate) {
        EVENT_BUS.post(new ConnectNetworkNodesEvent(this, node));
        if (networkNodes.size() == maxConnections) {
            return new StatusMessage(false, NetworkMessages.MAX_CONNECTIONS, maxConnections);
        } else if (!canConnectToMachines && (node instanceof TileEntityGenerator)) {     //TODO: Check if the node is a machine
            return new StatusMessage(false, NetworkMessages.MACHINES_CAN_NOT_CONNECTED);
        } else if (this == node) {
            return new StatusMessage(false, NetworkMessages.SAME_NODE);
        } else if (networkNodes.contains(node)) {
            return new StatusMessage(false, NetworkMessages.ALREADY_CONNECTED);
        }
        if (node instanceof TileEntity && !isSecond) {
            if(!this.isInRange(node) &&
                    (node instanceof INetworkSwitch ? !((INetworkSwitch) node).isInRange(this) : true)) {
                return new StatusMessage(false, NetworkMessages.OUT_OF_RANGE);
            }
        }
        if (!isSecond) {
            IStatusMessage message = node.connectToNode(this, true, simulate);
            if (!message.isSuccessful()) {
                return message;
            }
        }
        if (!simulate) {
            networkNodes.add(node);
            if (node.getController() != null && this.controller == null) {
                this.connectToController(node.getController().getMaster().pos, node.getConnectionPriority() + 1);
            }
        }
        return new StatusMessage(true, NetworkMessages.SUCCESSFUL_CONNECTED);
    }

    @Override
    public IStatusMessage disconnectFromNode(INetworkNode node, boolean isSecond, boolean simulate) {
        EVENT_BUS.post(new DisconnectNetworkNodesEvent(this, node));
        if (!networkNodes.contains(node)) {
            return new StatusMessage(false, NetworkMessages.NOT_CONNECTED);
        }
        if (!isSecond) {
            IStatusMessage message = node.disconnectFromNode(node, true, simulate);
            if (!message.isSuccessful()) {
                return message;
            }
        }
        if (!simulate) {
            networkNodes.remove(node);
            node.disconnectFromController();
        }
        return new StatusMessage(true, NetworkMessages.SUCCESSFUL_DISCONNECTED);
    }

    @Override
    public IStatusMessage disconnectedFromAllNodes() {
        List<INetworkNode> tempNodes = new ArrayList<>(networkNodes);
        for (INetworkNode node : tempNodes) {
            if (node instanceof INetworkSwitch) {
                node.disconnectFromNode(this, true, false);
            } else {
                node.disconnectFromNode();
            }
        }
        return new StatusMessage(true, NetworkMessages.SUCCESSFUL_DISCONNECTED);
    }

    @Override
    public IStatusMessage disconnectFromNode() {
        throw new UnsupportedOperationException("Method disconnectFromNode is not allowed in class TileEntityNetworkSwitch");
    }

    @Override
    public INetworkNode getConnection() {
        throw new UnsupportedOperationException("Method getConnection is not allowed in class TileEntityNetworkSwitch");
    }

    @Override
    public boolean isInRange(INetworkNode node) {
        if (node instanceof TileEntity) {
            TileEntity te = (TileEntity) node;
            double range = Math.sqrt(te.getDistanceSq(pos.getX(), pos.getY(), pos.getZ()));
            return range < (this.range / 2);
        }
        return false;
    }

    public TileEntityController getController(BlockPos pos) {
        return NetworkHandler.getController(worldObj, pos);
    }

    @Override
    public TileEntityController getController() {
        return getController(controller);
    }

    @Override
    public int getConnectionPriority() {
        return connectionPriority;
    }

    @Override
    public void connectToController(BlockPos controllerPos, int connectionPriority) {
        controller = controllerPos;
        this.connectionPriority = connectionPriority;
        for (INetworkNode node : networkNodes) {
            if (node.getController() == null) {
                node.connectToController(controllerPos, connectionPriority + 1);
            }
        }
    }

    @Override
    public void disconnectFromController() {
        controller = null;
        connectionPriority = 0;
        for (INetworkNode node : networkNodes) {
            if(node.getController() != null) {
                node.disconnectFromController();
            }
        }
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
