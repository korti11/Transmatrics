package at.korti.transmatrics.block.network;

import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.api.network.INetworkNode;
import at.korti.transmatrics.api.network.NetworkHandler;
import at.korti.transmatrics.block.MachineBlock;
import at.korti.transmatrics.tileentity.network.TileEntityController;
import at.korti.transmatrics.util.helper.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Korti on 08.03.2016.
 */
public class Controller extends MachineBlock{

    public Controller() {
        super(Material.iron, TransmatricsBlock.CONTROLLER.getRegName(), TileEntityController.class);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileEntityController) {
            TileEntityController controller = (TileEntityController) te;
            List<BlockPos> neighbors = WorldHelper.hasNeighbors(worldIn, pos, TransmatricsBlock.CONTROLLER.getBlock());
            boolean isConnected = false;

            for(BlockPos neighborPos : neighbors){
                if (neighborPos != null) {
                    TileEntity extensionTe = worldIn.getTileEntity(neighborPos);
                    if (extensionTe instanceof TileEntityController) {
                        TileEntityController extensionController = (TileEntityController) extensionTe;
                        if (extensionController.isMaster()) {
                            extensionController.addExtensions(controller);
                            extensionController.validateConstruction();
                            isConnected = true;
                        } else if (extensionController.getMaster() != null) {
                            extensionController.addExtensions(controller);
                            extensionController.validateConstruction();
                            isConnected = true;
                        }
                    }
                }
            }

            if(!isConnected){
                controller.setIsMaster();
            }
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntityController controller = NetworkHandler.getController(worldIn, pos);
        TileEntityController master = controller != null ? controller.getMaster() : null;
        List<INetworkNode> connectedNodes = controller != null ? new LinkedList<>(controller.getConnections()) : null;
        if(controller != null) {
            controller.removeExtension();
        }
        super.breakBlock(worldIn, pos, state);
        if (master != null) {
            for (INetworkNode node : connectedNodes) {
                master.connectToNode(node, false, false);
            }
        }
        BlockPos neighbor = WorldHelper.hasNeighbor(worldIn, pos, TransmatricsBlock.CONTROLLER.getBlock());
        controller = NetworkHandler.getController(worldIn, neighbor);
        if (controller != null) {
            controller.validateConstruction();
        }
    }
}
