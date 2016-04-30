package at.korti.transmatrics.block.generator;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.GuiIds;
import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.tileentity.generator.TileEntityHydrogenGenerator;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

/**
 * Created by Korti on 04.03.2016.
 */
public class HydrogenGenerator extends FluidGeneratorBlock {

    public HydrogenGenerator() {
        super(Material.iron, TransmatricsBlock.HYDROGEN_GENERATOR.getRegName(), GuiIds.HYDROGEN_GENERATOR_GUI_ID, TileEntityHydrogenGenerator.class);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (isActive(worldIn, pos)) {
            double d0 = (double) pos.getX() + 0.5;
            double d1 = (double) pos.getY() + rand.nextDouble() * 6 / 16;
            double d2 = (double) pos.getZ() + 0.5;

            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1 + 1, d2, 0, 0, 0);
        }
    }

}
