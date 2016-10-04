package at.korti.transmatrics.world;

import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

/**
 * Created by Korti on 30.04.2016.
 */
public class OreGeneration implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        int dimID = world.provider.getDimension();
        if(dimID != -1 && dimID != 1) {
            generate(world, random, chunkX, chunkZ);
        }
    }

    public void generate(World world, Random random, int x, int z) {
        generateOre(TransmatricsBlock.ORE_BLOCK.getBlock(), 0, world, random, x, z, 9);
        generateOre(TransmatricsBlock.ORE_BLOCK.getBlock(), 1, world, random, x, z, 8);
        generateOre(TransmatricsBlock.ORE_BLOCK.getBlock(), 2, world, random, x, z, 5);
        generateOre(TransmatricsBlock.ORE_BLOCK.getBlock(), 3, world, random, x, z, 4);
        generateOre(TransmatricsBlock.ORE_BLOCK.getBlock(), 4, world, random, x, z, 5);
    }

    public void generateOre(Block ore, int meta, World world, Random random, int blockXPos, int blockZPos, int maxVeinSize){

        int seaLevel = world.provider.getAverageGroundLevel() + 1;

        if (seaLevel < 20) {
            int x = (blockXPos << 4) + 8;
            int z = (blockZPos << 4) + 8;
            seaLevel = world.getHeight(new BlockPos(x, 0, z)).getY();
        }

        if (ore == null) {
            return;
        }

        double oreDepthMultiplier = maxVeinSize * seaLevel / 64;
        int scale = (int) Math.round(random.nextGaussian() * Math.sqrt(oreDepthMultiplier) + oreDepthMultiplier);

        for (int x = 0; x < (random.nextBoolean() ? 2 : scale) / 2; ++x) {
            WorldGenMinable blockOre = new WorldGenMinable(ore.getStateFromMeta(meta), maxVeinSize);
            int cx = blockXPos * 16 + random.nextInt(22);
            int cy = random.nextInt(40 * seaLevel / 64) + random.nextInt(22 * seaLevel / 64) + 12 * seaLevel / 64;
            int cz = blockZPos * 16 + random.nextInt(22);
            blockOre.generate(world, random, new BlockPos(cx, cy, cz));
        }

    }


}
