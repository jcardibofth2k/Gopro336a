package me.darki.konas;

import java.awt.Color;
import java.text.DecimalFormat;

import me.darki.konas.gui.hud.Element;
import me.darki.konas.setting.Setting;

public class Class120
extends Element {
    public Setting<ColorValue> Field2309 = new Setting<>("TextColor", new ColorValue(new Color(255, 85, 255, 255).hashCode(), false));
    public static DecimalFormat Field2310 = new DecimalFormat("#.#");

    @Override
    public void onRender2D() {
        block0: {
            long l = System.currentTimeMillis() - Class109.Field2570.Method736();
            String string = "Server not responding for " + Field2310.format((float)l / 1000.0f) + "s";
            float f = Math.max(5.0f, Class557.Method800(string));
            this.Method2323(f + 1.0f);
            this.Method2319(Class557.Method799(string) + 1.0f);
            if (!Class109.Field2570.Method737(2500.0) && !(Class120.mc.currentScreen instanceof Class193)) break block0;
            Class557.Method801(string, (float)((int)this.Method2320() + (int)this.Method2329()) - Class557.Method800(string), (int)this.Method2324(), this.Field2309.getValue().Method774());
        }
    }

    public Class120() {
        super("LagNotifier", 200.0f, 0.0f, 5.0f, 10.0f);
    }
}
