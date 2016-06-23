package at.korti.transmatrics.modintegration.waila;

import at.korti.transmatrics.api.Constants.NBT;
import at.korti.transmatrics.api.block.IChangeMode;
import at.korti.transmatrics.api.block.IModeInfo;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Korti on 17.06.2016.
 */
public class WailaModeInfoHandler implements IWailaDataProvider {
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
        currenttip.add("Mode: " + compound.getString(NBT.MODE_NAME));
        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {
        if(te instanceof IModeInfo && te instanceof IChangeMode){
            IChangeMode<Enum> changeMode = (IChangeMode<Enum>) te;
            IModeInfo<Enum> modeInfo = (IModeInfo<Enum>) te;
            tag.setString(NBT.MODE_NAME,
                    modeInfo.getColorForMode(changeMode.getCurrentMode()) + modeInfo.getCurrentModeName()
                            + TextFormatting.RESET);
        }
        return tag;
    }
}
