package at.korti.transmatrics.item.energy;

import at.korti.transmatrics.api.Constants.ToolTips;
import at.korti.transmatrics.item.ModEnergyItem;
import at.korti.transmatrics.util.helper.TextHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Korti on 13.05.2016.
 */
public class ItemCapacitor extends ModEnergyItem {

    public ItemCapacitor(String name, int capacity) {
        super(name, capacity);
        this.setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(TextHelper.localize(ToolTips.CAPACITOR_ENERGY_LABEL));
        int energy = getEnergyStored(stack);
        energy = energy > 0 ? energy : 0;
        int capacity = getMaxEnergyStored(stack);
        tooltip.add(TextHelper.localize(ToolTips.CAPACITOR_ENERGY, energy, capacity));
    }

    @Override
    @SuppressWarnings("deprecation")
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if(this.isInCreativeTab(tab)) {
            items.add(new ItemStack(this));
            ItemStack chargedCapacitor = new ItemStack(this);
            this.setEnergy(chargedCapacitor, getMaxEnergyStored(chargedCapacitor));
            items.add(chargedCapacitor);
        }
    }
}
