package at.korti.transmatrics.tileentity.generator;

import at.korti.transmatrics.api.Constants.Energy;
import at.korti.transmatrics.block.ActiveMachineBlock;
import at.korti.transmatrics.tileentity.TileEntityHeightGenerator;
import net.minecraft.util.EnumFacing.AxisDirection;

/**
 * Created by Korti on 01.03.2016.
 */
public class TileEntityThermalGenerator extends TileEntityHeightGenerator {
    public TileEntityThermalGenerator() {
        super(Energy.THERMAL_GENERATOR_MIN_GENERATE, Energy.THERMAL_GENERATOR_MAX_GENERATE, Energy.THERMAL_GENERATOR_CAPACITY,
                Energy.THERMAL_GENERATOR_EXTRACTION, Energy.THERMAL_GENERATOR_MIN_HEIGHT, Energy.THERMAL_GENERATOR_MAX_HEIGHT, AxisDirection.NEGATIVE);
    }

    @Override
    public void onLoad() {
        int seaLevel = getWorld().getSeaLevel();
        if (minHeight > seaLevel) {
            int diff = minHeight - seaLevel;
            minHeight = seaLevel;
            maxHeight = Math.max(maxHeight - diff, 0);
        }
        super.onLoad();
    }

    @Override
    public void update() {
        super.update();
        if (canProduceEnergy() && !ActiveMachineBlock.isActive(getWorld(), pos) && !getWorld().isRemote) {
            ActiveMachineBlock.setState(true, getWorld(), pos);
        } else if (!canProduceEnergy() && ActiveMachineBlock.isActive(getWorld(), pos) && !getWorld().isRemote) {
            ActiveMachineBlock.setState(false, getWorld(), pos);
        }
    }
}
