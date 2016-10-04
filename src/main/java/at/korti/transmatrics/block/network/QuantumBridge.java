package at.korti.transmatrics.block.network;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.NBT;
import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.api.network.quantum.QuantumBridgeHandler;
import at.korti.transmatrics.block.MachineBlock;
import at.korti.transmatrics.tileentity.network.TileEntityQuantumBridge;
import at.korti.transmatrics.util.helper.ItemStackHelper;
import at.korti.transmatrics.util.math.DimensionBlockPos;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * Created by Korti on 27.06.2016.
 */
public class QuantumBridge extends MachineBlock {

    public QuantumBridge() {
        super(Material.IRON, TransmatricsBlock.QUANTUM_BRIDGE.getRegName(), TileEntityQuantumBridge.class);
    }

    @Override
    protected ItemStack writeToStack(TileEntity tileEntity, @Nonnull ItemStack stack) {
        if (tileEntity instanceof TileEntityQuantumBridge) {
            NBTTagCompound compound = ItemStackHelper.getTagCompound(stack);
            String mapName = ((TileEntityQuantumBridge) tileEntity).getQuantumBridgeMapName();
            compound.setString(NBT.QUANTUM_BRIDGE_MAP_NAME, mapName);
            QuantumBridgeHandler.clearQuantumBridgePos(mapName, new DimensionBlockPos(tileEntity.getPos(),
                    tileEntity.getWorld().provider.getDimension()));
        }
        return stack;
    }
}
