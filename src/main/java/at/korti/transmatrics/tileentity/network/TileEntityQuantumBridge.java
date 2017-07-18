package at.korti.transmatrics.tileentity.network;

import at.korti.transmatrics.Transmatrics;
import at.korti.transmatrics.api.Constants.Energy;
import at.korti.transmatrics.api.Constants.NBT;
import at.korti.transmatrics.api.Constants.Network;
import at.korti.transmatrics.api.energy.EnergyHandler;
import at.korti.transmatrics.api.network.INetworkNode;
import at.korti.transmatrics.api.network.INetworkSwitch;
import at.korti.transmatrics.api.network.quantum.QuantumBridgeHandler;
import at.korti.transmatrics.tileentity.TileEntityEnergySwitch;
import at.korti.transmatrics.util.helper.WorldHelper;
import at.korti.transmatrics.util.math.DimensionBlockPos;
import cofh.redstoneflux.api.IEnergyReceiver;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;

import java.util.List;

/**
 * Created by Korti on 27.06.2016.
 */
public class TileEntityQuantumBridge extends TileEntityEnergySwitch {

    private String quantumBridgeMapName;
    private final int energyUse;
    private boolean brigdeConnected = false;
    private Ticket chunkLoadTicket;

    public TileEntityQuantumBridge() {
        super(Network.SMALL_SWITCH_MAX_CONNECTIONS, Network.LARGE_SWITCH_MACHINES_CONNECT,
                Network.LARGE_SWITCH_RANGE, Energy.QUANTUM_BRIDGE_CAPACITY, Energy.QUANTUM_BRIDGE_TRANSFER);
        this.energyUse = Energy.QUANTUM_BRIDGE_ENERGY_USE;
        ForgeChunkManager.setForcedChunkLoadingCallback(Transmatrics.instance, new ForgeChunkManager.LoadingCallback() {
            @Override
            public void ticketsLoaded(List<Ticket> tickets, World world) {

            }
        });
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
        if(!getWorld().isRemote) {
            this.quantumBridgeMapName = compound.getString(NBT.QUANTUM_BRIDGE_MAP_NAME);
            QuantumBridgeHandler.setQuantumBridgePos(this.quantumBridgeMapName, new DimensionBlockPos(getPos(),
                    getWorld().provider.getDimension()));
            connectQuantumBridge();
            chunkLoadTicket = ForgeChunkManager.requestTicket(Transmatrics.instance, this.getWorld(), ForgeChunkManager.Type.NORMAL);
            forceChunks();
        }
    }

    public void forceChunkLoading() {
        if (chunkLoadTicket != null) {
            chunkLoadTicket = ForgeChunkManager.requestTicket(Transmatrics.instance, this.getWorld(), ForgeChunkManager.Type.NORMAL);
        }
        forceChunks();
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        boolean flag = super.shouldRefresh(world, pos, oldState, newState);
        if (flag) {
            ForgeChunkManager.releaseTicket(chunkLoadTicket);
            chunkLoadTicket = null;
        }
        return flag;
    }

    public String getQuantumBridgeMapName() {
        return quantumBridgeMapName;
    }

    @Override
    public void update() {
        super.update();
        if(!getWorld().isRemote) {
            if (energyStorage.getEnergyStored() >= energyUse) {
                energyStorage.modifyEnergyStored(-energyUse);
            }
            for(INetworkNode node : getConnections()){
                if (node instanceof IEnergyReceiver) {
                    IEnergyReceiver consumer = (IEnergyReceiver) node;
                    if (node.getConnectionPriority() > this.getConnectionPriority() || (node.getConnectionPriority() == 0 && this.getConnectionPriority() == 0)) {
                        EnergyHandler.transferEnergy(this, consumer);
                    }
                }
            }
            System.out.println("Chunk loaded - Bridge ID: " + quantumBridgeMapName + ", Dimension ID: " + getWorld().provider.getDimension());
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
            int dimID = getWorld().provider.getDimension();
            DimensionBlockPos dimPos = QuantumBridgeHandler.getDifferentQuantumBridgePos(quantumBridgeMapName,
                    new DimensionBlockPos(pos, dimID));
            if (dimPos != null) {
                World world;
                if (dimID == dimPos.getDimensionID()) {
                    world = getWorld();
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

    private void forceChunks() {
        ChunkPos chunkPos = getWorld().getChunkFromBlockCoords(getPos()).getPos();
        ChunkPos tempPos;
        for(int x = -1; x <= 1; x++) {
            for(int z = -1; z <= 1; z++) {
                tempPos = new ChunkPos(chunkPos.x + x, chunkPos.z + z);
                ForgeChunkManager.forceChunk(chunkLoadTicket, tempPos);
            }
        }
    }
}
