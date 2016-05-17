package at.korti.transmatrics.modintegration.tconstruct.block;

import at.korti.transmatrics.api.Constants.GuiIds;
import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.block.crafting.FluidCraftingMachineBlock;
import at.korti.transmatrics.modintegration.tconstruct.tileentity.TileEntityAlloyMixer;
import net.minecraft.block.material.Material;

/**
 * Created by Korti on 12.05.2016.
 */
public class AlloyMixer extends FluidCraftingMachineBlock {

    public AlloyMixer() {
        super(Material.iron, TransmatricsBlock.ALLOY_MIXER.getRegName(), TileEntityAlloyMixer.class,
                GuiIds.ALLOY_MIXER_GUI_ID);
    }
}
