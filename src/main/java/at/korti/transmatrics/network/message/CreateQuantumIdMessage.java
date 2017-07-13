package at.korti.transmatrics.network.message;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.network.quantum.QuantumBridgeHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

/**
 * @author Korti
 * @since 20.09.2016
 */
public class CreateQuantumIdMessage implements IMessage {

    private int slot;

    public CreateQuantumIdMessage() {
        this.slot = -1;
    }

    public CreateQuantumIdMessage(int slot) {
        this.slot = slot;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.slot = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(slot);
    }

    public static class MessageHandler implements IMessageHandler<CreateQuantumIdMessage, IMessage> {

        @Override
        public IMessage onMessage(CreateQuantumIdMessage message, MessageContext ctx) {
            if (ctx.side == Side.SERVER && message.slot != -1) {
                EntityPlayer player = ctx.getServerHandler().player;
                ItemStack stack = player.inventory.getStackInSlot(message.slot);
                NBTTagCompound compound;
                if(stack != null) {
                    if ((compound = stack.getTagCompound()) == null) {
                        stack.setTagCompound(compound = new NBTTagCompound());
                    }
                    if (!compound.hasKey(Constants.NBT.QUANTUM_BRIDGE_MAP_NAME)) {
                        compound.setString(Constants.NBT.QUANTUM_BRIDGE_MAP_NAME, QuantumBridgeHandler.createQuantumBridge());
                    }
                }
            }
            return null;
        }

    }

}
