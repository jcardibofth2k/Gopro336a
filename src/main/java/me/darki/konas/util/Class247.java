package me.darki.konas.util;

import me.darki.konas.*;
import java.awt.Color;

import me.darki.konas.command.commands.fontCommand;
import me.darki.konas.unremaped.CfontRenderer;
import me.darki.konas.unremaped.Class244;
import me.darki.konas.unremaped.Class255;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

public class Class247 {
    public static CfontRenderer smallCFontRenderer = new CfontRenderer(fontCommand.Field1351, 16.0f);
    public static CfontRenderer cfontRenderer = new CfontRenderer(fontCommand.Field1351, 18.0f);

    public static void Method2042(Class244 class244, Class255 class255, float f) {
        Vector2f[] vector2fArray = class244.Method2029();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel((int)7425);
        GlStateManager.glLineWidth((float)f);
        GL11.glEnable((int)2848);
        bufferBuilder.begin(class244.Method1802() ? 2 : 3, DefaultVertexFormats.POSITION_COLOR);
        for (Vector2f vector2f : vector2fArray) {
            Color color = class255.Method1920(class244, vector2f.x, vector2f.y);
            bufferBuilder.pos((double)vector2f.x, (double)vector2f.y, 0.0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        }
        tessellator.draw();
        GL11.glDisable((int)2848);
        GlStateManager.glLineWidth((float)1.0f);
        GlStateManager.shadeModel((int)7424);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void Method2043(Class244 class244, Class255 class255) {
        Vector2f[] vector2fArray = class244.Method2029();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel((int)7425);
        bufferBuilder.begin(9, DefaultVertexFormats.POSITION_COLOR);
        for (Vector2f vector2f : vector2fArray) {
            Color color = class255.Method1920(class244, vector2f.x, vector2f.y);
            bufferBuilder.pos((double)vector2f.x, (double)vector2f.y, 0.0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        }
        tessellator.draw();
        GlStateManager.shadeModel((int)7424);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}