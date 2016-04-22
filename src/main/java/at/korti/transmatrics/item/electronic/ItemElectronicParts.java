package at.korti.transmatrics.item.electronic;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.TransmatricsItem;
import at.korti.transmatrics.api.electronic.IElectronicPart;
import at.korti.transmatrics.item.ModMetaItem;
import at.korti.transmatrics.util.helper.TextHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by Korti on 20.04.2016.
 */
public class ItemElectronicParts extends ModMetaItem implements IElectronicPart{

    public static final String[] extensions = {"inductance", "capacitance", "heatSink"};

    public ItemElectronicParts() {
        super(TransmatricsItem.ELECTRONIC_PARTS.getRegName(), extensions);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, playerIn, tooltip, advanced);
        tooltip.add(TextHelper.localize(String.format(Constants.ToolTips.ELECTRONIC_PART_META, stack.getItemDamage()),
                getImprovementValue(stack.getItemDamage())));
    }

    @Override
    public int getImprovementValue(int meta) {
        switch (meta){
            case 0:     // transfer rate improvement of 100 TF/t
                return 100;
            case 1:     // capacity improvement of 500 TF
                return 500;
            case 2:     // max efficiency improvement
                return 1;
            default:
                return 0;
        }
    }
}
