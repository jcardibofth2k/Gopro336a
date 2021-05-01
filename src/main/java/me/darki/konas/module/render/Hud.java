package me.darki.konas.module.render;

import cookiedragon.eventsystem.Subscriber;
import java.awt.Color;
import java.util.Comparator;

import me.darki.konas.command.commands.fontCommand;
import me.darki.konas.event.events.OpenGuiEvent;
import me.darki.konas.gui.clickgui.unremappedGuiStuff.Class193;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.ListenableSettingDecorator;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.*;
import me.darki.konas.setting.ColorValue;
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
    public static Setting<Boolean> overlap = new Setting<>("Overlap", true);
    public static Setting<Boolean> potionIcons = new Setting<>("PotionIcons", false);
    public static Setting<Boolean> blur = new Setting<>("Blur", false);
    public static Setting<Boolean> blurEverything = new Setting<>("BlurEverything", true).visibleIf(Field1393::getValue);
    public Setting<Boolean> Field1395 = new ListenableSettingDecorator("CustomFont", true, new Class159(this));
    public static Setting<Boolean> hotbar = new Setting<>("Hotbar", true);
    public static Setting<Boolean> xPBar = new Setting<>("XPBar", true);
    public static Setting<Class165> armorMode = new Setting<>("ArmorMode", Class165.BOTH);
    public static Setting<Class189> numberMode = new Setting<>("NumberMode", Class189.PERCENTAGE).visibleIf(Hud::Method388);
    public static Setting<Class163> colorMode = new Setting<>("ColorMode", Class163.DYNAMIC).visibleIf(Hud::Method394);
    public static Setting<ColorValue> color = new Setting<>("Color", new ColorValue(new Color(255, 85, 255, 255).hashCode(), false)).visibleIf(Hud::Method393);
    public static Setting<Boolean> health = new Setting<>("Health", true);
    public static Setting<Boolean> food = new Setting<>("Food", true);
    public static Setting<Boolean> crosshair = new Setting<>("Crosshair", true);
    public static CfontRenderer Field1405 = new CfontRenderer(fontCommand.Field1351, 18.0f);

    @Subscriber
    public void Method1454(RenderGameOverlayEvent.Post post) {
        GuiIngameForge.renderHealth = health.getValue();
        GuiIngameForge.renderArmor = armorMode.getValue() == Class165.BOTH || armorMode.getValue() == Class165.VANILLA;
        GuiIngameForge.renderExperiance = xPBar.getValue();
        GuiIngameForge.renderHotbar = hotbar.getValue();
        GuiIngameForge.renderFood = food.getValue();
        GuiIngameForge.renderCrosshairs = crosshair.getValue();
    }

    public String Method1455(PotionEffect potionEffect) {
        return I18n.format(potionEffect.getEffectName());
    }

    public int Method1456(ItemStack itemStack) {
        int n = itemStack.getMaxDamage();
        int n2 = n - itemStack.getItemDamage();
        int n3 = (int)Math.round((double)n2 / ((double)n * 0.01));
        boolean bl = numberMode.getValue() == Class189.PERCENTAGE;
        return bl ? n3 : n2;
    }

    @Subscriber
    public void Method1451(OpenGuiEvent openGuiEvent) {
        block2: {
            block1: {
                if (Hud.mc.player == null || Hud.mc.world == null) {
                    return;
                }
                if (openGuiEvent.Method1161() == null || !blur.getValue().booleanValue() || openGuiEvent.Method1161() instanceof GuiChat) break block1;
                if (!(openGuiEvent.Method1161() instanceof Class193) && !blurEverything.getValue().booleanValue()) break block2;
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
        return colorMode.getValue() == Class163.STATIC && (armorMode.getValue() == Class165.BOTH || armorMode.getValue() == Class165.KONAS);
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
        return armorMode.getValue() == Class165.BOTH || armorMode.getValue() == Class165.KONAS;
    }

    public int Method1459(ItemStack itemStack) {
        if (colorMode.getValue() == Class163.DYNAMIC) {
            return itemStack.getItem().getRGBDurabilityForDisplay(itemStack);
        }
        return color.getValue().Method774();
    }

    public String Method1460(PotionEffect potionEffect) {
        return this.Method1455(potionEffect) + " " + Potion.getPotionDurationString(potionEffect, 1.0f);
    }

    public void Method1461(FontRenderer fontRenderer, ScaledResolution scaledResolution) {
        int[] nArray = Hud.mc.ingameGUI.getChatGUI().getChatOpen() ? new int[]{scaledResolution.getScaledHeight() - (Hud.mc.player.getActivePotionEffects().size() * fontRenderer.FONT_HEIGHT + 5 + (fontRenderer.FONT_HEIGHT + 5))} : new int[]{scaledResolution.getScaledHeight() - (Hud.mc.player.getActivePotionEffects().size() * fontRenderer.FONT_HEIGHT + 5)};
        Hud.mc.player.getActivePotionEffects().stream().sorted(Comparator.comparingInt(arg_0 -> this.Method1457(fontRenderer, arg_0))).forEach(arg_0 -> this.Method1463(fontRenderer, scaledResolution, nArray, arg_0));
    }

    public Hud() {
        super("HUD", "Displays information on the ingame screen", Category.CLIENT);
    }

    public static boolean Method394() {
        return armorMode.getValue() == Class165.BOTH || armorMode.getValue() == Class165.KONAS;
    }

    @Subscriber
    public void Method1462(Class12 class12) {
        block0: {
            if (!potionIcons.getValue().booleanValue()) break block0;
            class12.Cancel();
        }
    }

    @Subscriber
    public void Method466(Class91 class91) {
        if (Hud.mc.world == null || Hud.mc.player == null) {
            return;
        }
        if (armorMode.getValue() == Class165.KONAS || armorMode.getValue() == Class165.BOTH) {
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