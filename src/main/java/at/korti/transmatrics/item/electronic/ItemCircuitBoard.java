package at.korti.transmatrics.item.electronic;

import at.korti.transmatrics.Transmatrics;
import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.NBT;
import at.korti.transmatrics.api.Constants.TransmatricsItem;
import at.korti.transmatrics.item.NBTColoredMetaItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

/**
 * Created by Korti on 16.04.2016.
 */
public class ItemCircuitBoard extends NBTColoredMetaItem {

    public static final String[] extensions = {"redstone", "quartz", "ender"};
    private static final Integer[] boardColors = {0x720000, 0xcdc1b3, 0x0b4d42};
    public static final Integer[] conductionColors = {0xef7e0c, 0xc1dede, 0x8cf4e2};
    public static final ItemStack[] conductionItems = {new ItemStack(TransmatricsItem.INGOT.getItem()),
            new ItemStack(TransmatricsItem.INGOT.getItem(), 1, 2), new ItemStack(Items.diamond)};

    public ItemCircuitBoard() {
        super(TransmatricsItem.CIRCUIT_BOARDER.getRegName(), extensions);
        addColors(boardColors, 0);
        addColors(conductionColors, 1);
    }

    public void addConductionItem(ItemStack circuitBoard, ItemStack conductionItem) {
        NBTTagCompound conductionNBT = circuitBoard.getSubCompound(NBT.CONDUCTION_ITEM, true);
        conductionItem.writeToNBT(conductionNBT);
    }

    public ItemStack getConductionItem(ItemStack circuitBoard) {
        NBTTagCompound conductionNBT = circuitBoard.getSubCompound(NBT.CONDUCTION_ITEM, false);
        return ItemStack.loadItemStackFromNBT(conductionNBT);
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        super.getSubItems(itemIn, tab, subItems);
        for (int i = 0; i < subItems.size(); i++) {
            ItemStack tempStack = subItems.get(i);
            if(tempStack.getItem() instanceof ItemCircuitBoard) {
                this.addConductionItem(tempStack, conductionItems[tempStack.getItemDamage()].copy());
            }
        }
    }
}
