package at.korti.transmatrics.tileentity;

import at.korti.transmatrics.api.Constants.Energy;
import at.korti.transmatrics.api.Constants.Tanks;

/**
 * Created by Korti on 27.02.2016.
 */
public class TileEntityLavaGenerator extends TileEntityFluidGenerator {

    public TileEntityLavaGenerator() {
        super(Energy.LAVA_GENERATOR_GENERATE, Energy.LAVA_GENERATOR_CAPACITY, Energy.LAVA_GENERATOR_EXTRACTION, Tanks.LAVA_GENERATOR_CAPACITY, Energy.LAVA_GENERATOR_FLUID_USE, Tanks.LAVA_GENERATOR_FLUID);
    }

}
