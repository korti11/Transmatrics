package at.korti.transmatrics.item.energy;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.NBT;
import at.korti.transmatrics.api.energy.EnergyHandler;
import at.korti.transmatrics.api.energy.IRechargeable;
import at.korti.transmatrics.item.ModItem;
import at.korti.transmatrics.util.helper.ItemStackHelper;
import at.korti.transmatrics.util.helper.TextHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

/**
 * Created by Korti on 17.07.2016.
 */
public abstract class EnergyItem extends ModItem implements IRechargeable {

    protected final boolean tooltip;

    public EnergyItem(String name, TextFormatting nameColor, int maxCapacity, boolean tooltip) {
        super(name, nameColor);
        this.setMaxDamage(maxCapacity);
        this.tooltip = tooltip;
    }

    public EnergyItem(String name, int maxCapacity, boolean tooltip) {
        this(name, TextFormatting.WHITE, maxCapacity, tooltip);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, playerIn, tooltip, advanced);

        if(this.tooltip) {
            tooltip.add(TextHelper.localize(Constants.ToolTips.CAPACITOR_ENERGY_LABEL));
            int energy = getEnergy(stack);
            energy = energy > 0 ? energy : 0;
            int capacity = getCapacity(stack);
            tooltip.add(TextHelper.localize(Constants.ToolTips.CAPACITOR_ENERGY, energy, capacity));
        }
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
        NBTTagCompound tagCompound = ItemStackHelper.getCompound(stack);
        tagCompound.setInteger(NBT.ENERGY, damage);
        if (tagCompound.getInteger(NBT.ENERGY) < 0) {
            tagCompound.setInteger(NBT.ENERGY, 0);
        }
    }

    @Override
    public int getDamage(ItemStack stack) {
        NBTTagCompound tagCompound = ItemStackHelper.getCompound(stack);
        return tagCompound.getInteger(NBT.ENERGY);
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        NBTTagCompound tagCompound = ItemStackHelper.getCompound(stack);
        return tagCompound.getInteger(NBT.ENERGY) > 0;
    }

    @Override
    public int charge(ItemStack stack, int energy, boolean simulate) {
        return EnergyHandler.storeEnergy(stack, energy, simulate);
    }

    @Override
    public int discharge(ItemStack stack, int energy, boolean simulate) {
        return EnergyHandler.extractEnergy(stack, energy, simulate);
    }

    @Override
    public int getEnergy(ItemStack stack) {
        return stack.getMaxDamage() - stack.getItemDamage();
    }

    @Override
    public int getCapacity() {
        return this.getMaxDamage();
    }

    @Override
    public int getCapacity(ItemStack stack) {
        return getCapacity();
    }
}
