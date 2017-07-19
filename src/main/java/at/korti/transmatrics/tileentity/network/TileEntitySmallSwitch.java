package at.korti.transmatrics.tileentity.network;

import at.korti.transmatrics.api.Constants.Energy;
import at.korti.transmatrics.api.Constants.Network;
import at.korti.transmatrics.tileentity.TileEntityEnergySwitch;
import at.korti.transmatrics.tileentity.TileEntityNetworkSwitch;

/**
 * Created by Korti on 06.03.2016.
 */
public class TileEntitySmallSwitch extends TileEntityNetworkSwitch {

    public TileEntitySmallSwitch() {
        super(Network.SMALL_SWITCH_MAX_CONNECTIONS, Network.SMALL_SWITCH_MACHINES_CONNECT, Network.SMALL_SWITCH_RANGE);
    }

}
