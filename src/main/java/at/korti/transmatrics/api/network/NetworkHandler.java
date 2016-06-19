package at.korti.transmatrics.api.network;

import at.korti.transmatrics.tileentity.network.TileEntityController;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Korti on 09.03.2016.
 */
public final class NetworkHandler {

    public static TileEntityController getController(World worldIn, BlockPos posIn) {
        if(worldIn != null && posIn != null) {
            TileEntity te = worldIn.getTileEntity(posIn);
            if (te instanceof TileEntityController) {
                return (TileEntityController) te;
            }
        }
        return null;
    }

    public static INetworkNode getNetworkNode(World worldIn, BlockPos posIn){
        if(worldIn != null && posIn != null){
            TileEntity te = worldIn.getTileEntity(posIn);
            if(te instanceof INetworkNode){
                return (INetworkNode) te;
            }
        }
        return null;
    }

    public static List<INetworkNode> getNetworkNodes(World worldIn, List<BlockPos> posListIn) {
        List<INetworkNode> nodes = new LinkedList<>();
        for (BlockPos pos : posListIn) {
            nodes.add(getNetworkNode(worldIn, pos));
        }
        return nodes;
    }

}
