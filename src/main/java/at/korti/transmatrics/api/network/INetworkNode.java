package at.korti.transmatrics.api.network;

import at.korti.transmatrics.tileentity.network.TileEntityController;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;

/**
 * Created by Korti on 06.03.2016.
 */
public interface INetworkNode {

    /**
     * Connect to the given network node.
     * @param node Network node that want to get connected.
     * @return If the node is successful connected.
     */
    IStatusMessage connectToNode(INetworkNode node, boolean isSecond, boolean simulate);

    /**
     * Disconnect from the given network node.
     * @param node Network node that want to get disconnected.
     * @return If the node is successful disconnected.
     */
    IStatusMessage disconnectFromNode(INetworkNode node, boolean isSecond, boolean simulate);

    void writeNodeToNBT(NBTTagCompound tagCompound);

    void readNodeFromNBT(NBTTagCompound tagCompound);

    void writeSelfToNBT(NBTTagCompound tagCompound);

    /**
     * Disconnect from the connected network node.
     * @return If the node is successful disconnected.
     */
    IStatusMessage disconnectFromNode();

    /**
     * @return Get the connected network node.
     */
    INetworkNode getConnection();

    TileEntityController getController();

    int getConnectionPriority();

    void connectToController(BlockPos controllerPos, int connectionPriority);

    void disconnectFromController();

}
