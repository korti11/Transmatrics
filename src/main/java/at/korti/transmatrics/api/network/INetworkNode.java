package at.korti.transmatrics.api.network;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Korti on 06.03.2016.
 */
public interface INetworkNode {

    /**
     * Connect to the given network node.
     * @param node Network node that want to get connected.
     * @return If the node is successful connected.
     */
    IOperationMessage connectToNode(INetworkNode node);

    /**
     * Disconnect from the given network node.
     * @param node Network node that want to get disconnected.
     * @return If the node is successful disconnected.
     */
    IOperationMessage disconnectFromNode(INetworkNode node);

    void writeNodeToNBT(NBTTagCompound tagCompound);

    void readNodeFromNBT(NBTTagCompound tagCompound);

    void writeSelfToNBT(NBTTagCompound tagCompound);

    /**
     * Disconnect from the connected network node.
     * @return If the node is successful disconnected.
     */
    IOperationMessage disconnectFromNode();

    /**
     * @return Get the connected network node.
     */
    INetworkNode getConnection();

}
