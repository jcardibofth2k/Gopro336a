package me.darki.konas;

import java.awt.Color;

import me.darki.konas.gui.hud.Element;
import me.darki.konas.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class Class141
extends Element {
    public static Setting<Class144> Field2009 = new Setting<>("Mode", Class144.IMAGE);
    public Setting<ColorValue> Field2010 = new Setting<>("TextColor", new ColorValue(new Color(255, 85, 255, 255).hashCode(), false)).Method1191(Class141::Method901);

    public Class141() {
        super("Watermark", 5.0f, 5.0f, 100.0f, 60.0f);
    }

    @Override
    public void onRender2D() {
        super.onRender2D();
        switch (Class118.Field2446[Field2009.getValue().ordinal()]) {
            case 1: {
                String string = KonasMod.Field732 + " " + "0.10.2";
                this.Method2319(Class557.Method799(string) + 1.0f);
                this.Method2323(Class557.Method800(string) + 1.0f);
                Class557.Method801(string, (int)this.Method2320(), (int)this.Method2324(), this.Field2010.getValue().Method774());
                break;
            }
            case 2: {
                this.Method2319(60.0f);
                this.Method2323(60.0f);
                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("konas/textures/konas.png"));
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                GlStateManager.enableAlpha();
                Gui.drawModalRectWithCustomSizedTexture((int)this.Method2320(), (int)this.Method2324(), 0.0f, 0.0f, 60, 60, 60.0f, 60.0f);
                GlStateManager.disableAlpha();
                break;
            }
        }
    }

    public static boolean Method901() {
        return Field2009.getValue() == Class144.TEXT;
    }
}
