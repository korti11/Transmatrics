package at.korti.transmatrics.fluid;

import at.korti.transmatrics.api.Constants.Mod;
import at.korti.transmatrics.util.helper.TextHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

/**
 * Created by Korti on 31.03.2016.
 */
public class MoltenMetal extends Fluid {

    public static final ResourceLocation ICON_FLUID_STILL = new ResourceLocation(Mod.MODID, "blocks/molten_metal_still");
    public static final ResourceLocation ICON_FLUID_FLOWING = new ResourceLocation(Mod.MODID, "blocks/molten_metal_flowing");

    private final int color;

    public MoltenMetal(String fluidName, int color, int temperture) {
        this(fluidName, color, temperture, ICON_FLUID_STILL, ICON_FLUID_FLOWING);
    }

    public MoltenMetal(String fluidName, int color, int temperture, ResourceLocation still, ResourceLocation flowing) {
        super(fluidName, still, flowing);

        this.setTemperature(temperture);

        if (((color >> 24) & 0xFF) == 0) {
            color |= 0xFF << 24;
        }
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }

}
