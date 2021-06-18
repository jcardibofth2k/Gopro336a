package me.darki.konas.module.render;

import cookiedragon.eventsystem.Subscriber;
import java.awt.Color;

import me.darki.konas.setting.ColorValue;
import me.darki.konas.setting.ParentSetting;
import me.darki.konas.mixin.mixins.IDestroyBlockProgress;
import me.darki.konas.mixin.mixins.IEntityRenderer;
import me.darki.konas.mixin.mixins.IPlayerControllerMP;
import me.darki.konas.mixin.mixins.IRenderGlobal;
import me.darki.konas.mixin.mixins.IRenderManager;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.*;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.DestroyBlockProgress;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class Interactions
extends Module {
    public static Setting<ParentSetting> breaking = new Setting<>("Breaking", new ParentSetting(false));
    public Setting<Class400> bRenderMove = new Setting<>("BRenderMove", Class400.GROW).setParentSetting(breaking);
    public Setting<Float> bRange = new Setting<>("BRange", Float.valueOf(15.0f), Float.valueOf(255.0f), Float.valueOf(5.0f), Float.valueOf(5.0f)).setParentSetting(breaking);
    public Setting<Boolean> bOutline = new Setting<>("BOutline", true).setParentSetting(breaking);
    public Setting<Boolean> bWireframe = new Setting<>("BWireframe", false).setParentSetting(breaking);
    public Setting<Float> bWidth = new Setting<>("BWidth", Float.valueOf(1.5f), Float.valueOf(10.0f), Float.valueOf(1.0f), Float.valueOf(1.0f)).setParentSetting(breaking);
    public Setting<ColorValue> bOutlineColor = new Setting<>("BOutlineColor", new ColorValue(-65536)).setParentSetting(breaking);
    public Setting<ColorValue> bCrossOutlineColor = new Setting<>("BCrossOutlineColor", new ColorValue(-65536)).setParentSetting(breaking).visibleIf(this::Method396);
    public Setting<Boolean> bFill = new Setting<>("BFill", true).setParentSetting(breaking);
    public Setting<ColorValue> bFillColor = new Setting<>("BFillColor", new ColorValue(0x66FF0000)).setParentSetting(breaking);
    public Setting<ColorValue> bCrossFillColor = new Setting<>("BCrossFillColor", new ColorValue(0x66FF0000)).setParentSetting(breaking).visibleIf(this::Method394);
    public Setting<Boolean> dynamicColor = new Setting<>("DynamicColor", true).setParentSetting(breaking);
    public Setting<Boolean> bTracer = new Setting<>("BTracer", false).setParentSetting(breaking);
    public Setting<ColorValue> bTracerColor = new Setting<>("BTracerColor", new ColorValue(-65536)).setParentSetting(breaking);
    public Setting<Boolean> showName = new Setting<>("ShowName", false).setParentSetting(breaking);
    public static Setting<ParentSetting> placing = new Setting<>("Placing", new ParentSetting(false));
    public Setting<Boolean> pOutline = new Setting<>("POutline", true).setParentSetting(placing);
    public Setting<Boolean> pWireframe = new Setting<>("PWireframe", false).setParentSetting(placing);
    public Setting<Float> pWidth = new Setting<>("PWidth", Float.valueOf(1.5f), Float.valueOf(10.0f), Float.valueOf(1.0f), Float.valueOf(1.0f)).setParentSetting(placing);
    public Setting<ColorValue> pOutlineColor = new Setting<>("POutlineColor", new ColorValue(-16776961)).setParentSetting(placing);
    public Setting<Boolean> pFill = new Setting<>("PFill", true).setParentSetting(placing);
    public Setting<ColorValue> pFillColor = new Setting<>("PFillColor", new ColorValue(0x660000FF)).setParentSetting(placing);

    public static Color Method1169(Color color, Color color2, double d, int n) {
        double d2 = Interactions.Method1170(color.getRed(), color2.getRed(), d);
        double d3 = Interactions.Method1170(color.getGreen(), color2.getGreen(), d);
        double d4 = Interactions.Method1170(color.getBlue(), color2.getBlue(), d);
        return new Color((int)Math.round(d2), (int)Math.round(d3), (int)Math.round(d4), n);
    }

    //@Override
    public boolean Method396() {
        return this.bRenderMove.getValue() == Class400.CROSS;
    }

    public boolean Method394() {
        return this.bRenderMove.getValue() == Class400.CROSS;
    }

    public void Method732(double d, double d2, double d3, double d4, double d5, double d6, int n) {
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glLineWidth((float)1.5f);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glColor4f((float)((float)(n >> 16 & 0xFF) / 255.0f), (float)((float)(n >> 8 & 0xFF) / 255.0f), (float)((float)(n & 0xFF) / 255.0f), (float)((float)(n >> 24 & 0xFF) / 255.0f));
        GlStateManager.disableLighting();
        GL11.glLoadIdentity();
        ((IEntityRenderer) Interactions.mc.entityRenderer).orientCamera(mc.getRenderPartialTicks());
        GL11.glEnable((int)2848);
        GL11.glBegin((int)1);
        GL11.glVertex3d((double)d, (double)d2, (double)d3);
        GL11.glVertex3d((double)d4, (double)d5, (double)d6);
        GL11.glVertex3d((double)d4, (double)d5, (double)d6);
        GL11.glEnd();
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glColor3d((double)1.0, (double)1.0, (double)1.0);
        GlStateManager.enableLighting();
    }

    public static double Method1170(double d, double d2, double d3) {
        return d + (d2 - d) * d3;
    }

    @Subscriber
    public void Method139(Render3DEvent render3DEvent) {
        if (Interactions.mc.player == null || Interactions.mc.world == null) {
            return;
        }
        if (Interactions.mc.playerController.getIsHittingBlock()) {
            float f = ((IPlayerControllerMP) Interactions.mc.playerController).getCurBlockDamageMP();
            BlockPos blockPos = ((IPlayerControllerMP) Interactions.mc.playerController).getCurrentBlock();
            AxisAlignedBB axisAlignedBB = Interactions.mc.world.getBlockState(blockPos).getBoundingBox((IBlockAccess) Interactions.mc.world, blockPos).offset(blockPos);
            switch (Class402.Field1230[((Class400)((Object)this.bRenderMove.getValue())).ordinal()]) {
                case 1: {
                    this.Method1173(axisAlignedBB.shrink(0.5 - (double)f * 0.5), (ColorValue)this.bFillColor.getValue(), (ColorValue)this.bOutlineColor.getValue(), f);
                    break;
                }
                case 2: {
                    this.Method1173(axisAlignedBB.shrink((double)f * 0.5), (ColorValue)this.bFillColor.getValue(), (ColorValue)this.bOutlineColor.getValue(), f);
                    break;
                }
                case 3: {
                    this.Method1173(axisAlignedBB.shrink(0.5 - (double)f * 0.5), (ColorValue)this.bFillColor.getValue(), (ColorValue)this.bOutlineColor.getValue(), f);
                    this.Method1173(axisAlignedBB.shrink((double)f * 0.5), (ColorValue)this.bCrossFillColor.getValue(), (ColorValue)this.bCrossOutlineColor.getValue(), f);
                    break;
                }
                default: {
                    this.Method1173(axisAlignedBB, (ColorValue)this.bFillColor.getValue(), (ColorValue)this.bOutlineColor.getValue(), f);
                    break;
                }
            }
            if (((Boolean)this.bTracer.getValue()).booleanValue()) {
                Vec3d vec3d = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-((float)Math.toRadians(Interactions.mc.player.rotationPitch))).rotateYaw(-((float)Math.toRadians(Interactions.mc.player.rotationYaw)));
                this.Method732(vec3d.x, vec3d.y + (double) Interactions.mc.player.getEyeHeight(), vec3d.z, (double)blockPos.getX() - ((IRenderManager)mc.getRenderManager()).getRenderPosX() + 0.5, (double)blockPos.getY() - ((IRenderManager)mc.getRenderManager()).getRenderPosY() + 0.5, (double)blockPos.getZ() - ((IRenderManager)mc.getRenderManager()).getRenderPosZ() + 0.5, ((ColorValue)this.bTracerColor.getValue()).Method774());
            }
        }
        ((IRenderGlobal) Interactions.mc.renderGlobal).getDamagedBlocks().forEach(this::Method1171);
    }

    public void Method1171(Integer n, DestroyBlockProgress destroyBlockProgress) {
        this.Method1175(destroyBlockProgress);
    }

    public void Method1172(AxisAlignedBB axisAlignedBB) {
        block3: {
            if (((Boolean)this.pFill.getValue()).booleanValue()) {
                EspRenderUtil.Method1386();
                EspRenderUtil.Method1379(axisAlignedBB, (ColorValue)this.pFillColor.getValue());
                EspRenderUtil.Method1385();
            }
            if (!((Boolean)this.pOutline.getValue()).booleanValue()) break block3;
            EspRenderUtil.Method1386();
            if (((Boolean)this.pWireframe.getValue()).booleanValue()) {
                Class523.Method1219(axisAlignedBB.offset(-((IRenderManager)Module.mc.getRenderManager()).getRenderPosX(), -((IRenderManager)Module.mc.getRenderManager()).getRenderPosY(), -((IRenderManager)Module.mc.getRenderManager()).getRenderPosZ()), ((ColorValue)this.pOutlineColor.getValue()).Method774(), ((Float)this.pWidth.getValue()).floatValue());
            } else {
                EspRenderUtil.Method1374(axisAlignedBB, ((Float)this.pWidth.getValue()).floatValue(), (ColorValue)this.pOutlineColor.getValue());
            }
            EspRenderUtil.Method1385();
        }
    }

    public void Method1173(AxisAlignedBB axisAlignedBB, ColorValue colorValue, ColorValue class4402, float f) {
        block4: {
            if (((Boolean)this.dynamicColor.getValue()).booleanValue()) {
                colorValue = new ColorValue(Interactions.Method1174(f, colorValue.Method782()).getRGB());
                class4402 = colorValue.Method784(class4402.Method782());
            }
            if (((Boolean)this.bFill.getValue()).booleanValue()) {
                EspRenderUtil.Method1386();
                EspRenderUtil.Method1379(axisAlignedBB, colorValue);
                EspRenderUtil.Method1385();
            }
            if (!((Boolean)this.bOutline.getValue()).booleanValue()) break block4;
            EspRenderUtil.Method1386();
            if (((Boolean)this.bWireframe.getValue()).booleanValue()) {
                Class523.Method1219(axisAlignedBB.offset(-((IRenderManager)mc.getRenderManager()).getRenderPosX(), -((IRenderManager)mc.getRenderManager()).getRenderPosY(), -((IRenderManager)mc.getRenderManager()).getRenderPosZ()), class4402.Method774(), ((Float)this.bWidth.getValue()).floatValue());
            } else {
                EspRenderUtil.Method1374(axisAlignedBB, ((Float)this.bWidth.getValue()).floatValue(), class4402);
            }
            EspRenderUtil.Method1385();
        }
    }

    public Interactions() {
        super("Interactions", "Render interactions with the world", Category.RENDER, new String[0]);
    }

    public static Color Method1174(float f, int n) {
        if ((double)f < 0.5) {
            return Interactions.Method1169(Color.RED, Color.YELLOW, (double)f / 0.5, n);
        }
        return Interactions.Method1169(Color.YELLOW, Color.GREEN, ((double)f - 0.5) / 0.5, n);
    }

    public void Method1175(DestroyBlockProgress destroyBlockProgress) {
        block19: {
            if (destroyBlockProgress == null) break block19;
            BlockPos blockPos = destroyBlockProgress.getPosition();
            if (Interactions.mc.playerController.getIsHittingBlock() && ((IPlayerControllerMP) Interactions.mc.playerController).getCurrentBlock().equals((Object)blockPos)) {
                return;
            }
            if (Interactions.mc.player.getDistance((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5) > (double)((Float)this.bRange.getValue()).floatValue()) {
                return;
            }
            float f = Math.min(1.0f, (float)destroyBlockProgress.getPartialBlockDamage() / 8.0f);
            AxisAlignedBB axisAlignedBB = Interactions.mc.world.getBlockState(blockPos).getBoundingBox((IBlockAccess) Interactions.mc.world, blockPos).offset(blockPos);
            switch (Class402.Field1230[((Class400)((Object)this.bRenderMove.getValue())).ordinal()]) {
                case 1: {
                    this.Method1173(axisAlignedBB.shrink(0.5 - (double)f * 0.5), (ColorValue)this.bFillColor.getValue(), (ColorValue)this.bOutlineColor.getValue(), f);
                    break;
                }
                case 2: {
                    this.Method1173(axisAlignedBB.shrink((double)f * 0.5), (ColorValue)this.bFillColor.getValue(), (ColorValue)this.bOutlineColor.getValue(), f);
                    break;
                }
                case 3: {
                    this.Method1173(axisAlignedBB.shrink(0.5 - (double)f * 0.5), (ColorValue)this.bFillColor.getValue(), (ColorValue)this.bOutlineColor.getValue(), f);
                    this.Method1173(axisAlignedBB.shrink((double)f * 0.5), (ColorValue)this.bCrossFillColor.getValue(), (ColorValue)this.bCrossOutlineColor.getValue(), f);
                    break;
                }
                default: {
                    this.Method1173(axisAlignedBB, (ColorValue)this.bFillColor.getValue(), (ColorValue)this.bOutlineColor.getValue(), f);
                    break;
                }
            }
            if (((Boolean)this.bTracer.getValue()).booleanValue()) {
                Vec3d vec3d = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-((float)Math.toRadians(Interactions.mc.player.rotationPitch))).rotateYaw(-((float)Math.toRadians(Interactions.mc.player.rotationYaw)));
                this.Method732(vec3d.x, vec3d.y + (double) Interactions.mc.player.getEyeHeight(), vec3d.z, (double)blockPos.getX() - ((IRenderManager)mc.getRenderManager()).getRenderPosX() + 0.5, (double)blockPos.getY() - ((IRenderManager)mc.getRenderManager()).getRenderPosY() + 0.5, (double)blockPos.getZ() - ((IRenderManager)mc.getRenderManager()).getRenderPosZ() + 0.5, ((ColorValue)this.bTracerColor.getValue()).Method774());
            }
            if (((Boolean)this.showName.getValue()).booleanValue()) {
                int n = ((IDestroyBlockProgress)destroyBlockProgress).getMiningPlayerEntId();
                Entity entity = Interactions.mc.world.getEntityByID(n);
                if (entity == null || entity == Interactions.mc.player) {
                    return;
                }
                String string = entity.getName();
                GlStateManager.pushMatrix();
                BlockPos blockPos2 = blockPos;
                int n2 = blockPos2.getX();
                float f2 = (float)n2 + 0.5f;
                BlockPos blockPos3 = blockPos;
                int n3 = blockPos3.getY();
                float f3 = (float)n3 + 0.5f;
                BlockPos blockPos4 = blockPos;
                int n4 = blockPos4.getZ();
                float f4 = (float)n4 + 0.5f;
                EntityPlayerSP entityPlayerSP = Interactions.mc.player;
                float f5 = 1.0f;
                try {
                    RenderUtil3D.Method1395(f2, f3, f4, (EntityPlayer)entityPlayerSP, f5);
                }
                catch (Exception exception) {
                    // empty catch block
                }
                GlStateManager.disableDepth();
                GlStateManager.disableTexture2D();
                GlStateManager.disableLighting();
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                GlStateManager.scale((double)0.15, (double)0.15, (double)1.0);
                NameTags.Field958.Method826(string, (float)(-((double) NameTags.Field958.Method830(string) / 2.0)), (int)(-NameTags.Field958.Method831(string) / 2.0f), -1);
                GlStateManager.scale((double)6.666666666666667, (double)6.666666666666667, (double)1.0);
                GlStateManager.enableLighting();
                GlStateManager.enableTexture2D();
                GlStateManager.enableDepth();
                GlStateManager.popMatrix();
            }
        }
    }
}