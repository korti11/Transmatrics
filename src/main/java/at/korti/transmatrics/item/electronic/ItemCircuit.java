package at.korti.transmatrics.item.electronic;

import at.korti.transmatrics.api.Constants.NBT;
import at.korti.transmatrics.api.Constants.ToolTips;
import at.korti.transmatrics.api.Constants.TransmatricsItem;
import at.korti.transmatrics.item.NBTColoredMetaItem;
import at.korti.transmatrics.util.helper.TextHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;
import java.util.Set;

/**
 * Created by Korti on 20.04.2016.
 */
public class ItemCircuit extends NBTColoredMetaItem {

    public static final String[] extensions = {"redstone", "quartz", "ender"};
    private static final Integer[] boardColors = {0x720000, 0xcdc1b3, 0x0b4d42};
    public static final Integer[] conductionColors = {0xef7e0c, 0xc1dede, 0x8cf4e2};
    private static final int maxParts = 10;

    public ItemCircuit() {
        super(TransmatricsItem.CIRCUIT.getRegName(), extensions);
        addColors(boardColors, 0);
        addColors(conductionColors, 1);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, playerIn, tooltip, advanced);
        NBTTagCompound electronicParts = stack.getSubCompound(NBT.ELECTRONIC_PARTS, false);
        if (electronicParts != null) {
            tooltip.add(TextHelper.localize(ToolTips.ELECTRONIC_PARTS));
            Set<String> keys = electronicParts.getKeySet();
            for (String key : keys) {
                ItemStack part = ItemStack.loadItemStackFromNBT(electronicParts.getCompoundTag(key));
                Item item = part.getItem();
                tooltip.add(TextHelper.localize(ToolTips.ELECTRONIC_PART, part.stackSize,
                        TextHelper.localize(item.getUnlocalizedName(part))));
            }
        }
    }

    public boolean addElectronicPart(ItemStack circuit, ItemStack electronicPart) {
        NBTTagCompound electronicParts = circuit.getSubCompound(NBT.ELECTRONIC_PARTS, false);
        if (electronicParts == null) {
            electronicParts = circuit.getSubCompound(NBT.ELECTRONIC_PARTS, true);
        }

        if(countParts(circuit) + electronicPart.stackSize <= maxParts) {
            String key = electronicPart.getItem().getRegistryName();
            ItemStack stack;
            if (electronicParts.hasKey(key)) {
                stack = ItemStack.loadItemStackFromNBT(electronicParts.getCompoundTag(key));
                stack.stackSize += electronicPart.stackSize;
            } else {
                stack = electronicPart;
            }
            NBTTagCompound compound = electronicParts.getCompoundTag(key);
            stack.writeToNBT(compound);
            electronicParts.setTag(key, compound);
            return true;
        }
        return false;
    }

    public int countParts(ItemStack circuit) {
        NBTTagCompound electronicParts = circuit.getSubCompound(NBT.ELECTRONIC_PARTS, false);
        int parts = 0;
        if (electronicParts != null) {
            Set<String> keys = electronicParts.getKeySet();
            for (String key : keys) {
                ItemStack stack = ItemStack.loadItemStackFromNBT(electronicParts.getCompoundTag(key));
                parts += stack.stackSize;
            }
        }
        return parts;
    }
}
