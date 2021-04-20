package me.darki.konas.gui.hud.elements;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;

import me.darki.konas.unremaped.Class557;
import me.darki.konas.setting.ColorValue;
import me.darki.konas.gui.hud.Element;
import me.darki.konas.mixin.mixins.IMinecraft;
import me.darki.konas.mixin.mixins.ITimer;
import me.darki.konas.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;

public class SpeedElement
extends Element {
    public Setting<ColorValue> textColor = new Setting<>("TextColor", new ColorValue(new Color(255, 85, 255, 255).hashCode(), false));
    public Setting<Boolean> kilometers = new Setting<>("Kilometers", true);
    public Setting<Integer> places = new Setting<>("Places", 2, 5, 0, 1);
    public Setting<Boolean> vertical = new Setting<>("Vertical", true);
    public double[] Field281 = new double[20];
    public int Field282 = 0;

    public double Method452(double d, int n) {
        if (n < 0) {
            return d;
        }
        return new BigDecimal(d).setScale(n, RoundingMode.HALF_UP).doubleValue();
    }

    public static double speed(char c, Minecraft minecraft) {
        switch (c) {
            case 'x': {
                return minecraft.player.posX - minecraft.player.prevPosX;
            }
            case 'y': {
                return minecraft.player.posY - minecraft.player.prevPosY;
            }
            case 'z': {
                return minecraft.player.posZ - minecraft.player.prevPosZ;
            }
        }
        return 0.0;
    }

    public SpeedElement() {
        super("Speed", 100.0f, 200.0f, 5.0f, 10.0f);
    }

    @Override
    public void onRender2D() {
        super.onRender2D();
        String string = (kilometers.getValue() ? "KPH: " : "BPS: ") + calcSpeed();
        float f = Math.max(5.0f, Class557.Method800(string));
        Method2323(f + 1.0f);
        Method2319(Class557.Method799(string) + 1.0f);
        Class557.Method801(string, (int)Method2320(), (int)Method2324(), textColor.getValue().Method774());
    }

    public String calcSpeed() {
        double d;
        float f = ((ITimer)((IMinecraft)mc).getTimer()).getTickLength() / 1000.0f;
        double d2 = 1.0;
        if (kilometers.getValue()) {
            d2 = 3.6;
        }
        Field281[Field282 % Field281.length] = d = (double)(MathHelper.sqrt(Math.pow(SpeedElement.speed('x', mc), 2.0) + (vertical.getValue() ? Math.pow(SpeedElement.speed('y', mc), 2.0) : 0.0) + Math.pow(SpeedElement.speed('z', mc), 2.0)) / f) * d2;
        ++Field282;
        int n = 1;
        double d3 = 0.0;
        for (double d4 : Field281) {
            d3 += d4;
            ++n;
        }
        return "" + Method452(d3 / (double)n, places.getValue());
    }
}