package at.korti.transmatrics.tileentity;

import at.korti.transmatrics.api.Constants.Energy;
import at.korti.transmatrics.block.ActiveMachineBlock;
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
    public void update() {
        super.update();
        if (canProduceEnergy() && !ActiveMachineBlock.isActive(worldObj, pos) && !worldObj.isRemote) {
            ActiveMachineBlock.setState(true, worldObj, pos);
        } else if (!canProduceEnergy() && ActiveMachineBlock.isActive(worldObj, pos) && !worldObj.isRemote) {
            ActiveMachineBlock.setState(false, worldObj, pos);
        }
    }
}
