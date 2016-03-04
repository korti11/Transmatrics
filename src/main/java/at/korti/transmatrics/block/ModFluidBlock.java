package at.korti.transmatrics.block;

import at.korti.transmatrics.api.Constants.Mod;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

/**
 * Created by Korti on 04.03.2016.
 */
public class ModFluidBlock extends BlockFluidClassic {

    public ModFluidBlock(Fluid fluid, MapColor mapColor) {
        super(fluid, new MaterialLiquid(mapColor));

        setUnlocalizedName(Mod.MODID + "." + fluid.getName());
    }

}
