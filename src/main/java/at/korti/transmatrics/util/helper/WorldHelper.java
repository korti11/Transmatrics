package at.korti.transmatrics.util.helper;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

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

}
