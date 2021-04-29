package me.darki.konas.gui.hud.elements;

import me.darki.konas.util.PlayerUtil;
import me.darki.konas.gui.hud.Element;
import me.darki.konas.setting.Setting;
import net.minecraft.client.Minecraft;

public class PlayerViewer
extends Element {
    public Setting<Boolean> yaw = new Setting<>("Yaw", true);
    public Setting<Boolean> pitch = new Setting<>("Pitch", true);

    public PlayerViewer() {
        super("Player", 3.0f, 80.0f, 100.0f, 115.0f);
    }

    @Override
    public void onRender2D() {
        super.onRender2D();
        if (PlayerViewer.mc.player == null || PlayerViewer.mc.world == null) {
            return;
        }
        PlayerUtil.Method1090((int)(this.Method2320() + this.Method2329() / 2.0f), (int)(this.Method2324() + this.Method2322()), 50, -30.0f, 0.0f, Minecraft.getMinecraft().player, this.yaw.getValue(), this.pitch.getValue());
    }
}