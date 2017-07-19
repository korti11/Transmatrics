package at.korti.transmatrics.tileentity;

import at.korti.transmatrics.api.energy.IEnergyProducer;
import net.minecraft.block.state.IBlockState;

/**
 * Created by Korti on 25.02.2016.
 */
public abstract class TileEntityGenerator extends TileEntityEnergyNode implements IEnergyProducer {

    protected int energyPerTick;

    public TileEntityGenerator(int energyPerTick, int capacity, int maxExtract) {
        super(capacity, 0, maxExtract);
        this.energyPerTick = energyPerTick;
    }

    public TileEntityGenerator(int energyPerTick, int maxValue) {
        this(energyPerTick, maxValue, maxValue);
    }

    @Override
    public void update() {
        super.update();
        if(!getWorld().isRemote) {
            produceEnergy();
        }
    }

    protected void produceEnergy() {
        if (canProduceEnergy()) {
            energyStorage.modifyEnergyStored(energyPerTick);
        }
        markDirty();
        IBlockState state = getWorld().getBlockState(pos);
        getWorld().notifyBlockUpdate(pos, state, state, 3);
    }

    protected boolean canProduceEnergy() {
        return this.getEnergyStored() < this.getMaxEnergyStored();
    }
}
