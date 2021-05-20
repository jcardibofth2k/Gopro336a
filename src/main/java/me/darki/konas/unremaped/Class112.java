package me.darki.konas.unremaped;

import com.google.common.primitives.Ints;
import java.awt.Color;
import java.text.DecimalFormat;
import java.util.Comparator;

import me.darki.konas.setting.ColorValue;
import me.darki.konas.util.PlayerUtil;
import me.darki.konas.command.Command;
import me.darki.konas.gui.hud.Element;
import me.darki.konas.module.client.KonasGlobals;
import me.darki.konas.setting.Setting;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class Class112
extends Element {
    public float Field2469 = Class557.Method802() + 2;
    public Setting<Boolean> yaw = new Setting<>("Yaw", true);
    public Setting<Boolean> pitch = new Setting<>("Pitch", true);
    public static boolean Field2472 = false;

    public int Method2122(float f) {
        if (f > 20.0f) {
            return 0xFFAA00;
        }
        int n = Ints.constrainToRange((int)((double)((int)f) * 12.75), 0, 255);
        return new Color(Ints.constrainToRange(255 - n, 0, 255), Ints.constrainToRange(n, 0, 255), 0).hashCode();
    }

    @Override
    public void Method2123() {
        super.Method2123();
        Field2472 = false;
    }

    public String Method2124(EntityPlayer entityPlayer) {
        String string = "";
        PotionEffect potionEffect = entityPlayer.getActivePotionEffect(MobEffects.STRENGTH);
        if (potionEffect != null && entityPlayer.isPotionActive(MobEffects.STRENGTH)) {
            string = Command.Field122 + "c S";
        }
        return string;
    }

    public void Method2125(int n, int n2, ItemStack itemStack) {
        GL11.glPushMatrix();
        GL11.glDepthMask((boolean)true);
        GlStateManager.clear((int)256);
        GlStateManager.disableDepth();
        GlStateManager.enableDepth();
        RenderHelper.enableStandardItemLighting();
        Class112.mc.getRenderItem().zLevel = -100.0f;
        GlStateManager.scale((float)1.0f, (float)1.0f, (float)0.01f);
        mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, n, n2);
        Class112.mc.getRenderItem().zLevel = 0.0f;
        GlStateManager.scale((float)1.0f, (float)1.0f, (float)1.0f);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GL11.glPopMatrix();
    }

    public String Method2126(EntityPlayer entityPlayer) {
        String string = "";
        PotionEffect potionEffect = entityPlayer.getActivePotionEffect(MobEffects.WEAKNESS);
        if (potionEffect != null && entityPlayer.isPotionActive(MobEffects.WEAKNESS)) {
            string = Command.Field122 + "0 W";
        }
        return string;
    }

    public static Float Method2097(Entity entity) {
        return Float.valueOf(Class112.mc.player.getDistance(entity));
    }

    public static boolean Method2100(Entity entity) {
        return entity instanceof EntityPlayer;
    }

    @Override
    public void onRender2D() {
        super.onRender2D();
        if (Class112.mc.player == null || Class112.mc.world == null) {
            return;
        }
        EntityPlayer entityPlayer = KonasGlobals.INSTANCE.Field1133.Method429().stream().filter(Class112::Method2100).min(Comparator.comparing(Class112::Method2097)).orElse(null);
        if (entityPlayer == null) {
            return;
        }
        EntityPlayer entityPlayer2 = null;
        if (Class112.mc.world.getPlayerEntityByName(entityPlayer.getName()) != null) {
            entityPlayer2 = Class112.mc.world.getPlayerEntityByName(entityPlayer.getName());
        }
        if (entityPlayer2 == null || entityPlayer2.isDead || entityPlayer2.getHealth() <= 0.0f) {
            return;
        }
        Class112.Method2130(this.Method2320(), this.Method2324(), this.Method2320() + this.Method2329(), this.Method2324() + this.Method2322(), ((ColorValue)this.color.getValue()).Method774(), false);
        Class112.Method2130(this.Method2320(), this.Method2324(), this.Method2320() + this.Method2329(), this.Method2324() + this.Method2322(), ((ColorValue)this.outline.getValue()).Method774(), true);
        Field2472 = true;
        PlayerUtil.Method1090((int)this.Method2320() + 50, (int)this.Method2324() + 100, 50, -30.0f, 0.0f, entityPlayer2, (Boolean)this.yaw.getValue(), (Boolean)this.pitch.getValue());
        Field2472 = false;
        Class557.Method801(entityPlayer2.getDisplayNameString() + this.Method2124(entityPlayer2) + this.Method2126(entityPlayer2), (int)this.Method2320() + 100, (int)this.Method2324() + (int)this.Field2469, Color.WHITE.hashCode());
        DecimalFormat decimalFormat = new DecimalFormat("##");
        Class557.Method801(decimalFormat.format(entityPlayer2.getHealth() + entityPlayer2.getAbsorptionAmount()) + "hp", (int)(this.Method2320() + 100.0f), (int)((float)((int)this.Method2324()) + this.Field2469 * 2.0f), this.Method2122(entityPlayer2.getHealth() + entityPlayer2.getAbsorptionAmount()));
        boolean bl = Class112.mc.player.connection.getPlayerInfo(entityPlayer2.getName()) == null;
        Class557.Method801(bl ? "0" : Class112.mc.player.connection.getPlayerInfo(entityPlayer2.getName()).getResponseTime() + "ms", (int)(this.Method2320() + 100.0f), (int)((float)((int)this.Method2324()) + this.Field2469 * 3.0f), Color.WHITE.hashCode());
        int n = Class87.Field262.getOrDefault(entityPlayer2.getName(), 0);
        Class557.Method801(n + " pops", (int)(this.Method2320() + 100.0f), (int)((float)((int)this.Method2324()) + this.Field2469 * 4.0f), Color.WHITE.hashCode());
        if (this.Method2129(entityPlayer2) != 0.0f) {
            Class557.Method801(this.Method2129(entityPlayer2) == 0.0f ? "No Armor" : decimalFormat.format(this.Method2127(entityPlayer2) / this.Method2129(entityPlayer2) * 100.0f) + "%", (int)this.Method2320() + 100, (int)((float)((int)this.Method2324()) + this.Field2469 * 5.0f), this.Method2128(this.Method2127(entityPlayer2) / this.Method2129(entityPlayer2)));
        }
        this.Method2125((int)this.Method2320() + 100, (int)this.Method2324() + (int)(this.Field2469 * 6.0f), entityPlayer2.getHeldItemMainhand());
        this.Method2125((int)this.Method2320() + 116, (int)this.Method2324() + (int)(this.Field2469 * 6.0f), entityPlayer2.getHeldItemOffhand());
        int n2 = 100;
        for (int i = 3; i >= 0; --i) {
            ItemStack itemStack = (ItemStack)entityPlayer2.inventory.armorInventory.get(i);
            this.Method2125((int)(this.Method2320() + (float)n2), (int)this.Method2324() + (int)(this.Field2469 * 7.0f + 6.0f), itemStack);
            n2 += 16;
        }
    }

    public float Method2127(EntityPlayer entityPlayer) {
        int n = 0;
        for (ItemStack itemStack : entityPlayer.getArmorInventoryList()) {
            n += itemStack.getMaxDamage() - itemStack.getItemDamage();
        }
        return n;
    }

    public int Method2128(float f) {
        return MathHelper.hsvToRGB((float)(Math.max(0.0f, f) / 3.0f), (float)1.0f, (float)1.0f);
    }

    public float Method2129(EntityPlayer entityPlayer) {
        int n = 0;
        for (ItemStack itemStack : entityPlayer.getArmorInventoryList()) {
            n += itemStack.getMaxDamage();
        }
        return n;
    }

    public static void Method2130(float f, float f2, float f3, float f4, int n, boolean bl) {
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

    public Class112() {
        super("Target", 200.0f, 600.0f, 200.0f, 105.0f);
        ((ColorValue)this.color.getValue()).Method771(Integer.MIN_VALUE);
        ((ColorValue)this.outline.getValue()).Method771(-805306368);
    }
}