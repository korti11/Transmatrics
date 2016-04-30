package at.korti.transmatrics.fluid;

import at.korti.transmatrics.api.Constants;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

/**
 * Created by Korti on 04.03.2016.
 */
public class FluidStateMapper extends StateMapperBase implements ItemMeshDefinition {

    public final Fluid fluid;
    public final ModelResourceLocation location;

    public FluidStateMapper(Fluid fluid) {
        this.fluid = fluid;

        this.location = new ModelResourceLocation(new ResourceLocation(Constants.Mod.MODID, "ModFluid"), fluid.getName());
    }

    @Override
    public ModelResourceLocation getModelLocation(ItemStack stack) {
        return location;
    }

    @Override
    protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
        return location;
    }
}
