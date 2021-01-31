package me.darki.konas.module.render;

import cookiedragon.eventsystem.Subscriber;
import java.awt.Color;
import java.util.Comparator;

import me.darki.konas.*;
import me.darki.konas.command.commands.fontCommand;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class Hud
extends Module {
    public static Setting<Boolean> Field1391 = new Setting<>("Overlap", true);
    public static Setting<Boolean> Field1392 = new Setting<>("PotionIcons", false);
    public static Setting<Boolean> Field1393 = new Setting<>("Blur", false);
    public static Setting<Boolean> Field1394 = new Setting<>("BlurEverything", true).Method1191(Field1393::getValue);
    public Setting<Boolean> Field1395 = new IdkWhatThisSettingThingDoes("CustomFont", true, new Class159(this));
    public static Setting<Boolean> Field1396 = new Setting<>("Hotbar", true);
    public static Setting<Boolean> Field1397 = new Setting<>("XPBar", true);
    public static Setting<Class165> Field1398 = new Setting<>("ArmorMode", Class165.BOTH);
    public static Setting<Class189> Field1399 = new Setting<>("NumberMode", Class189.PERCENTAGE).Method1191(Hud::Method388);
    public static Setting<Class163> Field1400 = new Setting<>("ColorMode", Class163.DYNAMIC).Method1191(Hud::Method394);
    public static Setting<ColorValue> Field1401 = new Setting<>("Color", new ColorValue(new Color(255, 85, 255, 255).hashCode(), false)).Method1191(Hud::Method393);
    public static Setting<Boolean> Field1402 = new Setting<>("Health", true);
    public static Setting<Boolean> Field1403 = new Setting<>("Food", true);
    public static Setting<Boolean> Field1404 = new Setting<>("Crosshair", true);
    public static Class555 Field1405 = new Class555(fontCommand.Field1351, 18.0f);

    @Subscriber
    public void Method1454(RenderGameOverlayEvent.Post post) {
        GuiIngameForge.renderHealth = (Boolean)Field1402.getValue();
        GuiIngameForge.renderArmor = Field1398.getValue() == Class165.BOTH || Field1398.getValue() == Class165.VANILLA;
        GuiIngameForge.renderExperiance = (Boolean)Field1397.getValue();
        GuiIngameForge.renderHotbar = (Boolean)Field1396.getValue();
        GuiIngameForge.renderFood = (Boolean)Field1403.getValue();
        GuiIngameForge.renderCrosshairs = (Boolean)Field1404.getValue();
    }

    public String Method1455(PotionEffect potionEffect) {
        return I18n.format((String)potionEffect.getEffectName(), (Object[])new Object[0]);
    }

    public int Method1456(ItemStack itemStack) {
        int n = itemStack.getMaxDamage();
        int n2 = n - itemStack.getItemDamage();
        int n3 = (int)Math.round((double)n2 / ((double)n * 0.01));
        boolean bl = Field1399.getValue() == Class189.PERCENTAGE;
        return bl ? n3 : n2;
    }

    @Subscriber
    public void Method1451(Class654 class654) {
        block2: {
            block1: {
                if (Hud.mc.player == null || Hud.mc.world == null) {
                    return;
                }
                if (class654.Method1161() == null || !((Boolean)Field1393.getValue()).booleanValue() || class654.Method1161() instanceof GuiChat) break block1;
                if (!(class654.Method1161() instanceof Class193) && !((Boolean)Field1394.getValue()).booleanValue()) break block2;
                Hud.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
                break block2;
            }
            if (!Hud.mc.entityRenderer.isShaderActive()) break block2;
            Hud.mc.entityRenderer.stopUseShader();
        }
    }

    public int Method1457(FontRenderer fontRenderer, PotionEffect potionEffect) {
        return fontRenderer.getStringWidth(this.Method1460(potionEffect));
    }

    public static boolean Method393() {
        return Field1400.getValue() == Class163.STATIC && (Field1398.getValue() == Class165.BOTH || Field1398.getValue() == Class165.KONAS);
    }

    public int Method1458(PotionEffect potionEffect) {
        String string;
        switch (string = this.Method1455(potionEffect)) {
            case "Absorption": {
                return new Color(33, 118, 255).hashCode();
            }
            case "Fire Resistance": {
                return new Color(247, 152, 36).hashCode();
            }
            case "Regeneration": {
                return new Color(232, 142, 237).hashCode();
            }
            case "Strength": 
            case "Resistance": {
                return new Color(237, 28, 36).hashCode();
            }
            case "Hunger": {
                return new Color(41, 191, 18).hashCode();
            }
            case "Jump Boost": {
                return new Color(0, 204, 51).hashCode();
            }
            case "Haste": {
                return new Color(255, 207, 0).hashCode();
            }
            case "Speed": {
                return new Color(0, 255, 227).hashCode();
            }
        }
        return Color.WHITE.hashCode();
    }

    public static boolean Method388() {
        return Field1398.getValue() == Class165.BOTH || Field1398.getValue() == Class165.KONAS;
    }

    public int Method1459(ItemStack itemStack) {
        if (Field1400.getValue() == Class163.DYNAMIC) {
            return itemStack.getItem().getRGBDurabilityForDisplay(itemStack);
        }
        return ((ColorValue)Field1401.getValue()).Method774();
    }

    public String Method1460(PotionEffect potionEffect) {
        return this.Method1455(potionEffect) + " " + Potion.getPotionDurationString((PotionEffect)potionEffect, (float)1.0f);
    }

    public void Method1461(FontRenderer fontRenderer, ScaledResolution scaledResolution) {
        int[] nArray = Hud.mc.ingameGUI.getChatGUI().getChatOpen() ? new int[]{scaledResolution.getScaledHeight() - (Hud.mc.player.getActivePotionEffects().size() * fontRenderer.FONT_HEIGHT + 5 + (fontRenderer.FONT_HEIGHT + 5))} : new int[]{scaledResolution.getScaledHeight() - (Hud.mc.player.getActivePotionEffects().size() * fontRenderer.FONT_HEIGHT + 5)};
        Hud.mc.player.getActivePotionEffects().stream().sorted(Comparator.comparingInt(arg_0 -> this.Method1457(fontRenderer, arg_0))).forEach(arg_0 -> this.Method1463(fontRenderer, scaledResolution, nArray, arg_0));
    }

    public Hud() {
        super("HUD", "Displays information on the ingame screen", Category.CLIENT, new String[0]);
    }

    public static boolean Method394() {
        return Field1398.getValue() == Class165.BOTH || Field1398.getValue() == Class165.KONAS;
    }

    @Subscriber
    public void Method1462(Class12 class12) {
        block0: {
            if (!((Boolean)Field1392.getValue()).booleanValue()) break block0;
            class12.Cancel();
        }
    }

    @Subscriber
    public void Method466(Class91 class91) {
        if (Hud.mc.world == null || Hud.mc.player == null) {
            return;
        }
        if (Field1398.getValue() == Class165.KONAS || Field1398.getValue() == Class165.BOTH) {
            ScaledResolution scaledResolution = new ScaledResolution(mc);
            int n = 3;
            for (int i = 3; i >= 0; --i) {
                ItemStack itemStack = Hud.mc.player.inventory.armorItemInSlot(i);
                if (itemStack == null || !(itemStack.getItem() instanceof ItemArmor) && !(itemStack.getItem() instanceof ItemElytra)) continue;
                GlStateManager.enableDepth();
                Hud.mc.getRenderItem().zLevel = 200.0f;
                String string = "" + this.Method1456(itemStack);
                int n2 = (int)(scaledResolution.getScaledWidth_double() * (double)0.5085f);
                int n3 = scaledResolution.getScaledHeight() - 68;
                Class557.Method801(string, n2 + n + 8 - (int)(Class557.Method800(string) / 2.0f), n3, this.Method1459(itemStack));
                mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, n2 + n, n3 + 8);
                mc.getRenderItem().renderItemOverlayIntoGUI(Hud.mc.standardGalacticFontRenderer, itemStack, n2 + n, n3 + 12, "");
                Hud.mc.getRenderItem().zLevel = 0.0f;
                GlStateManager.enableTexture2D();
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                n += 20;
            }
        }
    }

    public void Method1463(FontRenderer fontRenderer, ScaledResolution scaledResolution, int[] nArray, PotionEffect potionEffect) {
        fontRenderer.drawStringWithShadow(this.Method1460(potionEffect), (float)(scaledResolution.getScaledWidth() - 2 - fontRenderer.getStringWidth(this.Method1460(potionEffect))), (float)nArray[0], this.Method1458(potionEffect));
        nArray[0] = nArray[0] + fontRenderer.FONT_HEIGHT;
    }
}
