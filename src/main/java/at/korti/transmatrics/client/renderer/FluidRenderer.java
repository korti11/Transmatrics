package at.korti.transmatrics.client.renderer;

import com.google.common.collect.Maps;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Korti on 01.04.2016.
 */
public final class FluidRenderer {

    private static FluidRenderer instance;

    public enum FluidType{
        FLOWING,
        STILL
    }

    private final Map<FluidType, Map<Fluid, TextureAtlasSprite>> textureMap;

    private static TextureAtlasSprite missingIcon = null;

    private FluidRenderer() {
        textureMap = new HashMap<>();
    }

    public static FluidRenderer instance() {
        if (instance == null) {
            instance = new FluidRenderer();
        }

        return instance;
    }

    @SubscribeEvent
    public void setupTextureMap(TextureStitchEvent.Post event) {
        TextureMap map = event.map;
        missingIcon = map.getMissingSprite();

        textureMap.clear();

        for (FluidType type : FluidType.values()) {
            textureMap.put(type, new HashMap<Fluid, TextureAtlasSprite>());
        }

        for (Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
            if (fluid.getFlowing() != null) {
                String flow = fluid.getFlowing().toString();
                TextureAtlasSprite sprite;
                if (map.getTextureExtry(flow) != null) {
                    sprite = map.getTextureExtry(flow);
                } else {
                    sprite = map.registerSprite(fluid.getFlowing());
                }

                textureMap.get(FluidType.FLOWING).put(fluid, sprite);
            }

            if (fluid.getStill() != null) {
                String still = fluid.getStill().toString();
                TextureAtlasSprite sprite;
                if (map.getTextureExtry(still) != null) {
                    sprite = map.getTextureExtry(still);
                } else {
                    sprite = map.registerSprite(fluid.getStill());
                }

                textureMap.get(FluidType.STILL).put(fluid, sprite);
            }
        }
    }

    public static TextureAtlasSprite getFluidTexture(FluidStack stack, FluidType type) {
        if (stack == null) {
            return missingIcon;
        }

        return getFluidTexture(stack.getFluid(), type);
    }

    public static TextureAtlasSprite getFluidTexture(Fluid fluid, FluidType type) {
        if (fluid == null || type == null) {
            return missingIcon;
        }
        Map<Fluid, TextureAtlasSprite> map = instance().textureMap.get(type);
        return map.containsKey(fluid) ? map.get(fluid) : missingIcon;
    }

}
