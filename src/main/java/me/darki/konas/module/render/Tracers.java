package me.darki.konas.module.render;

import com.google.common.primitives.Ints;
import cookiedragon.eventsystem.Subscriber;
import java.awt.Color;
import me.darki.konas.module.Category;
import me.darki.konas.setting.ColorValue;
import me.darki.konas.unremaped.Class460;
import me.darki.konas.unremaped.Class492;
import me.darki.konas.unremaped.RenderUtil3D;
import me.darki.konas.unremaped.Class516;
import me.darki.konas.module.client.KonasGlobals;
import me.darki.konas.unremaped.Render3DEvent;
import me.darki.konas.unremaped.Class91;
import me.darki.konas.mixin.mixins.IEntityRenderer;
import me.darki.konas.mixin.mixins.IRenderManager;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class Tracers
extends Module {
    public static Setting<Class460> mode = new Setting<>("Mode", Class460.LINES);
    public static Setting<Boolean> showTargets = new Setting<>("ShowTargets", true);
    public static Setting<Boolean> showDistanceColor = new Setting<>("ShowDistanceColor", true);
    public static Setting<Boolean> showFriends = new Setting<>("ShowFriends", true);
    public static Setting<ColorValue> color = new Setting<>("Color", new ColorValue(-1));
    public static Setting<Boolean> visible = new Setting<>("Visible", false).visibleIf(Tracers::Method393);
    public static Setting<Boolean> fade = new Setting<>("Fade", false).visibleIf(Tracers::Method394);
    public static Setting<Integer> distance = new Setting<>("Distance", 100, 200, 50, 1).visibleIf(Tracers::Method388);
    public static Setting<Integer> radius = new Setting<>("Radius", 30, 200, 10, 1);
    public static Setting<Float> width = new Setting<>("Width", Float.valueOf(2.0f), Float.valueOf(5.0f), Float.valueOf(0.1f), Float.valueOf(0.5f));
    public static Setting<Float> range = new Setting<>("Range", Float.valueOf(220.0f), Float.valueOf(500.0f), Float.valueOf(1.0f), Float.valueOf(1.0f));

    public static boolean Method388() {
        return mode.getValue() == Class460.ARROWS && fade.getValue() != false;
    }

    public static boolean Method394() {
        return mode.getValue() == Class460.ARROWS;
    }

    public static boolean Method393() {
        return mode.getValue() == Class460.ARROWS;
    }

    @Subscriber
    public void Method466(Class91 class91) {
        if (Tracers.mc.world == null || Tracers.mc.player == null) {
            return;
        }
        if (mode.getValue() == Class460.LINES) {
            return;
        }
        for (Entity entity : Tracers.mc.world.loadedEntityList) {
            int n;
            Vec3d vec3d;
            Vec3d vec3d2;
            if (!(entity instanceof EntityPlayer) || entity == Tracers.mc.player || !(Tracers.mc.player.getDistance(entity) <= range.getValue().floatValue()) || (vec3d2 = KonasGlobals.INSTANCE.Field1137.Method2026(vec3d = new Vec3d(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)mc.getRenderPartialTicks(), entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)mc.getRenderPartialTicks(), entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)mc.getRenderPartialTicks()).add(0.0, (double)entity.getEyeHeight(), 0.0))) == null || this.Method467(vec3d2) || Class516.Method1288(entity) && !visible.getValue().booleanValue()) continue;
            GL11.glPushMatrix();
            int n2 = -1;
            if (showTargets.getValue().booleanValue() && KonasGlobals.INSTANCE.Field1133.Method423(entity)) {
                n = KonasGlobals.INSTANCE.Field1133.Method428(entity);
                n2 = new Color(255, n, n).hashCode();
            } else {
                n2 = Class492.Method1989(entity.getName()) && showFriends.getValue() != false ? Color.CYAN.hashCode() : (showDistanceColor.getValue() != false ? this.Method468(entity.getDistance(Tracers.mc.player)) : color.getValue().Method774());
            }
            n = n2 >> 24 & 0xFF;
            int n3 = n2 >> 16 & 0xFF;
            int n4 = n2 >> 8 & 0xFF;
            int n5 = n2 & 0xFF;
            Color color = new Color(n3, n4, n5, (int)(fade.getValue() != false ? MathHelper.clamp(255.0f - 255.0f / (float) distance.getValue().intValue() * Tracers.mc.player.getDistance(entity), 100.0f, 255.0f) : (float)n));
            int n6 = Display.getWidth() / 2 / (Tracers.mc.gameSettings.guiScale == 0 ? 1 : Tracers.mc.gameSettings.guiScale);
            int n7 = Display.getHeight() / 2 / (Tracers.mc.gameSettings.guiScale == 0 ? 1 : Tracers.mc.gameSettings.guiScale);
            float f = this.Method469(entity) - Tracers.mc.player.rotationYaw;
            GL11.glTranslatef((float)n6, (float)n7, 0.0f);
            GL11.glRotatef(f, 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef((float)(-n6), (float)(-n7), 0.0f);
            Class516.Method1270(n6, n7 - radius.getValue(), width.getValue().floatValue() * 5.0f, 2.0f, 1.0f, false, 1.0f, color.getRGB());
            GL11.glTranslatef((float)n6, (float)n7, 0.0f);
            GL11.glRotatef(-f, 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef((float)(-n6), (float)(-n7), 0.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glPopMatrix();
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean Method467(Vec3d vec3d) {
        if (!(vec3d.x > -1.0)) return false;
        if (!(vec3d.y < 1.0)) return false;
        int n = Tracers.mc.gameSettings.guiScale == 0 ? 1 : Tracers.mc.gameSettings.guiScale;
        if (!(vec3d.x / (double)n >= 0.0)) return false;
        int n2 = Tracers.mc.gameSettings.guiScale == 0 ? 1 : Tracers.mc.gameSettings.guiScale;
        if (!(vec3d.x / (double)n2 <= (double)Display.getWidth())) return false;
        int n3 = Tracers.mc.gameSettings.guiScale == 0 ? 1 : Tracers.mc.gameSettings.guiScale;
        if (!(vec3d.y / (double)n3 >= 0.0)) return false;
        int n4 = Tracers.mc.gameSettings.guiScale == 0 ? 1 : Tracers.mc.gameSettings.guiScale;
        return vec3d.y / (double) n4 <= (double) Display.getHeight();
    }

    public int Method468(float f) {
        int n = Ints.constrainToRange((int)f * 4, 0, 255);
        return new Color(Ints.constrainToRange(255 - n, 0, 255), Ints.constrainToRange(n, 0, 255), 0).hashCode();
    }

    public float Method469(Entity entity) {
        double d = entity.posX - Tracers.mc.player.posX;
        double d2 = entity.posZ - Tracers.mc.player.posZ;
        return (float)(-(Math.atan2(d, d2) * 57.29577951308232));
    }

    public Tracers() {
        super("Tracers", Category.RENDER);
    }

    @Subscriber
    public void Method139(Render3DEvent render3DEvent) {
        if (Tracers.mc.world == null || Tracers.mc.player == null) {
            return;
        }
        if (mode.getValue() == Class460.ARROWS) {
            return;
        }
        for (Entity entity : Tracers.mc.world.loadedEntityList) {
            int n;
            if (!(entity instanceof EntityPlayer) || entity == Tracers.mc.player || !(Tracers.mc.player.getDistance(entity) <= range.getValue().floatValue())) continue;
            Vec3d vec3d = RenderUtil3D.Method1393(entity, render3DEvent.Method436()).subtract(((IRenderManager)mc.getRenderManager()).getRenderPosX(), ((IRenderManager)mc.getRenderManager()).getRenderPosY(), ((IRenderManager)mc.getRenderManager()).getRenderPosZ());
            GL11.glBlendFunc(770, 771);
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.glLineWidth(width.getValue().floatValue());
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            GlStateManager.enableBlend();
            GlStateManager.disableDepth();
            GlStateManager.disableLighting();
            GlStateManager.disableCull();
            GlStateManager.enableAlpha();
            GlStateManager.color(1.0f, 1.0f, 1.0f);
            boolean bl = Tracers.mc.gameSettings.viewBobbing;
            Tracers.mc.gameSettings.viewBobbing = false;
            ((IEntityRenderer)Tracers.mc.entityRenderer).setupCameraTransform(render3DEvent.Method436(), 0);
            Vec3d vec3d2 = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-((float)Math.toRadians(Tracers.mc.player.rotationPitch))).rotateYaw(-((float)Math.toRadians(Tracers.mc.player.rotationYaw)));
            if (showTargets.getValue().booleanValue() && KonasGlobals.INSTANCE.Field1133.Method423(entity)) {
                int n2 = KonasGlobals.INSTANCE.Field1133.Method428(entity);
                n = new Color(255, n2, n2).hashCode();
            } else {
                n = Class492.Method1989(entity.getName()) && showFriends.getValue() != false ? Color.CYAN.hashCode() : (showDistanceColor.getValue() != false ? this.Method468(entity.getDistance(Tracers.mc.player)) : color.getValue().Method774());
            }
            RenderUtil3D.Method1408((float)vec3d2.x, (float)vec3d2.y + Tracers.mc.player.getEyeHeight(), (float)vec3d2.z, (float)vec3d.x, (float)vec3d.y, (float)vec3d.z, width.getValue().floatValue(), n);
            RenderUtil3D.Method1408((float)vec3d.x, (float)vec3d.y, (float)vec3d.z, (float)vec3d.x, (float)vec3d.y + entity.getEyeHeight(), (float)vec3d.z, width.getValue().floatValue(), n);
            Tracers.mc.gameSettings.viewBobbing = bl;
            ((IEntityRenderer)Tracers.mc.entityRenderer).setupCameraTransform(render3DEvent.Method436(), 0);
            GlStateManager.enableCull();
            GlStateManager.depthMask(true);
            GlStateManager.enableTexture2D();
            GlStateManager.enableBlend();
            GlStateManager.enableDepth();
        }
    }
}