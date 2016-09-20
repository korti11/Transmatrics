package at.korti.transmatrics.network;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.network.message.CreateQuantumIdMessage;
import at.korti.transmatrics.util.helper.MessageHelper;
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
        NETWORK_WRAPPER.registerMessage(MessageHelper.PacketStatusMessage.PacketHandler.class, MessageHelper.PacketStatusMessage.class, id++, Side.CLIENT);
        NETWORK_WRAPPER.registerMessage(CreateQuantumIdMessage.MessageHandler.class, CreateQuantumIdMessage.class, id++, Side.SERVER);
    }

    public static void sendToAllAround(IMessage message, TileEntity tileEntity, int range) {
        NETWORK_WRAPPER.sendToAllAround(message,
                new NetworkRegistry.TargetPoint(tileEntity.getWorld().provider.getDimension(),
                        tileEntity.getPos().getX(), tileEntity.getPos().getY(), tileEntity.getPos().getZ(),
                        range));
    }

    public static void sendToAllAround(IMessage message, TileEntity tileEntity) {
        sendToAllAround(message, tileEntity, 64);
    }

    public static void sendTo(IMessage message, EntityPlayerMP player) {
        NETWORK_WRAPPER.sendTo(message, player);
    }

    public static void sendToServer(IMessage message) {
        NETWORK_WRAPPER.sendToServer(message);
    }
}
