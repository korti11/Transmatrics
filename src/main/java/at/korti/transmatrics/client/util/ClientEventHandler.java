package at.korti.transmatrics.client.util;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.TransmatricsApi;
import at.korti.transmatrics.api.network.INetworkNode;
import at.korti.transmatrics.api.network.INetworkSwitch;
import at.korti.transmatrics.item.tool.ItemConnector;
import at.korti.transmatrics.item.tool.ItemConnector.ConnectorMode;
import at.korti.transmatrics.util.helper.InventoryHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Korti on 19.06.2016.
 */
public class ClientEventHandler {

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void renderBlockBoundary(RenderWorldLastEvent event) {
        Minecraft minecraft = Minecraft.getMinecraft();
        EntityPlayerSP player = minecraft.player;
        World world = player.world;
        ItemStack currentItem = InventoryHelper.getStackFromHandForItem(player, Constants.TransmatricsItem.CONNECTOR.getItem());

        if (currentItem != null) {
            ItemConnector connector = (ItemConnector) currentItem.getItem();
            if (connector.hasNetworkNodeStored(currentItem)) {
                NBTTagCompound tagCompound = currentItem.getTagCompound();
                int x = tagCompound.getInteger(Constants.NBT.NETWORK_X);
                int y = tagCompound.getInteger(Constants.NBT.NETWORK_Y);
                int z = tagCompound.getInteger(Constants.NBT.NETWORK_Z);
                BlockPos pos = new BlockPos(x, y, z);
                RenderHelper.renderBlockBoundary(world, player, pos, 0xffff1a, event.getPartialTicks());

                if (connector.getCurrentMode(currentItem) == ConnectorMode.DISCONNECT ||
                        connector.getCurrentMode(currentItem) == ConnectorMode.SHOW_CONNECTION) {
                    TileEntity te = world.getTileEntity(pos);
                    if (te instanceof INetworkNode) {
                        INetworkNode node = (INetworkNode) te;
                        List<INetworkNode> connections;
                        if (node instanceof INetworkSwitch) {
                            connections = ((INetworkSwitch) node).getConnections();
                        } else {
                            connections = Arrays.asList(node.getConnection());
                        }
                        for (INetworkNode networkNode : connections) {
                            if (networkNode instanceof TileEntity) {
                                BlockPos tePos = ((TileEntity) networkNode).getPos();
                                RenderHelper.renderBlockBoundary(world, player, tePos, 0x00ff00, event.getPartialTicks());
                            }
                        }
                    }
                }
            }
        }
    }

}
