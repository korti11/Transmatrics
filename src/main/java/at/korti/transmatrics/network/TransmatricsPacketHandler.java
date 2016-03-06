package at.korti.transmatrics.network;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.util.helper.ChatHelper;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by Korti on 25.02.2016.
 */
public class TransmatricsPacketHandler {

    public static final SimpleNetworkWrapper NETWORK_WRAPPER = new SimpleNetworkWrapper(Constants.Mod.MODID);
    private static int id = 0;

    public static void init() {
        NETWORK_WRAPPER.registerMessage(ChatHelper.PacketNoSpamChat.Handler.class, ChatHelper.PacketNoSpamChat.class, id++, Side.CLIENT);
    }

    public static void sendToAllAround(IMessage message, TileEntity tileEntity, int range) {
        NETWORK_WRAPPER.sendToAllAround(message,
                new NetworkRegistry.TargetPoint(tileEntity.getWorld().provider.getDimensionId(),
                        tileEntity.getPos().getX(), tileEntity.getPos().getY(), tileEntity.getPos().getZ(),
                        range));
    }

    public static void sendToAllAround(IMessage message, TileEntity tileEntity) {
        sendToAllAround(message, tileEntity, 64);
    }

    public static void sendTo(IMessage message, EntityPlayerMP player) {
        NETWORK_WRAPPER.sendTo(message, player);
    }
}
