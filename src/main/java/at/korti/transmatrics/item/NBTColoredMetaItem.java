package at.korti.transmatrics.item;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.NBT;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Korti on 16.04.2016.
 */
public abstract class NBTColoredMetaItem extends ModMetaItem {

    private Map<Integer, Integer[]> colorMap;

    public NBTColoredMetaItem(String name, String... extensions) {
        this(name, EnumChatFormatting.WHITE, extensions);
    }

    public NBTColoredMetaItem(String name, EnumChatFormatting nameColor, String... extensions) {
        super(name, nameColor, extensions);
        colorMap = new HashMap<>();
    }

    public void addColors(Integer[] colors, Integer renderPass) {
        colorMap.put(renderPass, colors);
    }

    public void loadMapColors(ItemStack stack) {
        for (Map.Entry<Integer, Integer[]> entry : colorMap.entrySet()) {
            setColorForItemStack(stack, entry.getKey(), entry.getValue()[stack.getItemDamage()]);
        }
    }

    public NBTColoredMetaItem setColorForItemStack(ItemStack stack, int renderPass, int color) {
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
        if(colorLayers.hasKey(String.format(NBT.COLOR_LAYER, renderPass))) {
            return colorLayers.getInteger(String.format(NBT.COLOR_LAYER, renderPass));
        }
        return super.getColorFromItemStack(stack, renderPass);
    }

}
