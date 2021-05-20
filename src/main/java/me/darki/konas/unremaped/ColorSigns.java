package me.darki.konas.unremaped;

import me.darki.konas.*;
import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.mixin.mixins.ICPacketUpdateSign;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.network.play.client.CPacketUpdateSign;

public class ColorSigns
extends Module {
    public Setting<Class254> mode = new Setting<>("Mode", Class254.VANILLA);

    @Subscriber
    public void Method536(Class24 class24) {
        if (class24.getPacket() instanceof CPacketUpdateSign && mc.getCurrentServerData() != null) {
            block4: for (int i = 0; i < 4; ++i) {
                if (((ICPacketUpdateSign)class24.getPacket()).getLines()[i] == null) continue;
                switch (Class227.Field2571[((Class254)((Object)this.mode.getValue())).ordinal()]) {
                    case 1: {
                        ((ICPacketUpdateSign)class24.getPacket()).getLines()[i] = ((ICPacketUpdateSign)class24.getPacket()).getLines()[i].replace("&", "\u00a7\u00a70");
                        continue block4;
                    }
                    case 2: {
                        ((ICPacketUpdateSign)class24.getPacket()).getLines()[i] = ((ICPacketUpdateSign)class24.getPacket()).getLines()[i].replace("&", "\u00a7\u00a7\u00a700");
                    }
                }
            }
        }
    }

    public ColorSigns() {
        super("ColorSigns", "Lets you use color codes on signs", -7213820, Category.EXPLOIT, new String[0]);
    }
}