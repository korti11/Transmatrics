package at.korti.transmatrics.api.network;

import java.util.List;

/**
 * Created by Korti on 06.03.2016.
 */
public interface INetworkSwitch extends INetworkNode {

    /**
     * @return Get a list of all connected nodes.
     */
    List<INetworkNode> getConnections();

    /**
     * Disconnected from all connected nodes.
     * @return Message that can displayed to a player.
     */
    IStatusMessage disconnectedFromAllNodes();

}
