package me.darki.konas.unremaped;

import me.darki.konas.*;
import java.awt.Color;

import me.darki.konas.module.ModuleManager;
import me.darki.konas.setting.ColorValue;
import me.darki.konas.util.RenderUtil2;
import me.darki.konas.gui.hud.Element;
import me.darki.konas.module.movement.ElytraFly;
import me.darki.konas.setting.Setting;

public class Class140
extends Element {
    public Setting<ColorValue> textColor = new Setting<>("TextColor", new ColorValue(new Color(255, 85, 255, 255).hashCode(), false));

    @Override
    public void onRender2D() {
        super.onRender2D();
        if (Class140.mc.player == null || Class140.mc.world == null) {
            return;
        }
        if (!ModuleManager.getModuleByClass(ElytraFly.class).isEnabled() || !ElytraFly.Method976()) {
            return;
        }
        RenderUtil2.Method1338(this.Method2320(), this.Method2324(), this.Method2329(), this.Method2322() / 2.0f, Class140.mc.player.rotationPitch <= 0.0f ? -14974014 : -15441777);
        RenderUtil2.Method1338(this.Method2320(), this.Method2324() + this.Method2322() / 2.0f, this.Method2329(), this.Method2322() / 2.0f, Class140.mc.player.rotationPitch > 0.0f ? -9938892 : -11582680);
        float f = this.Method2324() + (Class140.mc.player.rotationPitch + 90.0f) / 180.0f * this.Method2322();
        RenderUtil2.Method1338(this.Method2320(), f, this.Method2329(), 2.0f, ((ColorValue)this.textColor.getValue()).Method774());
        RenderUtil2.Method1336(this.Method2320(), this.Method2324(), this.Method2329(), this.Method2322(), 2.0f, ((ColorValue)this.textColor.getValue()).Method774());
    }

    public Class140() {
        super("FlightPitch", 5.0f, 100.0f, 50.0f, 100.0f);
    }
}