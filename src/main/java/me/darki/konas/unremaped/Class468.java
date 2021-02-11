package me.darki.konas;

import cookiedragon.eventsystem.Subscriber;
import java.awt.Color;

import me.darki.konas.mixin.mixins.IRenderManager;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;

public class Class468
extends Module {
    public static Setting<Class462> Field2592 = new Setting<>("Mode", Class462.FULL);
    public Setting<Boolean> Field2593 = new Setting<>("Facing", false);
    public static Setting<Boolean> Field2594 = new Setting<>("NotWhenTrajectories", true);
    public Setting<Boolean> Field2595 = new Setting<>("Depth", false);
    public Setting<Boolean> Field2596 = new Setting<>("Vanilla", false);
    public static Setting<Float> Field2597 = new Setting<>("Radius", Float.valueOf(0.1f), Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(0.1f)).Method1191(Class468::Method519);
    public static Setting<Integer> Field2598 = new Setting<>("Slices", 8, 24, 3, 1).Method1191(Class468::Method393);
    public static Setting<Float> Field2599 = new Setting<>("Scale", Float.valueOf(0.6f), Float.valueOf(4.0f), Float.valueOf(0.1f), Float.valueOf(0.1f)).Method1191(Class468::Method388);
    public static Setting<Double> Field2600 = new Setting<>("SpinSpeed", 15.0, 30.0, 0.0, 0.1).Method1191(Class468::Method394);
    public static Setting<Float> Field2601 = new Setting<>("Width", Float.valueOf(1.5f), Float.valueOf(10.0f), Float.valueOf(0.1f), Float.valueOf(0.1f));
    public Setting<ColorValue> Field2602 = new Setting<>("Color", new ColorValue(575714484));
    public RayTraceResult Field2603;
    public static Class566 Field2604 = new Class566();

    @Subscriber
    public void Method1523(Class89 class89) {
        block11: {
            RayTraceResult rayTraceResult;
            block12: {
                if (Class468.mc.world == null || Class468.mc.player == null) {
                    return;
                }
                if (Field2592.getValue() != Class462.VECTOR) {
                    if (Field2592.getValue() != Class462.BASED) {
                        return;
                    }
                }
                if (Field2594.getValue().booleanValue() && !Field2604.Method737(75.0)) {
                    return;
                }
                rayTraceResult = Class468.mc.objectMouseOver;
                if (rayTraceResult == null) {
                    return;
                }
                if (rayTraceResult.typeOfHit != RayTraceResult.Type.BLOCK) break block11;
                if (Field2592.getValue() != Class462.VECTOR) break block12;
                GlStateManager.pushMatrix();
                Class507.Method1386();
                GlStateManager.glLineWidth(Field2601.getValue().floatValue());
                GlStateManager.disableTexture2D();
                GlStateManager.depthMask(false);
                if (!this.Field2595.getValue().booleanValue()) {
                    GlStateManager.disableDepth();
                }
                GL11.glLineWidth(Field2601.getValue().floatValue());
                GL11.glColor4f((float) this.Field2602.getValue().Method769() / 255.0f, (float) this.Field2602.getValue().Method770() / 255.0f, (float) this.Field2602.getValue().Method779() / 255.0f, (float) this.Field2602.getValue().Method782() / 255.0f);
                GlStateManager.translate(rayTraceResult.hitVec.x - ((IRenderManager)mc.getRenderManager()).Method69(), rayTraceResult.hitVec.y - ((IRenderManager)mc.getRenderManager()).Method70(), rayTraceResult.hitVec.z - ((IRenderManager)mc.getRenderManager()).Method71());
                EnumFacing enumFacing = rayTraceResult.sideHit;
                switch (enumFacing) {
                    case NORTH: 
                    case SOUTH: {
                        GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                        break;
                    }
                    case EAST: 
                    case WEST: {
                        GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
                        break;
                    }
                }
                Cylinder cylinder = new Cylinder();
                GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.0f);
                cylinder.setDrawStyle(100011);
                cylinder.draw(Field2597.getValue().floatValue() * 2.0f, Field2597.getValue().floatValue(), 0.0f, Field2598.getValue().intValue(), 1);
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                if (!this.Field2595.getValue().booleanValue()) {
                    GlStateManager.enableDepth();
                }
                GlStateManager.depthMask(true);
                GlStateManager.enableTexture2D();
                Class507.Method1385();
                GlStateManager.popMatrix();
                break block11;
            }
            if (Field2592.getValue() != Class462.BASED) break block11;
            this.Method2215(rayTraceResult, Field2599.getValue().floatValue(), Field2600.getValue(), this.Field2602.getValue().Method775());
        }
    }

    public static boolean Method388() {
        return Field2592.getValue() == Class462.BASED;
    }

    public void Method1172(@NotNull AxisAlignedBB axisAlignedBB) {
        if (Class468.mc.world == null || Class468.mc.player == null) {
            return;
        }
        if (Field2594.getValue().booleanValue() && !Field2604.Method737(75.0)) {
            return;
        }
        GlStateManager.pushMatrix();
        Class507.Method1386();
        if (this.Field2595.getValue().booleanValue()) {
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
        }
        if (Field2592.getValue() == Class462.FULL) {
            Class507.Method1379(axisAlignedBB, this.Field2602.getValue());
        }
        Class507.Method1374(axisAlignedBB, Field2601.getValue().floatValue(), this.Field2602.getValue().Method784(255));
        GlStateManager.resetColor();
        Class507.Method1385();
        GlStateManager.popMatrix();
    }

    public void Method2215(RayTraceResult rayTraceResult, float f, double d, Color color) {
        GL11.glPushAttrib(1048575);
        GL11.glPushMatrix();
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(false);
        GL11.glDisable(2929);
        GL11.glDisable(2884);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4352);
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        GL11.glLineWidth(2.0f);
        GL11.glColor4f((float)color.getRed() / 255.0f, (float)color.getGreen() / 255.0f, (float)color.getBlue() / 255.0f, (float)color.getAlpha() / 255.0f);
        GL11.glTranslated(rayTraceResult.hitVec.x - ((IRenderManager)mc.getRenderManager()).Method69(), rayTraceResult.hitVec.y - ((IRenderManager)mc.getRenderManager()).Method70(), rayTraceResult.hitVec.z - ((IRenderManager)mc.getRenderManager()).Method71());
        switch (rayTraceResult.sideHit) {
            case WEST: {
                GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
                GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
                break;
            }
            case EAST: {
                GL11.glRotatef(-90.0f, 0.0f, 0.0f, 1.0f);
                GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
                break;
            }
            case NORTH: {
                GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
                break;
            }
            case UP: {
                GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
                break;
            }
            case DOWN: {
                GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
                break;
            }
        }
        double d2 = Math.ceil((double)System.currentTimeMillis() / Math.abs(d - 30.1));
        d2 %= 360.0;
        if (d == 0.0) {
            d2 = 0.0;
        }
        GL11.glRotated(d2, 0.0, 0.0, 1.0);
        GL11.glScalef(f * 0.5f, f * 0.5f, f * 0.5f);
        GL11.glBegin(3);
        GL11.glVertex3d(0.0, 0.0, 0.0);
        GL11.glVertex3d(0.0, 1.0, 0.0);
        GL11.glVertex3d(1.0, 1.0, 0.0);
        GL11.glEnd();
        GL11.glBegin(3);
        GL11.glVertex3d(0.0, 0.0, 0.0);
        GL11.glVertex3d(1.0, 0.0, 0.0);
        GL11.glVertex3d(1.0, -1.0, 0.0);
        GL11.glEnd();
        GL11.glBegin(3);
        GL11.glVertex3d(0.0, 0.0, 0.0);
        GL11.glVertex3d(0.0, -1.0, 0.0);
        GL11.glVertex3d(-1.0, -1.0, 0.0);
        GL11.glEnd();
        GL11.glBegin(3);
        GL11.glVertex3d(0.0, 0.0, 0.0);
        GL11.glVertex3d(-1.0, 0.0, 0.0);
        GL11.glVertex3d(-1.0, 1.0, 0.0);
        GL11.glEnd();
        GL11.glEnable(2896);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glEnable(2884);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glPopMatrix();
        GL11.glPopAttrib();
    }

    public static boolean Method394() {
        return Field2592.getValue() == Class462.BASED;
    }

    public static boolean Method519() {
        return Field2592.getValue() == Class462.VECTOR;
    }

    public static boolean Method393() {
        return Field2592.getValue() == Class462.VECTOR;
    }

    @Subscriber
    public void Method139(Class89 class89) {
        block12: {
            if (Class468.mc.world == null || Class468.mc.player == null) {
                return;
            }
            if (this.Field2603 == null) {
                return;
            }
            if (Field2592.getValue() == Class462.VECTOR || Field2592.getValue() == Class462.BASED) {
                return;
            }
            if (this.Field2603.typeOfHit != RayTraceResult.Type.BLOCK) break block12;
            BlockPos blockPos = this.Field2603.getBlockPos();
            IBlockState iBlockState = Class468.mc.world.getBlockState(blockPos);
            AxisAlignedBB axisAlignedBB = iBlockState.getSelectedBoundingBox(Class468.mc.world, blockPos);
            if (this.Field2593.getValue().booleanValue()) {
                switch (this.Field2603.sideHit) {
                    case DOWN: {
                        axisAlignedBB = new AxisAlignedBB(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
                        break;
                    }
                    case UP: {
                        axisAlignedBB = new AxisAlignedBB(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ, axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
                        break;
                    }
                    case NORTH: {
                        axisAlignedBB = new AxisAlignedBB(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
                        break;
                    }
                    case SOUTH: {
                        axisAlignedBB = new AxisAlignedBB(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ, axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
                        break;
                    }
                    case EAST: {
                        axisAlignedBB = new AxisAlignedBB(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ, axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
                        break;
                    }
                    case WEST: {
                        axisAlignedBB = new AxisAlignedBB(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
                    }
                }
            }
            this.Method1172(axisAlignedBB);
        }
    }

    public Class468() {
        super("BlockHighlight", Category.RENDER);
    }

    @Subscriber
    public void Method2216(Class40 class40) {
        block0: {
            if (this.Field2596.getValue().booleanValue()) break block0;
            class40.setCanceled(true);
        }
    }

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        if (Class468.mc.world == null || Class468.mc.player == null) {
            return;
        }
        this.Field2603 = Class468.mc.objectMouseOver;
    }
}
