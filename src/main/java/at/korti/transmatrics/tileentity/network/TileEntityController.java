package at.korti.transmatrics.tileentity.network;

import at.korti.transmatrics.api.Constants.*;
import at.korti.transmatrics.api.network.INetworkNode;
import at.korti.transmatrics.api.network.IStatusMessage;
import at.korti.transmatrics.tileentity.TileEntityEnergySwitch;
import at.korti.transmatrics.util.helper.WorldHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Korti on 08.03.2016.
 */
public class TileEntityController extends TileEntityEnergySwitch {

    //Master
    private boolean isMaster;
    private List<BlockPos> extensions;

    //Extension
    private BlockPos master;

    public TileEntityController() {
        super(Network.CONTROLLER_MAX_CONNECTIONS, Network.CONTROLLER_MACHINES_CONNECT, Network.CONTROLLER_RANGE,
                Energy.CONTROLLER_CAPACITY, Energy.CONTROLLER_TRANSFER);

        this.isMaster = false;
        this.extensions = new LinkedList<>();
        this.connectionPriority = 0;
    }

    //region Tile Entity
    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setBoolean(NBT.IS_MASTER, isMaster);       // Set if it is the master controller tile entity.
        if (isMaster) {         // Check if it is the master controller.
            NBTTagList extensions = new NBTTagList();
            for (BlockPos extension : this.extensions) {        // Write all extensions to the nbt data.
                NBTTagCompound extensionCompound = new NBTTagCompound();
                extensionCompound.setInteger(NBT.EXT_CONTROLLER_X, extension.getX());
                extensionCompound.setInteger(NBT.EXT_CONTROLLER_Y, extension.getY());
                extensionCompound.setInteger(NBT.EXT_CONTROLLER_Z, extension.getZ());
                extensions.appendTag(extensionCompound);
            }
            compound.setTag(NBT.CONTROLLER_EXTENSIONS, extensions);
        } else if(master != null) {     // If it is not the master controller and the master variable is not null save the master to the nbt data.
            compound.setInteger(NBT.EXT_CONTROLLER_X, master.getX());
            compound.setInteger(NBT.EXT_CONTROLLER_Y, master.getY());
            compound.setInteger(NBT.EXT_CONTROLLER_Z, master.getZ());
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        boolean isMaster = compound.getBoolean(NBT.IS_MASTER);      // Get if it is tha master controller tile entity.
        if (isMaster) {     // Check if it is the master controller.
            this.isMaster = true;
            NBTTagList extensions = compound.getTagList(NBT.CONTROLLER_EXTENSIONS, 10);
            for (int i = 0; i < extensions.tagCount(); i++) {       // Read all extensions from the nbt data.
                NBTTagCompound extensionCompound = extensions.getCompoundTagAt(i);
                this.extensions.add(new BlockPos(extensionCompound.getInteger(NBT.EXT_CONTROLLER_X),
                        extensionCompound.getInteger(NBT.EXT_CONTROLLER_Y),
                        extensionCompound.getInteger(NBT.EXT_CONTROLLER_Z)));
            }
        } else if(compound.hasKey(NBT.EXT_CONTROLLER_X)) {      // Check if there is the key EXT_CONTROLLER_X(ecx)
            master = new BlockPos(compound.getInteger(NBT.EXT_CONTROLLER_X),      // Read the master controller from the nbt data.
                    compound.getInteger(NBT.EXT_CONTROLLER_Y),
                    compound.getInteger(NBT.EXT_CONTROLLER_Z));
        }
    }
    //endregion

    //region Tile Entity Network Switch
    @Override
    public IStatusMessage connectToNode(INetworkNode node, boolean isSecond, boolean simulate) {
        IStatusMessage message;
        if(isMaster) {
            message = super.connectToNode(node, isSecond, true);     // Check if the node can connect to the master controller
            if (!message.isSuccessful() &&
                    !message.getUnlocalizedMessage().equals(NetworkMessages.MAX_CONNECTIONS) &&
                    !message.getUnlocalizedMessage().equals(NetworkMessages.OUT_OF_RANGE)) {        // If the node can't connect and the message is not MAX_CONNECTIONS and OUT_OF_RANGE
                return message;     // Return a status with unsuccessful and a message that is not MAX_CONNECTIONS and OUT_OF_RANGE
            }
            for (BlockPos extension : extensions) {     // Check if the node can't connect to one of the extensions
                TileEntityController controller = getController(extension);
                if (controller != null) {
                    IStatusMessage subMessage = controller.superConnectToNode(node, isSecond, true);      // Check if the node can connect to the extension controller
                    if (!subMessage.isSuccessful() &&
                            !subMessage.getUnlocalizedMessage().equals(NetworkMessages.MAX_CONNECTIONS) &&
                            !subMessage.getUnlocalizedMessage().equals(NetworkMessages.OUT_OF_RANGE)) {     // If the node can't connect and the message is not MAX_CONNECTIONS and OUT_OF_RANGE
                        return subMessage;      // Return a status with unsuccessful and a message that is not MAX_CONNECTIONS and OUT_OF_RANGE
                    }
                }
            }
        } else {
            message = getMaster().connectToNode(node, isSecond, true);
            if (!message.isSuccessful()) {
                return message;
            }
        }
        if(!simulate) {
            message = super.connectToNode(node, isSecond, false);
            if (message.isSuccessful()) {
                node.disconnectFromController();
                node.connectToController(pos, connectionPriority + 1);
                return message;
            }
            TileEntityController master = getMaster();
            if(master != null) {
                message = master.superConnectToNode(node, false, false);       // Connect to the master controller, if it is possible
                if(!message.isSuccessful()) {       // If the node didn't connect to the master controller check the extension controllers
                    for (BlockPos extension : extensions) {
                        TileEntityController controller = getController(extension);
                        if (controller != null) {
                            IStatusMessage subMessage = controller.superConnectToNode(node, false, false);   // Connect to the extension controller, if it is possible
                            if (subMessage.isSuccessful()) {        // If the node did connect to the extension controller, return a status with successful and a message with SUCCESSFUL_CONNECTED message. If not check the next extension controller.
                                node.disconnectFromController();
                                node.connectToController(controller.pos, controller.connectionPriority + 1);
                                return subMessage;
                            }
                        }
                    }
                } else {
                    node.disconnectFromController();
                    node.connectToController(master.pos, master.connectionPriority + 1);
                }
            }
        }
        return message;
    }

    private IStatusMessage superConnectToNode(INetworkNode node, boolean isSecond, boolean simulate) {
        return super.connectToNode(node, isSecond, simulate);
    }

    @Override
    public IStatusMessage disconnectFromNode(INetworkNode node, boolean isSecond, boolean simulate) {
        if (isMaster) {
            IStatusMessage message = super.disconnectFromNode(node, isSecond, simulate);
            if (message.isSuccessful()) {  // If the disconnect was successful return the message.
                return message;
            }
            // Iterate over all extensions and try disconnect the node from one of the extensions.
            for (BlockPos extension : extensions) {
                TileEntityController controller = getController(extension);
                if (controller != null) {
                    IStatusMessage subMessage = controller.extDisconnectFromNode(node, isSecond, simulate);
                    if (subMessage.isSuccessful()) {
                        return subMessage;
                    }
                }
            }
            return message;
        } else {
            return getMaster().disconnectFromNode(node, isSecond, simulate);
        }
    }

    private IStatusMessage extDisconnectFromNode(INetworkNode node, boolean isSecond, boolean simulate) {
        if (!isMaster) {
            return super.disconnectFromNode(node, isSecond, simulate);
        }
        return null;
    }

    @Override
    public int getNetworkConnections() {
        if (isMaster) {
            // Calculate the connections of all tile entities.
            int connections = super.getNetworkConnections();
            for (BlockPos extension : extensions) {
                TileEntityController controller = getController(extension);
                if (controller != null) {
                    connections += controller.networkNodes.size();
                }
            }
            return connections;
        } else {
            return getMaster().getNetworkConnections();
        }
    }

    @Override
    public int getMaxNetworkConnections() {
        if (isMaster) {
            // Calculate the max connection of all tile entities.
            int connections = super.getMaxNetworkConnections();
            for (BlockPos extension : extensions) {
                TileEntityController controller = getController(extension);
                if (controller != null) {
                    connections += controller.maxConnections;
                }
            }
            return connections;
        } else {
            return getMaster().getMaxNetworkConnections();
        }
    }

    @Override
    public TileEntityController getController() {
        return this;
    }

    @Override
    public int getConnectionPriority() {
        return 0;
    }
    //endregion

    //region Tile Entity Energy Switch
    @Override
    public int receiveEnergy(int energy, boolean simulate) {
        if (isMaster) {
            int received = super.receiveEnergy(energy, simulate);
            // Calculate the left energy.
            int leftEnergy = energy - received;
            // Iterate over all extensions and store the left energy in them.
            for (BlockPos extension : extensions) {
                TileEntityController controller = getController(extension);
                if (controller != null) {
                    received += controller.extReceiveEnergy(leftEnergy, simulate);
                    leftEnergy = energy - received;
                    if (leftEnergy == 0) {
                        break;
                    }
                }
            }
            return received;
        } else {
            return getMaster().receiveEnergy(energy, simulate);
        }
    }

    private int extReceiveEnergy(int energy, boolean simulate) {
        return super.receiveEnergy(energy, simulate);
    }

    @Override
    public int extractEnergy(int energy, boolean simulate) {
        if (isMaster) {
            int extract = super.extractEnergy(energy, simulate);
            // Calculate the left energy.
            int leftEnergy = energy - extract;
            // Iterate over all extensions and extract the left energy of them.
            for (BlockPos extension : extensions) {
                TileEntityController controller = getController(extension);
                if (controller != null) {
                    extract += controller.extExtractEnergy(leftEnergy, simulate);
                    leftEnergy = energy - extract;
                    if (leftEnergy == 0) {
                        break;
                    }
                }
            }
            return extract;
        } else {
            return getMaster().extractEnergy(energy, simulate);
        }
    }

    private int extExtractEnergy(int energy, boolean simulate) {
        return super.extractEnergy(energy, simulate);
    }

    @Override
    public int getEnergyStored() {
        if(isMaster) {
            // Calculate the stored energy of all tile entities.
            int energy = energyStorage.getEnergyStored();
            for (BlockPos extension : extensions) {
                TileEntityController controller = getController(extension);
                if (controller != null) {
                    energy += controller.energyStorage.getEnergyStored();
                }
            }
            return energy;
        } else {
            return getMaster().getEnergyStored();
        }
    }

    @Override
    public int getMaxEnergyStored() {
        if (isMaster) {
            // Calculate the capacity of all tile entities.
            int capacity = energyStorage.getMaxEnergyStored();
            for (BlockPos extension : extensions) {
                TileEntityController controller = getController(extension);
                if (controller != null) {
                    capacity += controller.energyStorage.getMaxEnergyStored();
                }
            }
            return capacity;
        } else {
            return getMaster().getMaxEnergyStored();
        }
    }

    @Override
    public boolean canProvideEnergy() {
        if(isMaster) {
            for (BlockPos pos : extensions) {
                TileEntityController controller = getController(pos);
                if (controller.superCanProvideEnergy()) {
                    return true;
                }
            }
            return false;
        } else {
            TileEntityController master = getMaster();
            if(master != null) {
                return getMaster().canProvideEnergy();
            } else {
                return false;
            }
        }
    }

    private boolean superCanProvideEnergy() {
        return super.canProvideEnergy();
    }
    //endregion

    //region Tile Entity Controller
    public TileEntityController setIsMaster() {
        isMaster = true;
        return this;
    }

    public TileEntityController addExtensions(TileEntityController tile) {
        if(this.isMaster) {
            // Add the extension and set the master of the extension.
            extensions.add(tile.pos);
            tile.master = this.pos;
        } else {
            getController(master).addExtensions(tile);
        }
        markDirty();
        return this;
    }

    public TileEntityController removeExtension(TileEntityController tile) {
        if (isMaster) {
            if(tile != this) {
                // If the extension to remove is not the master, remove it from the extension list and
                // set the master of the extension to null.
                extensions.remove(tile.getPos());
                tile.master = null;
            } else if(extensions.size() > 0) {
                // If the extension to remove is the master and there are other extension, take the first,
                // set it to the new master, transfer the energy to it, transfer the connected nodes,
                // connect the connected nodes to the new node, set the extensions new master and
                // add the extensions to the new master.
                TileEntityController newMaster = getController(extensions.get(0));
                newMaster.setIsMaster();
                newMaster.modifyEnergy(this.getEnergyStored());
                List<INetworkNode> nodes = getNetworkNodes();
                if(nodes != null) {
                    for (INetworkNode node : nodes) {
                        newMaster.connectToNode(node, false, false);
                    }
                }
                for (int i = 1; i < extensions.size(); i++) {
                    getController(extensions.get(i)).master = newMaster.getPos();
                    newMaster.extensions.add(extensions.get(i));
                }
            }
        } else {
            getController(master).removeExtension(this);
        }
        markDirty();
        return this;
    }

    public TileEntityController removeExtension() {
        return removeExtension(this);
    }

    public boolean isMaster() {
        return isMaster;
    }

    public void validateConstruction() {
        if (isMaster) {
            int energy = getEnergyStored();
            List<BlockPos> copy = new LinkedList<>(extensions);
            for (BlockPos extension : copy) {
                removeExtension(getController(extension));
            }
            List<BlockPos> neighbors = WorldHelper.hasNeighbors(worldObj, pos, TransmatricsBlock.CONTROLLER.getBlock());
            for (BlockPos neighbor : neighbors) {
                TileEntityController controller = getController(neighbor);
                if (controller != null) {
                    reconnectToMaster(this.pos, this.pos);
                }
            }
            energyStorage.modifyEnergy(energy);
            for (BlockPos extension : copy) {
                if (!extensions.contains(extension)) {
                    TileEntityController controller = getController(extension);
                    if (controller != null) {
                        controller.setIsMaster();
                        controller.validateConstruction();
                        break;
                    }
                }
            }
        } else {
            TileEntityController master = getMaster();
            if(master != null) {
                master.validateConstruction();
            }
        }
    }

    private void reconnectToMaster(BlockPos master, BlockPos executer) {
        TileEntityController masterController = getController(master);
        if (masterController != null && masterController != this) {
            masterController.addExtensions(this);
        }
        List<BlockPos> neighbors = WorldHelper.hasNeighbors(worldObj, pos, TransmatricsBlock.CONTROLLER.getBlock());
        for (BlockPos neighbor : neighbors) {
            TileEntityController controller = getController(neighbor);
            if (controller != null) {
                if (!neighbor.equals(master) && controller.isMaster) {
                    controller.isMaster = false;
                }
                if (!neighbor.equals(executer) && controller.getMaster() == null) {
                    controller.reconnectToMaster(master, this.pos);
                }
            }
        }
    }

    public TileEntityController getMaster() {
        if(isMaster) {
            return this;
        }
        return getController(master);
    }

    public void modifyEnergy(int energy) {
        if(isMaster) {
            int canStore = Math.min(energyStorage.getCapacity() - energyStorage.getEnergyStored(), energy);
            energyStorage.modifyEnergy(canStore);
            energy -= canStore;
            for (BlockPos extension : extensions) {
                TileEntityController controller = getController(extension);
                if (controller != null) {
                    canStore = Math.min(controller.energyStorage.getCapacity() - energyStorage.getEnergyStored(), energy);
                    energyStorage.modifyEnergy(canStore);
                    energy -= canStore;
                    if (energy == 0) {
                        break;
                    }
                }
            }
        } else {
            getMaster().modifyEnergy(energy);
        }
    }
    //endregion
}