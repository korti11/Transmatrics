package at.korti.transmatrics.client.gui;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.tileentity.container.ContainerPoweredFurnace;
import at.korti.transmatrics.tileentity.container.ContainerPulverizer;
import at.korti.transmatrics.tileentity.crafting.TileEntityPoweredFurnace;
import at.korti.transmatrics.tileentity.crafting.TileEntityPulverizer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import static at.korti.transmatrics.api.Constants.GuiIds.*;

/**
 * Created by Korti on 15.03.2016.
 */
public class GuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);

        switch (ID) {
            case PULVERIZER_GUI_ID:
                return new ContainerPulverizer(player.inventory, (TileEntityPulverizer) world.getTileEntity(pos));
            case POWERED_FURNACE_GUI_ID:
                return new ContainerPoweredFurnace(player.inventory, (TileEntityPoweredFurnace) world.getTileEntity(pos));
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(world instanceof WorldClient) {
            BlockPos pos = new BlockPos(x, y, z);

            switch (ID) {
                case PULVERIZER_GUI_ID:
                    return new GuiPulverizer(player.inventory, (TileEntityPulverizer) world.getTileEntity(pos));
                case POWERED_FURNACE_GUI_ID:
                    return new GuiPoweredFurnace(player.inventory, (TileEntityPoweredFurnace) world.getTileEntity(pos));
            }
        }
        return null;
    }
}
