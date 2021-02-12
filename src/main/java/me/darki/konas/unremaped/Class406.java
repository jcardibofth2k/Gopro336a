package me.darki.konas.unremaped;

import cookiedragon.eventsystem.Subscriber;
import java.awt.Color;

import me.darki.konas.ColorValue;
import me.darki.konas.ParentSetting;
import me.darki.konas.mixin.mixins.IDestroyBlockProgress;
import me.darki.konas.mixin.mixins.IEntityRenderer;
import me.darki.konas.mixin.mixins.IPlayerControllerMP;
import me.darki.konas.mixin.mixins.IRenderGlobal;
import me.darki.konas.mixin.mixins.IRenderManager;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
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

public class Class406
extends Module {
    public static Setting<ParentSetting> Field1190 = new Setting<>("Breaking", new ParentSetting(false));
    public Setting<Class400> Field1191 = new Setting<>("BRenderMove", Class400.GROW).setParentSetting(Field1190);
    public Setting<Float> Field1192 = new Setting<>("BRange", Float.valueOf(15.0f), Float.valueOf(255.0f), Float.valueOf(5.0f), Float.valueOf(5.0f)).setParentSetting(Field1190);
    public Setting<Boolean> Field1193 = new Setting<>("BOutline", true).setParentSetting(Field1190);
    public Setting<Boolean> Field1194 = new Setting<>("BWireframe", false).setParentSetting(Field1190);
    public Setting<Float> Field1195 = new Setting<>("BWidth", Float.valueOf(1.5f), Float.valueOf(10.0f), Float.valueOf(1.0f), Float.valueOf(1.0f)).setParentSetting(Field1190);
    public Setting<ColorValue> Field1196 = new Setting<>("BOutlineColor", new ColorValue(-65536)).setParentSetting(Field1190);
    public Setting<ColorValue> Field1197 = new Setting<>("BCrossOutlineColor", new ColorValue(-65536)).setParentSetting(Field1190).Method1191(this::Method396);
    public Setting<Boolean> Field1198 = new Setting<>("BFill", true).setParentSetting(Field1190);
    public Setting<ColorValue> Field1199 = new Setting<>("BFillColor", new ColorValue(0x66FF0000)).setParentSetting(Field1190);
    public Setting<ColorValue> Field1200 = new Setting<>("BCrossFillColor", new ColorValue(0x66FF0000)).setParentSetting(Field1190).Method1191(this::Method394);
    public Setting<Boolean> Field1201 = new Setting<>("DynamicColor", true).setParentSetting(Field1190);
    public Setting<Boolean> Field1202 = new Setting<>("BTracer", false).setParentSetting(Field1190);
    public Setting<ColorValue> Field1203 = new Setting<>("BTracerColor", new ColorValue(-65536)).setParentSetting(Field1190);
    public Setting<Boolean> Field1204 = new Setting<>("ShowName", false).setParentSetting(Field1190);
    public static Setting<ParentSetting> Field1205 = new Setting<>("Placing", new ParentSetting(false));
    public Setting<Boolean> Field1206 = new Setting<>("POutline", true).setParentSetting(Field1205);
    public Setting<Boolean> Field1207 = new Setting<>("PWireframe", false).setParentSetting(Field1205);
    public Setting<Float> Field1208 = new Setting<>("PWidth", Float.valueOf(1.5f), Float.valueOf(10.0f), Float.valueOf(1.0f), Float.valueOf(1.0f)).setParentSetting(Field1205);
    public Setting<ColorValue> Field1209 = new Setting<>("POutlineColor", new ColorValue(-16776961)).setParentSetting(Field1205);
    public Setting<Boolean> Field1210 = new Setting<>("PFill", true).setParentSetting(Field1205);
    public Setting<ColorValue> Field1211 = new Setting<>("PFillColor", new ColorValue(0x660000FF)).setParentSetting(Field1205);

    public static Color Method1169(Color color, Color color2, double d, int n) {
        double d2 = Class406.Method1170(color.getRed(), color2.getRed(), d);
        double d3 = Class406.Method1170(color.getGreen(), color2.getGreen(), d);
        double d4 = Class406.Method1170(color.getBlue(), color2.getBlue(), d);
        return new Color((int)Math.round(d2), (int)Math.round(d3), (int)Math.round(d4), n);
    }

    @Override
    public boolean Method396() {
        return this.Field1191.getValue() == Class400.CROSS;
    }

    public boolean Method394() {
        return this.Field1191.getValue() == Class400.CROSS;
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
        ((IEntityRenderer)Class406.mc.entityRenderer).Method1909(mc.getRenderPartialTicks());
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
    public void Method139(Class89 class89) {
        if (Class406.mc.player == null || Class406.mc.world == null) {
            return;
        }
        if (Class406.mc.playerController.getIsHittingBlock()) {
            float f = ((IPlayerControllerMP)Class406.mc.playerController).Method295();
            BlockPos blockPos = ((IPlayerControllerMP)Class406.mc.playerController).Method296();
            AxisAlignedBB axisAlignedBB = Class406.mc.world.getBlockState(blockPos).getBoundingBox((IBlockAccess)Class406.mc.world, blockPos).offset(blockPos);
            switch (Class402.Field1230[((Class400)((Object)this.Field1191.getValue())).ordinal()]) {
                case 1: {
                    this.Method1173(axisAlignedBB.shrink(0.5 - (double)f * 0.5), (ColorValue)this.Field1199.getValue(), (ColorValue)this.Field1196.getValue(), f);
                    break;
                }
                case 2: {
                    this.Method1173(axisAlignedBB.shrink((double)f * 0.5), (ColorValue)this.Field1199.getValue(), (ColorValue)this.Field1196.getValue(), f);
                    break;
                }
                case 3: {
                    this.Method1173(axisAlignedBB.shrink(0.5 - (double)f * 0.5), (ColorValue)this.Field1199.getValue(), (ColorValue)this.Field1196.getValue(), f);
                    this.Method1173(axisAlignedBB.shrink((double)f * 0.5), (ColorValue)this.Field1200.getValue(), (ColorValue)this.Field1197.getValue(), f);
                    break;
                }
                default: {
                    this.Method1173(axisAlignedBB, (ColorValue)this.Field1199.getValue(), (ColorValue)this.Field1196.getValue(), f);
                    break;
                }
            }
            if (((Boolean)this.Field1202.getValue()).booleanValue()) {
                Vec3d vec3d = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-((float)Math.toRadians(Class406.mc.player.rotationPitch))).rotateYaw(-((float)Math.toRadians(Class406.mc.player.rotationYaw)));
                this.Method732(vec3d.x, vec3d.y + (double)Class406.mc.player.getEyeHeight(), vec3d.z, (double)blockPos.getX() - ((IRenderManager)mc.getRenderManager()).Method69() + 0.5, (double)blockPos.getY() - ((IRenderManager)mc.getRenderManager()).Method70() + 0.5, (double)blockPos.getZ() - ((IRenderManager)mc.getRenderManager()).Method71() + 0.5, ((ColorValue)this.Field1203.getValue()).Method774());
            }
        }
        ((IRenderGlobal)Class406.mc.renderGlobal).Method74().forEach(this::Method1171);
    }

    public void Method1171(Integer n, DestroyBlockProgress destroyBlockProgress) {
        this.Method1175(destroyBlockProgress);
    }

    public void Method1172(AxisAlignedBB axisAlignedBB) {
        block3: {
            if (((Boolean)this.Field1210.getValue()).booleanValue()) {
                Class507.Method1386();
                Class507.Method1379(axisAlignedBB, (ColorValue)this.Field1211.getValue());
                Class507.Method1385();
            }
            if (!((Boolean)this.Field1206.getValue()).booleanValue()) break block3;
            Class507.Method1386();
            if (((Boolean)this.Field1207.getValue()).booleanValue()) {
                Class523.Method1219(axisAlignedBB.offset(-((IRenderManager)Module.mc.getRenderManager()).Method69(), -((IRenderManager)Module.mc.getRenderManager()).Method70(), -((IRenderManager)Module.mc.getRenderManager()).Method71()), ((ColorValue)this.Field1209.getValue()).Method774(), ((Float)this.Field1208.getValue()).floatValue());
            } else {
                Class507.Method1374(axisAlignedBB, ((Float)this.Field1208.getValue()).floatValue(), (ColorValue)this.Field1209.getValue());
            }
            Class507.Method1385();
        }
    }

    public void Method1173(AxisAlignedBB axisAlignedBB, ColorValue colorValue, ColorValue class4402, float f) {
        block4: {
            if (((Boolean)this.Field1201.getValue()).booleanValue()) {
                colorValue = new ColorValue(Class406.Method1174(f, colorValue.Method782()).getRGB());
                class4402 = colorValue.Method784(class4402.Method782());
            }
            if (((Boolean)this.Field1198.getValue()).booleanValue()) {
                Class507.Method1386();
                Class507.Method1379(axisAlignedBB, colorValue);
                Class507.Method1385();
            }
            if (!((Boolean)this.Field1193.getValue()).booleanValue()) break block4;
            Class507.Method1386();
            if (((Boolean)this.Field1194.getValue()).booleanValue()) {
                Class523.Method1219(axisAlignedBB.offset(-((IRenderManager)mc.getRenderManager()).Method69(), -((IRenderManager)mc.getRenderManager()).Method70(), -((IRenderManager)mc.getRenderManager()).Method71()), class4402.Method774(), ((Float)this.Field1195.getValue()).floatValue());
            } else {
                Class507.Method1374(axisAlignedBB, ((Float)this.Field1195.getValue()).floatValue(), class4402);
            }
            Class507.Method1385();
        }
    }

    public Class406() {
        super("Interactions", "Render interactions with the world", Category.RENDER, new String[0]);
    }

    public static Color Method1174(float f, int n) {
        if ((double)f < 0.5) {
            return Class406.Method1169(Color.RED, Color.YELLOW, (double)f / 0.5, n);
        }
        return Class406.Method1169(Color.YELLOW, Color.GREEN, ((double)f - 0.5) / 0.5, n);
    }

    public void Method1175(DestroyBlockProgress destroyBlockProgress) {
        block19: {
            if (destroyBlockProgress == null) break block19;
            BlockPos blockPos = destroyBlockProgress.getPosition();
            if (Class406.mc.playerController.getIsHittingBlock() && ((IPlayerControllerMP)Class406.mc.playerController).Method296().equals((Object)blockPos)) {
                return;
            }
            if (Class406.mc.player.getDistance((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5) > (double)((Float)this.Field1192.getValue()).floatValue()) {
                return;
            }
            float f = Math.min(1.0f, (float)destroyBlockProgress.getPartialBlockDamage() / 8.0f);
            AxisAlignedBB axisAlignedBB = Class406.mc.world.getBlockState(blockPos).getBoundingBox((IBlockAccess)Class406.mc.world, blockPos).offset(blockPos);
            switch (Class402.Field1230[((Class400)((Object)this.Field1191.getValue())).ordinal()]) {
                case 1: {
                    this.Method1173(axisAlignedBB.shrink(0.5 - (double)f * 0.5), (ColorValue)this.Field1199.getValue(), (ColorValue)this.Field1196.getValue(), f);
                    break;
                }
                case 2: {
                    this.Method1173(axisAlignedBB.shrink((double)f * 0.5), (ColorValue)this.Field1199.getValue(), (ColorValue)this.Field1196.getValue(), f);
                    break;
                }
                case 3: {
                    this.Method1173(axisAlignedBB.shrink(0.5 - (double)f * 0.5), (ColorValue)this.Field1199.getValue(), (ColorValue)this.Field1196.getValue(), f);
                    this.Method1173(axisAlignedBB.shrink((double)f * 0.5), (ColorValue)this.Field1200.getValue(), (ColorValue)this.Field1197.getValue(), f);
                    break;
                }
                default: {
                    this.Method1173(axisAlignedBB, (ColorValue)this.Field1199.getValue(), (ColorValue)this.Field1196.getValue(), f);
                    break;
                }
            }
            if (((Boolean)this.Field1202.getValue()).booleanValue()) {
                Vec3d vec3d = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-((float)Math.toRadians(Class406.mc.player.rotationPitch))).rotateYaw(-((float)Math.toRadians(Class406.mc.player.rotationYaw)));
                this.Method732(vec3d.x, vec3d.y + (double)Class406.mc.player.getEyeHeight(), vec3d.z, (double)blockPos.getX() - ((IRenderManager)mc.getRenderManager()).Method69() + 0.5, (double)blockPos.getY() - ((IRenderManager)mc.getRenderManager()).Method70() + 0.5, (double)blockPos.getZ() - ((IRenderManager)mc.getRenderManager()).Method71() + 0.5, ((ColorValue)this.Field1203.getValue()).Method774());
            }
            if (((Boolean)this.Field1204.getValue()).booleanValue()) {
                int n = ((IDestroyBlockProgress)destroyBlockProgress).Method908();
                Entity entity = Class406.mc.world.getEntityByID(n);
                if (entity == null || entity == Class406.mc.player) {
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
                EntityPlayerSP entityPlayerSP = Class406.mc.player;
                float f5 = 1.0f;
                try {
                    Class502.Method1395(f2, f3, f4, (EntityPlayer)entityPlayerSP, f5);
                }
                catch (Exception exception) {
                    // empty catch block
                }
                GlStateManager.disableDepth();
                GlStateManager.disableTexture2D();
                GlStateManager.disableLighting();
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                GlStateManager.scale((double)0.15, (double)0.15, (double)1.0);
                Class425.Field958.Method826(string, (float)(-((double)Class425.Field958.Method830(string) / 2.0)), (int)(-Class425.Field958.Method831(string) / 2.0f), -1);
                GlStateManager.scale((double)6.666666666666667, (double)6.666666666666667, (double)1.0);
                GlStateManager.enableLighting();
                GlStateManager.enableTexture2D();
                GlStateManager.enableDepth();
                GlStateManager.popMatrix();
            }
        }
    }
}
