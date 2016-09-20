package at.korti.transmatrics.tileentity.network;

import at.korti.transmatrics.api.Constants.Energy;
import at.korti.transmatrics.api.Constants.NBT;
import at.korti.transmatrics.api.energy.EnergyHandler;
import at.korti.transmatrics.api.energy.IEnergyConsumer;
import at.korti.transmatrics.api.network.INetworkNode;
import at.korti.transmatrics.api.network.INetworkSwitch;
import at.korti.transmatrics.api.network.quantum.QuantumBridgeHandler;
import at.korti.transmatrics.tileentity.TileEntityEnergyNode;
import at.korti.transmatrics.util.helper.WorldHelper;
import at.korti.transmatrics.util.math.DimensionBlockPos;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by Korti on 27.06.2016.
 */
public class TileEntityQuantumBridge extends TileEntityEnergyNode {

    private String quantumBridgeMapName;
    private final int energyUse;

    public TileEntityQuantumBridge() {
        super(Energy.QUANTUM_BRIDGE_CAPACITY, Energy.QUANTUM_BRIDGE_TRANSFER);
        this.energyUse = Energy.QUANTUM_BRIDGE_ENERGY_USE;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        compound.setString(NBT.QUANTUM_BRIDGE_MAP_NAME, quantumBridgeMapName);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        quantumBridgeMapName = compound.getString(NBT.QUANTUM_BRIDGE_MAP_NAME);
    }

    @Override
    public void update() {
        super.update();
        if (canProvideEnergy()) {
            energyStorage.modifyEnergy(-energyUse);
        }
        INetworkNode node = getNetworkNode();
        if (node instanceof IEnergyConsumer) {
            IEnergyConsumer consumer = (IEnergyConsumer) node;
            if (node.getConnectionPriority() > this.getConnectionPriority() || (node.getConnectionPriority() == 0 && this.getConnectionPriority() == 0)) {
                EnergyHandler.transferEnergy(this, consumer);
            }
        }
        transferEnergyToBridge();
    }

    private void transferEnergyToBridge() {
        int dimID = worldObj.provider.getDimension();
        DimensionBlockPos dimPos = QuantumBridgeHandler.getDifferentQuantumBridgePos(quantumBridgeMapName,
                new DimensionBlockPos(pos, dimID));
        World world;
        if (dimID == dimPos.getDimensionID()) {
            world = worldObj;
        } else {
            world = WorldHelper.getWorld(dimID);
        }
        TileEntity te = world.getTileEntity(dimPos);
        if (te instanceof TileEntityQuantumBridge) {
            EnergyHandler.transferEnergy(this, (IEnergyConsumer) te);
        }
    }
}
