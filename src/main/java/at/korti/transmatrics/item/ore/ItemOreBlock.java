package at.korti.transmatrics.item.ore;

import at.korti.transmatrics.Transmatrics;
import at.korti.transmatrics.api.Constants;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

/**
 * Created by Korti on 19.03.2016.
 */
public class ItemOreBlock extends ItemBlock {

    public ItemOreBlock(Block block) {
        super(block);

        this.setHasSubtypes(true);
        this.setRegistryName(block.getRegistryName());
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        String extension;
        switch (stack.getItemDamage()) {
            case 0:
                extension = "copper";
                break;
            case 1:
                extension = "tin";
                break;
            case 2:
                extension = "silver";
                break;
            case 3:
                extension = "lead";
                break;
            default:
                extension = "unknown";
                break;
        }
        return super.getUnlocalizedName(stack) + "." + extension;
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }
}
