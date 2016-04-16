package at.korti.transmatrics.modintegration.jei.pulverizer;

import at.korti.transmatrics.registry.crafting.PulverizerCraftingRegistry;
import at.korti.transmatrics.util.helper.TextHelper;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Korti on 14.04.2016.
 */
public class PulverizerRecipeJEI extends BlankRecipeWrapper {

    private final ItemStack input;

    private final ItemStack primaryOutput;
    private final ItemStack secondaryOutput;
    private final float secondOutputChance;

    public PulverizerRecipeJEI(PulverizerCraftingRegistry.PulverizerCraftingEntry craftingEntry) {
        this.input = craftingEntry.getInputs()[0];
        this.primaryOutput = craftingEntry.getOutputs()[0];
        if(craftingEntry.getOutputs().length > 1) {
            this.secondaryOutput = craftingEntry.getOutputs()[1].copy();
            secondOutputChance = craftingEntry.getOutputChances()[2];
        } else {
            secondaryOutput = null;
            secondOutputChance = 0;
        }
    }

    @Nonnull
    @Override
    public List getInputs() {
        return Collections.singletonList(input);
    }

    @Nonnull
    @Override
    public List getOutputs() {
        List<ItemStack> outputs = new LinkedList<>();
        outputs.add(primaryOutput);
        if(secondaryOutput != null) {
            outputs.add(secondaryOutput);
        }
        return outputs;
    }

    @SubscribeEvent
    public void addChanceInfomation(ItemTooltipEvent event) {
        if (event.itemStack.equals(secondaryOutput)) {
            event.toolTip.add(TextHelper.localize("tooltip.pulverizer.output.chance") + " " + secondOutputChance * 100 + "%");
        }
    }
}
