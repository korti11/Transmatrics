package at.korti.transmatrics.api.network;

/**
 * Created by Korti on 19.07.2017.
 */
public interface INetworkPackage {

    INetworkNode getSender();

    boolean canHandlePackage(INetworkNode node);

    void handlePackage(INetworkNode node);

    IStatusMessage getMessage();

}
