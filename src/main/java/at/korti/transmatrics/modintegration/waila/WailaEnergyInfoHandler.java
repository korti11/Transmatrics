package at.korti.transmatrics.modintegration.waila;

import at.korti.transmatrics.api.Constants.NBT;
import at.korti.transmatrics.api.energy.IEnergyInfo;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Korti on 02.03.2016.
 */
public class  WailaEnergyInfoHandler implements IWailaDataProvider {

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
        currenttip.add("Energy: " + compound.getInteger(NBT.ENERGY) + "/" + compound.getInteger(NBT.CAPACITY) + " TF");
        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {
        if (te instanceof IEnergyInfo) {
            IEnergyInfo energyInfo = (IEnergyInfo) te;
            tag.setInteger(NBT.ENERGY, energyInfo.getEnergyStored());
            tag.setInteger(NBT.CAPACITY, energyInfo.getMaxEnergyStored());
        }
        return tag;
    }

}
