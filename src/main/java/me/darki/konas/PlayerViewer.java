package me.darki.konas;

import me.darki.konas.gui.hud.Element;
import me.darki.konas.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerViewer
extends Element {
    public Setting<Boolean> Field1987 = new Setting<>("Yaw", true);
    public Setting<Boolean> Field1988 = new Setting<>("Pitch", true);

    public PlayerViewer() {
        super("Player", 3.0f, 80.0f, 100.0f, 115.0f);
    }

    @Override
    public void onRender2D() {
        super.onRender2D();
        if (PlayerViewer.mc.player == null || PlayerViewer.mc.world == null) {
            return;
        }
        MathUtil.Method1090((int)(this.Method2320() + this.Method2329() / 2.0f), (int)(this.Method2324() + this.Method2322()), 50, -30.0f, 0.0f, (EntityPlayer)Minecraft.getMinecraft().player, (Boolean)this.Field1987.getValue(), (Boolean)this.Field1988.getValue());
    }
}
