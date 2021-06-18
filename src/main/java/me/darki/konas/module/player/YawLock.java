package me.darki.konas.module.player;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.mixin.mixins.IRenderManager;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.Render3DEvent;
import me.darki.konas.util.math.Interpolation;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class YawLock
extends Module {
    public static Setting<Boolean> diagonals = new Setting<>("Diagonals", true);
    public static Setting<Boolean> render = new Setting<>("Render", true);
    public static Setting<Boolean> entities = new Setting<>("Entities", false);
    public static Setting<Float> speed = new Setting<>("Speed", 0.1f, 5.0f, 0.0f, 0.1f);
    public int Field2551 = 0;
    public float Field2552 = 0.0f;

    @Subscriber
    public void Method139(Render3DEvent render3DEvent) {
        block11: {
            if (YawLock.mc.player == null) {
                return;
            }
            if (YawLock.mc.mouseHelper.deltaX != 0 || YawLock.mc.mouseHelper.deltaY != 0 || this.Method394()) {
                this.Field2551 = 4;
            } else {
                float f = 360.0f / ((Boolean)diagonals.getValue() != false ? 8.0f : 4.0f);
                if (this.Field2551 <= 0) {
                    float f2 = YawLock.mc.player.rotationYaw + 180.0f;
                    f2 = (float)Math.round(f2 / f) * f;
                    YawLock.mc.player.prevRotationYaw = YawLock.mc.player.rotationYaw;
                    YawLock.mc.player.rotationYaw = Interpolation.Method363(YawLock.mc.player.rotationYaw, f2 -= 180.0f, mc.getRenderPartialTicks(), ((Float)speed.getValue()).floatValue());
                    if (((Boolean)entities.getValue()).booleanValue() && YawLock.mc.player.isRiding()) {
                        YawLock.mc.player.getRidingEntity().prevRotationYaw = YawLock.mc.player.getRidingEntity().rotationYaw;
                        YawLock.mc.player.getRidingEntity().rotationYaw = YawLock.mc.player.rotationYaw;
                    }
                } else {
                    --this.Field2551;
                }
            }
            if (!((Boolean)render.getValue()).booleanValue() || this.Field2551 <= 0 && !(this.Field2552 > 0.0f)) break block11;
            double d = 300.0;
            Vec3d vec3d = YawLock.mc.player.getPositionVector();
            Vec3d[] vec3dArray = (Boolean)diagonals.getValue() != false ? new Vec3d[]{vec3d.addVector(d, 0.0, 0.0), vec3d.addVector(d / 2.0, 0.0, d / 2.0), vec3d.addVector(0.0, 0.0, d), vec3d.addVector(-d / 2.0, 0.0, d / 2.0), vec3d.addVector(-d, 0.0, 0.0), vec3d.addVector(-d / 2.0, 0.0, -d / 2.0), vec3d.addVector(0.0, 0.0, -d), vec3d.addVector(d / 2.0, 0.0, -d / 2.0)} : new Vec3d[]{vec3d.addVector(d, 0.0, 0.0), vec3d.addVector(0.0, 0.0, d), vec3d.addVector(-d, 0.0, 0.0), vec3d.addVector(0.0, 0.0, -d)};
            if (this.Field2551 > 0) {
                this.Field2552 = Interpolation.Method363(this.Field2552, 255.0f, mc.getRenderPartialTicks(), ((Float)speed.getValue()).floatValue());
            } else if (this.Field2552 > 0.0f) {
                this.Field2552 = Interpolation.Method363(this.Field2552, 0.0f, mc.getRenderPartialTicks(), ((Float)speed.getValue()).floatValue());
            }
            if (this.Field2552 != 0.0f) {
                GlStateManager.pushMatrix();
                GlStateManager.blendFunc((int)770, (int)771);
                GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
                GlStateManager.enableBlend();
                GlStateManager.disableLighting();
                GlStateManager.disableCull();
                GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                GlStateManager.disableDepth();
                GlStateManager.depthMask((boolean)false);
                GlStateManager.shadeModel((int)7425);
                GlStateManager.glLineWidth((float)2.0f);
                GlStateManager.disableTexture2D();
                GL11.glEnable((int)2848);
                GL11.glHint((int)3154, (int)4354);
                double d2 = ((IRenderManager)mc.getRenderManager()).getRenderPosX();
                double d3 = ((IRenderManager)mc.getRenderManager()).getRenderPosY();
                double d4 = ((IRenderManager)mc.getRenderManager()).getRenderPosZ();
                GlStateManager.translate((double)(-d2), (double)(-d3), (double)(-d4));
                for (Vec3d vec3d2 : vec3dArray) {
                    YawLock.Method2187(vec3d2.subtract(0.0, (double)this.Field2552, 0.0), vec3d2.addVector(0.0, (double)this.Field2552, 0.0), 0.96f, 0.19f, 0.19f, this.Field2552 / 255.0f / 2.0f + 127.5f);
                }
                GlStateManager.shadeModel((int)7424);
                GL11.glDisable((int)2848);
                GlStateManager.enableDepth();
                GlStateManager.depthMask((boolean)true);
                GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                GlStateManager.enableCull();
                GlStateManager.enableLighting();
                GlStateManager.disableBlend();
                GlStateManager.enableTexture2D();
                GlStateManager.popMatrix();
            }
        }
    }

    public static void Method2186(Vec3d vec3d, Vec3d vec3d2, int n, boolean bl, float f) {
        YawLock.Method2188(vec3d, vec3d2, n);
    }

    public YawLock() {
        super("YawLock", "Lock your yaw rotation", Category.PLAYER, new String[0]);
    }

    public static void Method2187(Vec3d vec3d, Vec3d vec3d2, float f, float f2, float f3, float f4) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(vec3d.x, vec3d.y, vec3d.z).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(vec3d2.x, vec3d2.y, vec3d2.z).color(f, f2, f3, f4).endVertex();
        tessellator.draw();
    }

    public boolean Method394() {
        for (int i = 0; i < Mouse.getButtonCount(); ++i) {
            if (!Mouse.isButtonDown((int)i)) continue;
            return true;
        }
        return false;
    }

    public static void Method2188(Vec3d vec3d, Vec3d vec3d2, int n) {
        float f = (float)(n >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(n >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(n & 0xFF) / 255.0f;
        float f4 = (float)(n >> 24 & 0xFF) / 255.0f;
        YawLock.Method2187(vec3d, vec3d2, f, f2, f3, f4);
    }
}