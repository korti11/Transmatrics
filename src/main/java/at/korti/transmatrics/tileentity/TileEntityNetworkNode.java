package at.korti.transmatrics.tileentity;

import at.korti.transmatrics.api.Constants.NBT;
import at.korti.transmatrics.api.Constants.NetworkMessages;
import at.korti.transmatrics.api.energy.IEnergyProducer;
import at.korti.transmatrics.api.network.*;
import at.korti.transmatrics.event.ConnectNetworkNodesEvent;
import at.korti.transmatrics.event.DisconnectNetworkNodesEvent;
import at.korti.transmatrics.tileentity.network.TileEntityController;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

/**
 * Created by Korti on 06.03.2016.
 */
public abstract class TileEntityNetworkNode extends TileEntity implements INetworkNode, ITickable, INetworkNodeInfo {

    protected BlockPos networkNode;
    private BlockPos controller;
    protected int connectionPriority;

    protected INetworkNode getNetworkNode(){
        return NetworkHandler.getNetworkNode(worldObj, networkNode);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        writeNetworkNodeToNBT(compound);
        compound.setInteger(NBT.CONNECTION_PRIORITY, connectionPriority);
        if(controller != null) {
            compound.setInteger(NBT.CONTROLLER_X, controller.getX());
            compound.setInteger(NBT.CONTROLLER_Y, controller.getY());
            compound.setInteger(NBT.CONTROLLER_Z, controller.getZ());
        }
    }

    private void writeNetworkNodeToNBT(NBTTagCompound compound) {
        if(getNetworkNode() instanceof TileEntity) {
            TileEntity te = (TileEntity) getNetworkNode();
            compound.setInteger(NBT.NETWORK_X, te.getPos().getX());
            compound.setInteger(NBT.NETWORK_Y, te.getPos().getY());
            compound.setInteger(NBT.NETWORK_Z, te.getPos().getZ());
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        connectionPriority = compound.getInteger(NBT.CONNECTION_PRIORITY);
        readNetworkNodeFromNBT(compound);
        if(compound.hasKey(NBT.CONTROLLER_X)) {
            int x = compound.getInteger(NBT.CONTROLLER_X);
            int y = compound.getInteger(NBT.CONTROLLER_Y);
            int z = compound.getInteger(NBT.CONTROLLER_Z);
            controller = new BlockPos(x, y, z);
        }
    }

    private void readNetworkNodeFromNBT(NBTTagCompound compound) {
        if(compound.hasKey(NBT.NETWORK_X)) {
            int x = compound.getInteger(NBT.NETWORK_X);
            int y = compound.getInteger(NBT.NETWORK_Y);
            int z = compound.getInteger(NBT.NETWORK_Z);
            networkNode = new BlockPos(x, y, z);
        } else {
            networkNode = null;
        }
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return newState.getBlock() == Blocks.AIR;
    }

    @Override
    public void update() {

    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound compound = new NBTTagCompound();
        writeNetworkNodeToNBT(compound);
        return new SPacketUpdateTileEntity(getPos(), -1, compound);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        readNetworkNodeFromNBT(pkt.getNbtCompound());
    }

    protected void syncClient() {
        if (!worldObj.isRemote) {
            markDirty();
            IBlockState state = worldObj.getBlockState(pos);
            worldObj.notifyBlockUpdate(pos, state, state, 3);
        }
    }

    @Override
    public IStatusMessage connectToNode(INetworkNode node, boolean isSecond, boolean simulate) {
        EVENT_BUS.post(new ConnectNetworkNodesEvent(this, node));
        if (node == null || !(node instanceof TileEntity)) {
            return new StatusMessage(false, NetworkMessages.CAN_NOT_CONNECTED);
        } else if (this == node) {
            return new StatusMessage(false, NetworkMessages.SAME_NODE);
        } else if ((!(this instanceof IEnergyProducer) && !(node instanceof IEnergyProducer)) &&
                node instanceof INetworkNode && !(node instanceof INetworkSwitch) ||
                ((this instanceof IEnergyProducer) && (node instanceof IEnergyProducer))) {
            return new StatusMessage(false, NetworkMessages.MACHINES_CAN_NOT_CONNECTED);
        }
        if (!isSecond) {
            IStatusMessage message = node.connectToNode(this, true, simulate);
            if (!message.isSuccessful()) {
                return message;
            }
        }
        if(!simulate) {
            if(networkNode != null) {
                disconnectFromNode();
            }
            networkNode = ((TileEntity)node).getPos();
            if (node.getController() != null && this.controller == null) {
                controller = node.getController().getPos();
                connectionPriority = node.getConnectionPriority() + 1;
            }
            syncClient();
        }
        return new StatusMessage(true, NetworkMessages.SUCCESSFUL_CONNECTED);
    }

    @Override
    public IStatusMessage disconnectFromNode(INetworkNode node, boolean isSecond, boolean simulate) {
        EVENT_BUS.post(new DisconnectNetworkNodesEvent(this, node));
        if (node == null || getNetworkNode() == null || getNetworkNode() != node) {
            return new StatusMessage(false, NetworkMessages.NOT_CONNECTED);
        }

        if (!isSecond) {
            IStatusMessage message = node.disconnectFromNode(this, true, simulate);
            if (!message.isSuccessful()) {
                return message;
            }
        }
        if (!simulate) {
            networkNode = null;
            syncClient();
        }
        return new StatusMessage(true, NetworkMessages.SUCCESSFUL_DISCONNECTED);
    }

    @Override
    public IStatusMessage disconnectFromNode() {
        return disconnectFromNode(getNetworkNode(), false, false);
    }

    @Override
    public INetworkNode getConnection() {
        return getNetworkNode();
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
    public void connectToController(BlockPos controllerPos, int connectionPriority) {
        controller = controllerPos;
        this.connectionPriority = connectionPriority;
    }

    @Override
    public void disconnectFromController() {
        controller = null;
        connectionPriority = 0;
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
