package at.korti.transmatrics.tileentity.network;

import at.korti.transmatrics.api.Constants.Energy;
import at.korti.transmatrics.api.Constants.NBT;
import at.korti.transmatrics.api.Constants.Network;
import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.api.energy.IEnergyMultiInfo;
import at.korti.transmatrics.api.network.INetworkMultiSwitchInfo;
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
public class TileEntityController extends TileEntityEnergySwitch implements INetworkMultiSwitchInfo, IEnergyMultiInfo{

    //TODO: Connect two controller to one if they touch each other.
    //TODO: Separate to two controller if the lose connection.
    //TODO: Store the energy on the separate tile entities.

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
        boolean isMaster = compound.getBoolean(NBT.IS_MASTER);
        if (isMaster) {
            this.isMaster = true;
            NBTTagList extensions = compound.getTagList(NBT.CONTROLLER_EXTENSIONS, 10);
            for (int i = 0; i < extensions.tagCount(); i++) {
                NBTTagCompound extensionCompound = extensions.getCompoundTagAt(i);
                this.extensions.add(new BlockPos(extensionCompound.getInteger(NBT.CONTROLLER_X),
                        extensionCompound.getInteger(NBT.CONTROLLER_Y),
                        extensionCompound.getInteger(NBT.CONTROLLER_Z)));
                this.maxConnections += Network.CONTROLLER_MAX_CONNECTIONS;
                this.energyStorage.setCapacity(getMaxEnergyStored() + Energy.CONTROLLER_CAPACITY);
            }
        } else if(compound.hasKey(NBT.CONTROLLER_X)) {
            master = new BlockPos(compound.getInteger(NBT.CONTROLLER_X),
                    compound.getInteger(NBT.CONTROLLER_Y),
                    compound.getInteger(NBT.CONTROLLER_Z));
        }
        super.readFromNBT(compound);
    }

    @Override
    public IStatusMessage connectToNode(INetworkNode node, boolean isSecond) {
        if(isMaster) {
            return super.connectToNode(node, isSecond);
        } else {
            return getMaster().connectToNode(node, isSecond);
        }
    }

    public TileEntityController setIsMaster() {
        isMaster = true;
        return this;
    }

    public TileEntityController addExtensions(TileEntityController tile) {
        if(this.isMaster) {
            extensions.add(tile.pos);
            tile.master = this.pos;
            super.energyStorage.setCapacity(this.getMaxEnergyStored() + Energy.CONTROLLER_CAPACITY);
            super.maxConnections += Network.CONTROLLER_MAX_CONNECTIONS;
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
                super.energyStorage.setCapacity(this.getMaxEnergyStored() - Energy.CONTROLLER_CAPACITY);
                super.maxConnections -= Network.CONTROLLER_MAX_CONNECTIONS;
            } else if(extensions.size() > 0) {
                TileEntityController newMaster = getController(worldObj, extensions.get(0));
                newMaster.setIsMaster();
                newMaster.energyStorage.setCapacity(this.getMaxEnergyStored() - Energy.CONTROLLER_CAPACITY);
                newMaster.energyStorage.modifyEnergy(this.getEnergyStored());
                newMaster.networkNodes.addAll(this.networkNodes);
                for (INetworkNode node : newMaster.networkNodes) {
                    node.connectToNode(newMaster, true);
                }
                newMaster.maxConnections = this.maxConnections - Network.CONTROLLER_MAX_CONNECTIONS;
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
            if (controller != null && !neighbor.equals(executer) && controller.getMaster() == null) {
                controller.reconnectToMaster(master, this.pos);
            }
        }
    }

    public TileEntityController getMaster() {
        if(isMaster) {
            return this;
        }
        return getController(worldObj, master);
    }
}
