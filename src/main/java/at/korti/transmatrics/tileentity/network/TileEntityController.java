package at.korti.transmatrics.tileentity.network;

import at.korti.transmatrics.api.Constants.Energy;
import at.korti.transmatrics.api.Constants.Network;
import at.korti.transmatrics.tileentity.TileEntityEnergySwitch;

/**
 * Created by Korti on 08.03.2016.
 */
public class TileEntityController extends TileEntityEnergySwitch{

    public TileEntityController() {
        super(Network.CONTROLLER_MAX_CONNECTIONS, Network.CONTROLLER_MACHINES_CONNECT, Network.CONTROLLER_RANGE,
                Energy.CONTROLLER_CAPACITY, Energy.CONTROLLER_TRANSFER);
    }

}
