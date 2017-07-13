package at.korti.transmatrics.item.crafting;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;


/**
 * Created by Korti on 22.04.2016.
 */
public class ItemMachineBlock extends ItemBlock {

    public ItemMachineBlock(Block block) {
        super(block);
        this.setRegistryName(block.getRegistryName());
    }
}
