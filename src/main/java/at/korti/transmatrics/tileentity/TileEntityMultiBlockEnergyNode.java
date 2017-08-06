package at.korti.transmatrics.tileentity;

import at.korti.transmatrics.api.Constants.NBT;
import at.korti.transmatrics.api.Constants.NetworkMessages;
import at.korti.transmatrics.api.network.INetworkNode;
import at.korti.transmatrics.api.network.INetworkPackage;
import at.korti.transmatrics.api.network.IStatusMessage;
import at.korti.transmatrics.api.network.NetworkHandler;
import at.korti.transmatrics.util.helper.WorldHelper;
import cofh.redstoneflux.impl.EnergyStorage;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by Korti on 04.08.2017.
 */
public class TileEntityMultiBlockEnergyNode extends TileEntityEnergyNode {

    // Master Node
    private boolean isMasterNode;
    private List<BlockPos> extensionNodes;
    private BlockPos connectedExtensionNode;

    // Extension Node
    private BlockPos masterNode;

    public TileEntityMultiBlockEnergyNode(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);

        this.isMasterNode = false;
        this.extensionNodes = new LinkedList<>();
        this.connectedExtensionNode = null;
        this.masterNode = null;
    }

    public TileEntityMultiBlockEnergyNode(int capacity, int transfer) {
        this(capacity, transfer, transfer);
    }

    public TileEntityMultiBlockEnergyNode(int maxEnergy) {
        this(maxEnergy, maxEnergy);
    }

    //region Tile Entity
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setBoolean(NBT.IS_MASTER_NODE, this.isMasterNode);     // Set if this tile entity is the master node of the multi block structer.
        if (this.isMasterNode) {                                        // If this is the master node write the positions of the extension nodes to a nbt list.
            NBTTagList nbtExtensionNodes = new NBTTagList();            // NBT list with the positions of the extension nodes.
            for(BlockPos extensionNode : this.extensionNodes) {         // Iterate over the extension nodes and save the position to a nbt tag compound.
                nbtExtensionNodes.appendTag(                            // Write the extension node position to a new nbt tag compound and add it to the position nbt tag list.
                        writeExtensionNodeToNBT(
                                new NBTTagCompound(), extensionNode
                        )
                );
            }
            compound.setTag(NBT.EXTENSION_NODES, nbtExtensionNodes);    // Save the nbt list with the extension nodes position to the nbt tag compound of the tile entity.
            NBTTagCompound connectedExtensionNodeNBT =
                    writeExtensionNodeToNBT(
                            new NBTTagCompound(),
                            this.connectedExtensionNode
                    );
            compound.setTag(NBT.CONNECTED_EXTENSION_NODE,
                    connectedExtensionNodeNBT);
            compound.setInteger(NBT.CAPACITY,
                    this.energyStorage.getMaxEnergyStored());
        } else {                                                        // If this is a extension node then write the position of the master node to the nbt tag compound of the tile entity.
            writeExtensionNodeToNBT(compound, this.masterNode);         // Write the position of the master node to the nbt tag compound of the tile entity.
        }
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.isMasterNode = compound.getBoolean(NBT.IS_MASTER_NODE);    // Read if this is the master node of the multi block structer.
        if(this.isMasterNode) {                                         // If this is the master node read the positions of the extension nodes of the nbt list.
            NBTTagList nbtExtensionNodes = compound                     // Read the nbt list with the positions of the extensions nodes.
                    .getTagList(NBT.EXTENSION_NODES, 10);
            for (NBTBase tag : nbtExtensionNodes) {                     // Iterate over the nbt tag compounds and read the position from it.
                if (tag instanceof NBTTagCompound) {
                    BlockPos extensionNode = readExtensionNodeFromNBT((NBTTagCompound) tag);
                    if(extensionNode != null) {
                        this.extensionNodes.add(
                            extensionNode
                        );
                    }
                }
            }
            this.connectedExtensionNode = readExtensionNodeFromNBT(
                    compound.getCompoundTag(NBT.CONNECTED_EXTENSION_NODE)
            );
            this.energyStorage.setCapacity(
                    compound.getInteger(NBT.CAPACITY)
            );
        } else {                                                        // If this is a extension node then read the position of the master node from the nbt tag compound of the tile entity.
            this.masterNode = readExtensionNodeFromNBT(compound);       // Read the position of the master node from the nbt tag compound of the tile entity.
        }
    }

    /**
     * Writes the given block position to the given nbt tag compound.
     * @param extensionNodeTag NBTTagCompound where the position should be saved.
     * @param pos The block position that should be saved.
     * @return Given nbt tag compound with the saved block position.
     */
    private NBTTagCompound writeExtensionNodeToNBT(NBTTagCompound extensionNodeTag, BlockPos pos) {
        if(pos != null) {
            extensionNodeTag.setInteger(NBT.EXTENSION_NODE_X, pos.getX());
            extensionNodeTag.setInteger(NBT.EXTENSION_NODE_Y, pos.getY());
            extensionNodeTag.setInteger(NBT.EXTENSION_NODE_Z, pos.getZ());
        }
        return extensionNodeTag;
    }

    /**
     * Read the block position of the given nbt tag compound.
     * @param extensionNodeTag NBTTagCompound where the position should be read from.
     * @return Saved block position from the given nbt tag compound.
     */
    private BlockPos readExtensionNodeFromNBT(NBTTagCompound extensionNodeTag) {
        if(extensionNodeTag.hasKey(NBT.EXTENSION_NODE_X) &&
                extensionNodeTag.hasKey(NBT.EXTENSION_NODE_Y) &&
                extensionNodeTag.hasKey(NBT.EXTENSION_NODE_Z)) {
            int x = extensionNodeTag.getInteger(NBT.EXTENSION_NODE_X);
            int y = extensionNodeTag.getInteger(NBT.EXTENSION_NODE_Y);
            int z = extensionNodeTag.getInteger(NBT.EXTENSION_NODE_Z);
            return new BlockPos(x, y, z);
        }
        return null;
    }
    //endregion

    //region TileEntityNetworkNode
    //region Connect to node
    @Override
    public IStatusMessage connectToNode(INetworkNode node, boolean isSecond, boolean simulate) {
        IStatusMessage message;
        if(this.isMasterNode) {
            message = masterConnectToNode(node, isSecond);
        } else {
            message = extensionConnectToNode(node, isSecond);
        }

        if (!message.isSuccessful()) {
            return message;
        }

        if (!simulate) {
            TileEntityMultiBlockEnergyNode masterNode = isMasterNode ? this : getMasterNode();
            message = masterNode.superConnectToNode(node, isSecond, false);
            if (message.isSuccessful()) {
                this.connectedExtensionNode = this.getPos();
                return message;
            } else {
                for (BlockPos extensionNodePos : this.extensionNodes) {
                    TileEntityMultiBlockEnergyNode extensionNode = getNetworkNode(extensionNodePos);
                    if (extensionNode != null) {
                        IStatusMessage subMessage = extensionNode.superConnectToNode(node, isSecond, false);
                        if (subMessage.isSuccessful()) {
                            getMasterNode().connectedExtensionNode = this.getPos();
                            return subMessage;
                        }
                    }
                }
            }
        }
        return message;
    }

    private IStatusMessage masterConnectToNode(INetworkNode node, boolean isSecond) {
        IStatusMessage message = super.connectToNode(node, isSecond, true);                     // Check if the node can connect to the master node.
        if (!message.isSuccessful() &&
                !message.getUnlocalizedMessage().equals(NetworkMessages.OUT_OF_RANGE)) {
            return message;
        } else {
            for (BlockPos extensionNodePosition : this.extensionNodes) {
                TileEntityMultiBlockEnergyNode extensionNode = getNetworkNode(extensionNodePosition);
                if (extensionNode != null) {
                    IStatusMessage extensionMessage = extensionNode.superConnectToNode(node, isSecond, true);
                    if (!extensionMessage.isSuccessful() &&
                            !extensionMessage.getUnlocalizedMessage().equals(NetworkMessages.OUT_OF_RANGE)) {
                        return extensionMessage;
                    }
                }
            }
        }
        return message;
    }

    private IStatusMessage extensionConnectToNode(INetworkNode node, boolean isSecond) {
        return getMasterNode().connectToNode(node, isSecond, true);
    }

    private IStatusMessage superConnectToNode(INetworkNode node, boolean isSecond, boolean simulate) {
        return super.connectToNode(node, isSecond, simulate);
    }
    //endregion

    //region Disconnect from node
    @Override
    public IStatusMessage disconnectFromNode(INetworkNode node, boolean isSecond, boolean simulate) {
        if (this.isMasterNode) {
            IStatusMessage message = super.disconnectFromNode(node, isSecond, simulate);
            if (message.isSuccessful()) {
                this.connectedExtensionNode = null;
                return message;
            }
            for (BlockPos extensionNodePos : this.extensionNodes) {
                TileEntityMultiBlockEnergyNode extensionNode = getNetworkNode(extensionNodePos);
                if (extensionNode != null) {
                    IStatusMessage subMessage = extensionNode.superDisconnectFromNode(node, isSecond, simulate);
                    if (subMessage.isSuccessful()) {
                        this.connectedExtensionNode = null;
                        return message;
                    }
                }
            }
        } else {
            TileEntityMultiBlockEnergyNode masterNode = getMasterNode();
            if (masterNode != null) {
                return masterNode.disconnectFromNode(node, isSecond, simulate);
            }
        }

        return null;
    }

    private IStatusMessage superDisconnectFromNode(INetworkNode node, boolean isSecond, boolean simulate) {
        return super.disconnectFromNode(node, isSecond, simulate);
    }
    //endregion

    private void sendNetworkPackage(Function<TileEntityMultiBlockEnergyNode, INetworkPackage> packageCreator) {
        TileEntityMultiBlockEnergyNode extensionNode = getNetworkNode(connectedExtensionNode);
        if(extensionNode != null) {
            this.sendNetworkPackage(packageCreator.apply(extensionNode));
        }
    }

    @Override
    public void sendNetworkPackage(INetworkPackage networkPackage) {
        if (this.getNetworkNode() != null) {
            super.sendNetworkPackage(networkPackage);
        } else if (this.isMasterNode) {
            TileEntityMultiBlockEnergyNode senderNode = getNetworkNode(this.connectedExtensionNode);
            if (senderNode != null) {
                senderNode.sendNetworkPackage(networkPackage);
            }
        } else {
            TileEntityMultiBlockEnergyNode masterNode = getMasterNode();
            if (masterNode != null) {
                masterNode.sendNetworkPackage(networkPackage);
            }
        }
    }

    @Override
    public void receiveNetworkPackage(INetworkPackage networkPackage) {
        if(this.isMasterNode) {
            super.receiveNetworkPackage(networkPackage);
        } else {
            TileEntityMultiBlockEnergyNode masterNode = getMasterNode();
            if (masterNode != null) {
                masterNode.receiveNetworkPackage(networkPackage);
            }
        }
    }

    @Override
    public boolean isConnected() {
        if (this.isMasterNode) {
            return this.connectedExtensionNode != null;
        } else {
            TileEntityMultiBlockEnergyNode masterNode = getMasterNode();
            if (masterNode != null) {
                return masterNode.isConnected();
            }
        }
        return false;
    }

    //endregion

    //region TileEntityEnergyNode
    @Override
    protected void requestEnergy() {
        if(this.isMasterNode) {
            super.requestEnergy();
        }
    }

    @Override
    public int receiveEnergy(EnumFacing enumFacing, int energy, boolean simulate) {
        if (this.isMasterNode) {
            return super.receiveEnergy(enumFacing, energy, simulate);
        } else {
            TileEntityMultiBlockEnergyNode masterNode = getMasterNode();
            if(masterNode != null) {
                return masterNode.receiveEnergy(enumFacing, energy, simulate);
            }
        }
        return 0;
    }

    @Override
    public int extractEnergy(EnumFacing enumFacing, int energy, boolean simulate) {
        if(this.isMasterNode) {
            return super.extractEnergy(enumFacing, energy, simulate);
        } else {
            TileEntityMultiBlockEnergyNode masterNode = getMasterNode();
            if (masterNode != null) {
                return masterNode.extractEnergy(enumFacing, energy, simulate);
            }
        }
        return 0;
    }

    @Override
    public int getEnergyStored() {
        if(this.isMasterNode) {
            return super.getEnergyStored();
        } else {
            TileEntityMultiBlockEnergyNode masterNode = getMasterNode();
            if (masterNode != null) {
                return masterNode.getEnergyStored();
            }
        }
        return 0;
    }

    @Override
    public int getMaxEnergyStored() {
        if (this.isMasterNode) {
            return super.getMaxEnergyStored();
        } else {
            TileEntityMultiBlockEnergyNode masterNode = getMasterNode();
            if (masterNode != null) {
                return masterNode.getMaxEnergyStored();
            }
        }
        return 0;
    }

    private void transferEnergyInfos(TileEntityMultiBlockEnergyNode node) {
        EnergyStorage oldEnergyStorage = node.energyStorage;
        EnergyStorage newEnergyStorage = this.energyStorage;
        newEnergyStorage.setCapacity(oldEnergyStorage.getMaxEnergyStored() - newEnergyStorage.getMaxEnergyStored()).
                setEnergyStored(oldEnergyStorage.getEnergyStored());
    }
    //endregion

    //region TileEntityMultiBlockEnergyNode
    protected TileEntityMultiBlockEnergyNode getNetworkNode(BlockPos extensionNode) {
        return (TileEntityMultiBlockEnergyNode) NetworkHandler.getNetworkNode(getWorld(), extensionNode);
    }

    protected TileEntityMultiBlockEnergyNode getMasterNode() {
        return getNetworkNode(this.masterNode);
    }

    public TileEntityMultiBlockEnergyNode setIsMaster() {
        this.isMasterNode = true;
        return this;
    }

    public TileEntityMultiBlockEnergyNode addExtensionNode(TileEntityMultiBlockEnergyNode extensionNode) {
        if (this.isMasterNode) {
            // Add the extension to the master node and set the master node on the extension node
            this.extensionNodes.add(extensionNode.getPos());
            extensionNode.masterNode = this.getPos();
            this.energyStorage.setCapacity(
                this.energyStorage.getMaxEnergyStored() + extensionNode.energyStorage.getMaxEnergyStored()
            );
        } else {
            TileEntityMultiBlockEnergyNode masterNode = getMasterNode();
            if (masterNode != null) {
                masterNode.addExtensionNode(extensionNode);
            }
        }
        syncClient();
        return this;
    }

    public TileEntityMultiBlockEnergyNode removeExtensionNode(TileEntityMultiBlockEnergyNode extensionNode) {
        if (this.isMasterNode) {
            if (extensionNode != this) {
                // If the extension node to remove is not the master node, remove it from the extension node list and
                // set the master node of the extension node null.
                this.extensionNodes.remove(extensionNode.getPos());
                extensionNode.masterNode = null;
                this.energyStorage.setCapacity(
                    this.energyStorage.getMaxEnergyStored() - extensionNode.energyStorage.getMaxEnergyStored()
                );
                INetworkNode connectedNode = extensionNode.getNetworkNode();
                if (connectedNode != null) {
                    this.connectToNode(connectedNode, false, false);
                }
            } else if (this.extensionNodes.size() > 0) {
                // If the extension node to remove is the master node and there are other extension nodes,
                // take the first extension node in the list and set it to the new master node.
                // If the master node was the connected node, then connect the connected node to a new node,
                // set the extension nodes the new master node and add the extension nodes to the new master node.
                TileEntityMultiBlockEnergyNode newMasterNode = getNetworkNode(this.extensionNodes.get(0));
                newMasterNode.setIsMaster();
                newMasterNode.transferEnergyInfos(this);
                INetworkNode connectedNode = getNetworkNode();
                if (connectedNode != null) {
                    newMasterNode.connectToNode(connectedNode, false, false);
                }
                for (BlockPos extensionNodePos : this.extensionNodes) {
                    TileEntityMultiBlockEnergyNode node = getNetworkNode(extensionNodePos);
                    if (node != null) {
                        newMasterNode.extensionNodes.add(node.getPos());
                        node.masterNode = this.getPos();
                    }
                }
            } else {
                this.disconnectFromNode();
            }
        } else {
            TileEntityMultiBlockEnergyNode masterNode = getMasterNode();
            if (masterNode != null) {
                masterNode.removeExtensionNode(extensionNode);
            }
        }
        syncClient();
        return this;
    }

    public TileEntityMultiBlockEnergyNode removeExtensionNode() {
        return this.removeExtensionNode(this);
    }

    public boolean isMasterNode() {
        return this.isMasterNode;
    }

    public void validateConstruction() {
        if (this.isMasterNode) {
            int storedEnergy = this.getEnergyStored();
            List<BlockPos> copyExtensionNodes = new LinkedList<>(this.extensionNodes);
            for (BlockPos extensionNodePos : copyExtensionNodes) {
                TileEntityMultiBlockEnergyNode extensionNode = getNetworkNode(extensionNodePos);
                if(extensionNode != null) {
                    this.removeExtensionNode(extensionNode);
                }
            }
            List<BlockPos> neighbors = WorldHelper.hasNeighbors(getWorld(), this.getPos(), this.getClass());
            for (BlockPos neighbor : neighbors) {
                TileEntityMultiBlockEnergyNode neighborNode = getNetworkNode(neighbor);
                if (neighborNode != null) {
                    reconnectToMaster(this.getPos(), this.getPos());
                }
            }
            energyStorage.setEnergyStored(storedEnergy);
            for (BlockPos extensionNodePos : copyExtensionNodes) {
                if (!this.extensionNodes.contains(extensionNodePos)) {
                    TileEntityMultiBlockEnergyNode extensionNode = getNetworkNode(extensionNodePos);
                    if (extensionNode != null && (extensionNode.isMasterNode() || extensionNode.masterNode != null)) {
                        extensionNode.setIsMaster();
                        extensionNode.validateConstruction();
                    }
                }
            }
        } else {
            TileEntityMultiBlockEnergyNode masterNode = getMasterNode();
            if(masterNode != null) {
                masterNode.validateConstruction();
            }
        }
    }

    private void reconnectToMaster(BlockPos masterNodePos, BlockPos executer) {
        TileEntityMultiBlockEnergyNode masterNode = getNetworkNode(masterNodePos);
        if (masterNode != null && masterNode != this) {
            masterNode.addExtensionNode(this);
        }
        List<BlockPos> neigbors = WorldHelper.hasNeighbors(getWorld(), this.getPos(), this.getClass());
        for (BlockPos neighbor : neigbors) {
            TileEntityMultiBlockEnergyNode neighborNode = getNetworkNode(neighbor);
            if (neighborNode != null) {
                if (!neighbor.equals(masterNodePos) && neighborNode.isMasterNode) {
                    neighborNode.isMasterNode = false;
                }
                if (!neighbor.equals(executer) && neighborNode.getMasterNode() == null) {
                    neighborNode.reconnectToMaster(masterNodePos, this.getPos());
                }
            }
        }
    }
    //endregion
}
