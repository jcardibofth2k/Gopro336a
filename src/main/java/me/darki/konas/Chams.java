package me.darki.konas;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Category;
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

public class Chams
extends Module {
    public static Setting<ParentSetting> living = new Setting<>("Living", new ParentSetting(false));
    public static Setting<PlayerPreview> preview = new Setting<>("Preview", new PlayerPreview(false, true)).setParentSetting(living);
    public static Setting<Boolean> render = new Setting<>("Render", false).setParentSetting(living);
    public static Setting<Boolean> renderDepth = new Setting<>("RenderDepth", false).setParentSetting(living);
    public static Setting<ChamsGlintMode> glint = new Setting<>("Glint", ChamsGlintMode.NONE).setParentSetting(living);
    public static Setting<Boolean> glintDepth = new Setting<>("GlintDepth", false).setParentSetting(living);
    public static Setting<Float> glintSpeed = new Setting<>("GlintSpeed", 5.0f, 20.0f, 0.1f, 0.1f).setParentSetting(living);
    public static Setting<Float> glintScale = new Setting<>("GlintScale", 1.0f, 10.0f, 0.1f, 0.1f).setParentSetting(living);
    public static Setting<ColorValue> glintColor = new Setting<>("GlintColor", new ColorValue(0x7700FFFF)).setParentSetting(living);
    public static Setting<Boolean> fill = new Setting<>("Fill", false).setParentSetting(living);
    public static Setting<Boolean> fillDepth = new Setting<>("FillDepth", false).setParentSetting(living);
    public static Setting<Boolean> lighting = new Setting<>("Lighting", false).setParentSetting(living);
    public static Setting<ColorValue> fillColor = new Setting<>("FillColor", new ColorValue(0x7700FFFF)).setParentSetting(living);
    public static Setting<Boolean> outline = new Setting<>("Outline", false).setParentSetting(living);
    public static Setting<Boolean> outlineDepth = new Setting<>("OutlineDepth", false).setParentSetting(living);
    public static Setting<Float> width = new Setting<>("Width", 1.0f, 10.0f, 0.1f, 0.1f).setParentSetting(living);
    public static Setting<ColorValue> gOutlineColor = new Setting<>("GOutlineColor", new ColorValue(-16711681)).setParentSetting(living);
    public static Setting<Boolean> players = new Setting<>("Players", true).setParentSetting(living);
    public static Setting<Boolean> self = new Setting<>("Self", true).setParentSetting(living);
    public static Setting<Boolean> animals = new Setting<>("Animals", true).setParentSetting(living);
    public static Setting<Boolean> monsters = new Setting<>("Monsters", true).setParentSetting(living);
    public static Setting<Boolean> invis = new Setting<>("Invis", true).setParentSetting(living);
    public static Setting<ParentSetting> crystal = new Setting<>("Crystal", new ParentSetting(false));
    public static Setting<Boolean> crystals = new Setting<>("Crystals", true).setParentSetting(crystal);
    public static Setting<Boolean> cRender = new Setting<>("CRender", false).setParentSetting(crystal);
    public static Setting<Boolean> cRenderDepth = new Setting<>("CRenderDepth", false).setParentSetting(crystal);
    public static Setting<ChamsGlintMode> cGlint = new Setting<>("CGlint", ChamsGlintMode.NONE).setParentSetting(crystal);
    public static Setting<Boolean> cGlintDepth = new Setting<>("CGlintDepth", false).setParentSetting(crystal);
    public static Setting<Float> cGlintSpeed = new Setting<>("CGlintSpeed", 5.0f, 20.0f, 0.1f, 0.1f).setParentSetting(crystal);
    public static Setting<Float> cGlintScale = new Setting<>("CGlintScale", 1.0f, 10.0f, 0.1f, 0.1f).setParentSetting(crystal);
    public static Setting<ColorValue> cGlintColor = new Setting<>("CGlintColor", new ColorValue(0x770000FF)).setParentSetting(crystal);
    public static Setting<Boolean> cFill = new Setting<>("CFill", false).setParentSetting(crystal);
    public static Setting<Boolean> cFillDepth = new Setting<>("CFillDepth", false).setParentSetting(crystal);
    public static Setting<Boolean> cLighting = new Setting<>("CLighting", false).setParentSetting(crystal);
    public static Setting<ColorValue> cFillColor = new Setting<>("CFillColor", new ColorValue(0x770000FF)).setParentSetting(crystal);
    public static Setting<Boolean> cOutline = new Setting<>("COutline", false).setParentSetting(crystal);
    public static Setting<Boolean> cOutlineDepth = new Setting<>("COutlineDepth", false).setParentSetting(crystal);
    public static Setting<Float> cWidth = new Setting<>("CWidth", 1.0f, 10.0f, 0.1f, 0.1f).setParentSetting(crystal);
    public static Setting<ColorValue> cgOutlineColor = new Setting<>("CGOutlineColor", new ColorValue(-16776961)).setParentSetting(crystal);
    public static Setting<Float> scale = new Setting<>("Scale", 1.0f, 10.0f, 0.1f, 0.1f).setParentSetting(crystal);
    public static Setting<Float> spinSpeed = new Setting<>("SpinSpeed", 1.0f, 10.0f, 0.1f, 0.1f).setParentSetting(crystal);
    public static Setting<Float> bounciness = new Setting<>("Bounciness", 1.0f, 10.0f, 0.1f, 0.1f).setParentSetting(crystal);
    public static Setting<ParentSetting> hand = new Setting<>("Hand", new ParentSetting(false));
    public static Setting<Boolean> hands = new Setting<>("Hands", true).setParentSetting(hand);
    public static Setting<ColorValue> handColor = new Setting<>("HandColor", new ColorValue(469762303)).Method1191(hands::getValue).setParentSetting(hand);
    public static Setting<ChamsGlintMode> hGlint = new Setting<>("HGlint", ChamsGlintMode.NONE).setParentSetting(hand);
    public static Setting<Float> hGlintSpeed = new Setting<>("HGlintSpeed", 5.0f, 20.0f, 0.1f, 0.1f).setParentSetting(hand);
    public static Setting<Float> hGlintScale = new Setting<>("HGlintScale", 1.0f, 10.0f, 0.1f, 0.1f).setParentSetting(hand);
    public static Setting<ColorValue> hGlintColor = new Setting<>("HGlintColor", new ColorValue(0x770000FF)).setParentSetting(hand);
    public static ResourceLocation location = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    public static ResourceLocation location1 = new ResourceLocation("konas/textures/enchant_glint.png");
    public static boolean Field2147 = false;

    public static boolean Method513(Entity entity) {
        if (entity == null) {
            return false;
        }
        if (entity.isInvisible() && !((Boolean) invis.getValue()).booleanValue()) {
            return false;
        }
        if (entity.equals((Object) Chams.mc.player) && !((Boolean) self.getValue()).booleanValue()) {
            return false;
        }
        if (entity instanceof EntityPlayer && !((Boolean) players.getValue()).booleanValue()) {
            return false;
        }
        if (Chams.Method386(entity) && !((Boolean) animals.getValue()).booleanValue()) {
            return false;
        }
        return Chams.Method386(entity) || entity instanceof EntityPlayer || (Boolean) monsters.getValue() != false;
    }

    public static void Method1948(ColorValue colorValue, ModelBase modelBase, Entity entity, float f, float f2, float f3, float f4, float f5, float f6, float f7) {
        GL11.glPushMatrix();
        GL11.glPushAttrib((int)1048575);
        GL11.glPolygonMode((int)1032, (int)6914);
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2929);
        GL11.glEnable((int)3042);
        Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(glint.getValue() == ChamsGlintMode.CUSTOM ? location1 : location);
        GL11.glPolygonMode((int)1032, (int)6914);
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2929);
        GL11.glEnable((int)3042);
        GL11.glColor4f((float)((float) colorValue.Method769() / 255.0f), (float)((float) colorValue.Method770() / 255.0f), (float)((float) colorValue.Method779() / 255.0f), (float)((float) colorValue.Method782() / 255.0f));
        GlStateManager.blendFunc((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_COLOR, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE);
        for (int i = 0; i < 2; ++i) {
            GlStateManager.matrixMode((int)5890);
            GlStateManager.loadIdentity();
            float f8 = 0.33333334f * ((Float) glintScale.getValue()).floatValue();
            GlStateManager.scale((float)f8, (float)f8, (float)f8);
            GlStateManager.rotate((float)(30.0f - (float)i * 60.0f), (float)0.0f, (float)0.0f, (float)1.0f);
            GlStateManager.translate((float)0.0f, (float)(((float)entity.ticksExisted + mc.getRenderPartialTicks()) * (0.001f + (float)i * 0.003f) * ((Float) glintSpeed.getValue()).floatValue()), (float)0.0f);
            GlStateManager.matrixMode((int)5888);
            GL11.glTranslatef((float)f7, (float)f7, (float)f7);
            GlStateManager.color((float)((float) colorValue.Method769() / 255.0f), (float)((float) colorValue.Method770() / 255.0f), (float)((float) colorValue.Method779() / 255.0f), (float)((float) colorValue.Method782() / 255.0f));
            if (((Boolean) glintDepth.getValue()).booleanValue()) {
                GL11.glDepthMask((boolean)true);
                GL11.glEnable((int)2929);
            }
            modelBase.render(entity, f, f2, f3, f4, f5, f6);
            if (!((Boolean) glintDepth.getValue()).booleanValue()) continue;
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
            if (!((Boolean) hands.getValue()).booleanValue()) break block0;
            class102.Method341(((ColorValue) handColor.getValue()).Method769());
            class102.Method344(((ColorValue) handColor.getValue()).Method770());
            class102.Method1102(((ColorValue) handColor.getValue()).Method779());
            class102.Method294(((ColorValue) handColor.getValue()).Method782());
            class102.setCanceled(true);
        }
    }

    public static void Method1950(ColorValue colorValue, ModelBase modelBase, Entity entity, float f, float f2, float f3, float f4, float f5, float f6, float f7) {
        GL11.glPushMatrix();
        GL11.glPushAttrib((int)1048575);
        GL11.glPolygonMode((int)1032, (int)6914);
        GL11.glDisable((int)3553);
        if (((Boolean) lighting.getValue()).booleanValue()) {
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
        if (((Boolean) fillDepth.getValue()).booleanValue()) {
            GL11.glDepthMask((boolean)true);
            GL11.glEnable((int)2929);
        }
        modelBase.render(entity, f, f2, f3, f4, f5, f6);
        if (((Boolean) fillDepth.getValue()).booleanValue()) {
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
        if (((Boolean) outlineDepth.getValue()).booleanValue()) {
            GL11.glDepthMask((boolean)true);
            GL11.glEnable((int)2929);
        }
        modelBase.render(entity, f2, f3, f4, f5, f6, f7);
        if (((Boolean) outlineDepth.getValue()).booleanValue()) {
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
        }
        GlStateManager.resetColor();
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

    public static boolean Method1952(ModelBase modelBase, Entity entity, float f, float f2, float f3, float f4, float f5, float f6) {
        if (!Chams.Method513(entity)) {
            return false;
        }
        boolean bl = Chams.mc.gameSettings.fancyGraphics;
        Chams.mc.gameSettings.fancyGraphics = false;
        if (((Boolean) render.getValue()).booleanValue()) {
            if (!((Boolean) renderDepth.getValue()).booleanValue()) {
                OpenGlHelper.setLightmapTextureCoords((int)OpenGlHelper.lightmapTexUnit, (float)240.0f, (float)240.0f);
                GL11.glEnable((int)32823);
                GL11.glPolygonOffset((float)1.0f, (float)-1100000.0f);
            }
            modelBase.render(entity, f, f2, f3, f4, f5, f6);
            if (!((Boolean) renderDepth.getValue()).booleanValue()) {
                GL11.glDisable((int)32823);
                GL11.glPolygonOffset((float)1.0f, (float)1100000.0f);
            }
        }
        float f7 = Chams.mc.gameSettings.gammaSetting;
        Chams.mc.gameSettings.gammaSetting = 10000.0f;
        if (((Boolean) fill.getValue()).booleanValue()) {
            Chams.Method1950((ColorValue) fillColor.getValue(), modelBase, entity, f, f2, f3, f4, f5, f6, 0.0f);
        }
        if (((Boolean) outline.getValue()).booleanValue()) {
            Chams.Method1951((ColorValue) gOutlineColor.getValue(), ((Float) width.getValue()).floatValue(), modelBase, entity, f, f2, f3, f4, f5, f6, 0.0f);
        }
        if (glint.getValue() != ChamsGlintMode.NONE) {
            Chams.Method1948((ColorValue) glintColor.getValue(), modelBase, entity, f, f2, f3, f4, f5, f6, 0.0f);
        }
        try {
            Chams.mc.gameSettings.fancyGraphics = bl;
            Chams.mc.gameSettings.gammaSetting = f7;
        }
        catch (Exception exception) {
            // empty catch block
        }
        return true;
    }

    public Chams() {
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
