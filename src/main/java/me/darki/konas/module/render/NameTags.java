package me.darki.konas.module.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import cookiedragon.eventsystem.Subscriber;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Map;

import me.darki.konas.module.ModuleManager;
import me.darki.konas.setting.ColorValue;
import me.darki.konas.setting.ListenableSettingDecorator;
import me.darki.konas.setting.ParentSetting;
import me.darki.konas.command.Command;
import me.darki.konas.command.commands.fontCommand;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.module.client.KonasGlobals;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.*;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class NameTags
extends Module {      
      
    public static Setting<ParentSetting> targets = new Setting<>("Targets", new ParentSetting(false));
    public static Setting<Boolean> animals = new Setting<>("Animals", false).setParentSetting(Field910);
    public static Setting<Boolean> mobs = new Setting<>("Mobs", false).setParentSetting(Field910);
    public static Setting<Boolean> players = new Setting<>("Players", true).setParentSetting(Field910);
    public static Setting<Boolean> waypoints = new Setting<>("Waypoints", true).setParentSetting(Field910);
    public static Setting<Boolean> coords = new Setting<>("Coords", true).setParentSetting(Field910).visibleIf(Field914::getValue);
    public static Setting<Boolean> dist = new Setting<>("Dist", false).setParentSetting(Field910).visibleIf(Field914::getValue);
    public static Setting<ParentSetting> name = new Setting<>("Name", new ParentSetting(false));
    public static Setting<Double> nameRange = new Setting<>("NameRange", 150.0, 256.0, 5.0, 0.5).setParentSetting(Field917);
    public static Setting<Boolean> gamemode = new Setting<>("Gamemode", false).setParentSetting(Field917);
    public static Setting<Boolean> ping = new Setting<>("Ping", false).setParentSetting(Field917);
    public static Setting<Boolean> health = new Setting<>("Health", true).setParentSetting(Field917);
    public static Setting<Boolean> pops = new Setting<>("Pops", false).setParentSetting(Field917);
    public static Setting<Boolean> friends = new Setting<>("Friends", true).setParentSetting(Field917);
    public static Setting<Boolean> fill = new Setting<>("Fill", true).setParentSetting(Field917);
    public static Setting<Boolean> outline = new Setting<>("Outline", true).setParentSetting(Field917);
    public static Setting<Float> lineWidth = new Setting<>("LineWidth", Float.valueOf(1.0f), Float.valueOf(10.0f), Float.valueOf(0.1f), Float.valueOf(0.1f)).setParentSetting(Field917).visibleIf(Field925::getValue);
    public static Setting<ParentSetting> colors = new Setting<>("Colors", new ParentSetting(false));
    public static Setting<ColorValue> fillColorA = new Setting<>("FillColorA", new ColorValue(Integer.MIN_VALUE)).setParentSetting(Field927);
    public static Setting<ColorValue> fillColorB = new Setting<>("FillColorB", new ColorValue(Integer.MIN_VALUE)).setParentSetting(Field927);
    public static Setting<ColorValue> fillColorC = new Setting<>("FillColorC", new ColorValue(Integer.MIN_VALUE)).setParentSetting(Field927);
    public static Setting<ColorValue> fillColorD = new Setting<>("FillColorD", new ColorValue(Integer.MIN_VALUE)).setParentSetting(Field927);
    public static Setting<ColorValue> outlineColorA = new Setting<>("OutlineColorA", new ColorValue(-805306368)).setParentSetting(Field927);
    public static Setting<ColorValue> outlineColorB = new Setting<>("OutlineColorB", new ColorValue(-805306368)).setParentSetting(Field927);
    public static Setting<ColorValue> outlineColorC = new Setting<>("OutlineColorC", new ColorValue(-805306368)).setParentSetting(Field927);
    public static Setting<ColorValue> outlineColorD = new Setting<>("OutlineColorD", new ColorValue(-805306368)).setParentSetting(Field927);
    public static Setting<ParentSetting> info = new Setting<>("Info", new ParentSetting(false));
    public static Setting<Double> armorRange = new Setting<>("ArmorRange", 30.0, 256.0, 5.0, 0.5).setParentSetting(Field936);
    public static Setting<Boolean> armor = new Setting<>("Armor", true).setParentSetting(Field936);
    public static Setting<Boolean> stacks = new Setting<>("Stacks", true).setParentSetting(Field936);
    public static Setting<Boolean> durability = new Setting<>("Durability", true).setParentSetting(Field936);
    public static Setting<Boolean> enchants = new Setting<>("Enchants", true).setParentSetting(Field936);
    public static Setting<ParentSetting> scaling = new Setting<>("Scaling", new ParentSetting(false));
    public static Setting<Boolean> frustumCheck = new Setting<>("FrustumCheck", true).setParentSetting(Field942);
    public static Setting<Double> zoomFactor = new Setting<>("ZoomFactor", 3.0, 10.0, 0.0, 0.1).setParentSetting(Field942);
    public static Setting<Double> scale = new Setting<>("Scale", 1.0, 5.0, 0.0, 0.1).setParentSetting(Field942);
    public static Setting<Double> scaleFactor = new Setting<>("ScaleFactor", 2.0, 5.0, 0.0, 0.1).setParentSetting(Field942);
    public static Setting<Double> scaleLimit = new Setting<>("ScaleLimit", 3.0, 10.0, 0.0, 0.1).setParentSetting(Field942);
    public static Setting<Integer> armorSpacing = new Setting<>("ArmorSpacing", 42, 70, 0, 1).setParentSetting(Field942);
    public static Setting<Integer> enchantSpacing = new Setting<>("EnchantSpacing", 70, 100, -30, 5).setParentSetting(Field942);
    public static Setting<Double> enchantScale = new Setting<>("EnchantScale", 1.0, 2.5, 0.5, 0.05).setParentSetting(Field942);
    public static Setting<Double> yOffset = new Setting<>("YOffset", 0.2, 1.0, 0.0, 0.05).setParentSetting(Field942);
    public static Setting<ParentSetting> misc = new Setting<>("Misc", new ParentSetting(false));
    public static Setting<Boolean> selfNametag = new Setting<>("SelfNametag", false).setParentSetting(Field952);
    public static Setting<Class427> Field954 = new ListenableSettingDecorator("Font", Class427.VANILLA, new Class404());
  
    public DecimalFormat Field955 = new DecimalFormat("#.##");
    public static ICamera Field956 = new Frustum();
    public static fontHelper Field957 = new CfontRenderer(fontCommand.Field1351, 20.0f);
    public static fontHelper Field958 = new CfontRenderer(fontCommand.Field1351, 60.0f);

    public void Method949(EntityPlayer entityPlayer, ItemStack itemStack, double d, double d2) {
        GL11.glPushMatrix();
        GL11.glDepthMask((boolean)true);
        GlStateManager.clear((int)256);
        GlStateManager.disableDepth();
        GlStateManager.enableDepth();
        RenderHelper.enableStandardItemLighting();
        NameTags.mc.getRenderItem().zLevel = -100.0f;
        GlStateManager.scale((double)3.0, (double)3.0, (double)0.01f);
        mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, (int)(d / 3.0), (int)(d2 / 3.0));
        this.Method956(itemStack, d / 3.0, d2 / 3.0);
        NameTags.mc.getRenderItem().zLevel = 0.0f;
        GlStateManager.scale((float)1.0f, (float)1.0f, (float)1.0f);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.enableDepth();
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glPopMatrix();
    }

    @Subscriber
    public void Method950(Class139 class139) {
        block0: {
            if (!this.Method384((Entity)class139.Method1894())) break block0;
            class139.setCanceled(true);
        }
    }

    public static void Method951(float f, float f2, float f3, float f4, ColorValue colorValue, ColorValue class4402, ColorValue class4403, ColorValue class4404, boolean bl) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.shadeModel((int)7425);
        bufferBuilder.begin(bl ? 2 : 7, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos((double)f, (double)f4, 0.0).color((float) colorValue.Method769() / 255.0f, (float) colorValue.Method770() / 255.0f, (float) colorValue.Method779() / 255.0f, (float) colorValue.Method782() / 255.0f).endVertex();
        bufferBuilder.pos((double)f3, (double)f4, 0.0).color((float)class4402.Method769() / 255.0f, (float)class4402.Method770() / 255.0f, (float)class4402.Method779() / 255.0f, (float)class4402.Method782() / 255.0f).endVertex();
        bufferBuilder.pos((double)f3, (double)f2, 0.0).color((float)class4403.Method769() / 255.0f, (float)class4403.Method770() / 255.0f, (float)class4403.Method779() / 255.0f, (float)class4403.Method782() / 255.0f).endVertex();
        bufferBuilder.pos((double)f, (double)f2, 0.0).color((float)class4404.Method769() / 255.0f, (float)class4404.Method770() / 255.0f, (float)class4404.Method779() / 255.0f, (float)class4404.Method782() / 255.0f).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel((int)7424);
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void Method952(float f, float f2, float f3, float f4, int n, boolean bl) {
        float f5 = (float)(n >> 24 & 0xFF) / 255.0f;
        float f6 = (float)(n >> 16 & 0xFF) / 255.0f;
        float f7 = (float)(n >> 8 & 0xFF) / 255.0f;
        float f8 = (float)(n & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        bufferBuilder.begin(bl ? 2 : 7, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos((double)f, (double)f4, 0.0).color(f6, f7, f8, f5).endVertex();
        bufferBuilder.pos((double)f3, (double)f4, 0.0).color(f6, f7, f8, f5).endVertex();
        bufferBuilder.pos((double)f3, (double)f2, 0.0).color(f6, f7, f8, f5).endVertex();
        bufferBuilder.pos((double)f, (double)f2, 0.0).color(f6, f7, f8, f5).endVertex();
        tessellator.draw();
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static boolean Method386(Entity entity) {
        return (Boolean)frustumCheck.getValue() == false || Field956.isBoundingBoxInFrustum(entity.getEntityBoundingBox().grow(2.0));
    }

    public void Method953(String string, Vec3d vec3d, Vec3d vec3d2, Vec3d vec3d3) {
        GL11.glPushMatrix();
        double d = MathHelper.clamp((double)vec3d2.distanceTo(vec3d3), (double)0.0, (double)((Double)scaleLimit.getValue() * 10.0)) * 0.2;
        d = 1.0 / (d * (Double)scaleFactor.getValue() + 1.0);
        double d2 = (Double)scale.getValue() * d;
        if (Field954.getValue() != Class427.HIGHRES) {
            d2 *= 3.0;
        }
        GL11.glTranslated((double)vec3d.x, (double)vec3d.y, (double)0.0);
        GL11.glScaled((double)d2, (double)d2, (double)1.0);
        float f = Class551.Method877(string);
        float f2 = Class551.Method882(string);
        if (((Boolean)fill.getValue()).booleanValue()) {
            NameTags.Method951(-(f / 2.0f) * 1.05f, -f2, f / 2.0f * 1.05f, f2 * 0.1f, (ColorValue)fillColorA.getValue(), (ColorValue)fillColorB.getValue(), (ColorValue)fillColorC.getValue(), (ColorValue)fillColorD.getValue(), false);
        }
        if (((Boolean)outline.getValue()).booleanValue()) {
            GL11.glLineWidth((float)((Float)lineWidth.getValue()).floatValue());
            NameTags.Method951(-(f / 2.0f) * 1.05f, -f2, f / 2.0f * 1.05f, f2 * 0.1f, (ColorValue)outlineColorA.getValue(), (ColorValue)outlineColorB.getValue(), (ColorValue)outlineColorC.getValue(), (ColorValue)outlineColorD.getValue(), true);
        }
        Class551.Method878(string, -(f / 2.0f), -f2, -1);
        GL11.glPopMatrix();
    }

    public void Method954(Vec3d vec3d, Entity entity) {
        Vec3d vec3d2 = new Vec3d(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)mc.getRenderPartialTicks(), entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)mc.getRenderPartialTicks(), entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)mc.getRenderPartialTicks()).add(0.0, (double)entity.height + (Double)yOffset.getValue(), 0.0);
        Vec3d vec3d3 = KonasGlobals.INSTANCE.Field1137.Method2026(vec3d2);
        this.Method958(entity, vec3d3, vec3d, vec3d2);
    }

    public NameTags() {
        super("Nametags", Category.RENDER, new String[0]);
    }

    public static void Method955(Class427 class427) {
        if (class427 == Class427.HIGHRES) {
            Class551.Method879(Field958);
        } else if (class427 == Class427.CUSTOM) {
            Class551.Method879(Field957);
        } else {
            Class551.Method879(FontUtil.Field779);
        }
    }

    /*
     * Unable to fully structure code
     */
    public void Method956(ItemStack var1_1, double var2_2, double var4_3) {
        block35: {
            if (var1_1.isEmpty()) break block35;
            if (var1_1.getCount() != 1 && ((Boolean) NameTags.stacks.getValue()).booleanValue()) {
                var6_4 = String.valueOf(var1_1.getCount());
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                GlStateManager.disableBlend();
                GlStateManager.translate((double)(var2_2 + 15.0), (double)(var4_3 + 13.0), (double)0.0);
                var7_7 = 0.13;
                if (NameTags.Field954.getValue() != Class427.HIGHRES) {
                    var7_7 *= 3.0;
                }
                GlStateManager.scale((double)var7_7, (double)var7_7, (double)1.0);
                Class551.Method878(var6_4, -Class551.Method877(var6_4), 0.0f, -1);
                GlStateManager.scale((double)(1.0 / var7_7), (double)(1.0 / var7_7), (double)1.0);
                GlStateManager.translate((double)(-(var2_2 + 15.0)), (double)(-(var4_3 + 13.0)), (double)0.0);
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
                GlStateManager.enableBlend();
            }
            if (var1_1.getItem().showDurabilityBar(var1_1) && ((Boolean) NameTags.durability.getValue()).booleanValue()) {
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                GlStateManager.disableTexture2D();
                GlStateManager.disableAlpha();
                GlStateManager.disableBlend();
                var6_5 = var1_1.getItem().getDurabilityForDisplay(var1_1);
                var8_8 = var1_1.getItem().getRGBDurabilityForDisplay(var1_1);
                GlStateManager.translate((double)(var2_2 + 3.5), (double)(var4_3 + 15.0), (double)0.0);
                GlStateManager.scale((double)0.75, (double)0.75, (double)1.0);
                NameTags.Method952(0.0f, 0.0f, 12.0f, 2.0f, -16777216, false);
                NameTags.Method952(0.0f, 0.0f, Math.round(12.0f - (float)var6_5 * 12.0f), 1.0f, var8_8 | -16777216, false);
                GlStateManager.scale((double)1.3333333333333333, (double)1.3333333333333333, (double)1.0);
                GlStateManager.translate((double)(-(var2_2 + 3.5)), (double)(-(var4_3 + 15.0)), (double)0.0);
                GlStateManager.enableBlend();
                GlStateManager.enableAlpha();
                GlStateManager.enableTexture2D();
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
            }
            if (!((Boolean) NameTags.enchants.getValue()).booleanValue()) break block35;
            var6_6 = ((Integer) NameTags.enchantSpacing.getValue()).intValue();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            GlStateManager.disableBlend();
            GlStateManager.translate((double)(var2_2 + 5.0), (double)(var4_3 + 10.0), (double)0.0);
            var7_7 = 0.13 * (Double) NameTags.enchantScale.getValue();
            if (NameTags.Field954.getValue() != Class427.HIGHRES) {
                var7_7 *= 3.0;
                var6_6 /= 3.0f;
            }
            GlStateManager.scale((double)var7_7, (double)var7_7, (double)1.0);
            var9_9 = var1_1.getEnchantmentTagList();
            for (var10_10 = 0; var10_10 < var9_9.tagCount(); ++var10_10) {
                block34: {
                    var11_11 = var9_9.getCompoundTagAt(var10_10).getShort("id");
                    var12_12 = var9_9.getCompoundTagAt(var10_10).getShort("lvl");
                    var13_13 = Enchantment.getEnchantmentByID((int)var11_11);
                    if (var13_13 == null) continue;
                    v0 = var13_13;
                    v1 = v0.isCurse();
                    if (!v1) ** GOTO lbl78
                    v2 = var13_13;
                    v3 = var12_12;
                    v4 = v2.getTranslatedName((int)v3);
                    v5 = 11;
                    v6 = v4.substring(v5);
                    v7 = 0;
                    v8 = 1;
                    v9 = v6.substring(v7, v8);
                    v10 = v9.toLowerCase();
                    break block34;
lbl78:
                    // 1 sources

                    v11 = var13_13;
                    v12 = var12_12;
                    v13 = v11.getTranslatedName((int)v12);
                    v14 = 0;
                    v15 = 1;
                    v16 = v13.substring(v14, v15);
                    v10 = v16.toLowerCase();
                }
                var14_14 = v10;
                v17 = v18;
                v19 = v18;
                v17();
                v20 = var14_14;
                v21 = v19.append(v20);
                v22 = var12_12;
                v23 = v21.append(v22);
                v24 = v23.toString();
                var14_14 = v24;
                v25 = var6_6;
                v26 = var14_14;
                v27 = Class551.Method882(v26);
                var6_6 = v25 + v27;
                v28 = var14_14;
                v29 = 0.0f;
                v30 = -var6_6;
                v31 = -1;
                try {
                    Class551.Method881(v28, v29, v30, v31);
                    continue;
                }
                catch (IndexOutOfBoundsException var14_15) {
                    // empty catch block
                }
            }
            GlStateManager.scale((double)(1.0 / var7_7), (double)(1.0 / var7_7), (double)1.0);
            GlStateManager.translate((double)(-(var2_2 + 15.0)), (double)(-(var4_3 + 10.0)), (double)0.0);
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
            GlStateManager.enableBlend();
        }
    }

    public static boolean Method513(Entity entity) {
        return entity instanceof EntityLivingBase;
    }

    @Subscriber
    public void Method466(Class91 class91) {
        Waypoints waypoints;
        Vec3d vec3d = new Vec3d(NameTags.mc.getRenderViewEntity().lastTickPosX + (NameTags.mc.getRenderViewEntity().posX - NameTags.mc.getRenderViewEntity().lastTickPosX) * (double)mc.getRenderPartialTicks(), NameTags.mc.getRenderViewEntity().lastTickPosY + (NameTags.mc.getRenderViewEntity().posY - NameTags.mc.getRenderViewEntity().lastTickPosY) * (double)mc.getRenderPartialTicks(), NameTags.mc.getRenderViewEntity().lastTickPosZ + (NameTags.mc.getRenderViewEntity().posZ - NameTags.mc.getRenderViewEntity().lastTickPosZ) * (double)mc.getRenderPartialTicks()).add(0.0, 2.0, 0.0);
        if (((Boolean)frustumCheck.getValue()).booleanValue()) {
            Field956.setPosition(NameTags.mc.getRenderViewEntity().posX, NameTags.mc.getRenderViewEntity().posY, NameTags.mc.getRenderViewEntity().posZ);
        }
        NameTags.mc.world.loadedEntityList.stream().filter(NameTags::Method513).filter(this::Method384).filter(NameTags::Method386).forEach(arg_0 -> this.Method954(vec3d, arg_0));
      
        if (((Boolean)waypoints.getValue()).booleanValue() && (waypoints = (Waypoints)ModuleManager.getModuleByClass(Waypoints.class)) != null && waypoints.Method1651()) {

            EntityPlayer entityPlayer;
            for (Class559 object : KonasGlobals.INSTANCE.Field1138.Method759()) {
                entityPlayer = new Vec3d(object.Method821(), object.Method820(), object.Method818()).add(0.5, 2.2, 0.5);
                Vec3d vec3d2 = KonasGlobals.INSTANCE.Field1137.Method2026((Vec3d)entityPlayer);
                String string = object.Method819();
                if (((Boolean)coords.getValue()).booleanValue()) {
                    DecimalFormat decimalFormat = new DecimalFormat("#.#");
                    double d = Double.parseDouble(decimalFormat.format(object.Method821()));
                    double d2 = Double.parseDouble(decimalFormat.format(object.Method820()));
                    double d3 = Double.parseDouble(decimalFormat.format(object.Method818()));
                    string = string + " " + d + ", " + d2 + ", " + d3;
                }
                if (((Boolean)dist.getValue()).booleanValue()) {
                    string = string + " " + (int)entityPlayer.distanceTo(vec3d);
                }
                this.Method953(string, vec3d2, vec3d, (Vec3d)entityPlayer);
            }
            for (Map.Entry entry : waypoints.Method1799().entrySet()) {
                entityPlayer = (EntityPlayer)entry.getKey();
                if (entityPlayer == NameTags.mc.player) continue;
                double d = Double.parseDouble(this.Field955.format(entityPlayer.posX));
                double d4 = Double.parseDouble(this.Field955.format(entityPlayer.posY));
                double d5 = Double.parseDouble(this.Field955.format(entityPlayer.posZ));
                Vec3d vec3d3 = new Vec3d(d, d4, d5).add(0.0, (double)entityPlayer.height + (Double)yOffset.getValue(), 0.0);
                Vec3d vec3d4 = KonasGlobals.INSTANCE.Field1137.Method2026(vec3d3);
                String string = entityPlayer.getName();
                if (((Boolean)coords.getValue()).booleanValue()) {
                    string = string + " " + d + ", " + d4 + ", " + d5;
                }
                if (((Boolean)dist.getValue()).booleanValue()) {
                    string = string + " " + (int)vec3d3.distanceTo(vec3d);
                }
                this.Method953(string, vec3d4, vec3d, vec3d3);
            }
        }
    }

    public static float Method957(float f, int n) {
        BigDecimal bigDecimal = new BigDecimal(f);
        bigDecimal = bigDecimal.setScale(n, RoundingMode.HALF_UP);
        return bigDecimal.floatValue();
    }

    public boolean Method384(Entity entity) {
        if ((double)entity.getDistance((Entity) NameTags.mc.player) > (Double)nameRange.getValue()) {
            return false;
        }
        if (((Boolean)animals.getValue()).booleanValue() && entity instanceof EntityAnimal) {
            return true;
        }
        if (((Boolean)mobs.getValue()).booleanValue() && entity instanceof IMob) {
            return true;
        }
        if (((Boolean)players.getValue()).booleanValue() && entity instanceof EntityPlayer && !Class546.Method963(entity)) {
            return (Boolean)selfNametag.getValue() != false || !(entity instanceof EntityPlayerSP);
        }
        return false;
    }

    public void Method958(Entity entity, Vec3d vec3d, Vec3d vec3d2, Vec3d vec3d3) {
        String string;
        float f;
        int n;
        GL11.glPushMatrix();

        double d = MathHelper.clamp((double)vec3d2.distanceTo(vec3d3), (double)0.0, (double)((Double)Field947.getValue() * 10.0)) * 0.2;
        d = 1.0 / (d * (Double)Field946.getValue() + 1.0);
        double d2 = (Double)Field945.getValue() * d;
        if (ModuleManager.getModuleByClass(Zoom.class).isEnabled()) {
            d2 *= (double)((Float) Zoom.Field778.getValue()).floatValue() * (Double)Field944.getValue();

        }
        if (Field954.getValue() != Class427.HIGHRES) {
            d2 *= 3.0;
        }
        GL11.glTranslated((double)vec3d.x, (double)vec3d.y, (double)0.0);
        GL11.glScaled((double)d2, (double)d2, (double)1.0);
        String string2 = entity.getName();
        if (string2.equalsIgnoreCase("antiflame")) {
            string2 = "god";
        }
        if (entity instanceof EntityPlayer) {
            if (((Boolean)gamemode.getValue()).booleanValue()) {
                string2 = string2 + (((EntityPlayer)entity).isCreative() ? " [C]" : " [S]");
            }
            if (((Boolean)ping.getValue()).booleanValue() && mc.getConnection() != null && mc.getConnection().getPlayerInfo(entity.getUniqueID()) != null) {
                n = mc.getConnection().getPlayerInfo(entity.getUniqueID()).getResponseTime();
                string2 = string2 + " " + n + "ms";
            }
        }
        if (((Boolean)health.getValue()).booleanValue() && entity instanceof EntityLivingBase) {
            EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
            f = NameTags.Method957(entityLivingBase.getHealth() + entityLivingBase.getAbsorptionAmount(), 1);
            if (entity.getName().equalsIgnoreCase("antiflame")) {
                f = 420.0f;
            }
            string = f < 5.0f ? ChatFormatting.RED.toString() + f : (f < 20.0f ? ChatFormatting.YELLOW.toString() + f : ChatFormatting.GREEN.toString() + f);
            string = string.replace(".0", "");
            string2 = string2 + " " + string + ChatFormatting.RESET.toString();
        }
        if (((Boolean)pops.getValue()).booleanValue()) {
            n = 0;
            if (Class87.Field262.containsKey(entity.getName())) {
                n = Class87.Field262.get(entity.getName());
            }
            string2 = string2 + " " + n;
        }
        float f2 = Class551.Method877(string2);
        f = Class551.Method882(string2);
        if (((Boolean)fill.getValue()).booleanValue()) {
            NameTags.Method951(-(f2 / 2.0f) * 1.05f, -f, f2 / 2.0f * 1.05f, f * 0.1f, (ColorValue)fillColorA.getValue(), (ColorValue)fillColorB.getValue(), (ColorValue)fillColorC.getValue(), (ColorValue)fillColorD.getValue(), false);
        }
        if (((Boolean)outline.getValue()).booleanValue()) {
            GL11.glLineWidth((float)((Float)lineWidth.getValue()).floatValue());
            NameTags.Method951(-(f2 / 2.0f) * 1.05f, -f, f2 / 2.0f * 1.05f, f * 0.1f, (ColorValue)outlineColorA.getValue(), (ColorValue)outlineColorB.getValue(), (ColorValue)outlineColorC.getValue(), (ColorValue)outlineColorD.getValue(), true);
        }
        if (Field954.getValue() == Class427.VANILLA) {
            f += 0.5f;
        }
        Class551.Method878((((Boolean)friends.getValue()).booleanValue() ? (Class492.Method1988(entity.getUniqueID().toString()) ? Command.Field122 + "b" : "") : "") + string2, -(f2 / 2.0f), Field954.getValue() == Class427.VANILLA ? -f + 2.0f : -f, -1);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        if (Field954.getValue() != Class427.HIGHRES) {
            GL11.glScaled((double)(1.0 / d2), (double)(1.0 / d2), (double)1.0);
          
            d2 = (Double)Field945.getValue() * d;
            if (ModuleManager.getModuleByClass(Zoom.class).isEnabled()) {
                d2 *= (double)((Float) Zoom.Field778.getValue()).floatValue() * (Double)Field944.getValue();
            }
            GL11.glScaled((double)d2, (double)d2, (double)1.0);
            f *= 3.0f;
        }
        if (((Boolean)armor.getValue()).booleanValue() && entity instanceof EntityPlayer) {
            string = (EntityPlayer)entity;
            if ((double)entity.getDistance((Entity) NameTags.mc.player) <= (Double)armorRange.getValue()) {
                double d3 = 0.0;
                for (ItemStack itemStack : string.inventory.armorInventory) {
                    if (itemStack == null) continue;
                    d3 -= (double)((Integer)armorSpacing.getValue()).intValue() / 2.0;
                }
                if (string.getHeldItemMainhand() != null) {
                    Iterator iterator = string.getHeldItemMainhand().copy();
                    this.Method949((EntityPlayer)string, (ItemStack)iterator, d3 -= (double)((Integer)armorSpacing.getValue()).intValue(), -((double)f + 55.0));
                    d3 += (double)((Integer)armorSpacing.getValue()).intValue();
                }
                for (int i = 3; i >= 0; --i) {
                    ItemStack itemStack;
                    itemStack = (ItemStack)string.inventory.armorInventory.get(i);
                    if (itemStack == null) continue;
                    this.Method949((EntityPlayer)string, itemStack, d3, -((double)f + 55.0));
                    d3 += (double)((Integer)armorSpacing.getValue()).intValue();
                }
                if (string.getHeldItemOffhand() != null) {
                    ItemStack itemStack = string.getHeldItemOffhand().copy();
                    this.Method949((EntityPlayer)string, itemStack, d3, -((double)f + 55.0));
                }
            }
        }
        GL11.glPopMatrix();
    }
}