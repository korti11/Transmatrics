package at.korti.transmatrics.block.generator;

import at.korti.transmatrics.api.Constants.GuiIds;
import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.tileentity.generator.TileEntityLavaGenerator;
import net.minecraft.block.material.Material;

/**
 * Created by Korti on 27.02.2016.
 */
public class LavaGenerator extends FluidGeneratorBlock{

    public LavaGenerator() {
        super(Material.IRON, TransmatricsBlock.LAVA_GENERATOR.getRegName(), GuiIds.LAVA_GENERATOR_GUI_ID, TileEntityLavaGenerator.class);
    }
}
