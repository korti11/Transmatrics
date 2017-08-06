package at.korti.transmatrics.block;

import at.korti.transmatrics.tileentity.TileEntityMultiBlockEnergyNode;
import at.korti.transmatrics.util.helper.WorldHelper;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by Korti on 05.08.2017.
 */
public abstract class MultiMachineBlock extends MachineBlock {

    protected MultiMachineBlock(Material material, MapColor mapColor, String name,
                                Class<? extends TileEntityMultiBlockEnergyNode> tileEntityClass) {
        super(material, mapColor, name, tileEntityClass);
    }

    protected MultiMachineBlock(Material materialIn, String name,
                                Class<? extends TileEntityMultiBlockEnergyNode> tileEntityClass) {
        super(materialIn, name, tileEntityClass);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof TileEntityMultiBlockEnergyNode) {
            TileEntityMultiBlockEnergyNode node = (TileEntityMultiBlockEnergyNode) tileEntity;
            BlockPos neighbor = WorldHelper.hasNeighbor(worldIn, pos, this);
            boolean isConnected = false;

            if (neighbor != null) {
                TileEntity extensionTileEntity = worldIn.getTileEntity(neighbor);
                if (extensionTileEntity instanceof TileEntityMultiBlockEnergyNode) {
                    TileEntityMultiBlockEnergyNode extensionNode = (TileEntityMultiBlockEnergyNode) extensionTileEntity;
                    extensionNode.addExtensionNode(node);
                    extensionNode.validateConstruction();
                    isConnected = true;
                }
            }

            if (!isConnected) {
                node.setIsMaster();
            }
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof TileEntityMultiBlockEnergyNode) {
            TileEntityMultiBlockEnergyNode node = (TileEntityMultiBlockEnergyNode) tileEntity;
            node.removeExtensionNode();
            super.breakBlock(worldIn, pos, state);
        }
        BlockPos neighbor = WorldHelper.hasNeighbor(worldIn, pos, this.getClass());
        if(neighbor != null) {
            tileEntity = worldIn.getTileEntity(neighbor);
            if (tileEntity instanceof TileEntityMultiBlockEnergyNode) {
                ((TileEntityMultiBlockEnergyNode) tileEntity).validateConstruction();
            }
        }
    }
}
