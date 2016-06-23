package at.korti.transmatrics.item.energy;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.ToolTips;
import at.korti.transmatrics.api.energy.EnergyHandler;
import at.korti.transmatrics.api.energy.IChargeable;
import at.korti.transmatrics.api.energy.IRechargeable;
import at.korti.transmatrics.item.ModItem;
import at.korti.transmatrics.util.helper.TextHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by Korti on 13.05.2016.
 */
public class ItemCapacitor extends ModItem implements IRechargeable{

    public ItemCapacitor(String name, int capacity) {
        super(name);
        this.setMaxDamage(capacity);
        this.setMaxStackSize(1);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, playerIn, tooltip, advanced);

        tooltip.add(TextHelper.localize(ToolTips.CAPACITOR_ENERGY_LABEL));
        int energy = getEnergy(stack);
        energy = energy > 0 ? energy : 0;
        int capacity = getCapacity(stack);
        tooltip.add(TextHelper.localize(ToolTips.CAPACITOR_ENERGY, energy, capacity));
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

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        super.getSubItems(itemIn, tab, subItems);
        subItems.add(new ItemStack(this, 1, this.getMaxDamage()));
    }
}
