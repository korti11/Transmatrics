package at.korti.transmatrics.tileentity.network;

import at.korti.transmatrics.api.Constants.Network;
import at.korti.transmatrics.tileentity.TileEntityNetworkSwitch;

/**
 * Created by Korti on 07.03.2016.
 */
public class TileEntityMediumSwitch extends TileEntityNetworkSwitch {

    public TileEntityMediumSwitch() {
        super(Network.MEDIUM_SWITCH_MAX_CONNECTIONS, Network.MEDIUM_SWITCH_MACHINES_CONNECT, Network.MEDIUM_SWITCH_RANGE);
    }

}
