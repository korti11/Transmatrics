package at.korti.transmatrics.fluid;

import at.korti.transmatrics.api.Constants.Mod;
import at.korti.transmatrics.util.helper.TextHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

/**
 * Created by Korti on 04.03.2016.
 */
public class GasColored extends Fluid {

    public static final ResourceLocation ICON_FLUID_STILL = new ResourceLocation(Mod.MODID, "blocks/FluidStill");
    public static final ResourceLocation ICON_FLUID_FLOWING = new ResourceLocation(Mod.MODID, "blocks/FluidFlowing");

    private final int color;

    public GasColored(String fluidName, int color) {
        this(fluidName, color, ICON_FLUID_STILL, ICON_FLUID_FLOWING);
    }

    public GasColored(String fluidName, int color, ResourceLocation still, ResourceLocation flowing) {
        super(fluidName, still, flowing);

        this.setGaseous(true);
        this.setDensity(-1000);

        if (((color >> 24) & 0xFF) == 0) {
            color |= 0xFF << 24;
        }
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public String getLocalizedName(FluidStack stack) {
        String s = this.getUnlocalizedName();
        return s == null ? "" : TextHelper.localize(s + ".name");
    }
}
