package at.korti.transmatrics.tileentity.network;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.Energy;
import at.korti.transmatrics.api.Constants.NBT;
import at.korti.transmatrics.api.Constants.Network;
import at.korti.transmatrics.api.energy.EnergyHandler;
import at.korti.transmatrics.api.energy.IEnergyConsumer;
import at.korti.transmatrics.api.network.INetworkNode;
import at.korti.transmatrics.api.network.INetworkSwitch;
import at.korti.transmatrics.api.network.quantum.QuantumBridgeHandler;
import at.korti.transmatrics.tileentity.TileEntityEnergyNode;
import at.korti.transmatrics.tileentity.TileEntityEnergySwitch;
import at.korti.transmatrics.tileentity.TileEntityNetworkSwitch;
import at.korti.transmatrics.util.helper.WorldHelper;
import at.korti.transmatrics.util.math.DimensionBlockPos;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.core.helpers.NameUtil;

/**
 * Created by Korti on 27.06.2016.
 */
public class TileEntityQuantumBridge extends TileEntityEnergySwitch {

    private String quantumBridgeMapName;
    private final int energyUse;
    private boolean brigdeConnected = false;

    public TileEntityQuantumBridge() {
        super(Network.SMALL_SWITCH_MAX_CONNECTIONS, Network.LARGE_SWITCH_MACHINES_CONNECT,
                Network.LARGE_SWITCH_RANGE, Energy.QUANTUM_BRIDGE_CAPACITY, Energy.QUANTUM_BRIDGE_TRANSFER);
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

    public void onCreate(NBTTagCompound compound) {
        if(!worldObj.isRemote) {
            this.quantumBridgeMapName = compound.getString(NBT.QUANTUM_BRIDGE_MAP_NAME);
            QuantumBridgeHandler.setQuantumBridgePos(this.quantumBridgeMapName, new DimensionBlockPos(getPos(),
                    worldObj.provider.getDimension()));
            connectQuantumBridge();
        }
    }

    public String getQuantumBridgeMapName() {
        return quantumBridgeMapName;
    }

    @Override
    public void update() {
        super.update();
        if(!worldObj.isRemote) {
            if (canProvideEnergy()) {
                energyStorage.modifyEnergy(-energyUse);
            }
            for(INetworkNode node : getConnections()){
                if (node instanceof IEnergyConsumer) {
                    IEnergyConsumer consumer = (IEnergyConsumer) node;
                    if (node.getConnectionPriority() > this.getConnectionPriority() || (node.getConnectionPriority() == 0 && this.getConnectionPriority() == 0)) {
                        EnergyHandler.transferEnergy(this, consumer);
                    }
                }
            }

            if(!brigdeConnected){
                connectQuantumBridge();
            } else {
                updateQuantumBridge();
            }
        }
    }

    private void updateQuantumBridge() {
        //TODO: Update if needed connection, priority and energy storage
    }

    private void connectQuantumBridge() {
        TileEntityQuantumBridge quantumBridge = getDifferentQuantumNode();
        if (quantumBridge != null) {
            this.energyStorage = quantumBridge.energyStorage;
            this.setController(quantumBridge);
            this.brigdeConnected = true;
            quantumBridge.brigdeConnected = true;
        }
    }

    private TileEntityQuantumBridge getDifferentQuantumNode() {
        if (quantumBridgeMapName != null && !quantumBridgeMapName.isEmpty()) {
            int dimID = worldObj.provider.getDimension();
            DimensionBlockPos dimPos = QuantumBridgeHandler.getDifferentQuantumBridgePos(quantumBridgeMapName,
                    new DimensionBlockPos(pos, dimID));
            if (dimPos != null) {
                World world;
                if (dimID == dimPos.getDimensionID()) {
                    world = worldObj;
                } else {
                    world = WorldHelper.getWorld(dimPos.getDimensionID());
                }
                TileEntity te = world.getTileEntity(new BlockPos(dimPos));
                if (te instanceof TileEntityQuantumBridge) {
                    return (TileEntityQuantumBridge) te;
                }
            }
        }
        return null;
    }

    private void setController(TileEntityQuantumBridge tileEntity){
        if (this.getController() == null && tileEntity.getController() != null) {
            this.connectToController(tileEntity.getController().getDimPos(), tileEntity.connectionPriority);
        } else if (this.getController() != null && tileEntity.getController() == null) {
            tileEntity.connectToController(this.getController().getDimPos(), this.connectionPriority);
        }
    }

    @Override
    public boolean reconnectToController(INetworkSwitch startNode, INetworkSwitch prevNode) {
        TileEntityQuantumBridge quantumBridge = getDifferentQuantumNode();
        boolean flag = super.reconnectToController(startNode, prevNode) &&
                quantumBridge.reconnectToController(quantumBridge, quantumBridge);
        return flag;
    }
}
