package at.korti.transmatrics.api.network.networkpackages;

import at.korti.transmatrics.api.Constants.NetworkMessages;
import at.korti.transmatrics.api.network.INetworkNode;
import at.korti.transmatrics.api.network.INetworkPackage;
import at.korti.transmatrics.api.network.IStatusMessage;
import at.korti.transmatrics.api.network.StatusMessage;

/**
 * Created by Korti on 19.07.2017.
 */
public class ErrorNetworkPackage implements INetworkPackage {

    private INetworkNode sender;

    public ErrorNetworkPackage(INetworkNode sender) {
        this.sender = sender;
    }

    @Override
    public INetworkNode getSender() {
        return sender;
    }

    @Override
    public boolean canHandlePackage(INetworkNode node) {
        return true;
    }

    @Override
    public void handlePackage(INetworkNode node) {

    }

    @Override
    public IStatusMessage getMessage() {
        return new StatusMessage(false, NetworkMessages.CAN_NOT_HANDLE_PACKAGE);
    }
}
