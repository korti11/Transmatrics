package at.korti.transmatrics.modintegration.waila;

import at.korti.transmatrics.api.Constants.NBT;
import at.korti.transmatrics.api.network.INetworkMultiSwitchInfo;
import at.korti.transmatrics.api.network.INetworkNodeInfo;
import at.korti.transmatrics.api.network.INetworkSwitchInfo;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Korti on 06.03.2016.
 */
public class WailaNetworkInfoHandler implements IWailaDataProvider {
    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        NBTTagCompound compound = accessor.getNBTData();
        if(compound.hasKey(NBT.NETWORK_CONNECTIONS) && compound.hasKey(NBT.MAX_NETWORK_CONNECTIONS)) {
            currenttip.add("Network connections: " + compound.getInteger(NBT.NETWORK_CONNECTIONS) + "/" + compound.getInteger(NBT.MAX_NETWORK_CONNECTIONS));
        }
        if (compound.hasKey(NBT.NETWORK_CONNECTED)) {
            currenttip.add(compound.getBoolean(NBT.NETWORK_CONNECTED) ? "Connected!" : "Not Connected!");
        }
        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {
        if (te instanceof INetworkSwitchInfo) {
            INetworkSwitchInfo networkInfo;
            if (te instanceof INetworkMultiSwitchInfo) {
                networkInfo = ((INetworkMultiSwitchInfo) te).getMaster();
            } else {
                networkInfo = (INetworkSwitchInfo) te;
            }
            tag.setInteger(NBT.NETWORK_CONNECTIONS, networkInfo.getNetworkConnections());
            tag.setInteger(NBT.MAX_NETWORK_CONNECTIONS, networkInfo.getMaxNetworkConnections());
        }
        if (te instanceof INetworkNodeInfo && player.isSneaking()) {
            INetworkNodeInfo networkInfo = (INetworkNodeInfo) te;
            tag.setBoolean(NBT.NETWORK_CONNECTED, networkInfo.isConnected());
        }
        return tag;
    }
}
