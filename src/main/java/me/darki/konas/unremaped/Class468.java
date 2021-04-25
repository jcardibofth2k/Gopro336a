package me.darki.konas.unremaped;

import cookiedragon.eventsystem.Subscriber;
import java.awt.Color;

import me.darki.konas.setting.ColorValue;
import me.darki.konas.event.events.TickEvent;
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
    public static Setting<Class462> mode = new Setting<>("Mode", Class462.FULL);
    public Setting<Boolean> facing = new Setting<>("Facing", false);
    public static Setting<Boolean> notWhenTrajectories = new Setting<>("NotWhenTrajectories", true);
    public Setting<Boolean> depth = new Setting<>("Depth", false);
    public Setting<Boolean> vanilla = new Setting<>("Vanilla", false);
    public static Setting<Float> radius = new Setting<>("Radius", Float.valueOf(0.1f), Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(0.1f)).visibleIf(Class468::Method519);
    public static Setting<Integer> slices = new Setting<>("Slices", 8, 24, 3, 1).visibleIf(Class468::Method393);
    public static Setting<Float> scale = new Setting<>("Scale", Float.valueOf(0.6f), Float.valueOf(4.0f), Float.valueOf(0.1f), Float.valueOf(0.1f)).visibleIf(Class468::Method388);
    public static Setting<Double> spinSpeed = new Setting<>("SpinSpeed", 15.0, 30.0, 0.0, 0.1).visibleIf(Class468::Method394);
    public static Setting<Float> width = new Setting<>("Width", Float.valueOf(1.5f), Float.valueOf(10.0f), Float.valueOf(0.1f), Float.valueOf(0.1f));
    public Setting<ColorValue> color = new Setting<>("Color", new ColorValue(575714484));
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
                if (mode.getValue() != Class462.VECTOR) {
                    if (mode.getValue() != Class462.BASED) {
                        return;
                    }
                }
                if (((Boolean)notWhenTrajectories.getValue()).booleanValue() && !Field2604.Method737(75.0)) {
                    return;
                }
                rayTraceResult = Class468.mc.objectMouseOver;
                if (rayTraceResult == null) {
                    return;
                }
                if (rayTraceResult.typeOfHit != RayTraceResult.Type.BLOCK) break block11;
                if (mode.getValue() != Class462.VECTOR) break block12;
                GlStateManager.pushMatrix();
                Class507.Method1386();
                GlStateManager.glLineWidth((float)((Float)width.getValue()).floatValue());
                GlStateManager.disableTexture2D();
                GlStateManager.depthMask((boolean)false);
                if (!((Boolean)this.depth.getValue()).booleanValue()) {
                    GlStateManager.disableDepth();
                }
                GL11.glLineWidth((float)((Float)width.getValue()).floatValue());
                GL11.glColor4f((float)((float)((ColorValue)this.color.getValue()).Method769() / 255.0f), (float)((float)((ColorValue)this.color.getValue()).Method770() / 255.0f), (float)((float)((ColorValue)this.color.getValue()).Method779() / 255.0f), (float)((float)((ColorValue)this.color.getValue()).Method782() / 255.0f));
                GlStateManager.translate((double)(rayTraceResult.hitVec.x - ((IRenderManager)mc.getRenderManager()).Method69()), (double)(rayTraceResult.hitVec.y - ((IRenderManager)mc.getRenderManager()).Method70()), (double)(rayTraceResult.hitVec.z - ((IRenderManager)mc.getRenderManager()).Method71()));
                EnumFacing enumFacing = rayTraceResult.sideHit;
                switch (enumFacing) {
                    case NORTH: 
                    case SOUTH: {
                        GlStateManager.rotate((float)90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                        break;
                    }
                    case EAST: 
                    case WEST: {
                        GlStateManager.rotate((float)90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                        break;
                    }
                }
                Cylinder cylinder = new Cylinder();
                GlStateManager.rotate((float)-90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                cylinder.setDrawStyle(100011);
                cylinder.draw(((Float)radius.getValue()).floatValue() * 2.0f, ((Float)radius.getValue()).floatValue(), 0.0f, ((Integer)slices.getValue()).intValue(), 1);
                GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                if (!((Boolean)this.depth.getValue()).booleanValue()) {
                    GlStateManager.enableDepth();
                }
                GlStateManager.depthMask((boolean)true);
                GlStateManager.enableTexture2D();
                Class507.Method1385();
                GlStateManager.popMatrix();
                break block11;
            }
            if (mode.getValue() != Class462.BASED) break block11;
            this.Method2215(rayTraceResult, ((Float)scale.getValue()).floatValue(), (Double)spinSpeed.getValue(), ((ColorValue)this.color.getValue()).Method775());
        }
    }

    public static boolean Method388() {
        return mode.getValue() == Class462.BASED;
    }

    public void Method1172(@NotNull AxisAlignedBB axisAlignedBB) {
        if (Class468.mc.world == null || Class468.mc.player == null) {
            return;
        }
        if (((Boolean)notWhenTrajectories.getValue()).booleanValue() && !Field2604.Method737(75.0)) {
            return;
        }
        GlStateManager.pushMatrix();
        Class507.Method1386();
        if (((Boolean)this.depth.getValue()).booleanValue()) {
            GlStateManager.enableDepth();
            GlStateManager.depthMask((boolean)true);
        }
        if (mode.getValue() == Class462.FULL) {
            Class507.Method1379(axisAlignedBB, (ColorValue)this.color.getValue());
        }
        Class507.Method1374(axisAlignedBB, ((Float)width.getValue()).floatValue(), ((ColorValue)this.color.getValue()).Method784(255));
        GlStateManager.resetColor();
        Class507.Method1385();
        GlStateManager.popMatrix();
    }

    public void Method2215(RayTraceResult rayTraceResult, float f, double d, Color color) {
        GL11.glPushAttrib((int)1048575);
        GL11.glPushMatrix();
        GL11.glDisable((int)3008);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDepthMask((boolean)false);
        GL11.glDisable((int)2929);
        GL11.glDisable((int)2884);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4352);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2896);
        GL11.glLineWidth((float)2.0f);
        GL11.glColor4f((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
        GL11.glTranslated((double)(rayTraceResult.hitVec.x - ((IRenderManager)mc.getRenderManager()).Method69()), (double)(rayTraceResult.hitVec.y - ((IRenderManager)mc.getRenderManager()).Method70()), (double)(rayTraceResult.hitVec.z - ((IRenderManager)mc.getRenderManager()).Method71()));
        switch (rayTraceResult.sideHit) {
            case WEST: {
                GL11.glRotatef((float)90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                GL11.glRotatef((float)-90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                break;
            }
            case EAST: {
                GL11.glRotatef((float)-90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                GL11.glRotatef((float)-90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                break;
            }
            case NORTH: {
                GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                break;
            }
            case UP: {
                GL11.glRotatef((float)-90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                break;
            }
            case DOWN: {
                GL11.glRotatef((float)90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                break;
            }
        }
        double d2 = Math.ceil((double)System.currentTimeMillis() / Math.abs(d - 30.1));
        d2 %= 360.0;
        if (d == 0.0) {
            d2 = 0.0;
        }
        GL11.glRotated((double)d2, (double)0.0, (double)0.0, (double)1.0);
        GL11.glScalef((float)(f * 0.5f), (float)(f * 0.5f), (float)(f * 0.5f));
        GL11.glBegin((int)3);
        GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
        GL11.glVertex3d((double)0.0, (double)1.0, (double)0.0);
        GL11.glVertex3d((double)1.0, (double)1.0, (double)0.0);
        GL11.glEnd();
        GL11.glBegin((int)3);
        GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
        GL11.glVertex3d((double)1.0, (double)0.0, (double)0.0);
        GL11.glVertex3d((double)1.0, (double)-1.0, (double)0.0);
        GL11.glEnd();
        GL11.glBegin((int)3);
        GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
        GL11.glVertex3d((double)0.0, (double)-1.0, (double)0.0);
        GL11.glVertex3d((double)-1.0, (double)-1.0, (double)0.0);
        GL11.glEnd();
        GL11.glBegin((int)3);
        GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
        GL11.glVertex3d((double)-1.0, (double)0.0, (double)0.0);
        GL11.glVertex3d((double)-1.0, (double)1.0, (double)0.0);
        GL11.glEnd();
        GL11.glEnable((int)2896);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)2848);
        GL11.glEnable((int)2884);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3008);
        GL11.glPopMatrix();
        GL11.glPopAttrib();
    }

    public static boolean Method394() {
        return mode.getValue() == Class462.BASED;
    }

    public static boolean Method519() {
        return mode.getValue() == Class462.VECTOR;
    }

    public static boolean Method393() {
        return mode.getValue() == Class462.VECTOR;
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
            if (mode.getValue() == Class462.VECTOR || mode.getValue() == Class462.BASED) {
                return;
            }
            if (this.Field2603.typeOfHit != RayTraceResult.Type.BLOCK) break block12;
            BlockPos blockPos = this.Field2603.getBlockPos();
            IBlockState iBlockState = Class468.mc.world.getBlockState(blockPos);
            AxisAlignedBB axisAlignedBB = iBlockState.getSelectedBoundingBox((World)Class468.mc.world, blockPos);
            if (((Boolean)this.facing.getValue()).booleanValue()) {
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
        super("BlockHighlight", Category.RENDER, new String[0]);
    }

    @Subscriber
    public void Method2216(Class40 class40) {
        block0: {
            if (((Boolean)this.vanilla.getValue()).booleanValue()) break block0;
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