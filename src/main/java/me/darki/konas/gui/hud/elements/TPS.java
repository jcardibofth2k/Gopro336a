package me.darki.konas.gui.hud.elements;

import java.awt.Color;
import java.text.DecimalFormat;

import me.darki.konas.gui.hud.Element;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.Class456;
import me.darki.konas.unremaped.Class557;
import me.darki.konas.setting.ColorValue;

public class TPS
extends Element {
    public Setting<ColorValue> textColor = new Setting<>("TextColor", new ColorValue(new Color(255, 85, 255, 255).hashCode(), false));

    public TPS() {
        super("TPS", 0.0f, 200.0f, 5.0f, 10.0f);
    }

    @Override
    public void onRender2D() {
        StringBuilder stringBuilder;
        DecimalFormat decimalFormat;
        super.onRender2D();
        DecimalFormat decimalFormat2 = decimalFormat;
        DecimalFormat decimalFormat3 = decimalFormat;
        String string = "#.##";
        decimalFormat2(string);
        DecimalFormat decimalFormat4 = decimalFormat3;
        StringBuilder stringBuilder2 = stringBuilder;
        StringBuilder stringBuilder3 = stringBuilder;
        stringBuilder2();
        String string2 = "TPS: ";
        StringBuilder stringBuilder4 = stringBuilder3.append(string2);
        DecimalFormat decimalFormat5 = decimalFormat4;
        Class456 class456 = Class456.Field486;
        float f = class456.Method564();
        double d = f;
        String string3 = decimalFormat5.format(d);
        StringBuilder stringBuilder5 = stringBuilder4.append(string3);
        String string4 = stringBuilder5.toString();
        String string5 = string4;
        float f2 = 5.0f;
        String string6 = string5;
        float f3 = Class557.Method800(string6);
        float f4 = Math.max(f2, f3);
        float f5 = f4;
        TPS TPS = this;
        float f6 = f5 + 1.0f;
        TPS.Method2323(f6);
        TPS class1282 = this;
        String string7 = string5;
        float f7 = Class557.Method799(string7);
        float f8 = f7 + 1.0f;
        class1282.Method2319(f8);
        String string8 = string5;
        TPS class1283 = this;
        float f9 = class1283.Method2320();
        float f10 = (int)f9;
        TPS class1284 = this;
        float f11 = class1284.Method2324();
        float f12 = (int)f11;
        Setting<ColorValue> setting = this.Field2205;
        Object t = setting.getValue();
        ColorValue colorValue = (ColorValue)t;
        int n = colorValue.Method774();
        try {
            Class557.Method801(string8, f10, f12, n);
        }
        catch (NullPointerException nullPointerException) {
            // empty catch block
        }
    }
}