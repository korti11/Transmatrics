package at.korti.transmatrics.modintegration.tconstruct.tools.modifier;

import at.korti.transmatrics.api.Constants.Mod;
import at.korti.transmatrics.api.Constants.NBT;
import at.korti.transmatrics.api.Constants.TConstructModifiers;
import at.korti.transmatrics.api.energy.IRechargeable;
import at.korti.transmatrics.item.energy.ItemCapacitor;
import at.korti.transmatrics.util.helper.TextHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.utils.TinkerUtil;

import java.util.List;

/**
 * Created by Korti on 16.05.2016.
 */
public class ModEnergetic extends ModifierTrait implements IRechargeable{

    private final int capacity;

    public ModEnergetic(ItemCapacitor capacitor) {
        super(TConstructModifiers.ENERGETIC_IDENTIFIER, TConstructModifiers.ENERGETIC_COLOR);
        this.capacity = capacitor.getCapacity();
        this.addItem(new ItemStack(capacitor, 1, OreDictionary.WILDCARD_VALUE), 1, 1);
    }

    @Override
    public int onToolDamage(ItemStack tool, int damage, int newDamage, EntityLivingBase entity) {
        if (discharge(tool, newDamage, false) > 0) {
            return 0;
        }
        return super.onToolDamage(tool, damage, newDamage, entity);
    }

    @Override
    public String getLocalizedName() {
        String key = String.format("modifier.%s.%s", Mod.MODID, getIdentifier());
        return TextHelper.localize(key);
    }

    @Override
    public String getTooltip(NBTTagCompound modifierTag, boolean detailed) {
        if(!detailed) {
            int energy = modifierTag.getInteger(NBT.ENERGY);
            int capacity = getCapacity();
            return String.format("%s (%d/%d TF)", getLocalizedName(), energy, capacity);
        }
        return super.getTooltip(modifierTag, detailed);
    }

    @Override
    public boolean canApplyCustom(ItemStack stack) {
        return !((ToolCore) stack.getItem()).hasCategory(Category.NO_MELEE);
    }

    @Override
    public int charge(ItemStack stack, int energy, boolean simulate) {
        int toStore = Math.min(energy, getCapacity() - getEnergy(stack));
        if (!simulate) {
            NBTTagCompound tag = TinkerUtil.getModifierTag(stack, getIdentifier());
            tag.setInteger(NBT.ENERGY, getEnergy(stack) + toStore);
        }
        return toStore;
    }

    @Override
    public int discharge(ItemStack stack, int energy, boolean simulate) {
        int toExtract = Math.min(energy, getEnergy(stack));
        if (!simulate) {
            NBTTagCompound tag = TinkerUtil.getModifierTag(stack, getIdentifier());
            tag.setInteger(NBT.ENERGY, getEnergy(stack) - toExtract);
        }
        return toExtract;
    }

    @Override
    public int getEnergy(ItemStack stack) {
        NBTTagCompound tag = TinkerUtil.getModifierTag(stack, getIdentifier());
        return tag.getInteger(NBT.ENERGY);
    }

    @Override
    public int getCapacity() {
        return capacity;
    }
}
