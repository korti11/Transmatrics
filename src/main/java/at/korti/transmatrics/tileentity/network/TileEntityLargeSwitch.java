package at.korti.transmatrics.tileentity.network;

import at.korti.transmatrics.api.Constants.Energy;
import at.korti.transmatrics.api.Constants.Network;
import at.korti.transmatrics.tileentity.TileEntityEnergySwitch;
import at.korti.transmatrics.tileentity.TileEntityNetworkSwitch;

/**
 * Created by Korti on 07.03.2016.
 */
public class TileEntityLargeSwitch extends TileEntityNetworkSwitch {

    public TileEntityLargeSwitch() {
        super(Network.LARGE_SWITCH_MAX_CONNECTIONS, Network.LARGE_SWITCH_MACHINES_CONNECT, Network.LARGE_SWITCH_RANGE);
    }

}
