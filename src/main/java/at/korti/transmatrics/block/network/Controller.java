package at.korti.transmatrics.block.network;

import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.block.MachineBlock;
import at.korti.transmatrics.tileentity.network.TileEntityController;
import at.korti.transmatrics.util.helper.WorldHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

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
            BlockPos neighborPos = WorldHelper.hasNeighbor(worldIn, pos, TransmatricsBlock.CONTROLLER.getBlock());
            if (neighborPos != null) {
                TileEntity extensionTe = worldIn.getTileEntity(neighborPos);
                if (extensionTe instanceof TileEntityController) {
                    ((TileEntityController) extensionTe).addExtensions(controller);
                }
            } else {
                controller.setIsMaster();
            }
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileEntityController) {
            ((TileEntityController) te).removeExtension();
        }
        super.breakBlock(worldIn, pos, state);
    }
}
