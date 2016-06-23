package at.korti.transmatrics.client.util;

import at.korti.transmatrics.client.renderer.FluidRenderer;
import at.korti.transmatrics.client.renderer.FluidRenderer.FluidType;
import at.korti.transmatrics.util.helper.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
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

    public static void drawGuiFluid(IFluidTankProperties tankInfo, int x, int y, float z, int width, int height) {
        drawGuiFluid(tankInfo.getContents(), x, y, z, width, height, tankInfo.getCapacity());
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

    @SideOnly(Side.CLIENT)
    public static void renderBlockBoundary(World worldIn, EntityPlayer player, BlockPos pos, int color, float partialTicks){

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        setGLColorFromInt(color);
        GL11.glLineWidth(2.0F);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        IBlockState state = worldIn.getBlockState(pos);

        double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)partialTicks;
        double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)partialTicks;
        double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)partialTicks;
        RenderGlobal.drawSelectionBoundingBox(state.getSelectedBoundingBox(worldIn, pos).expand(0.0020000000949949026D, 0.0020000000949949026D, 0.0020000000949949026D).offset(-d0, -d1, -d2));

        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

}
