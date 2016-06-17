package at.korti.transmatrics.modintegration.cofh.tileentity;

import at.korti.transmatrics.api.Constants.Energy;
import at.korti.transmatrics.tileentity.TileEntityEnergyNode;
import at.korti.transmatrics.util.helper.WorldHelper;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.util.EnumFacing;

/**
 * Created by Korti on 30.05.2016.
 */
public class TileEntityEnergyConverter extends TileEntityEnergyNode implements IEnergyProvider, IEnergyReceiver{

    public TileEntityEnergyConverter() {
        super(Energy.RF_CONVERTER_CAPACITY, Energy.RF_CONVERTER_TRANSFER);
    }

    @Override
    public void update() {
        super.update();

        if(canProvideEnergy()) {
            for (EnumFacing facing : EnumFacing.VALUES) {
                IEnergyReceiver receiver = WorldHelper.getNeighbor(worldObj, pos, facing, IEnergyReceiver.class);
                if(receiver != null) {
                    int energy = receiver.receiveEnergy(facing.getOpposite(), energyStorage.getMaxExtract(), true);
                    energy = extractEnergy(energy, false);
                    receiver.receiveEnergy(facing.getOpposite(), energy, false);
                }
            }
        }
    }

    @Override
    public int extractEnergy(EnumFacing enumFacing, int i, boolean b) {
        return energyStorage.extractEnergy(i * Energy.RF_CONVERT_MULTIPLIER, b);
    }

    @Override
    public int receiveEnergy(EnumFacing enumFacing, int i, boolean b) {
        return energyStorage.receiveEnergy(i * Energy.RF_CONVERT_MULTIPLIER, b);
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
}
