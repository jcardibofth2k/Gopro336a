package me.darki.konas.unremaped;

import me.darki.konas.*;
import java.awt.Color;
import java.util.UUID;

import me.darki.konas.setting.ColorValue;
import me.darki.konas.gui.hud.Element;
import me.darki.konas.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;

public class Class143
extends Element {
    public Setting<ColorValue> textColor = new Setting<>("TextColor", new ColorValue(new Color(255, 85, 255, 255).hashCode(), false));

    public int Method1852() {
        Minecraft minecraft;
        if (mc.getConnection() == null) {
            return 1;
        }
        if (Class143.mc.player == null) {
            return -1;
        }
        try {
            minecraft = mc;
        }
        catch (NullPointerException nullPointerException) {
            return -1;
        }
        NetHandlerPlayClient netHandlerPlayClient = minecraft.getConnection();
        EntityPlayerSP entityPlayerSP = Class143.mc.player;
        UUID uUID = entityPlayerSP.getUniqueID();
        NetworkPlayerInfo networkPlayerInfo = netHandlerPlayClient.getPlayerInfo(uUID);
        return networkPlayerInfo.getResponseTime();
    }

    @Override
    public void onRender2D() {
        super.onRender2D();
        String string = this.Method1852() + " ms";
        float f = Math.max(5.0f, Class557.Method800(string));
        this.Method2323(f + 1.0f);
        this.Method2319(Class557.Method799(string) + 1.0f);
        Class557.Method801(string, (int)this.Method2320(), (int)this.Method2324(), ((ColorValue)this.textColor.getValue()).Method774());
    }

    public Class143() {
        super("Ping", 100.0f, 200.0f, 5.0f, 10.0f);
    }
}