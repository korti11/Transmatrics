package at.korti.transmatrics.api.network;

import at.korti.transmatrics.tileentity.network.TileEntityController;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**
 * Created by Korti on 09.03.2016.
 */
public final class NetworkHandler {

    public static TileEntityController getController(World worldIn, BlockPos posIn) {
        TileEntity te = worldIn.getTileEntity(posIn);
        if (te instanceof TileEntityController) {
            return (TileEntityController) te;
        }
        return null;
    }

}
