package at.korti.transmatrics.util.helper;

import at.korti.transmatrics.util.math.DimensionBlockPos;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

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
        worldIn.spawnEntity(item);
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

    public static List<DimensionBlockPos> hasNeighbors(World worldIn, BlockPos posIn, Block searchBlockIn) {
        List<DimensionBlockPos> neighbors = new LinkedList<>();
        if (getBlock(worldIn, posIn.north()).equals(searchBlockIn)) {
            neighbors.add(getDimPos(worldIn, posIn.north()));
        }
        if(getBlock(worldIn, posIn.east()).equals(searchBlockIn)) {
            neighbors.add(getDimPos(worldIn, posIn.east()));
        }
        if (getBlock(worldIn, posIn.south()).equals(searchBlockIn)) {
            neighbors.add(getDimPos(worldIn, posIn.south()));
        }
        if (getBlock(worldIn, posIn.west()).equals(searchBlockIn)) {
            neighbors.add(getDimPos(worldIn, posIn.west()));
        }
        if (getBlock(worldIn, posIn.up()).equals(searchBlockIn)) {
            neighbors.add(getDimPos(worldIn, posIn.up()));
        }
        if (getBlock(worldIn, posIn.down()).equals(searchBlockIn)) {
            neighbors.add(getDimPos(worldIn, posIn.up()));
        }
        return neighbors;
    }

    public static BlockPos hasNeighbor(World worldIn, BlockPos posIn, Class clazz) {
        if (ReflectionHelper.hasClass(worldIn.getTileEntity(posIn.north()), clazz)) {
            return posIn.north();
        } else if (ReflectionHelper.hasClass(worldIn.getTileEntity(posIn.east()), clazz)) {
            return posIn.east();
        } else if (ReflectionHelper.hasClass(worldIn.getTileEntity(posIn.south()), clazz)) {
            return posIn.south();
        } else if (ReflectionHelper.hasClass(worldIn.getTileEntity(posIn.west()), clazz)) {
            return posIn.west();
        } else if (ReflectionHelper.hasClass(worldIn.getTileEntity(posIn.up()), clazz)) {
            return posIn.up();
        } else if (ReflectionHelper.hasClass(worldIn.getTileEntity(posIn.down()), clazz)) {
            return posIn.down();
        }
        return null;
    }

    public static BlockPos hasNeighbor(World worldIn, BlockPos posIn, EnumFacing facing, Class clazz) {
        if (ReflectionHelper.hasClass(worldIn.getTileEntity(posIn.offset(facing)), clazz)) {
            return posIn.offset(facing);
        }
        return null;
    }

    public static List<BlockPos> hasNeighbors(World worldIn, BlockPos posIn, Class clazz) {
        List<BlockPos> neighbors = new LinkedList<>();
        if (ReflectionHelper.hasClass(worldIn.getTileEntity(posIn.north()), clazz)) {
            neighbors.add(posIn.north());
        }
        if (ReflectionHelper.hasClass(worldIn.getTileEntity(posIn.east()), clazz)) {
            neighbors.add(posIn.east());
        }
        if (ReflectionHelper.hasClass(worldIn.getTileEntity(posIn.south()), clazz)) {
            neighbors.add(posIn.south());
        }
        if (ReflectionHelper.hasClass(worldIn.getTileEntity(posIn.west()), clazz)) {
            neighbors.add(posIn.west());
        }
        if (ReflectionHelper.hasClass(worldIn.getTileEntity(posIn.up()), clazz)) {
            neighbors.add(posIn.up());
        }
        if (ReflectionHelper.hasClass(worldIn.getTileEntity(posIn.down()), clazz)) {
            neighbors.add(posIn.down());
        }
        return neighbors;
    }

    public static <T> T getNeighbor(World worldIn, BlockPos posIn, EnumFacing facing, Class<T> clazz) {
        BlockPos pos = hasNeighbor(worldIn, posIn, facing, clazz);
        if (pos != null) {
            return ReflectionHelper.castTo(worldIn.getTileEntity(pos), clazz);
        }
        return null;
    }

    public static <T> List<T> getNeighbors(World worldIn, BlockPos posIn, Class<T> clazz) {
        List<BlockPos> neighborsPos = hasNeighbors(worldIn, posIn, clazz);
        List<T> neighbors = new LinkedList<>();
        for (BlockPos pos : neighborsPos) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if(ReflectionHelper.hasClass(tileEntity, clazz)) {
                neighbors.add(ReflectionHelper.castTo(tileEntity, clazz));
            }
        }
        return neighbors;
    }

    public static World getWorld(int dimensionID) {
        return FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(dimensionID);
    }

    public static DimensionBlockPos getDimPosForTileEntity(TileEntity tileEntity) {
        return getDimPos(tileEntity.getWorld(), tileEntity.getPos());
    }

    public static DimensionBlockPos getDimPos(World worldIn, BlockPos posIn) {
        return new DimensionBlockPos(posIn, worldIn.provider.getDimension());
    }

    public static TileEntity getTileEntity(DimensionBlockPos dimensionBlockPos) {
        World world = getWorld(dimensionBlockPos.getDimensionID());
        return world.getTileEntity(dimensionBlockPos);
    }
}
