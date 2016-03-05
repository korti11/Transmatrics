package at.korti.transmatrics.tileentity.generator;

import at.korti.transmatrics.api.Constants.Energy;
import at.korti.transmatrics.api.Constants.Tanks;
import at.korti.transmatrics.block.ActiveMachineBlock;
import at.korti.transmatrics.tileentity.TileEntityFluidGenerator;

/**
 * Created by Korti on 04.03.2016.
 */
public class TileEntityHydrogenGenerator extends TileEntityFluidGenerator {

    public TileEntityHydrogenGenerator() {
        super(Energy.HYDROGEN_GENERATOR_GENERATE, Energy.HYDROGEN_GENERATOR_CAPACITY, Energy.HYDROGEN_GENERATOR_EXTRACTION,
                Tanks.HYDROGEN_GENERATOR_CAPACITY, Energy.HYDROGEN_GENERATOR_FLUID_USE, Tanks.HYDROGEN_GENERATOR_FLUID);
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
