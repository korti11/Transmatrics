package at.korti.transmatrics.util.helper;

import at.korti.transmatrics.network.TransmatricsPacketHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Used source code of WayOfTime as help.
 * https://github.com/WayofTime/BloodMagic/blob/1.8/src/main/java/WayofTime/bloodmagic/util/ChatUtil.java
 */
public class ChatHelper {

    private static final int DELETION_ID = 2424242;
    private static int lastAdded;

    private static void sendNoSpamMessages(IChatComponent[] messages) {
        GuiNewChat chat = Minecraft.getMinecraft().ingameGUI.getChatGUI();
        for (int i = DELETION_ID + messages.length - 1; i <= lastAdded; i++) {
            chat.deleteChatLine(i);
        }
        for (int i = 0; i < messages.length; i++) {
            chat.printChatMessageWithOptionalDeletion(messages[i], DELETION_ID + i);
        }
        lastAdded = DELETION_ID + messages.length - 1;
    }

    public static IChatComponent wrap(String string) {
        return new ChatComponentText(string);
    }

    public static IChatComponent[] wrap(String... lines) {
        IChatComponent[] components = new IChatComponent[lines.length];
        for (int i = 0; i < components.length; i++) {
            components[i] = wrap(lines[i]);
        }
        return components;
    }

    public static void sendNoSpam(EntityPlayer player, String... message) {
        if (player instanceof EntityPlayerMP) {
            sendNoSpam((EntityPlayerMP) player, wrap(message));
        }
    }

    public static void sendNoSpam(EntityPlayerMP playerMP, IChatComponent... lines) {
        if (lines.length > 0) {
            TransmatricsPacketHandler.sendTo(new PacketNoSpamChat(lines), playerMP);
        }
    }

    public static class PacketNoSpamChat implements IMessage {
        private IChatComponent[] chatLines;

        public PacketNoSpamChat() {
            chatLines = new IChatComponent[0];
        }

        public PacketNoSpamChat(IChatComponent... lines) {
            this.chatLines = lines;
        }

        @Override
        public void fromBytes(ByteBuf buf) {
            chatLines = new IChatComponent[buf.readInt()];
            for (int i = 0; i < chatLines.length; i++) {
                chatLines[i] = IChatComponent.Serializer.jsonToComponent(ByteBufUtils.readUTF8String(buf));
            }
        }

        @Override
        public void toBytes(ByteBuf buf) {
            buf.writeInt(chatLines.length);
            for (IChatComponent component : chatLines) {
                ByteBufUtils.writeUTF8String(buf, IChatComponent.Serializer.componentToJson(component));
            }
        }

        public static class Handler implements IMessageHandler<PacketNoSpamChat, IMessage> {

            @Override
            public IMessage onMessage(PacketNoSpamChat message, MessageContext ctx) {
                sendNoSpamMessages(message.chatLines);
                return null;
            }
        }
    }

}
