package at.korti.transmatrics.client.util;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.TransmatricsApi;
import at.korti.transmatrics.item.tool.ItemConnector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by Korti on 19.06.2016.
 */
public class ClientEventHandler {

    @SubscribeEvent
    public void renderBlockBoundry(RenderWorldLastEvent event) {
        Minecraft minecraft = Minecraft.getMinecraft();
        EntityPlayerSP player = minecraft.thePlayer;
        World world = player.worldObj;
        ItemStack currentItem = player.getCurrentEquippedItem();

        if (currentItem.getItem() == TransmatricsApi.getItem(Constants.TransmatricsItem.CONNECTOR)) {
            ItemConnector connector = (ItemConnector) currentItem.getItem();
            if (connector.hasNetworkNodeStored(currentItem)) {
                NBTTagCompound tagCompound = currentItem.getTagCompound();
                int x = tagCompound.getInteger(Constants.NBT.NETWORK_X);
                int y = tagCompound.getInteger(Constants.NBT.NETWORK_Y);
                int z = tagCompound.getInteger(Constants.NBT.NETWORK_Z);
                RenderHelper.renderBlockBoundary(world, player, new BlockPos(x, y, z), 0xffff1a, event.partialTicks);
            }
        }
    }

}
