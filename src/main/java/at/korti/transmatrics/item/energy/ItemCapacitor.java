package at.korti.transmatrics.item.energy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by Korti on 13.05.2016.
 */
public class ItemCapacitor extends EnergyItem{

    public ItemCapacitor(String name, int capacity) {
        super(name, capacity, true);
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        super.getSubItems(itemIn, tab, subItems);
        ItemStack stack = new ItemStack(this, 1);
        stack.setItemDamage(this.getMaxDamage());
        subItems.add(stack);
    }
}
