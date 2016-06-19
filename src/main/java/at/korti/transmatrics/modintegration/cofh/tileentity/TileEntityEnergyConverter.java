package at.korti.transmatrics.modintegration.cofh.tileentity;

import at.korti.transmatrics.api.Constants.Energy;
import at.korti.transmatrics.api.block.IChangeMode;
import at.korti.transmatrics.api.block.IModeInfo;
import at.korti.transmatrics.api.block.modes.InOutMode;
import at.korti.transmatrics.api.energy.EnergyHandler;
import at.korti.transmatrics.api.energy.IEnergyConsumer;
import at.korti.transmatrics.api.network.INetworkNode;
import at.korti.transmatrics.tileentity.TileEntityEnergyNode;
import at.korti.transmatrics.tileentity.network.TileEntityController;
import at.korti.transmatrics.util.helper.TextHelper;
import at.korti.transmatrics.util.helper.WorldHelper;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;

/**
 * Created by Korti on 30.05.2016.
 */
public class TileEntityEnergyConverter extends TileEntityEnergyNode implements IEnergyProvider, IEnergyReceiver, IChangeMode<InOutMode>, IModeInfo<InOutMode>{

    private InOutMode mode;

    public TileEntityEnergyConverter() {
        super(Energy.RF_CONVERTER_CAPACITY, Energy.RF_CONVERTER_TRANSFER);
        this.mode = InOutMode.OUT;
    }

    @Override
    public void update() {
        super.update();

        if(canProvideEnergy() && !worldObj.isRemote) {
            if(mode == InOutMode.OUT) {
                for (EnumFacing facing : EnumFacing.VALUES) {
                    IEnergyReceiver receiver = WorldHelper.getNeighbor(worldObj, pos, facing, IEnergyReceiver.class);
                    if (receiver != null) {
                        int energy = receiver.receiveEnergy(facing.getOpposite(), energyStorage.getMaxExtract(), true);
                        energy = extractEnergy(energy, false);
                        receiver.receiveEnergy(facing.getOpposite(), energy, false);
                    }
                }
            } else if (mode == InOutMode.IN) {
                if (networkNode != null && getNetworkNode() != null) {
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
            }
        }
    }

    @Override
    public int receiveEnergy(int energy, boolean simulate) {
        if (mode != InOutMode.IN) {
            return super.receiveEnergy(energy, simulate);
        } else {
            return 0;
        }
    }

    //region IEnergyProvider/IEnergyReceiver
    @Override
    public int extractEnergy(EnumFacing enumFacing, int i, boolean b) {
        return energyStorage.extractEnergy(i * Energy.RF_CONVERT_MULTIPLIER, b);
    }

    @Override
    public int receiveEnergy(EnumFacing enumFacing, int i, boolean b) {
        if (mode == InOutMode.IN) {
            return energyStorage.receiveEnergy(i * Energy.RF_CONVERT_MULTIPLIER, b);
        } else {
            return 0;
        }
    }

    @Override
    public int getEnergyStored(EnumFacing enumFacing) {
        return getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(EnumFacing enumFacing) {
        return getMaxEnergyStored();
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
    public EnumChatFormatting getColorForMode(InOutMode mode) {
        switch (mode){
            case IN:
                return EnumChatFormatting.BLUE;
            case OUT:
                return EnumChatFormatting.GOLD;
            default:
                return EnumChatFormatting.WHITE;
        }
    }
    //endregion
}
