package at.korti.transmatrics.util.helper;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Korti on 27.02.2016.
 */
public class WorldHelper {

    public static void spawnItem(World worldIn, ItemStack itemStackIn, BlockPos posIn) {
        float f = 0.7F;
        double d0 = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
        double d1 = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
        double d2 = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
        EntityItem item = new EntityItem(worldIn, posIn.getX() + d0, posIn.getY() + d1, posIn.getZ() + d2, itemStackIn);
        item.setPickupDelay(10);
        worldIn.spawnEntityInWorld(item);
    }

    public static Block getBlock(World worldIn, BlockPos posIn) {
        return worldIn.getBlockState(posIn).getBlock();
    }

    public static BlockPos hasNeighbor(World worldIn, BlockPos posIn, Block searchBlockIn) {
        if (getBlock(worldIn, posIn.north()).equals(searchBlockIn)) {
            return posIn.north();
        } else if(getBlock(worldIn, posIn.east()).equals(searchBlockIn)) {
            return posIn.east();
        } else if (getBlock(worldIn, posIn.south()).equals(searchBlockIn)) {
            return posIn.south();
        } else if (getBlock(worldIn, posIn.west()).equals(searchBlockIn)) {
            return posIn.west();
        } else if (getBlock(worldIn, posIn.up()).equals(searchBlockIn)) {
            return posIn.up();
        } else if (getBlock(worldIn, posIn.down()).equals(searchBlockIn)) {
            return posIn.down();
        }

        return null;
    }

    public static List<BlockPos> hasNeighbors(World worldIn, BlockPos posIn, Block searchBlockIn) {
        List<BlockPos> neighbors = new LinkedList<>();
        if (getBlock(worldIn, posIn.north()).equals(searchBlockIn)) {
            neighbors.add(posIn.north());
        }
        if(getBlock(worldIn, posIn.east()).equals(searchBlockIn)) {
            neighbors.add(posIn.east());
        }
        if (getBlock(worldIn, posIn.south()).equals(searchBlockIn)) {
            neighbors.add(posIn.south());
        }
        if (getBlock(worldIn, posIn.west()).equals(searchBlockIn)) {
            neighbors.add(posIn.west());
        }
        if (getBlock(worldIn, posIn.up()).equals(searchBlockIn)) {
            neighbors.add(posIn.up());
        }
        if (getBlock(worldIn, posIn.down()).equals(searchBlockIn)) {
            neighbors.add(posIn.down());
        }
        return neighbors;
    }

}
