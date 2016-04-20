package at.korti.transmatrics.event;

import at.korti.transmatrics.api.network.INetworkNode;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * Created by Korti on 20.04.2016.
 */
public class DisconnectNetworkNodesEvent extends Event {

    public final INetworkNode nodeOne;
    public final INetworkNode nodeTwo;

    public DisconnectNetworkNodesEvent(INetworkNode nodeOne, INetworkNode nodeTwo) {
        this.nodeOne = nodeOne;
        this.nodeTwo = nodeTwo;
    }

}
