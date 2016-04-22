package at.korti.transmatrics.api.electronic;

import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by Korti on 22.04.2016.
 */
public interface IElectronicPartStorage {

    void addElectronicPart(ItemStack part);

    void addElectronicParts(List<ItemStack> itemStacks);

    List<ItemStack> getElectronicParts();

    void updateStorage();

    void clearStorage();

    int countElectronicParts();

}
