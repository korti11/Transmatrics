package at.korti.transmatrics.tileentity;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.NetworkMessages;
import at.korti.transmatrics.api.network.INetworkNode;
import at.korti.transmatrics.api.network.INetworkSwitch;
import at.korti.transmatrics.api.network.IOperationMessage;
import at.korti.transmatrics.api.network.OperationMessage;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.List;

import static at.korti.transmatrics.util.helper.TextHelper.localize;

/**
 * Created by Korti on 06.03.2016.
 */
public abstract class TileEntityNetworkSwitch extends TileEntity implements INetworkSwitch{

    protected List<INetworkNode> networkNodes;
    protected final int maxConnections;
    protected final boolean canConnectToMachines;
    protected final int range;

    public TileEntityNetworkSwitch(int maxConnections, boolean canConnectToMachines, int range) {
        this.networkNodes = new ArrayList<>();
        this.maxConnections = maxConnections;
        this.canConnectToMachines = canConnectToMachines;
        this.range = range;
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
}
