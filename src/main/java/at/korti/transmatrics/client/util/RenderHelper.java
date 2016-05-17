package at.korti.transmatrics.client.util;

import at.korti.transmatrics.client.renderer.FluidRenderer;
import at.korti.transmatrics.client.renderer.FluidRenderer.FluidType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

/**
 * Created by Korti on 01.04.2016.
 */
public class RenderHelper {

    @SideOnly(Side.CLIENT)
    public static void drawGuiFluid(FluidStack stack, int x, int y, float z, int width, int height, int maxCapacity) {
        if (stack == null || stack.getFluid() == null) {
            return;
        }

        TextureAtlasSprite sprite = FluidRenderer.getFluidTexture(stack, FluidType.STILL);

        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        setGLColorFromInt(stack.getFluid().getColor());

        int fullX = width / 16;
        int fullY = height / 16;
        int lastX = width - fullX * 16;
        int lastY = height - fullY * 16;
        int amount = stack.amount * height / maxCapacity;
        int fullAmount = (height - amount) / 16;
        int lastAmount = (height - amount) - fullAmount * 16;

        for (int i = 0; i < fullX; i++) {
            for (int j = 0; j < fullY; j++) {
                if (j >= fullAmount) {
                    renderCutIcon(sprite, x + i * 16, y + j * 16, z, 16, 16, j == fullAmount ? lastAmount : 0);
                }
            }
        }
        for (int i = 0; i < fullX; i++) {
            renderCutIcon(sprite, x + i * 16, y + fullY * 16, z, 16, lastY, fullAmount == fullY ? lastAmount : 0);
        }
        for (int i = 0; i < fullY; i++) {
            if (i >= fullAmount) {
                renderCutIcon(sprite, x + fullX * 16, y + i * 16, z, lastX, 16, i == fullAmount ? lastAmount : 0);
            }
        }
        renderCutIcon(sprite, x + fullX * 16, y + fullY * 16, z, lastX, lastY, fullAmount == fullY ? lastAmount : 0);
        GlStateManager.color(1, 1, 1, 1);
    }

    public static void drawGuiFluid(FluidTankInfo tankInfo, int x, int y, float z, int width, int height) {
        drawGuiFluid(tankInfo.fluid, x, y, z, width, height, tankInfo.capacity);
    }

    private static void renderCutIcon(TextureAtlasSprite sprite, int x, int y, float z, int width, int height, int cut) {
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer renderer = tessellator.getBuffer();
        renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        vertexUV(renderer, x, y + height, z, sprite.getMinU(), sprite.getInterpolatedV(height));
        vertexUV(renderer, x + width, y + height, z, sprite.getInterpolatedU(width), sprite.getInterpolatedV(height));
        vertexUV(renderer, x + width, y + cut, z, sprite.getInterpolatedU(width), sprite.getInterpolatedV(cut));
        vertexUV(renderer, x, y + cut, z, sprite.getMinU(), sprite.getInterpolatedV(cut));
        tessellator.draw();
    }

    private static void vertexUV(VertexBuffer renderer, double x, double y, double z, double u, double v) {
        renderer.pos(x, y, z).tex(u, v).endVertex();
    }

    @SideOnly(Side.CLIENT)
    public static void setGLColorFromInt(int color){
        float red = (color >> 16 & 0xFF) / 255.0F;
        float green = (color >> 8 & 0xFF) / 255.0F;
        float blue = (color & 0xFF) / 255.0F;

        GlStateManager.color(red, green, blue, 1.0F);
    }

}
