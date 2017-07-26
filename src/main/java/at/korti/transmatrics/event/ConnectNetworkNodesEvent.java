package at.korti.transmatrics.event;

import at.korti.transmatrics.api.network.INetworkNode;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * Created by Korti on 20.04.2016.
 */
public class ConnectNetworkNodesEvent extends Event {

    private final INetworkNode nodeOne;
    private final INetworkNode nodeTwo;

    public ConnectNetworkNodesEvent(INetworkNode nodeOne, INetworkNode nodeTwo) {
        this.nodeOne = nodeOne;
        this.nodeTwo = nodeTwo;
    }

    public INetworkNode getNodeOne() {
        return nodeOne;
    }

    public INetworkNode getNodeTwo() {
        return nodeTwo;
    }
}
