package me.darki.konas.gui.hud.elements;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.darki.konas.setting.ColorValue;
import me.darki.konas.gui.hud.Element;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.Class557;

public class Clock
extends Element {
    public Setting<ColorValue> textColor = new Setting<>("TextColor", new ColorValue(new Color(255, 85, 255, 255).hashCode(), false));

    @Override
    public void onRender2D() {
        super.onRender2D();
        String string = new SimpleDateFormat("H:mm").format(new Date());
        float f = Math.max(5.0f, Class557.Method800(string));
        this.Method2323(f + 1.0f);
        this.Method2319(Class557.Method799(string) + 1.0f);
        Class557.Method801(string, (float)((int)this.Method2320() + (int)this.Method2329()) - Class557.Method800(string), (int)this.Method2324(), this.textColor.getValue().Method774());
    }

    public Clock() {
        super("Clock", 150.0f, 50.0f, 5.0f, 10.0f);
    }
}