package at.korti.transmatrics.item;

import at.korti.transmatrics.api.Constants.NBT;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

import java.util.Map;

/**
 * Created by Korti on 16.04.2016.
 */
public abstract class NBTColoredItem extends ModItem {

    private Map<Integer, Integer> colorMap;

    public NBTColoredItem(String name) {
        super(name);
    }

    public NBTColoredItem(String name, EnumChatFormatting nameColor) {
        super(name, nameColor);
    }

    public void addColors(Integer color, Integer renderPass) {
        colorMap.put(renderPass, color);
    }

    public void loadMapColors(ItemStack stack) {
        for (Map.Entry<Integer, Integer> entry : colorMap.entrySet()) {
            setColorForItemStack(stack, entry.getKey(), entry.getValue());
        }
    }

    public NBTColoredItem setColorForItemStack(ItemStack stack, int renderPass, int color) {
        NBTTagCompound colorLayers;
        if (stack.getTagCompound() == null) {
            stack.setTagCompound(new NBTTagCompound());
            colorLayers = new NBTTagCompound();
            stack.getTagCompound().setTag(NBT.COLOR_LAYERS, colorLayers);
        } else {
            colorLayers = stack.getTagCompound().getCompoundTag(NBT.COLOR_LAYERS);
            if (colorLayers == null || !stack.getTagCompound().hasKey(NBT.COLOR_LAYERS)) {
                colorLayers = new NBTTagCompound();
                stack.getTagCompound().setTag(NBT.COLOR_LAYERS, colorLayers);
            }
        }

        colorLayers.setInteger(String.format(NBT.COLOR_LAYER, renderPass), color);
        return this;
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int renderPass) {
        if (stack.getTagCompound() == null) {
            stack.setTagCompound(new NBTTagCompound());
            loadMapColors(stack);
        } else if (!stack.getTagCompound().hasKey(NBT.COLOR_LAYERS)) {
            loadMapColors(stack);
        }
        NBTTagCompound colorLayers = stack.getTagCompound().getCompoundTag(NBT.COLOR_LAYERS);
        return colorLayers.getInteger(String.format(NBT.COLOR_LAYER, renderPass));
    }
}
