package at.korti.transmatrics.item.crafting;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.client.util.KeyHelper;
import at.korti.transmatrics.util.helper.TextHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

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
        NBTTagCompound electronicParts = stack.getSubCompound(Constants.NBT.ELECTRONIC_PARTS, false);
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
}
