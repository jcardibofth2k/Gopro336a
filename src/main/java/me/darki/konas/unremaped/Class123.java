package me.darki.konas.unremaped;

import me.darki.konas.*;
import java.awt.Color;

import me.darki.konas.setting.ColorValue;
import me.darki.konas.gui.hud.Element;
import me.darki.konas.setting.Setting;
import net.minecraft.client.Minecraft;

public class Class123
extends Element {
    public Setting<ColorValue> textColor = new Setting<>("TextColor", new ColorValue(new Color(255, 85, 255, 255).hashCode(), false));

    @Override
    public void onRender2D() {
        super.onRender2D();
        String string = Minecraft.getDebugFPS() + " FPS";
        float f = Math.max(5.0f, Class557.Method800(string));
        this.Method2323(f + 1.0f);
        this.Method2319(Class557.Method799(string) + 1.0f);
        Class557.Method801(string, (float)((int)this.Method2320() + (int)this.Method2329()) - Class557.Method800(string), (int)this.Method2324(), ((ColorValue)this.textColor.getValue()).Method774());
    }

    public Class123() {
        super("FPS", 100.0f, 250.0f, 5.0f, 10.0f);
    }
}