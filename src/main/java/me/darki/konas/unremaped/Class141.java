package me.darki.konas.unremaped;

import java.awt.Color;

import me.darki.konas.setting.ColorValue;
import me.darki.konas.KonasMod;
import me.darki.konas.gui.hud.Element;
import me.darki.konas.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class Class141
extends Element {
    public static Setting<Class144> mode = new Setting<>("Mode", Class144.IMAGE);
    public Setting<ColorValue> textColor = new Setting<>("TextColor", new ColorValue(new Color(255, 85, 255, 255).hashCode(), false)).visibleIf(Class141::Method901);

    public Class141() {
        super("Watermark", 5.0f, 5.0f, 100.0f, 60.0f);
    }

    @Override
    public void onRender2D() {
        super.onRender2D();
        switch (Class118.Field2446[((Class144)((Object)mode.getValue())).ordinal()]) {
            case 1: {
                String string = KonasMod.NAME + " " + "0.10.2";
                this.Method2319(Class557.Method799(string) + 1.0f);
                this.Method2323(Class557.Method800(string) + 1.0f);
                Class557.Method801(string, (int)this.Method2320(), (int)this.Method2324(), ((ColorValue)this.textColor.getValue()).Method774());
                break;
            }
            case 2: {
                this.Method2319(60.0f);
                this.Method2323(60.0f);
                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("konas/textures/konas.png"));
                GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                GlStateManager.enableAlpha();
                Gui.drawModalRectWithCustomSizedTexture((int)((int)this.Method2320()), (int)((int)this.Method2324()), (float)0.0f, (float)0.0f, (int)60, (int)60, (float)60.0f, (float)60.0f);
                GlStateManager.disableAlpha();
                break;
            }
        }
    }

    public static boolean Method901() {
        return mode.getValue() == Class144.TEXT;
    }
}