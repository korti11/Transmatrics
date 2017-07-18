package at.korti.transmatrics.modintegration.jei.pulverizer;

import at.korti.transmatrics.registry.crafting.PulverizerCraftingRegistry;
import at.korti.transmatrics.util.helper.TextHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Korti on 14.04.2016.
 */
public class PulverizerRecipeJEI implements IRecipeWrapper {

    private final ItemStack input;

    private final List<ItemStack> outputs;
    private final ItemStack secondaryOutput;
    private final float secondOutputChance;

    public PulverizerRecipeJEI(PulverizerCraftingRegistry.PulverizerCraftingEntry craftingEntry) {
        this.input = craftingEntry.getInputs()[0];
        this.outputs = new LinkedList<>();
        this.outputs.add(craftingEntry.getOutputs()[0]);
        if(craftingEntry.getOutputs().length > 1) {
            this.secondaryOutput = craftingEntry.getOutputs()[1].copy();
            this.outputs.add(this.secondaryOutput);
            secondOutputChance = craftingEntry.getOutputChances()[2];
        } else {
            secondaryOutput = null;
            secondOutputChance = 0;
        }
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(ItemStack.class, input);
        ingredients.setOutputs(ItemStack.class, outputs);
    }

    @SubscribeEvent
    public void addChanceInfomation(ItemTooltipEvent event) {
        if (event.getItemStack().equals(secondaryOutput)) {
            event.getToolTip().add(TextHelper.localize("tooltip.pulverizer.output.chance") + " " + secondOutputChance * 100 + "%");
        }
    }
}
