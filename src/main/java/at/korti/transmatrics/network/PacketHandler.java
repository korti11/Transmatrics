package at.korti.transmatrics.network;

import at.korti.transmatrics.api.Constants;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

/**
 * Created by Korti on 25.02.2016.
 */
public class PacketHandler {

    public static final SimpleNetworkWrapper NETWORK_WRAPPER = new SimpleNetworkWrapper(Constants.Mod.MODID);

    public static void init() {

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
