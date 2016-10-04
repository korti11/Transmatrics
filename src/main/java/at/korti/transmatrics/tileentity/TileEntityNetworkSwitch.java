package at.korti.transmatrics.tileentity;

import at.korti.transmatrics.api.Constants.NBT;
import at.korti.transmatrics.api.Constants.NetworkMessages;
import at.korti.transmatrics.api.network.*;
import at.korti.transmatrics.event.ConnectNetworkNodesEvent;
import at.korti.transmatrics.event.DisconnectNetworkNodesEvent;
import at.korti.transmatrics.tileentity.network.TileEntityController;
import at.korti.transmatrics.util.math.DimensionBlockPos;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

/**
 * Created by Korti on 06.03.2016.
 */
public abstract class TileEntityNetworkSwitch extends TileEntity implements INetworkSwitch, INetworkSwitchInfo, ITickable{

    protected List<BlockPos> networkNodes;
    protected final int maxConnections;
    protected final boolean canConnectToMachines;
    protected final int range;
    private DimensionBlockPos controller;
    protected int connectionPriority;

    public TileEntityNetworkSwitch(int maxConnections, boolean canConnectToMachines, int range) {
        this.networkNodes = new ArrayList<>();
        this.maxConnections = maxConnections;
        this.canConnectToMachines = canConnectToMachines;
        this.range = range;
    }

    protected List<INetworkNode> getNetworkNodes() {
        return NetworkHandler.getNetworkNodes(worldObj, networkNodes);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        writeNodesToNBT(compound);
        compound.setInteger(NBT.CONNECTION_PRIORITY, getConnectionPriority());
        if(controller != null) {
            compound.setInteger(NBT.CONTROLLER_X, controller.getX());
            compound.setInteger(NBT.CONTROLLER_Y, controller.getY());
            compound.setInteger(NBT.CONTROLLER_Z, controller.getZ());
        }
        return compound;
    }

    public void writeNodesToNBT(NBTTagCompound tagCompound) {
        List<INetworkNode> nodes = getNetworkNodes();
        if(nodes != null) {
            NBTTagList tagList = new NBTTagList();
            for (INetworkNode networkNode : nodes) {
                if (networkNode instanceof TileEntity) {
                    TileEntity te = (TileEntity) networkNode;
                    NBTTagCompound node = new NBTTagCompound();
                    node.setInteger(NBT.NETWORK_X, te.getPos().getX());
                    node.setInteger(NBT.NETWORK_Y, te.getPos().getY());
                    node.setInteger(NBT.NETWORK_Z, te.getPos().getZ());
                    node.setInteger(NBT.DIM_ID, te.getWorld().provider.getDimension());
                    tagList.appendTag(node);
                }
            }
            tagCompound.setTag(NBT.NETWORK_NODES, tagList);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        readNodesFromNBT(compound);
        connectionPriority = compound.getInteger(NBT.CONNECTION_PRIORITY);
        if(compound.hasKey(NBT.CONTROLLER_X)) {
            int x = compound.getInteger(NBT.CONTROLLER_X);
            int y = compound.getInteger(NBT.CONTROLLER_Y);
            int z = compound.getInteger(NBT.CONTROLLER_Z);
            int dimID = compound.getInteger(NBT.DIM_ID);
            controller = new DimensionBlockPos(x, y, z, dimID);
        }
    }

    public void readNodesFromNBT(NBTTagCompound tagCompound) {
        networkNodes.clear();
        NBTTagList tagList = tagCompound.getTagList(NBT.NETWORK_NODES, 10);
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound node = tagList.getCompoundTagAt(i);
            int x = node.getInteger(NBT.NETWORK_X);
            int y = node.getInteger(NBT.NETWORK_Y);
            int z = node.getInteger(NBT.NETWORK_Z);
            networkNodes.add(new BlockPos(x, y, z));
        }
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return newState.getBlock() == Blocks.AIR;
    }

    @Override
    public void update() {

    }

    protected void syncClient() {
        if (!worldObj.isRemote) {
            markDirty();
            IBlockState state = worldObj.getBlockState(pos);
            worldObj.notifyBlockUpdate(pos, state, state, 3);
        }
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound compound = new NBTTagCompound();
        writeNodesToNBT(compound);
        return new SPacketUpdateTileEntity(getPos(), -1, compound);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        readNodesFromNBT(pkt.getNbtCompound());
    }

    @Override
    public List<INetworkNode> getConnections() {
        return getNetworkNodes();
    }

    @Override
    public IStatusMessage connectToNode(INetworkNode node, boolean isSecond, boolean simulate) {
        EVENT_BUS.post(new ConnectNetworkNodesEvent(this, node));
        if (networkNodes.size() == maxConnections) {
            return new StatusMessage(false, NetworkMessages.MAX_CONNECTIONS, maxConnections);
        } else if (!canConnectToMachines && !(node instanceof INetworkSwitch)) {
            return new StatusMessage(false, NetworkMessages.MACHINES_CAN_NOT_CONNECTED);
        } else if (this == node) {
            return new StatusMessage(false, NetworkMessages.SAME_NODE);
        } else if (node instanceof TileEntity && networkNodes.contains(((TileEntity) node).getPos())) {
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
        if (!simulate && node instanceof TileEntity) {
            networkNodes.add(((TileEntity) node).getPos());
            if (node.getController() != null && this.controller == null) {
                this.connectToController(node.getController().getMaster().getDimPos(), node.getConnectionPriority() + 1);
            }
            syncClient();
        }
        return new StatusMessage(true, NetworkMessages.SUCCESSFUL_CONNECTED);
    }

    @Override
    public IStatusMessage disconnectFromNode(INetworkNode node, boolean isSecond, boolean simulate) {
        EVENT_BUS.post(new DisconnectNetworkNodesEvent(this, node));
        if (node instanceof TileEntity && !networkNodes.contains(((TileEntity) node).getPos())) {
            return new StatusMessage(false, NetworkMessages.NOT_CONNECTED);
        }
        if (!isSecond) {
            IStatusMessage message = node.disconnectFromNode(this, true, simulate);
            if (!message.isSuccessful()) {
                return message;
            }
        }
        if (!simulate && node instanceof TileEntity) {
            networkNodes.remove(((TileEntity) node).getPos());
            if(!isSecond) {
                if(node.getConnectionPriority() > connectionPriority) {
                    node.disconnectFromController();
                } else if (node.getConnectionPriority() < connectionPriority) {
                    this.disconnectFromController();
                }
            }
            syncClient();
        }
        return new StatusMessage(true, NetworkMessages.SUCCESSFUL_DISCONNECTED);
    }

    @Override
    public IStatusMessage disconnectedFromAllNodes() {
        List<INetworkNode> tempNodes = getNetworkNodes();
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

    public TileEntityController getController(DimensionBlockPos pos) {
        return NetworkHandler.getController(pos);
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
    public void connectToController(DimensionBlockPos controllerPos, int connectionPriority) {
        controller = controllerPos;
        this.connectionPriority = connectionPriority;
        List<INetworkNode> nodes = getNetworkNodes();
        for (INetworkNode node : nodes) {
            if (node.getController() == null) {
                node.connectToController(controllerPos, connectionPriority + 1);
            }
        }
    }

    public DimensionBlockPos getDimPos() {
        return new DimensionBlockPos(this.getPos(), worldObj.provider.getDimension());
    }

    private void overrideController(DimensionBlockPos controllerPos, int connectionPriority, INetworkNode preventedNode) {
        controller = controllerPos;
        this.connectionPriority = connectionPriority;
        List<INetworkNode> nodes = getNetworkNodes();
        for (INetworkNode node : nodes) {
            if (!preventedNode.equals(node)) {
                node.connectToController(controllerPos, connectionPriority + 1);
            }
        }
    }

    @Override
    public boolean reconnectToController(INetworkSwitch startNode, INetworkSwitch prevNode) {
        List<INetworkNode> nodes = getNetworkNodes();
        boolean flag = false;
        INetworkNode foundNode = null;
        for(INetworkNode node : nodes) {
            if (node instanceof INetworkSwitch) {
                if (startNode == this || this.getConnectionPriority() < prevNode.getConnectionPriority()) {
                    flag = ((INetworkSwitch) node).reconnectToController(startNode,this);
                } else {
                    return true;
                }
            }
            if (flag) {
                foundNode = node;
                break;
            }
        }
        if (flag && startNode == this) {
            overrideController(foundNode.getController().getDimPos(), foundNode.getConnectionPriority() + 1, foundNode);
        }
        return flag;
    }

    @Override
    public void disconnectFromController() {
        if(!reconnectToController(this,this)) {
            List<INetworkNode> nodes = getNetworkNodes();
            for (INetworkNode node : nodes) {
                if (node.getController() != null && node.getConnectionPriority() > connectionPriority) {
                    node.disconnectFromController();
                }
            }
            controller = null;
            connectionPriority = 0;
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
    public void writeSelfToNBT(NBTTagCompound tagCompound) {
        tagCompound.setInteger(NBT.NETWORK_X, pos.getX());
        tagCompound.setInteger(NBT.NETWORK_Y, pos.getY());
        tagCompound.setInteger(NBT.NETWORK_Z, pos.getZ());
    }
}
