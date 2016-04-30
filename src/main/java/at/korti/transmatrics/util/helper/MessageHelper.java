package at.korti.transmatrics.util.helper;

import at.korti.transmatrics.api.network.IStatusMessage;
import at.korti.transmatrics.network.TransmatricsPacketHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by Korti on 07.03.2016.
 */
public class MessageHelper {

    private static final int ID = 2424242;
    private static int last;

    private static void sendMessage(ITextComponent[] messages) {
        GuiNewChat guiChat = Minecraft.getMinecraft().ingameGUI.getChatGUI();
        for (int i = ID + messages.length; i <= last; i++) {
            guiChat.deleteChatLine(i);
        }
        for (int i = 0; i < messages.length; i++) {
            guiChat.printChatMessageWithOptionalDeletion(messages[i], ID + i);
        }
        last = ID + messages.length - 1;
    }

    public static ITextComponent messageToComponent(IStatusMessage message) {
        return new TextComponentString(message.getMessage());
    }

    public static ITextComponent[] messagesToComponents(IStatusMessage... messages) {
        ITextComponent[] components = new ITextComponent[messages.length];
        for (int i = 0; i < components.length; i++) {
            components[i] = messageToComponent(messages[i]);
        }
        return components;
    }

    public static void sendMessages(EntityPlayer player, IStatusMessage... messages) {
        if (player instanceof EntityPlayerMP) {
            sendMessages((EntityPlayerMP)player, messagesToComponents(messages));
        }
    }

    public static void sendMessages(EntityPlayerMP player, ITextComponent... messages) {
        if (messages.length > 0) {
            TransmatricsPacketHandler.sendTo(new PacketStatusMessage(messages), player);
        }
    }

    public static class PacketStatusMessage implements IMessage{

        private ITextComponent[] messages;

        public PacketStatusMessage() {
            messages = new ITextComponent[0];
        }

        public PacketStatusMessage(ITextComponent... messages) {
            this.messages = messages;
        }

        @Override
        public void fromBytes(ByteBuf buf) {
            messages = new ITextComponent[buf.readInt()];
            for (int i = 0; i < messages.length; i++) {
                messages[i] = ITextComponent.Serializer.jsonToComponent(ByteBufUtils.readUTF8String(buf));
            }
        }

        @Override
        public void toBytes(ByteBuf buf) {
            buf.writeInt(messages.length);
            for (ITextComponent component : messages) {
                ByteBufUtils.writeUTF8String(buf, ITextComponent.Serializer.componentToJson(component));
            }
        }

        public static class PacketHandler implements IMessageHandler<PacketStatusMessage, IMessage> {

            @Override
            public IMessage onMessage(PacketStatusMessage message, MessageContext ctx) {
                sendMessage(message.messages);
                return null;
            }
        }
    }

}
