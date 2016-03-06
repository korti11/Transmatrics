package at.korti.transmatrics.tileentity;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.NetworkMessages;
import at.korti.transmatrics.api.network.INetworkNode;
import at.korti.transmatrics.api.network.IOperationMessage;
import at.korti.transmatrics.api.network.OperationMessage;
import net.minecraft.tileentity.TileEntity;

import static at.korti.transmatrics.util.helper.TextHelper.localize;

/**
 * Created by Korti on 06.03.2016.
 */
public abstract class TileEntityNetworkNode extends TileEntity implements INetworkNode {

    protected INetworkNode networkNode;

    @Override
    public IOperationMessage connectToNode(INetworkNode node) {
        if (networkNode != null) {
            networkNode = node;
            return new OperationMessage(localize(NetworkMessages.SUCCESSFUL_RECONNECTED), true);
        } else if (node == null) {
            return new OperationMessage(localize(NetworkMessages.CAN_NOT_CONNECTED), false);
        }
        networkNode = node;
        return new OperationMessage(localize(NetworkMessages.SUCCESSFUL_CONNECTED), true);
    }

    @Override
    public IOperationMessage disconnectFromNode(INetworkNode node) {
        if (networkNode != node) {
            return new OperationMessage(localize(NetworkMessages.NOT_CONNECTED), false);
        }
        networkNode = null;
        return new OperationMessage(localize(NetworkMessages.SUCCESSFUL_DISCONNECTED), true);
    }

    @Override
    public IOperationMessage disconnectFromNode() {
        return disconnectFromNode(networkNode);
    }

    @Override
    public INetworkNode getConnection() {
        return networkNode;
    }
}
