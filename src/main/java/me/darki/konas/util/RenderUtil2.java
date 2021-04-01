package me.darki.konas.util;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class RenderUtil2 {
    public static void Method1336(float f, float f2, float f3, float f4, float f5, int n) {
        float f6 = f + f3;
        float f7 = f2 + f4;
        float f8 = (float)(n >> 24 & 0xFF) / 255.0f;
        float f9 = (float)(n >> 16 & 0xFF) / 255.0f;
        float f10 = (float)(n >> 8 & 0xFF) / 255.0f;
        float f11 = (float)(n & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(f9, f10, f11, f8);
        GL11.glEnable(2848);
        GlStateManager.glLineWidth(f5);
        bufferBuilder.begin(1, DefaultVertexFormats.POSITION);
        bufferBuilder.pos(f, f7, 0.0).endVertex();
        bufferBuilder.pos(f6, f7, 0.0).endVertex();
        bufferBuilder.pos(f6, f7, 0.0).endVertex();
        bufferBuilder.pos(f6, f2, 0.0).endVertex();
        bufferBuilder.pos(f6, f2, 0.0).endVertex();
        bufferBuilder.pos(f, f2, 0.0).endVertex();
        bufferBuilder.pos(f, f2, 0.0).endVertex();
        bufferBuilder.pos(f, f7, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void Method1337(float f, float f2, float f3, float f4, int n, int n2) {
        RenderUtil2.Method1338(f, f2, f3, f4, n2);
        RenderUtil2.Method1336(f, f2, f3, f4, 1.0f, n);
    }

    public static void Method1338(float f, float f2, float f3, float f4, int n) {
        float f5 = f + f3;
        float f6 = f2 + f4;
        float f7 = (float)(n >> 24 & 0xFF) / 255.0f;
        float f8 = (float)(n >> 16 & 0xFF) / 255.0f;
        float f9 = (float)(n >> 8 & 0xFF) / 255.0f;
        float f10 = (float)(n & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(f8, f9, f10, f7);
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferBuilder.pos(f, f6, 0.0).endVertex();
        bufferBuilder.pos(f5, f6, 0.0).endVertex();
        bufferBuilder.pos(f5, f2, 0.0).endVertex();
        bufferBuilder.pos(f, f2, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void Method1339(ScaledResolution scaledResolution, float f, float f2, float f3, float f4) {
        float f5 = f + f3;
        float f6 = f2 + f4;
        int n = scaledResolution.getScaleFactor();
        GL11.glScissor((int)(f * (float)n), (int)(((float)scaledResolution.getScaledHeight() - f6) * (float)n), (int)((f5 - f) * (float)n), (int)((f6 - f2) * (float)n));
    }
}