package at.korti.transmatrics.api.network.networkpackages;

import at.korti.transmatrics.api.energy.EnergyHandler;
import at.korti.transmatrics.api.network.INetworkNode;
import at.korti.transmatrics.api.network.INetworkPackage;
import at.korti.transmatrics.api.network.IStatusMessage;
import cofh.redstoneflux.api.IEnergyProvider;
import cofh.redstoneflux.api.IEnergyReceiver;

/**
 * Created by Korti on 19.07.2017.
 */
public class EnergyRequestNetworkPackage implements INetworkPackage {

    private INetworkNode sender;

    public EnergyRequestNetworkPackage(INetworkNode sender) {
        this.sender = sender;
    }

    @Override
    public INetworkNode getSender() {
        return sender;
    }

    @Override
    public boolean canHandlePackage(INetworkNode node) {
        return node instanceof IEnergyProvider;
    }

    @Override
    public void handlePackage(INetworkNode node) {
        IEnergyProvider energyProvider = (IEnergyProvider) node;
        if (sender instanceof IEnergyReceiver) {
            EnergyHandler.transferEnergy(energyProvider, (IEnergyReceiver) sender);
        }
    }

    @Override
    public IStatusMessage getMessage() {
        return null;
    }
}
