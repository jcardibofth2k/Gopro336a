package me.darki.konas.unremaped;

import me.darki.konas.*;
import java.awt.Color;

import me.darki.konas.setting.ColorValue;
import me.darki.konas.gui.hud.Element;
import me.darki.konas.setting.Setting;

public class Class138
extends Element {
    public Setting<ColorValue> textColor = new Setting<>("TextColor", new ColorValue(new Color(255, 85, 255, 255).hashCode(), false));

    @Override
    public void onRender2D() {
        block0: {
            super.onRender2D();
            if (Class138.mc.player == null) break block0;
            String string = (mc.isIntegratedServerRunning() ? "Singleplayer: " : "Server: ") + (Class138.mc.player.getServerBrand() == null ? "Unknown" : Class138.mc.player.getServerBrand());
            this.Method2319(Class557.Method799(string) + 1.0f);
            this.Method2323(Class557.Method800(string) + 1.0f);
            Class557.Method801(string, this.Method2320(), this.Method2324(), ((ColorValue)this.textColor.getValue()).Method774());
        }
    }

    public Class138() {
        super("ServerBrand", 200.0f, 1000.0f, 50.0f, 10.0f);
    }
}