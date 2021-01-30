package me.darki.konas;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class Class493
extends Module {
    public static Setting<ParentSetting> Field2096 = new Setting<>("Living", new ParentSetting(false));
    public static Setting<Class524> Field2097 = new Setting<>("Preview", new Class524(false, true)).setParentSetting(Field2096);
    public static Setting<Boolean> Field2098 = new Setting<>("Render", false).setParentSetting(Field2096);
    public static Setting<Boolean> Field2099 = new Setting<>("RenderDepth", false).setParentSetting(Field2096);
    public static Setting<Class495> Field2100 = new Setting<>("Glint", Class495.NONE).setParentSetting(Field2096);
    public static Setting<Boolean> Field2101 = new Setting<>("GlintDepth", false).setParentSetting(Field2096);
    public static Setting<Float> Field2102 = new Setting<>("GlintSpeed", Float.valueOf(5.0f), Float.valueOf(20.0f), Float.valueOf(0.1f), Float.valueOf(0.1f)).setParentSetting(Field2096);
    public static Setting<Float> Field2103 = new Setting<>("GlintScale", Float.valueOf(1.0f), Float.valueOf(10.0f), Float.valueOf(0.1f), Float.valueOf(0.1f)).setParentSetting(Field2096);
    public static Setting<ColorValue> Field2104 = new Setting<>("GlintColor", new ColorValue(0x7700FFFF)).setParentSetting(Field2096);
    public static Setting<Boolean> Field2105 = new Setting<>("Fill", false).setParentSetting(Field2096);
    public static Setting<Boolean> Field2106 = new Setting<>("FillDepth", false).setParentSetting(Field2096);
    public static Setting<Boolean> Field2107 = new Setting<>("Lighting", false).setParentSetting(Field2096);
    public static Setting<ColorValue> Field2108 = new Setting<>("FillColor", new ColorValue(0x7700FFFF)).setParentSetting(Field2096);
    public static Setting<Boolean> Field2109 = new Setting<>("Outline", false).setParentSetting(Field2096);
    public static Setting<Boolean> Field2110 = new Setting<>("OutlineDepth", false).setParentSetting(Field2096);
    public static Setting<Float> Field2111 = new Setting<>("Width", Float.valueOf(1.0f), Float.valueOf(10.0f), Float.valueOf(0.1f), Float.valueOf(0.1f)).setParentSetting(Field2096);
    public static Setting<ColorValue> Field2112 = new Setting<>("GOutlineColor", new ColorValue(-16711681)).setParentSetting(Field2096);
    public static Setting<Boolean> Field2113 = new Setting<>("Players", true).setParentSetting(Field2096);
    public static Setting<Boolean> Field2114 = new Setting<>("Self", true).setParentSetting(Field2096);
    public static Setting<Boolean> Field2115 = new Setting<>("Animals", true).setParentSetting(Field2096);
    public static Setting<Boolean> Field2116 = new Setting<>("Monsters", true).setParentSetting(Field2096);
    public static Setting<Boolean> Field2117 = new Setting<>("Invis", true).setParentSetting(Field2096);
    public static Setting<ParentSetting> Field2118 = new Setting<>("Crystal", new ParentSetting(false));
    public static Setting<Boolean> Field2119 = new Setting<>("Crystals", true).setParentSetting(Field2118);
    public static Setting<Boolean> Field2120 = new Setting<>("CRender", false).setParentSetting(Field2118);
    public static Setting<Boolean> Field2121 = new Setting<>("CRenderDepth", false).setParentSetting(Field2118);
    public static Setting<Class495> Field2122 = new Setting<>("CGlint", Class495.NONE).setParentSetting(Field2118);
    public static Setting<Boolean> Field2123 = new Setting<>("CGlintDepth", false).setParentSetting(Field2118);
    public static Setting<Float> Field2124 = new Setting<>("CGlintSpeed", Float.valueOf(5.0f), Float.valueOf(20.0f), Float.valueOf(0.1f), Float.valueOf(0.1f)).setParentSetting(Field2118);
    public static Setting<Float> Field2125 = new Setting<>("CGlintScale", Float.valueOf(1.0f), Float.valueOf(10.0f), Float.valueOf(0.1f), Float.valueOf(0.1f)).setParentSetting(Field2118);
    public static Setting<ColorValue> Field2126 = new Setting<>("CGlintColor", new ColorValue(0x770000FF)).setParentSetting(Field2118);
    public static Setting<Boolean> Field2127 = new Setting<>("CFill", false).setParentSetting(Field2118);
    public static Setting<Boolean> Field2128 = new Setting<>("CFillDepth", false).setParentSetting(Field2118);
    public static Setting<Boolean> Field2129 = new Setting<>("CLighting", false).setParentSetting(Field2118);
    public static Setting<ColorValue> Field2130 = new Setting<>("CFillColor", new ColorValue(0x770000FF)).setParentSetting(Field2118);
    public static Setting<Boolean> Field2131 = new Setting<>("COutline", false).setParentSetting(Field2118);
    public static Setting<Boolean> Field2132 = new Setting<>("COutlineDepth", false).setParentSetting(Field2118);
    public static Setting<Float> Field2133 = new Setting<>("CWidth", Float.valueOf(1.0f), Float.valueOf(10.0f), Float.valueOf(0.1f), Float.valueOf(0.1f)).setParentSetting(Field2118);
    public static Setting<ColorValue> Field2134 = new Setting<>("CGOutlineColor", new ColorValue(-16776961)).setParentSetting(Field2118);
    public static Setting<Float> Field2135 = new Setting<>("Scale", Float.valueOf(1.0f), Float.valueOf(10.0f), Float.valueOf(0.1f), Float.valueOf(0.1f)).setParentSetting(Field2118);
    public static Setting<Float> Field2136 = new Setting<>("SpinSpeed", Float.valueOf(1.0f), Float.valueOf(10.0f), Float.valueOf(0.1f), Float.valueOf(0.1f)).setParentSetting(Field2118);
    public static Setting<Float> Field2137 = new Setting<>("Bounciness", Float.valueOf(1.0f), Float.valueOf(10.0f), Float.valueOf(0.1f), Float.valueOf(0.1f)).setParentSetting(Field2118);
    public static Setting<ParentSetting> Field2138 = new Setting<>("Hand", new ParentSetting(false));
    public static Setting<Boolean> Field2139 = new Setting<>("Hands", true).setParentSetting(Field2138);
    public static Setting<ColorValue> Field2140 = new Setting<>("HandColor", new ColorValue(469762303)).Method1191(Field2139::getValue).setParentSetting(Field2138);
    public static Setting<Class495> Field2141 = new Setting<>("HGlint", Class495.NONE).setParentSetting(Field2138);
    public static Setting<Float> Field2142 = new Setting<>("HGlintSpeed", Float.valueOf(5.0f), Float.valueOf(20.0f), Float.valueOf(0.1f), Float.valueOf(0.1f)).setParentSetting(Field2138);
    public static Setting<Float> Field2143 = new Setting<>("HGlintScale", Float.valueOf(1.0f), Float.valueOf(10.0f), Float.valueOf(0.1f), Float.valueOf(0.1f)).setParentSetting(Field2138);
    public static Setting<ColorValue> Field2144 = new Setting<>("HGlintColor", new ColorValue(0x770000FF)).setParentSetting(Field2138);
    public static ResourceLocation Field2145 = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    public static ResourceLocation Field2146 = new ResourceLocation("konas/textures/enchant_glint.png");
    public static boolean Field2147 = false;

    public static boolean Method513(Entity entity) {
        if (entity == null) {
            return false;
        }
        if (entity.isInvisible() && !((Boolean)Field2117.getValue()).booleanValue()) {
            return false;
        }
        if (entity.equals((Object)Class493.mc.player) && !((Boolean)Field2114.getValue()).booleanValue()) {
            return false;
        }
        if (entity instanceof EntityPlayer && !((Boolean)Field2113.getValue()).booleanValue()) {
            return false;
        }
        if (Class493.Method386(entity) && !((Boolean)Field2115.getValue()).booleanValue()) {
            return false;
        }
        return Class493.Method386(entity) || entity instanceof EntityPlayer || (Boolean)Field2116.getValue() != false;
    }

    public static void Method1948(ColorValue colorValue, ModelBase modelBase, Entity entity, float f, float f2, float f3, float f4, float f5, float f6, float f7) {
        GL11.glPushMatrix();
        GL11.glPushAttrib((int)1048575);
        GL11.glPolygonMode((int)1032, (int)6914);
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2929);
        GL11.glEnable((int)3042);
        Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(Field2100.getValue() == Class495.CUSTOM ? Field2146 : Field2145);
        GL11.glPolygonMode((int)1032, (int)6914);
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2929);
        GL11.glEnable((int)3042);
        GL11.glColor4f((float)((float) colorValue.Method769() / 255.0f), (float)((float) colorValue.Method770() / 255.0f), (float)((float) colorValue.Method779() / 255.0f), (float)((float) colorValue.Method782() / 255.0f));
        GlStateManager.blendFunc((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_COLOR, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE);
        for (int i = 0; i < 2; ++i) {
            GlStateManager.matrixMode((int)5890);
            GlStateManager.loadIdentity();
            float f8 = 0.33333334f * ((Float)Field2103.getValue()).floatValue();
            GlStateManager.scale((float)f8, (float)f8, (float)f8);
            GlStateManager.rotate((float)(30.0f - (float)i * 60.0f), (float)0.0f, (float)0.0f, (float)1.0f);
            GlStateManager.translate((float)0.0f, (float)(((float)entity.ticksExisted + mc.getRenderPartialTicks()) * (0.001f + (float)i * 0.003f) * ((Float)Field2102.getValue()).floatValue()), (float)0.0f);
            GlStateManager.matrixMode((int)5888);
            GL11.glTranslatef((float)f7, (float)f7, (float)f7);
            GlStateManager.color((float)((float) colorValue.Method769() / 255.0f), (float)((float) colorValue.Method770() / 255.0f), (float)((float) colorValue.Method779() / 255.0f), (float)((float) colorValue.Method782() / 255.0f));
            if (((Boolean)Field2101.getValue()).booleanValue()) {
                GL11.glDepthMask((boolean)true);
                GL11.glEnable((int)2929);
            }
            modelBase.render(entity, f, f2, f3, f4, f5, f6);
            if (!((Boolean)Field2101.getValue()).booleanValue()) continue;
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
        }
        GlStateManager.matrixMode((int)5890);
        GlStateManager.loadIdentity();
        GlStateManager.matrixMode((int)5888);
        GlStateManager.blendFunc((GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

    @Subscriber
    public void Method1949(Class102 class102) {
        block0: {
            if (!((Boolean)Field2139.getValue()).booleanValue()) break block0;
            class102.Method341(((ColorValue)Field2140.getValue()).Method769());
            class102.Method344(((ColorValue)Field2140.getValue()).Method770());
            class102.Method1102(((ColorValue)Field2140.getValue()).Method779());
            class102.Method294(((ColorValue)Field2140.getValue()).Method782());
            class102.setCanceled(true);
        }
    }

    public static void Method1950(ColorValue colorValue, ModelBase modelBase, Entity entity, float f, float f2, float f3, float f4, float f5, float f6, float f7) {
        GL11.glPushMatrix();
        GL11.glPushAttrib((int)1048575);
        GL11.glPolygonMode((int)1032, (int)6914);
        GL11.glDisable((int)3553);
        if (((Boolean)Field2107.getValue()).booleanValue()) {
            GL11.glEnable((int)2896);
        } else {
            GL11.glDisable((int)2896);
        }
        GL11.glDisable((int)2929);
        GL11.glEnable((int)2848);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glTranslatef((float)f7, (float)f7, (float)f7);
        GlStateManager.color((float)((float) colorValue.Method769() / 255.0f), (float)((float) colorValue.Method770() / 255.0f), (float)((float) colorValue.Method779() / 255.0f), (float)((float) colorValue.Method782() / 255.0f));
        if (((Boolean)Field2106.getValue()).booleanValue()) {
            GL11.glDepthMask((boolean)true);
            GL11.glEnable((int)2929);
        }
        modelBase.render(entity, f, f2, f3, f4, f5, f6);
        if (((Boolean)Field2106.getValue()).booleanValue()) {
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
        }
        GlStateManager.resetColor();
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

    public static void Method1951(ColorValue colorValue, float f, ModelBase modelBase, Entity entity, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        GL11.glPushMatrix();
        GL11.glPushAttrib((int)1048575);
        GL11.glPolygonMode((int)1032, (int)6913);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2929);
        GL11.glEnable((int)2848);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glTranslatef((float)f8, (float)f8, (float)f8);
        GlStateManager.color((float)((float) colorValue.Method769() / 255.0f), (float)((float) colorValue.Method770() / 255.0f), (float)((float) colorValue.Method779() / 255.0f), (float)((float) colorValue.Method782() / 255.0f));
        GlStateManager.glLineWidth((float)f);
        if (((Boolean)Field2110.getValue()).booleanValue()) {
            GL11.glDepthMask((boolean)true);
            GL11.glEnable((int)2929);
        }
        modelBase.render(entity, f2, f3, f4, f5, f6, f7);
        if (((Boolean)Field2110.getValue()).booleanValue()) {
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
        }
        GlStateManager.resetColor();
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

    public static boolean Method1952(ModelBase modelBase, Entity entity, float f, float f2, float f3, float f4, float f5, float f6) {
        if (!Class493.Method513(entity)) {
            return false;
        }
        boolean bl = Class493.mc.gameSettings.fancyGraphics;
        Class493.mc.gameSettings.fancyGraphics = false;
        if (((Boolean)Field2098.getValue()).booleanValue()) {
            if (!((Boolean)Field2099.getValue()).booleanValue()) {
                OpenGlHelper.setLightmapTextureCoords((int)OpenGlHelper.lightmapTexUnit, (float)240.0f, (float)240.0f);
                GL11.glEnable((int)32823);
                GL11.glPolygonOffset((float)1.0f, (float)-1100000.0f);
            }
            modelBase.render(entity, f, f2, f3, f4, f5, f6);
            if (!((Boolean)Field2099.getValue()).booleanValue()) {
                GL11.glDisable((int)32823);
                GL11.glPolygonOffset((float)1.0f, (float)1100000.0f);
            }
        }
        float f7 = Class493.mc.gameSettings.gammaSetting;
        Class493.mc.gameSettings.gammaSetting = 10000.0f;
        if (((Boolean)Field2105.getValue()).booleanValue()) {
            Class493.Method1950((ColorValue)Field2108.getValue(), modelBase, entity, f, f2, f3, f4, f5, f6, 0.0f);
        }
        if (((Boolean)Field2109.getValue()).booleanValue()) {
            Class493.Method1951((ColorValue)Field2112.getValue(), ((Float)Field2111.getValue()).floatValue(), modelBase, entity, f, f2, f3, f4, f5, f6, 0.0f);
        }
        if (Field2100.getValue() != Class495.NONE) {
            Class493.Method1948((ColorValue)Field2104.getValue(), modelBase, entity, f, f2, f3, f4, f5, f6, 0.0f);
        }
        try {
            Class493.mc.gameSettings.fancyGraphics = bl;
            Class493.mc.gameSettings.gammaSetting = f7;
        }
        catch (Exception exception) {
            // empty catch block
        }
        return true;
    }

    public Class493() {
        super("Chams", "Makes you see entities through walls", Category.RENDER, new String[0]);
    }

    public static boolean Method386(Entity entity) {
        if (entity instanceof EntityWolf) {
            return !((EntityWolf)entity).isAngry();
        }
        if (entity instanceof EntityAgeable || entity instanceof EntityTameable || entity instanceof EntityAmbientCreature || entity instanceof EntitySquid) {
            return true;
        }
        if (entity instanceof EntityIronGolem) {
            return ((EntityIronGolem)entity).getRevengeTarget() == null;
        }
        return false;
    }
}
