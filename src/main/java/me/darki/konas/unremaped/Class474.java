package me.darki.konas;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.mixin.mixins.IRenderManager;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import me.darki.konas.util.math.Interpolation;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class Class474
extends Module {
    public static Setting<Boolean> Field2547 = new Setting<>("Diagonals", true);
    public static Setting<Boolean> Field2548 = new Setting<>("Render", true);
    public static Setting<Boolean> Field2549 = new Setting<>("Entities", false);
    public static Setting<Float> Field2550 = new Setting<>("Speed", Float.valueOf(0.1f), Float.valueOf(5.0f), Float.valueOf(0.0f), Float.valueOf(0.1f));
    public int Field2551 = 0;
    public float Field2552 = 0.0f;

    @Subscriber
    public void Method139(Class89 class89) {
        block11: {
            if (Class474.mc.player == null) {
                return;
            }
            if (Class474.mc.mouseHelper.deltaX != 0 || Class474.mc.mouseHelper.deltaY != 0 || this.Method394()) {
                this.Field2551 = 4;
            } else {
                float f = 360.0f / (Field2547.getValue() != false ? 8.0f : 4.0f);
                if (this.Field2551 <= 0) {
                    float f2 = Class474.mc.player.rotationYaw + 180.0f;
                    f2 = (float)Math.round(f2 / f) * f;
                    Class474.mc.player.prevRotationYaw = Class474.mc.player.rotationYaw;
                    Class474.mc.player.rotationYaw = Interpolation.Method363(Class474.mc.player.rotationYaw, f2 -= 180.0f, mc.getRenderPartialTicks(), Field2550.getValue().floatValue());
                    if (Field2549.getValue().booleanValue() && Class474.mc.player.isRiding()) {
                        Class474.mc.player.getRidingEntity().prevRotationYaw = Class474.mc.player.getRidingEntity().rotationYaw;
                        Class474.mc.player.getRidingEntity().rotationYaw = Class474.mc.player.rotationYaw;
                    }
                } else {
                    --this.Field2551;
                }
            }
            if (!Field2548.getValue().booleanValue() || this.Field2551 <= 0 && !(this.Field2552 > 0.0f)) break block11;
            double d = 300.0;
            Vec3d vec3d = Class474.mc.player.getPositionVector();
            Vec3d[] vec3dArray = Field2547.getValue() != false ? new Vec3d[]{vec3d.add(d, 0.0, 0.0), vec3d.add(d / 2.0, 0.0, d / 2.0), vec3d.add(0.0, 0.0, d), vec3d.add(-d / 2.0, 0.0, d / 2.0), vec3d.add(-d, 0.0, 0.0), vec3d.add(-d / 2.0, 0.0, -d / 2.0), vec3d.add(0.0, 0.0, -d), vec3d.add(d / 2.0, 0.0, -d / 2.0)} : new Vec3d[]{vec3d.add(d, 0.0, 0.0), vec3d.add(0.0, 0.0, d), vec3d.add(-d, 0.0, 0.0), vec3d.add(0.0, 0.0, -d)};
            if (this.Field2551 > 0) {
                this.Field2552 = Interpolation.Method363(this.Field2552, 255.0f, mc.getRenderPartialTicks(), Field2550.getValue().floatValue());
            } else if (this.Field2552 > 0.0f) {
                this.Field2552 = Interpolation.Method363(this.Field2552, 0.0f, mc.getRenderPartialTicks(), Field2550.getValue().floatValue());
            }
            if (this.Field2552 != 0.0f) {
                GlStateManager.pushMatrix();
                GlStateManager.blendFunc(770, 771);
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                GlStateManager.enableBlend();
                GlStateManager.disableLighting();
                GlStateManager.disableCull();
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                GlStateManager.disableDepth();
                GlStateManager.depthMask(false);
                GlStateManager.shadeModel(7425);
                GlStateManager.glLineWidth(2.0f);
                GlStateManager.disableTexture2D();
                GL11.glEnable(2848);
                GL11.glHint(3154, 4354);
                double d2 = ((IRenderManager)mc.getRenderManager()).Method69();
                double d3 = ((IRenderManager)mc.getRenderManager()).Method70();
                double d4 = ((IRenderManager)mc.getRenderManager()).Method71();
                GlStateManager.translate(-d2, -d3, -d4);
                for (Vec3d vec3d2 : vec3dArray) {
                    Class474.Method2187(vec3d2.subtract(0.0, this.Field2552, 0.0), vec3d2.add(0.0, (double)this.Field2552, 0.0), 0.96f, 0.19f, 0.19f, this.Field2552 / 255.0f / 2.0f + 127.5f);
                }
                GlStateManager.shadeModel(7424);
                GL11.glDisable(2848);
                GlStateManager.enableDepth();
                GlStateManager.depthMask(true);
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                GlStateManager.enableCull();
                GlStateManager.enableLighting();
                GlStateManager.disableBlend();
                GlStateManager.enableTexture2D();
                GlStateManager.popMatrix();
            }
        }
    }

    public static void Method2186(Vec3d vec3d, Vec3d vec3d2, int n, boolean bl, float f) {
        Class474.Method2188(vec3d, vec3d2, n);
    }

    public Class474() {
        super("YawLock", "Lock your yaw rotation", Category.PLAYER);
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
            if (!Mouse.isButtonDown(i)) continue;
            return true;
        }
        return false;
    }

    public static void Method2188(Vec3d vec3d, Vec3d vec3d2, int n) {
        float f = (float)(n >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(n >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(n & 0xFF) / 255.0f;
        float f4 = (float)(n >> 24 & 0xFF) / 255.0f;
        Class474.Method2187(vec3d, vec3d2, f, f2, f3, f4);
    }
}
