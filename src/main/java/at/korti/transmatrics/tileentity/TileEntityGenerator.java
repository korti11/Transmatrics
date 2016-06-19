package at.korti.transmatrics.tileentity;

import at.korti.transmatrics.api.TransmatricsApi;
import at.korti.transmatrics.api.energy.*;
import at.korti.transmatrics.tileentity.network.TileEntityController;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;

/**
 * Created by Korti on 25.02.2016.
 */
public abstract class TileEntityGenerator extends TileEntityNetworkNode implements IEnergyProducer, IEnergyInfo, ITickable {

    protected int energyPerTick;
    private EnergyStorage energyStorage;

    public TileEntityGenerator(int energyPerTick, int capacity, int maxExtract) {
        this.energyPerTick = energyPerTick;
        this.energyStorage = new EnergyStorage(capacity, 0, maxExtract);
    }

    public TileEntityGenerator(int energyPerTick, int maxValue) {
        this(energyPerTick, maxValue, maxValue);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        energyStorage.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        energyStorage.readFromNBT(compound);
    }

    @Override
    public void update() {
        super.update();
        if(!worldObj.isRemote) {
            if (canProduceEnergy()) {
                energyStorage.modifyEnergy(energyPerTick);
            }
            if (canProvideEnergy() && networkNode != null && getNetworkNode() != null) {
                if (getNetworkNode().getController() == null) {
                    if(networkNode instanceof IEnergyConsumer) {
                        IEnergyConsumer consumer = (IEnergyConsumer) networkNode;
                        EnergyHandler.transferEnergy(this, consumer);
                    }
                } else {
                    TileEntityController controller = getNetworkNode().getController();
                    EnergyHandler.transferEnergy(this, controller);
                }
            }
            markDirty();
            worldObj.markBlockForUpdate(this.getPos());
        }
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return newState.getBlock() == Blocks.air;
    }

    @Override
    public int extractEnergy(int energy, boolean simulate) {
        return energyStorage.extractEnergy(energy, simulate);
    }

    @Override
    public int getEnergyPerTick() {
        return energyPerTick;
    }

    @Override
    public int getEnergyStored() {
        return energyStorage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored() {
        return energyStorage.getMaxEnergyStored();
    }

    @Override
    public boolean canProvideEnergy() {
        return energyStorage.getEnergyStored() > 0;
    }

    @Override
    public boolean canProduceEnergy() {
        return getEnergyStored() < getMaxEnergyStored();
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        writeToNBT(tagCompound);
        return new S35PacketUpdateTileEntity(this.getPos(), -1, tagCompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        readFromNBT(pkt.getNbtCompound());
    }
}
