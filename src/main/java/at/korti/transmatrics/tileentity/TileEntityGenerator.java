package at.korti.transmatrics.tileentity;

import at.korti.transmatrics.api.energy.*;
import at.korti.transmatrics.api.network.INetworkNode;
import at.korti.transmatrics.tileentity.network.TileEntityController;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

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
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        energyStorage.writeToNBT(compound);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        energyStorage.readFromNBT(compound);
    }

    @Override
    public void update() {
        super.update();
        if(!getWorld().isRemote) {
            if (canProduceEnergy()) {
                energyStorage.modifyEnergy(energyPerTick);
            }
            if (canProvideEnergy() && networkNode != null && getNetworkNode() != null) {
                INetworkNode node = getNetworkNode();
                if (node.getController() == null) {
                    if (node instanceof IEnergyConsumer) {
                        IEnergyConsumer consumer = (IEnergyConsumer) node;
                        EnergyHandler.transferEnergy(this, consumer);
                    }
                } else {
                    TileEntityController controller = getNetworkNode().getController();
                    EnergyHandler.transferEnergy(this, controller);
                }
            }
            markDirty();
            IBlockState state = getWorld().getBlockState(pos);
            getWorld().notifyBlockUpdate(pos, state, state, 3);
        }
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return newState.getBlock() == Blocks.AIR;
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

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        writeToNBT(tagCompound);
        return new SPacketUpdateTileEntity(this.getPos(), -1, tagCompound);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        readFromNBT(pkt.getNbtCompound());
    }
}
