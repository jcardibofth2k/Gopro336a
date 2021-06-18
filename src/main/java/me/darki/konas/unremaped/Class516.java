package me.darki.konas.unremaped;

import java.awt.Color;
import java.util.Collection;
import java.util.Collections;

import me.darki.konas.mixin.mixins.IRenderManager;
import me.darki.konas.util.RainbowUtil;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Sphere;

public class Class516 {
    public static Minecraft Field1284 = Minecraft.getMinecraft();
    public static Frustum Field1285 = new Frustum();

    public static void Method1257(Entity entity, int n, float f) {
        block0: {
            IRenderManager iRenderManager = (IRenderManager)Field1284.getRenderManager();
            double d = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)f - iRenderManager.getRenderPosX();
            double d2 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)f - iRenderManager.getRenderPosY();
            double d3 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)f - iRenderManager.getRenderPosZ();
            AxisAlignedBB axisAlignedBB = entity.getEntityBoundingBox();
            AxisAlignedBB axisAlignedBB2 = new AxisAlignedBB(axisAlignedBB.minX - entity.posX + d, axisAlignedBB.minY - entity.posY + d2, axisAlignedBB.minZ - entity.posZ + d3, axisAlignedBB.maxX - entity.posX + d, axisAlignedBB.maxY - entity.posY + d2, axisAlignedBB.maxZ - entity.posZ + d3);
            if (entity == Class516.Field1284.player) break block0;
            float[] fArray = RainbowUtil.Method806(n);
            Class516.Method1283(axisAlignedBB2.grow((double)0.002f), fArray[0], fArray[1], fArray[2], fArray[3]);
        }
    }

    public static void Method1258(AxisAlignedBB axisAlignedBB, float f, float f2, float f3, float f4) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(f, f2, f3, 0.0f).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ - 0.8 * (axisAlignedBB.maxZ - axisAlignedBB.minZ)).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(f, f2, f3, 0.0f).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ + 0.8 * (axisAlignedBB.maxZ - axisAlignedBB.minZ)).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(f, f2, f3, 0.0f).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ - 0.8 * (axisAlignedBB.maxZ - axisAlignedBB.minZ)).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(f, f2, f3, 0.0f).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ + 0.8 * (axisAlignedBB.maxZ - axisAlignedBB.minZ)).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(f, f2, f3, 0.0f).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX - 0.8 * (axisAlignedBB.maxX - axisAlignedBB.minX), axisAlignedBB.minY, axisAlignedBB.minZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(f, f2, f3, 0.0f).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX - 0.8 * (axisAlignedBB.maxX - axisAlignedBB.minX), axisAlignedBB.minY, axisAlignedBB.maxZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(f, f2, f3, 0.0f).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX + 0.8 * (axisAlignedBB.maxX - axisAlignedBB.minX), axisAlignedBB.minY, axisAlignedBB.minZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(f, f2, f3, 0.0f).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX + 0.8 * (axisAlignedBB.maxX - axisAlignedBB.minX), axisAlignedBB.minY, axisAlignedBB.maxZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(f, f2, f3, 0.0f).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY + 0.2 * (axisAlignedBB.maxY - axisAlignedBB.minY), axisAlignedBB.minZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(f, f2, f3, 0.0f).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY + 0.2 * (axisAlignedBB.maxY - axisAlignedBB.minY), axisAlignedBB.maxZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(f, f2, f3, 0.0f).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY + 0.2 * (axisAlignedBB.maxY - axisAlignedBB.minY), axisAlignedBB.minZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(f, f2, f3, 0.0f).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY + 0.2 * (axisAlignedBB.maxY - axisAlignedBB.minY), axisAlignedBB.maxZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(f, f2, f3, 0.0f).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ - 0.8 * (axisAlignedBB.maxZ - axisAlignedBB.minZ)).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(f, f2, f3, 0.0f).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ + 0.8 * (axisAlignedBB.maxZ - axisAlignedBB.minZ)).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(f, f2, f3, 0.0f).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ - 0.8 * (axisAlignedBB.maxZ - axisAlignedBB.minZ)).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(f, f2, f3, 0.0f).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ + 0.8 * (axisAlignedBB.maxZ - axisAlignedBB.minZ)).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(f, f2, f3, 0.0f).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX - 0.8 * (axisAlignedBB.maxX - axisAlignedBB.minX), axisAlignedBB.maxY, axisAlignedBB.minZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(f, f2, f3, 0.0f).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX - 0.8 * (axisAlignedBB.maxX - axisAlignedBB.minX), axisAlignedBB.maxY, axisAlignedBB.maxZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(f, f2, f3, 0.0f).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX + 0.8 * (axisAlignedBB.maxX - axisAlignedBB.minX), axisAlignedBB.maxY, axisAlignedBB.minZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(f, f2, f3, 0.0f).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX + 0.8 * (axisAlignedBB.maxX - axisAlignedBB.minX), axisAlignedBB.maxY, axisAlignedBB.maxZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(f, f2, f3, 0.0f).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY - 0.2 * (axisAlignedBB.maxY - axisAlignedBB.minY), axisAlignedBB.minZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(f, f2, f3, 0.0f).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY - 0.2 * (axisAlignedBB.maxY - axisAlignedBB.minY), axisAlignedBB.maxZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(f, f2, f3, 0.0f).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY - 0.2 * (axisAlignedBB.maxY - axisAlignedBB.minY), axisAlignedBB.minZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(f, f2, f3, 0.0f).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY - 0.2 * (axisAlignedBB.maxY - axisAlignedBB.minY), axisAlignedBB.maxZ).color(f, f2, f3, f4).endVertex();
        tessellator.draw();
    }

    public static void Method1259(AxisAlignedBB axisAlignedBB, Color color) {
        AxisAlignedBB axisAlignedBB2 = axisAlignedBB.offset(-Class516.Field1284.getRenderManager().viewerPosX, -Class516.Field1284.getRenderManager().viewerPosY, -Class516.Field1284.getRenderManager().viewerPosZ);
        Class516.Method1258(axisAlignedBB2.grow((double)0.002f), color.getRed() * 255, color.getGreen() * 255, color.getBlue() * 255, color.getAlpha() * 255);
    }

    public static void Method1260(double d, double d2, double d3, float f, int n, int n2) {
        Sphere sphere = new Sphere();
        GL11.glPushMatrix();
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glLineWidth((float)1.2f);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        sphere.setDrawStyle(100013);
        double d4 = ((IRenderManager)Field1284.getRenderManager()).getRenderPosX();
        double d5 = ((IRenderManager)Field1284.getRenderManager()).getRenderPosY();
        double d6 = ((IRenderManager)Field1284.getRenderManager()).getRenderPosZ();
        GL11.glTranslated((double)(d - d4), (double)(d2 - d5), (double)(d3 - d6));
        sphere.draw(f, n, n2);
        GL11.glLineWidth((float)2.0f);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
    }

    public static void Method1261() {
        GlStateManager.enableCull();
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
    }

    public static void Method1262(int n) {
        float f = (float)(n >> 24 & 0xFF) / 255.0f;
        float f2 = (float)(n >> 16 & 0xFF) / 255.0f;
        float f3 = (float)(n >> 8 & 0xFF) / 255.0f;
        float f4 = (float)(n & 0xFF) / 255.0f;
        GlStateManager.color((float)f2, (float)f3, (float)f4, (float)f);
    }

    public static void Method1263(AxisAlignedBB axisAlignedBB, int n) {
        Class516.Method1259(axisAlignedBB, new Color(n));
    }

    public static void Method1264(AxisAlignedBB axisAlignedBB, Color color) {
        AxisAlignedBB axisAlignedBB2 = axisAlignedBB.offset(-Class516.Field1284.getRenderManager().viewerPosX, -Class516.Field1284.getRenderManager().viewerPosY, -Class516.Field1284.getRenderManager().viewerPosZ);
        Class516.Method1284(color);
        Class516.Method1286(axisAlignedBB2);
    }

    public static float Method1265(EntityLivingBase entityLivingBase) {
        return Class516.Method1282(entityLivingBase.posX, entityLivingBase.posY, entityLivingBase.posZ);
    }

    public static void Method1266(AxisAlignedBB axisAlignedBB, float f, float f2, float f3, float f4) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(f, f2, f3, f4).endVertex();
        tessellator.draw();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(f, f2, f3, f4).endVertex();
        tessellator.draw();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(f, f2, f3, f4).endVertex();
        tessellator.draw();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(f, f2, f3, f4).endVertex();
        tessellator.draw();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(f, f2, f3, f4).endVertex();
        tessellator.draw();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(f, f2, f3, f4).endVertex();
        tessellator.draw();
    }

    public static void Method1267(AxisAlignedBB axisAlignedBB, float f, float f2, float f3, float f4) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
    }

    public static Minecraft Method1268() {
        return Field1284;
    }

    public static boolean Method1269(AxisAlignedBB axisAlignedBB) {
        Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
        Field1285.setPosition(entity.posX, entity.posY, entity.posZ);
        return Field1285.isBoundingBoxInFrustum(axisAlignedBB);
    }

    public static void Method1270(float f, float f2, float f3, float f4, float f5, boolean bl, float f6, int n) {
        boolean bl2 = GL11.glIsEnabled((int)3042);
        float f7 = (float)(n >> 24 & 0xFF) / 255.0f;
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glPushMatrix();
        Class516.Method1276(n);
        GL11.glBegin((int)7);
        GL11.glVertex2d((double)f, (double)f2);
        GL11.glVertex2d((double)(f - f3 / f4), (double)(f2 + f3));
        GL11.glVertex2d((double)f, (double)(f2 + f3 / f5));
        GL11.glVertex2d((double)(f + f3 / f4), (double)(f2 + f3));
        GL11.glVertex2d((double)f, (double)f2);
        GL11.glEnd();
        if (bl) {
            GL11.glLineWidth((float)f6);
            GL11.glColor4f((float)0.0f, (float)0.0f, (float)0.0f, (float)f7);
            GL11.glBegin((int)2);
            GL11.glVertex2d((double)f, (double)f2);
            GL11.glVertex2d((double)(f - f3 / f4), (double)(f2 + f3));
            GL11.glVertex2d((double)f, (double)(f2 + f3 / f5));
            GL11.glVertex2d((double)(f + f3 / f4), (double)(f2 + f3));
            GL11.glVertex2d((double)f, (double)f2);
            GL11.glEnd();
        }
        GL11.glPopMatrix();
        GL11.glEnable((int)3553);
        if (!bl2) {
            GL11.glDisable((int)3042);
        }
        GL11.glDisable((int)2848);
    }

    public static void Method1271(AxisAlignedBB axisAlignedBB, int n) {
        AxisAlignedBB axisAlignedBB2 = axisAlignedBB.offset(-Class516.Field1284.getRenderManager().viewerPosX, -Class516.Field1284.getRenderManager().viewerPosY, -Class516.Field1284.getRenderManager().viewerPosZ);
        Class516.Method1262(n);
        Class516.Method1286(axisAlignedBB2);
    }

    public static void Method1272(BlockPos blockPos, int n, double d, double d2) {
        float f = (float)(n >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(n >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(n & 0xFF) / 255.0f;
        float f4 = (float)(n >> 24 & 0xFF) / 255.0f;
        Class516.Method1273(Collections.singleton(blockPos), f, f2, f3, f4, d, d2);
    }

    public static void Method1273(Collection<BlockPos> collection, float f, float f2, float f3, float f4, double d, double d2) {
        Class516.Method1289();
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.disableDepth();
        GlStateManager.depthMask((boolean)false);
        double d3 = ((IRenderManager)Field1284.getRenderManager()).getRenderPosX();
        double d4 = ((IRenderManager)Field1284.getRenderManager()).getRenderPosY();
        double d5 = ((IRenderManager)Field1284.getRenderManager()).getRenderPosZ();
        GL11.glColor4d((double)f, (double)f2, (double)f3, (double)f4);
        for (BlockPos blockPos : collection) {
            GlStateManager.pushMatrix();
            double d6 = (double)blockPos.getX() - d3;
            double d7 = (double)blockPos.getY() - d4;
            double d8 = (double)blockPos.getZ() - d5;
            AxisAlignedBB axisAlignedBB = new AxisAlignedBB(d6, d7, d8, d6 + d2, d7 + 1.0, d8 + d);
            Class516.Method1266(axisAlignedBB, f, f2, f3, f4);
            GlStateManager.popMatrix();
        }
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.depthMask((boolean)true);
        GlStateManager.enableAlpha();
        Class516.Method1261();
    }

    public static void Method1274(RayTraceResult rayTraceResult, int n, float f) {
        block1: {
            if (rayTraceResult.typeOfHit != RayTraceResult.Type.BLOCK) break block1;
            float f2 = (float)(n >> 16 & 0xFF) / 255.0f;
            float f3 = (float)(n >> 8 & 0xFF) / 255.0f;
            float f4 = (float)(n & 0xFF) / 255.0f;
            float f5 = (float)(n >> 24 & 0xFF) / 255.0f;
            Class516.Method1289();
            GlStateManager.glLineWidth((float)2.0f);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask((boolean)false);
            BlockPos blockPos = rayTraceResult.getBlockPos();
            IBlockState iBlockState = Class516.Field1284.world.getBlockState(blockPos);
            if (iBlockState.getMaterial() != Material.AIR && Class516.Field1284.world.getWorldBorder().contains(blockPos)) {
                double d = Class516.Field1284.player.lastTickPosX + (Class516.Field1284.player.posX - Class516.Field1284.player.lastTickPosX) * (double)f;
                double d2 = Class516.Field1284.player.lastTickPosY + (Class516.Field1284.player.posY - Class516.Field1284.player.lastTickPosY) * (double)f;
                double d3 = Class516.Field1284.player.lastTickPosZ + (Class516.Field1284.player.posZ - Class516.Field1284.player.lastTickPosZ) * (double)f;
                Class516.Method1283(iBlockState.getSelectedBoundingBox((World)Class516.Field1284.world, blockPos).grow((double)0.002f).offset(-d, -d2, -d3), f2, f3, f4, f5);
            }
            Class516.Method1261();
            GlStateManager.depthMask((boolean)true);
            GlStateManager.enableTexture2D();
        }
    }

    public static void Method1275(float f, float f2, float f3, float f4, int n, int n2) {
        float f5 = (float)(n >> 24 & 0xFF) / 255.0f;
        float f6 = (float)(n >> 16 & 0xFF) / 255.0f;
        float f7 = (float)(n >> 8 & 0xFF) / 255.0f;
        float f8 = (float)(n & 0xFF) / 255.0f;
        float f9 = (float)(n2 >> 24 & 0xFF) / 255.0f;
        float f10 = (float)(n2 >> 16 & 0xFF) / 255.0f;
        float f11 = (float)(n2 >> 8 & 0xFF) / 255.0f;
        float f12 = (float)(n2 & 0xFF) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel((int)7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos((double)f3, (double)f2, 0.0).color(f10, f11, f12, f9).endVertex();
        bufferBuilder.pos((double)f, (double)f2, 0.0).color(f6, f7, f8, f5).endVertex();
        bufferBuilder.pos((double)f, (double)f4, 0.0).color(f6, f7, f8, f5).endVertex();
        bufferBuilder.pos((double)f3, (double)f4, 0.0).color(f10, f11, f12, f9).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel((int)7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void Method1276(int n) {
        float f = (float)(n >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(n >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(n & 0xFF) / 255.0f;
        float f4 = (float)(n >> 24 & 0xFF) / 255.0f;
        GL11.glColor4f((float)f, (float)f2, (float)f3, (float)f4);
    }

    public static void Method1277(float f, float f2, float f3, float f4, int n, int n2, int n3, int n4) {
        float f5 = (float)(n >> 24 & 0xFF) / 255.0f;
        float f6 = (float)(n >> 16 & 0xFF) / 255.0f;
        float f7 = (float)(n >> 8 & 0xFF) / 255.0f;
        float f8 = (float)(n & 0xFF) / 255.0f;
        float f9 = (float)(n3 >> 24 & 0xFF) / 255.0f;
        float f10 = (float)(n3 >> 16 & 0xFF) / 255.0f;
        float f11 = (float)(n3 >> 8 & 0xFF) / 255.0f;
        float f12 = (float)(n3 & 0xFF) / 255.0f;
        float f13 = (float)(n2 >> 24 & 0xFF) / 255.0f;
        float f14 = (float)(n2 >> 16 & 0xFF) / 255.0f;
        float f15 = (float)(n2 >> 8 & 0xFF) / 255.0f;
        float f16 = (float)(n2 & 0xFF) / 255.0f;
        float f17 = (float)(n4 >> 24 & 0xFF) / 255.0f;
        float f18 = (float)(n4 >> 16 & 0xFF) / 255.0f;
        float f19 = (float)(n4 >> 8 & 0xFF) / 255.0f;
        float f20 = (float)(n4 & 0xFF) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel((int)7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos((double)f3, (double)f2, 0.0).color(f18, f19, f20, f17).endVertex();
        bufferBuilder.pos((double)f, (double)f2, 0.0).color(f14, f15, f16, f13).endVertex();
        bufferBuilder.pos((double)f, (double)f4, 0.0).color(f6, f7, f8, f5).endVertex();
        bufferBuilder.pos((double)f3, (double)f4, 0.0).color(f10, f11, f12, f9).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel((int)7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void Method1278(int n, int n2, int n3, int n4, float f, int n5) {
        int n6;
        if (n < n3) {
            n6 = n;
            n = n3;
            n3 = n6;
        }
        if (n2 < n4) {
            n6 = n2;
            n2 = n4;
            n4 = n6;
        }
        float f2 = (float)(n5 >> 24 & 0xFF) / 255.0f;
        float f3 = (float)(n5 >> 16 & 0xFF) / 255.0f;
        float f4 = (float)(n5 >> 8 & 0xFF) / 255.0f;
        float f5 = (float)(n5 & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.color((float)f3, (float)f4, (float)f5, (float)f2);
        GL11.glEnable((int)2848);
        GlStateManager.glLineWidth((float)f);
        bufferBuilder.begin(1, DefaultVertexFormats.POSITION);
        bufferBuilder.pos((double)n, (double)n2, 0.0).endVertex();
        bufferBuilder.pos((double)n3, (double)n2, 0.0).endVertex();
        bufferBuilder.pos((double)n3, (double)n2, 0.0).endVertex();
        bufferBuilder.pos((double)n3, (double)n4, 0.0).endVertex();
        bufferBuilder.pos((double)n3, (double)n4, 0.0).endVertex();
        bufferBuilder.pos((double)n, (double)n4, 0.0).endVertex();
        bufferBuilder.pos((double)n, (double)n4, 0.0).endVertex();
        bufferBuilder.pos((double)n, (double)n2, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GL11.glDisable((int)2848);
    }

    public static void Method1279(Vec3d vec3d, Vec3d vec3d2, Color color) {
        double d = ((IRenderManager)Field1284.getRenderManager()).getRenderPosX();
        double d2 = ((IRenderManager)Field1284.getRenderManager()).getRenderPosY();
        double d3 = ((IRenderManager)Field1284.getRenderManager()).getRenderPosZ();
        Vec3d vec3d3 = vec3d.add(-d, -d2, -d3);
        Vec3d vec3d4 = vec3d2.add(-d, -d2, -d3);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(vec3d3.x, vec3d3.y, vec3d3.z).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferBuilder.pos(vec3d4.x, vec3d4.y, vec3d4.z).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        tessellator.draw();
    }

    public static void Method1280(Entity entity, int n, double d) {
        IRenderManager iRenderManager = (IRenderManager)Field1284.getRenderManager();
        double d2 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * d - iRenderManager.getRenderPosX();
        double d3 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * d - iRenderManager.getRenderPosY();
        double d4 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * d - iRenderManager.getRenderPosZ();
        AxisAlignedBB axisAlignedBB = entity.getEntityBoundingBox();
        AxisAlignedBB axisAlignedBB2 = new AxisAlignedBB(axisAlignedBB.minX - entity.posX + d2, axisAlignedBB.minY - entity.posY + d3, axisAlignedBB.minZ - entity.posZ + d4, axisAlignedBB.maxX - entity.posX + d2, axisAlignedBB.maxY - entity.posY + d3, axisAlignedBB.maxZ - entity.posZ + d4);
        GL11.glBlendFunc((int)770, (int)771);
        float[] fArray = RainbowUtil.Method806(n);
        GL11.glColor4f((float)fArray[0], (float)fArray[1], (float)fArray[2], (float)fArray[3]);
        Class516.Method1286(axisAlignedBB2);
    }

    public static void Method1281(AxisAlignedBB axisAlignedBB, int n) {
        Class516.Method1285(axisAlignedBB, new Color(n));
    }

    public static float Method1282(double d, double d2, double d3) {
        ScaledResolution scaledResolution = new ScaledResolution(Field1284);
        double d4 = (double)scaledResolution.getScaleFactor() / Math.pow(scaledResolution.getScaleFactor(), 2.0);
        return (float)d4 + (float)Class516.Field1284.player.getDistance(d, d2, d3) / 7.0f;
    }

    public static void Method1283(AxisAlignedBB axisAlignedBB, float f, float f2, float f3, float f4) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(f, f2, f3, 0.0f).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(f, f2, f3, 0.0f).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(f, f2, f3, 0.0f).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(f, f2, f3, 0.0f).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(f, f2, f3, 0.0f).endVertex();
        tessellator.draw();
    }

    public static void Method1284(Color color) {
        float f = (float)color.getRed() / 255.0f;
        float f2 = (float)color.getGreen() / 255.0f;
        float f3 = (float)color.getBlue() / 255.0f;
        float f4 = (float)color.getAlpha() / 255.0f;
        GlStateManager.color((float)f, (float)f2, (float)f3, (float)f4);
    }

    public static void Method1285(AxisAlignedBB axisAlignedBB, Color color) {
        AxisAlignedBB axisAlignedBB2 = axisAlignedBB.offset(-Class516.Field1284.getRenderManager().viewerPosX, -Class516.Field1284.getRenderManager().viewerPosY, -Class516.Field1284.getRenderManager().viewerPosZ);
        Class516.Method1283(axisAlignedBB2.grow((double)0.002f), color.getRed() * 255, color.getGreen() * 255, color.getBlue() * 255, color.getAlpha() * 255);
    }

    public static void Method1286(AxisAlignedBB axisAlignedBB) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        tessellator.draw();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        tessellator.draw();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        tessellator.draw();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        tessellator.draw();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        tessellator.draw();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        tessellator.draw();
    }

    public static void Method1287(double d, double d2, double d3, double d4, Color color) {
        double d5 = d - ((IRenderManager)Field1284.getRenderManager()).getRenderPosX();
        double d6 = d2 - ((IRenderManager)Field1284.getRenderManager()).getRenderPosY();
        double d7 = d3 - ((IRenderManager)Field1284.getRenderManager()).getRenderPosZ();
        GL11.glColor4f((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
        GL11.glBegin((int)1);
        for (int i = 0; i <= 360; ++i) {
            GL11.glVertex3d((double)(d5 + Math.sin((double)i * Math.PI / 180.0) * d4), (double)d6, (double)(d7 + Math.cos((double)i * Math.PI / 180.0) * d4));
        }
        GL11.glEnd();
    }

    public static boolean Method1288(Entity entity) {
        return Class516.Method1269(entity.getEntityBoundingBox()) || entity.ignoreFrustumCheck;
    }

    public static void Method1289() {
        GL11.glBlendFunc((int)770, (int)771);
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.enableBlend();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
    }
}