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

import static at.korti.transmatrics.api.network.NetworkHandler.getController;

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
    }

    //region Tile Entity
    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setBoolean(NBT.IS_MASTER, isMaster);
        if (isMaster) {
            NBTTagList extensions = new NBTTagList();
            for (BlockPos extension : this.extensions) {
                NBTTagCompound extensionCompound = new NBTTagCompound();
                extensionCompound.setInteger(NBT.CONTROLLER_X, extension.getX());
                extensionCompound.setInteger(NBT.CONTROLLER_Y, extension.getY());
                extensionCompound.setInteger(NBT.CONTROLLER_Z, extension.getZ());
                extensions.appendTag(extensionCompound);
            }
            compound.setTag(NBT.CONTROLLER_EXTENSIONS, extensions);
        } else if(master != null) {
            compound.setInteger(NBT.CONTROLLER_X, master.getX());
            compound.setInteger(NBT.CONTROLLER_Y, master.getY());
            compound.setInteger(NBT.CONTROLLER_Z, master.getZ());
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        boolean isMaster = compound.getBoolean(NBT.IS_MASTER);
        if (isMaster) {
            this.isMaster = true;
            NBTTagList extensions = compound.getTagList(NBT.CONTROLLER_EXTENSIONS, 10);
            for (int i = 0; i < extensions.tagCount(); i++) {
                NBTTagCompound extensionCompound = extensions.getCompoundTagAt(i);
                this.extensions.add(new BlockPos(extensionCompound.getInteger(NBT.CONTROLLER_X),
                        extensionCompound.getInteger(NBT.CONTROLLER_Y),
                        extensionCompound.getInteger(NBT.CONTROLLER_Z)));
            }
        } else if(compound.hasKey(NBT.CONTROLLER_X)) {
            master = new BlockPos(compound.getInteger(NBT.CONTROLLER_X),
                    compound.getInteger(NBT.CONTROLLER_Y),
                    compound.getInteger(NBT.CONTROLLER_Z));
        }
    }
    //endregion

    //region Tile Entity Network Switch
    @Override
    public IStatusMessage connectToNode(INetworkNode node, boolean isSecond, boolean simulate) {
        if(isMaster) {
            IStatusMessage message = super.connectToNode(node, isSecond, true);     // Check if the node can connect to the master controller
            if (!message.isSuccessful() &&
                    !message.getUnlocalizedMessage().equals(NetworkMessages.MAX_CONNECTIONS) &&
                    !message.getUnlocalizedMessage().equals(NetworkMessages.OUT_OF_RANGE)) {        // If the node can't connect and the message is not MAX_CONNECTIONS and OUT_OF_RANGE
                return message;     // Return a status with unsuccessful and a message that is not MAX_CONNECTIONS and OUT_OF_RANGE
            }
            for (BlockPos extension : extensions) {     // Check if the node can't connect to one of the extensions
                TileEntityController controller = getController(worldObj, extension);
                if (controller != null) {
                    IStatusMessage subMessage = controller.extConnectToNode(node, isSecond, true);      // Check if the node can connect to the extension controller
                    if (!subMessage.isSuccessful() &&
                            !subMessage.getUnlocalizedMessage().equals(NetworkMessages.MAX_CONNECTIONS) &&
                            !subMessage.getUnlocalizedMessage().equals(NetworkMessages.OUT_OF_RANGE)) {     // If the node can't connect and the message is not MAX_CONNECTIONS and OUT_OF_RANGE
                        return subMessage;      // Return a status with unsuccessful and a message that is not MAX_CONNECTIONS and OUT_OF_RANGE
                    }
                }
            }
            if(!simulate) {
                message = super.connectToNode(node, isSecond, false);       // Connect to the master controller, if it is possible
                if(!message.isSuccessful()) {       // If the node didn't connect to the master controller check the extension controllers
                    for (BlockPos extension : extensions) {
                        TileEntityController controller = getController(worldObj, extension);
                        if (controller != null) {
                            IStatusMessage subMessage = controller.extConnectToNode(node, isSecond, false);   // Connect to the extension controller, if it is possible
                            if (subMessage.isSuccessful()) {        // If the node did connect to the extension controller, return a status with successful and a message with SUCCESSFUL_CONNECTED message. If not check the next extension controller.
                                return subMessage;
                            }
                        }
                    }
                }
            }
            return message;         //Return a status if the node is connected
        } else {
            return getMaster().connectToNode(node, isSecond, simulate);
        }
    }

    private IStatusMessage extConnectToNode(INetworkNode node, boolean isSecond, boolean simulate) {
        if (!isMaster) {
            return super.connectToNode(node, isSecond, simulate);
        }
        return null;
    }

    @Override
    public int getNetworkConnections() {
        if (isMaster) {
            int connections = super.getNetworkConnections();
            for (BlockPos extension : extensions) {
                TileEntityController controller = getController(worldObj, extension);
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
            int connections = super.getMaxNetworkConnections();
            for (BlockPos extension : extensions) {
                TileEntityController controller = getController(worldObj, extension);
                if (controller != null) {
                    connections += controller.maxConnections;
                }
            }
            return connections;
        } else {
            return getMaster().getMaxNetworkConnections();
        }
    }
    //endregion

    //region Tile Entity Energy Switch
    @Override
    public int receiveEnergy(int energy, boolean simulate) {
        int received = super.receiveEnergy(energy, simulate);
        if (isMaster) {
            int leftEnergy = energy - received;
            for (BlockPos extension : extensions) {
                TileEntityController controller = getController(worldObj, extension);
                if (controller != null) {
                    received += controller.receiveEnergy(leftEnergy, simulate);
                    leftEnergy = energy - received;
                    if (leftEnergy == 0) {
                        break;
                    }
                }
            }
        }
        return received;
    }

    @Override
    public int extractEnergy(int energy, boolean simulate) {
        int extract = super.extractEnergy(energy, simulate);
        if (isMaster) {
            int leftEnergy = energy - extract;
            for (BlockPos extension : extensions) {
                TileEntityController controller = getController(worldObj, extension);
                if (controller != null) {
                    extract += controller.receiveEnergy(leftEnergy, simulate);
                    leftEnergy = energy - extract;
                    if (leftEnergy == 0) {
                        break;
                    }
                }
            }
        }
        return extract;
    }

    @Override
    public int getEnergyStored() {
        if(isMaster) {
            int energy = energyStorage.getEnergyStored();
            for (BlockPos extension : extensions) {
                TileEntityController controller = getController(worldObj, extension);
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
            int capacity = energyStorage.getMaxEnergyStored();
            for (BlockPos extension : extensions) {
                TileEntityController controller = getController(worldObj, extension);
                if (controller != null) {
                    capacity += controller.energyStorage.getMaxEnergyStored();
                }
            }
            return capacity;
        } else {
            return getMaster().getMaxEnergyStored();
        }
    }
    //endregion

    //region Tile Entity Controller
    public TileEntityController setIsMaster() {
        isMaster = true;
        return this;
    }

    public TileEntityController addExtensions(TileEntityController tile) {
        if(this.isMaster) {
            extensions.add(tile.pos);
            tile.master = this.pos;
        } else {
            getController(worldObj, master).addExtensions(tile);
        }
        markDirty();
        return this;
    }

    public TileEntityController removeExtension(TileEntityController tile) {
        if (isMaster) {
            if(tile != this) {
                extensions.remove(tile.getPos());
                tile.master = null;
            } else if(extensions.size() > 0) {
                TileEntityController newMaster = getController(worldObj, extensions.get(0));
                newMaster.setIsMaster();
                newMaster.modifyEnergy(this.getEnergyStored());
                newMaster.networkNodes.addAll(this.networkNodes);
                for (INetworkNode node : newMaster.networkNodes) {
                    node.connectToNode(newMaster, true, false);
                }
                for (int i = 1; i < extensions.size(); i++) {
                    getController(worldObj, extensions.get(i)).master = newMaster.getPos();
                    newMaster.extensions.add(extensions.get(i));
                }
            }
        } else {
            getController(worldObj, master).removeExtension(this);
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
                removeExtension(getController(worldObj, extension));
            }
            List<BlockPos> neighbors = WorldHelper.hasNeighbors(worldObj, pos, TransmatricsBlock.CONTROLLER.getBlock());
            for (BlockPos neighbor : neighbors) {
                TileEntityController controller = getController(worldObj, neighbor);
                if (controller != null) {
                    reconnectToMaster(this.pos, this.pos);
                }
            }
            energyStorage.modifyEnergy(energy);
            for (BlockPos extension : copy) {
                if (!extensions.contains(extension)) {
                    TileEntityController controller = getController(worldObj, extension);
                    if (controller != null) {
                        controller.setIsMaster();
                        controller.validateConstruction();
                        break;
                    }
                }
            }
        } else {
            getMaster().validateConstruction();
        }
    }

    private void reconnectToMaster(BlockPos master, BlockPos executer) {
        TileEntityController masterController = getController(worldObj, master);
        if (masterController != null && masterController != this) {
            masterController.addExtensions(this);
        }
        List<BlockPos> neighbors = WorldHelper.hasNeighbors(worldObj, pos, TransmatricsBlock.CONTROLLER.getBlock());
        for (BlockPos neighbor : neighbors) {
            TileEntityController controller = getController(worldObj, neighbor);
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
        return getController(worldObj, master);
    }

    public void modifyEnergy(int energy) {
        if(isMaster) {
            int canStore = Math.min(energyStorage.getCapacity() - energyStorage.getEnergyStored(), energy);
            energyStorage.modifyEnergy(canStore);
            energy -= canStore;
            for (BlockPos extension : extensions) {
                TileEntityController controller = getController(worldObj, extension);
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
