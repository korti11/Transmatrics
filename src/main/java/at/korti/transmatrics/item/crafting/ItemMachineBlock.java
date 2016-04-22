package at.korti.transmatrics.item.crafting;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.NBT;
import at.korti.transmatrics.api.electronic.IElectronicPartStorage;
import at.korti.transmatrics.client.util.KeyHelper;
import at.korti.transmatrics.util.helper.TextHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Korti on 22.04.2016.
 */
public class ItemMachineBlock extends ItemBlock {

    public ItemMachineBlock(Block block) {
        super(block);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, playerIn, tooltip, advanced);
        NBTTagCompound electronicParts = stack.getSubCompound(NBT.ELECTRONIC_PARTS, false);
        if (electronicParts != null) {
            tooltip.add(TextHelper.localize(Constants.ToolTips.ELECTRONIC_PARTS));
            if(KeyHelper.isShiftKeyDown()) {
                Set<String> keys = electronicParts.getKeySet();
                for (String key : keys) {
                    ItemStack part = ItemStack.loadItemStackFromNBT(electronicParts.getCompoundTag(key));
                    Item item = part.getItem();
                    tooltip.add(TextHelper.localize(Constants.ToolTips.ELECTRONIC_PART, part.stackSize,
                            TextHelper.localize(item.getUnlocalizedName(part) + ".name")));
                }
            } else {
                tooltip.add(TextHelper.localize(Constants.ToolTips.ELECTRONIC_CIRCUIT_SMALL_INFO));
            }
        }
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {

        boolean flag = super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState);

        NBTTagCompound electronicParts = stack.getSubCompound(NBT.ELECTRONIC_PARTS, false);
        List<ItemStack> stackParts = new LinkedList<>();

        if (electronicParts != null) {
            Set<String> keys = electronicParts.getKeySet();
            for (String key : keys) {
                ItemStack part = ItemStack.loadItemStackFromNBT(electronicParts.getCompoundTag(key));
                stackParts.add(part);
            }
        }

        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof IElectronicPartStorage) {
            IElectronicPartStorage electronicPartStorage = (IElectronicPartStorage) tileEntity;
            electronicPartStorage.addElectronicParts(stackParts);
            electronicPartStorage.updateStorage();
        }

        return flag;
    }
}
