package at.korti.transmatrics.tileentity.network;

import at.korti.transmatrics.api.Constants.Energy;
import at.korti.transmatrics.api.Constants.Network;
import at.korti.transmatrics.tileentity.TileEntityEnergySwitch;

/**
 * Created by Korti on 07.03.2016.
 */
public class TileEntityMediumSwitch extends TileEntityEnergySwitch {

    public TileEntityMediumSwitch() {
        super(Network.MEDIUM_SWITCH_MAX_CONNECTIONS, Network.MEDIUM_SWITCH_MACHINES_CONNECT, Network.MEDIUM_SWITCH_RANGE,
                Energy.MEDIUM_SWITCH_CAPACITY, Energy.MEDIUM_SWITCH_TRANSFER);
    }

}
