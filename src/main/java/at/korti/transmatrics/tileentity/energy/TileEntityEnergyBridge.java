package at.korti.transmatrics.tileentity.energy;

import at.korti.transmatrics.api.Constants.Energy;
import at.korti.transmatrics.api.Constants.NBT;
import at.korti.transmatrics.api.block.IChangeMode;
import at.korti.transmatrics.api.block.IModeInfo;
import at.korti.transmatrics.api.block.modes.InOutMode;
import at.korti.transmatrics.api.network.INetworkPackage;
import at.korti.transmatrics.api.network.networkpackages.EnergyRequestNetworkPackage;
import at.korti.transmatrics.tileentity.TileEntityEnergyNode;
import at.korti.transmatrics.util.helper.TextHelper;
import at.korti.transmatrics.util.helper.WorldHelper;
import cofh.redstoneflux.api.IEnergyReceiver;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextFormatting;

/**
 * Created by Korti on 30.05.2016.
 */
public class TileEntityEnergyBridge extends TileEntityEnergyNode implements IChangeMode<InOutMode>, IModeInfo<InOutMode>{

    private InOutMode mode;

    public TileEntityEnergyBridge() {
        super(Energy.RF_CONVERTER_CAPACITY, Energy.RF_CONVERTER_TRANSFER);
        this.mode = InOutMode.OUT;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        compound.setInteger(NBT.SELECTED_MODE, getCurrentMode().ordinal());
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        int mode = compound.getInteger(NBT.SELECTED_MODE);
        this.mode = getAllModes()[mode];
    }

    @Override
    public void update() {
        super.update();

        if(!getWorld().isRemote) {
            if(mode == InOutMode.OUT) {
                for (EnumFacing facing : EnumFacing.VALUES) {
                    IEnergyReceiver receiver = WorldHelper.getNeighbor(getWorld(), pos, facing, IEnergyReceiver.class);
                    if (receiver != null) {
                        int energy = receiver.receiveEnergy(facing.getOpposite(), energyStorage.getMaxExtract(), true);
                        energy = extractEnergy(facing, energy, false);
                        receiver.receiveEnergy(facing.getOpposite(), energy, false);
                    }
                }

            }
        }
    }

    @Override
    protected void requestEnergy() {
        if(mode == InOutMode.OUT) {
            super.requestEnergy();
        }
    }

    @Override
    protected void handlePackageQueue() {
        INetworkPackage networkPackage = getPackageQueue().peek();
        if(networkPackage instanceof EnergyRequestNetworkPackage && mode == InOutMode.IN) {
            getPackageQueue().poll();
            return;
        }
        super.handlePackageQueue();
    }

    //region IEnergyProvider/IEnergyReceiver
    @Override
    public int extractEnergy(EnumFacing enumFacing, int i, boolean b) {
        return energyStorage.extractEnergy(i, b);
    }

    @Override
    public int receiveEnergy(EnumFacing enumFacing, int i, boolean b) {
        return energyStorage.receiveEnergy(i, b);
    }

    @Override
    public boolean canConnectEnergy(EnumFacing enumFacing) {
        return true;
    }
    //endregion

    //region IChangeMode
    @Override
    public InOutMode[] getAllModes() {
        return InOutMode.values();
    }

    @Override
    public InOutMode getCurrentMode() {
        return this.mode;
    }

    @Override
    public void setMode(InOutMode mode) {
        this.mode = mode;
    }

    @Override
    public InOutMode cycleThroughMode() {
        InOutMode[] modes = getAllModes();
        if(getCurrentMode().ordinal() + 1 < modes.length) {
            return mode = modes[getCurrentMode().ordinal() + 1];
        } else {
            return mode = modes[0];
        }
    }
    //endregion

    //region IModeInfo
    @Override
    public String getCurrentModeName() {
        return TextHelper.firstCharUppercase(getCurrentMode().name().toLowerCase());
    }

    @Override
    public TextFormatting getColorForMode(InOutMode mode) {
        switch (mode){
            case IN:
                return TextFormatting.BLUE;
            case OUT:
                return TextFormatting.GOLD;
            default:
                return TextFormatting.WHITE;
        }
    }
    //endregion
}
