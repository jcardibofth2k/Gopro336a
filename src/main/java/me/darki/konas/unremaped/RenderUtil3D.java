package me.darki.konas.unremaped;

import me.darki.konas.mixin.mixins.IRenderManager;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class RenderUtil3D {
    public static Minecraft Field1356 = Minecraft.getMinecraft();

    public static Vec3d Method1389(Vec3d vec3d) {
        return new Vec3d(vec3d.x, vec3d.y, vec3d.z).subtract(((IRenderManager)Field1356.getRenderManager()).getRenderPosX(), ((IRenderManager)Field1356.getRenderManager()).getRenderPosY(), ((IRenderManager)Field1356.getRenderManager()).getRenderPosZ());
    }

    public static void Method1390(BlockPos blockPos, Float f, Float f2, Float f3, Float f4, Float f5) {
        RenderUtil3D.Method1410(new Vec3d((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ()), f, f2, f3, f4, 1.0, 1.0, 1.0, f5);
    }

    public static void Method1391(float f, float f2, float f3, float f4, int n) {
        RenderUtil3D.Method1394(f, f2, f3, f4, n, 7);
    }

    public static void Method1392() {
        GlStateManager.enableDepth();
        GlStateManager.depthMask((boolean)true);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.resetColor();
    }

    public static Vec3d Method1393(Entity entity, float f) {
        return new Vec3d(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)f, entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)f, entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)f);
    }

    public static void Method1394(float f, float f2, float f3, float f4, int n, int n2) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        RenderUtil3D.Method1409(n);
        bufferBuilder.begin(n2, DefaultVertexFormats.POSITION);
        bufferBuilder.pos((double)f, (double)f4, 0.0).endVertex();
        bufferBuilder.pos((double)f3, (double)f4, 0.0).endVertex();
        bufferBuilder.pos((double)f3, (double)f2, 0.0).endVertex();
        bufferBuilder.pos((double)f, (double)f2, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void Method1395(float f, float f2, float f3, EntityPlayer entityPlayer, float f4) {
        RenderUtil3D.Method1405(f, f2, f3);
        int n = (int)entityPlayer.getDistance((double)f, (double)f2, (double)f3);
        float f5 = (float)n / 2.0f / (2.0f + (2.0f - f4));
        if (f5 < 1.0f) {
            f5 = 1.0f;
        }
        GlStateManager.scale((float)f5, (float)f5, (float)f5);
    }

    public static Vec3d Method1396(Entity entity, double d) {
        return RenderUtil3D.Method1402(entity, d, d, d);
    }

    public static Float[] Method1397(int n) {
        float f = (float)(n >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(n >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(n & 0xFF) / 255.0f;
        float f4 = (float)(n >> 24 & 0xFF) / 255.0f;
        return new Float[]{Float.valueOf(f), Float.valueOf(f2), Float.valueOf(f3), Float.valueOf(f4)};
    }

    public static void Method1398(Vec3d vec3d, int n, int n2, int n3, int n4, double d, double d2, double d3, float f) {
        double d4 = vec3d.x - (RenderUtil3D.Field1356.player.lastTickPosX + (RenderUtil3D.Field1356.player.posX - RenderUtil3D.Field1356.player.lastTickPosX) * (double)f);
        double d5 = vec3d.y - (RenderUtil3D.Field1356.player.lastTickPosY + (RenderUtil3D.Field1356.player.posY - RenderUtil3D.Field1356.player.lastTickPosY) * (double)f);
        double d6 = vec3d.z - (RenderUtil3D.Field1356.player.lastTickPosZ + (RenderUtil3D.Field1356.player.posZ - RenderUtil3D.Field1356.player.lastTickPosZ) * (double)f);
        GlStateManager.color((float)((float)n / 255.0f), (float)((float)n2 / 255.0f), (float)((float)n3 / 255.0f), (float)((float)n4 / 255.0f));
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(d4, d5, d6, d4 + d, d5 + d2, d6 + d3);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        tessellator.draw();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        tessellator.draw();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        tessellator.draw();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        tessellator.draw();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        tessellator.draw();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        tessellator.draw();
    }

    public static void Method1399() {
        GlStateManager.blendFunc((int)770, (int)771);
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.enableBlend();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.resetColor();
    }

    public static Vec3d Method1400(Entity entity, float f) {
        return RenderUtil3D.Method1404(entity, f).subtract(((IRenderManager)Field1356.getRenderManager()).getRenderPosX(), ((IRenderManager)Field1356.getRenderManager()).getRenderPosY(), ((IRenderManager)Field1356.getRenderManager()).getRenderPosZ());
    }

    public static void Method1401(float f, float f2, float f3, float f4, int n) {
        RenderUtil3D.Method1394(f, f2, f3, f4, n, 2);
    }

    public static Vec3d Method1402(Entity entity, double d, double d2, double d3) {
        return new Vec3d((entity.posX - entity.lastTickPosX) * d, (entity.posY - entity.lastTickPosY) * d2, (entity.posZ - entity.lastTickPosZ) * d3);
    }

    public static void Method1403(TileEntity tileEntity, int n) {
        float f = (float)(n >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(n >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(n & 0xFF) / 255.0f;
        float f4 = (float)(n >> 24 & 0xFF) / 255.0f;
        GlStateManager.pushMatrix();
        RenderUtil3D.Method1413();
        GlStateManager.enableBlend();
        GlStateManager.glLineWidth((float)3.0f);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask((boolean)false);
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        BlockPos blockPos = tileEntity.getPos();
        IBlockState iBlockState = RenderUtil3D.Field1356.world.getBlockState(blockPos);
        AxisAlignedBB axisAlignedBB = iBlockState.getSelectedBoundingBox((World) RenderUtil3D.Field1356.world, blockPos).offset(-RenderUtil3D.Field1356.getRenderManager().viewerPosX, -RenderUtil3D.Field1356.getRenderManager().viewerPosY, -RenderUtil3D.Field1356.getRenderManager().viewerPosZ);
        RenderUtil3D.Method1411(axisAlignedBB.grow((double)0.002f), Float.valueOf(f), Float.valueOf(f2), Float.valueOf(f3), Float.valueOf(f4));
        GlStateManager.resetColor();
        GlStateManager.enableDepth();
        GlStateManager.depthMask((boolean)true);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        RenderUtil3D.Method1392();
        GlStateManager.popMatrix();
    }

    public static Vec3d Method1404(Entity entity, float f) {
        return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).add(RenderUtil3D.Method1396(entity, f));
    }

    public static void Method1405(float f, float f2, float f3) {
        float f4 = 0.02666667f;
        GlStateManager.translate((double)((double)f - ((IRenderManager)Field1356.getRenderManager()).getRenderPosX()), (double)((double)f2 - ((IRenderManager)Field1356.getRenderManager()).getRenderPosY()), (double)((double)f3 - ((IRenderManager)Field1356.getRenderManager()).getRenderPosZ()));
        GlStateManager.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.rotate((float)(-Minecraft.getMinecraft().player.rotationYaw), (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.rotate((float)Minecraft.getMinecraft().player.rotationPitch, (float)(Minecraft.getMinecraft().gameSettings.thirdPersonView == 2 ? -1.0f : 1.0f), (float)0.0f, (float)0.0f);
        GlStateManager.scale((float)(-f4), (float)(-f4), (float)f4);
    }

    public static void Method1406() {
        GlStateManager.resetColor();
        GlStateManager.enableCull();
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
    }

    public static void Method1407(BlockPos blockPos, int n, int n2, int n3, int n4, double d, double d2, double d3, float f) {
        double d4 = (double)blockPos.getX() - (RenderUtil3D.Field1356.player.lastTickPosX + (RenderUtil3D.Field1356.player.posX - RenderUtil3D.Field1356.player.lastTickPosX) * (double)f);
        double d5 = (double)blockPos.getY() - (RenderUtil3D.Field1356.player.lastTickPosY + (RenderUtil3D.Field1356.player.posY - RenderUtil3D.Field1356.player.lastTickPosY) * (double)f);
        double d6 = (double)blockPos.getZ() - (RenderUtil3D.Field1356.player.lastTickPosZ + (RenderUtil3D.Field1356.player.posZ - RenderUtil3D.Field1356.player.lastTickPosZ) * (double)f);
        GlStateManager.color((float)((float)n / 255.0f), (float)((float)n2 / 255.0f), (float)((float)n3 / 255.0f), (float)((float)n4 / 255.0f));
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(d4, d5, d6, d4 + d, d5 + d2, d6 + d3);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        tessellator.draw();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        tessellator.draw();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        tessellator.draw();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        tessellator.draw();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        tessellator.draw();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        tessellator.draw();
    }

    public static void Method1408(float f, float f2, float f3, float f4, float f5, float f6, float f7, int n) {
        Float[] floatArray = RenderUtil3D.Method1397(n);
        GL11.glLineWidth((float)f7);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos((double)f, (double)f2, (double)f3).color(floatArray[0].floatValue(), floatArray[1].floatValue(), floatArray[2].floatValue(), floatArray[3].floatValue()).endVertex();
        bufferBuilder.pos((double)f4, (double)f5, (double)f6).color(floatArray[0].floatValue(), floatArray[1].floatValue(), floatArray[2].floatValue(), floatArray[3].floatValue()).endVertex();
        bufferBuilder.pos((double)f4, (double)f5, (double)f6).color(floatArray[0].floatValue(), floatArray[1].floatValue(), floatArray[2].floatValue(), floatArray[3].floatValue()).endVertex();
        tessellator.draw();
        GL11.glDisable((int)2848);
    }

    public static void Method1409(int n) {
        float f = (float)(n >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(n >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(n & 0xFF) / 255.0f;
        float f4 = (float)(n >> 24 & 0xFF) / 255.0f;
        GlStateManager.color((float)f, (float)f2, (float)f3, (float)f4);
    }

    public static void Method1410(Vec3d vec3d, Float f, Float f2, Float f3, Float f4, double d, double d2, double d3, Float f5) {
        double d4 = RenderUtil3D.Field1356.player.lastTickPosX + (RenderUtil3D.Field1356.player.posX - RenderUtil3D.Field1356.player.lastTickPosX) * (double)f5.floatValue();
        double d5 = RenderUtil3D.Field1356.player.lastTickPosY + (RenderUtil3D.Field1356.player.posY - RenderUtil3D.Field1356.player.lastTickPosY) * (double)f5.floatValue();
        double d6 = RenderUtil3D.Field1356.player.lastTickPosZ + (RenderUtil3D.Field1356.player.posZ - RenderUtil3D.Field1356.player.lastTickPosZ) * (double)f5.floatValue();
        Vec3d vec3d2 = vec3d.add(-d4, -d5, -d6);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(vec3d2.x, vec3d2.y, vec3d2.z).color(f.floatValue(), f2.floatValue(), f3.floatValue(), 0.0f).endVertex();
        bufferBuilder.pos(vec3d2.x, vec3d2.y, vec3d2.z).color(f.floatValue(), f2.floatValue(), f3.floatValue(), f4.floatValue()).endVertex();
        bufferBuilder.pos(vec3d2.x + d, vec3d2.y, vec3d2.z).color(f.floatValue(), f2.floatValue(), f3.floatValue(), f4.floatValue()).endVertex();
        bufferBuilder.pos(vec3d2.x + d, vec3d2.y, vec3d2.z + d3).color(f.floatValue(), f2.floatValue(), f3.floatValue(), f4.floatValue()).endVertex();
        bufferBuilder.pos(vec3d2.x, vec3d2.y, vec3d2.z + d3).color(f.floatValue(), f2.floatValue(), f3.floatValue(), f4.floatValue()).endVertex();
        bufferBuilder.pos(vec3d2.x, vec3d2.y, vec3d2.z).color(f.floatValue(), f2.floatValue(), f3.floatValue(), f4.floatValue()).endVertex();
        bufferBuilder.pos(vec3d2.x, vec3d2.y + d2, vec3d2.z).color(f.floatValue(), f2.floatValue(), f3.floatValue(), f4.floatValue()).endVertex();
        bufferBuilder.pos(vec3d2.x + d, vec3d2.y + d2, vec3d2.z).color(f.floatValue(), f2.floatValue(), f3.floatValue(), f4.floatValue()).endVertex();
        bufferBuilder.pos(vec3d2.x + d, vec3d2.y + d2, vec3d2.z + d3).color(f.floatValue(), f2.floatValue(), f3.floatValue(), f4.floatValue()).endVertex();
        bufferBuilder.pos(vec3d2.x, vec3d2.y + d2, vec3d2.z + d3).color(f.floatValue(), f2.floatValue(), f3.floatValue(), f4.floatValue()).endVertex();
        bufferBuilder.pos(vec3d2.x, vec3d2.y + d2, vec3d2.z).color(f.floatValue(), f2.floatValue(), f3.floatValue(), f4.floatValue()).endVertex();
        bufferBuilder.pos(vec3d2.x, vec3d2.y + d2, vec3d2.z + d3).color(f.floatValue(), f2.floatValue(), f3.floatValue(), 0.0f).endVertex();
        bufferBuilder.pos(vec3d2.x, vec3d2.y, vec3d2.z + d3).color(f.floatValue(), f2.floatValue(), f3.floatValue(), f4.floatValue()).endVertex();
        bufferBuilder.pos(vec3d2.x + d, vec3d2.y + d2, vec3d2.z + d3).color(f.floatValue(), f2.floatValue(), f3.floatValue(), 0.0f).endVertex();
        bufferBuilder.pos(vec3d2.x + d, vec3d2.y, vec3d2.z + d3).color(f.floatValue(), f2.floatValue(), f3.floatValue(), f4.floatValue()).endVertex();
        bufferBuilder.pos(vec3d2.x + d, vec3d2.y + d2, vec3d2.z).color(f.floatValue(), f2.floatValue(), f3.floatValue(), 0.0f).endVertex();
        bufferBuilder.pos(vec3d2.x + d, vec3d2.y, vec3d2.z).color(f.floatValue(), f2.floatValue(), f3.floatValue(), f4.floatValue()).endVertex();
        bufferBuilder.pos(vec3d2.x + d, vec3d2.y, vec3d2.z).color(f.floatValue(), f2.floatValue(), f3.floatValue(), 0.0f).endVertex();
        tessellator.draw();
    }

    public static void Method1411(AxisAlignedBB axisAlignedBB, Float f, Float f2, Float f3, Float f4) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(f.floatValue(), f2.floatValue(), f3.floatValue(), 0.0f).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(f.floatValue(), f2.floatValue(), f3.floatValue(), f4.floatValue()).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(f.floatValue(), f2.floatValue(), f3.floatValue(), f4.floatValue()).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(f.floatValue(), f2.floatValue(), f3.floatValue(), f4.floatValue()).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(f.floatValue(), f2.floatValue(), f3.floatValue(), f4.floatValue()).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(f.floatValue(), f2.floatValue(), f3.floatValue(), f4.floatValue()).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(f.floatValue(), f2.floatValue(), f3.floatValue(), f4.floatValue()).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(f.floatValue(), f2.floatValue(), f3.floatValue(), f4.floatValue()).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(f.floatValue(), f2.floatValue(), f3.floatValue(), f4.floatValue()).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(f.floatValue(), f2.floatValue(), f3.floatValue(), f4.floatValue()).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(f.floatValue(), f2.floatValue(), f3.floatValue(), f4.floatValue()).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(f.floatValue(), f2.floatValue(), f3.floatValue(), 0.0f).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(f.floatValue(), f2.floatValue(), f3.floatValue(), f4.floatValue()).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(f.floatValue(), f2.floatValue(), f3.floatValue(), 0.0f).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(f.floatValue(), f2.floatValue(), f3.floatValue(), f4.floatValue()).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(f.floatValue(), f2.floatValue(), f3.floatValue(), 0.0f).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(f.floatValue(), f2.floatValue(), f3.floatValue(), f4.floatValue()).endVertex();
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(f.floatValue(), f2.floatValue(), f3.floatValue(), 0.0f).endVertex();
        tessellator.draw();
    }

    public static void Method1412(Entity entity, int n, Float f) {
        float f2 = (float)(n >> 16 & 0xFF) / 255.0f;
        float f3 = (float)(n >> 8 & 0xFF) / 255.0f;
        float f4 = (float)(n & 0xFF) / 255.0f;
        float f5 = (float)(n >> 24 & 0xFF) / 255.0f;
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.glLineWidth((float)1.0f);
        GlStateManager.disableTexture2D();
        GlStateManager.disableDepth();
        GlStateManager.depthMask((boolean)false);
        if (entity != RenderUtil3D.Field1356.player) {
            double d = RenderUtil3D.Field1356.player.lastTickPosX + (RenderUtil3D.Field1356.player.posX - RenderUtil3D.Field1356.player.lastTickPosX) * (double)f.floatValue();
            double d2 = RenderUtil3D.Field1356.player.lastTickPosY + (RenderUtil3D.Field1356.player.posY - RenderUtil3D.Field1356.player.lastTickPosY) * (double)f.floatValue();
            double d3 = RenderUtil3D.Field1356.player.lastTickPosZ + (RenderUtil3D.Field1356.player.posZ - RenderUtil3D.Field1356.player.lastTickPosZ) * (double)f.floatValue();
            RenderUtil3D.Method1411(entity.getEntityBoundingBox().offset(-d, -d2, -d3), Float.valueOf(f2), Float.valueOf(f3), Float.valueOf(f4), Float.valueOf(f5));
        }
        GlStateManager.enableDepth();
        GlStateManager.depthMask((boolean)true);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void Method1413() {
        GL11.glBlendFunc((int)770, (int)771);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.glLineWidth((float)10.0f);
        GlStateManager.disableTexture2D();
        GlStateManager.disableDepth();
        GlStateManager.depthMask((boolean)false);
        GlStateManager.resetColor();
    }

    public static void Method1414(AxisAlignedBB axisAlignedBB, int n, int n2) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(n, DefaultVertexFormats.POSITION_COLOR);
        float f = (float)(n2 >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(n2 >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(n2 & 0xFF) / 255.0f;
        float f4 = (float)(n2 >> 24 & 0xFF) / 255.0f;
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
}