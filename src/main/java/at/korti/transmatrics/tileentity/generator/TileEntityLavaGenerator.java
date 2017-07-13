package at.korti.transmatrics.tileentity.generator;

import at.korti.transmatrics.api.Constants.Energy;
import at.korti.transmatrics.api.Constants.Tanks;
import at.korti.transmatrics.block.ActiveMachineBlock;
import at.korti.transmatrics.tileentity.TileEntityFluidGenerator;

/**
 * Created by Korti on 27.02.2016.
 */
public class TileEntityLavaGenerator extends TileEntityFluidGenerator {

    public TileEntityLavaGenerator() {
        super(Energy.LAVA_GENERATOR_GENERATE, Energy.LAVA_GENERATOR_CAPACITY, Energy.LAVA_GENERATOR_EXTRACTION,
                Tanks.LAVA_GENERATOR_CAPACITY, Energy.LAVA_GENERATOR_FLUID_USE, Tanks.LAVA_GENERATOR_FLUID);
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
