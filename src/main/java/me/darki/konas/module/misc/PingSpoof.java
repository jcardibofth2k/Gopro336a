package me.darki.konas.module.misc;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.SendPacketEvent;
import me.darki.konas.util.TimerUtil;
import me.darki.konas.unremaped.Render3DEvent;
import net.minecraft.network.play.client.CPacketKeepAlive;

public class PingSpoof
extends Module {
    public Setting<Integer> ss = new Setting<>("ss", 200, 2000, 0, 1);
    public TimerUtil Field775 = new TimerUtil();
    public CPacketKeepAlive Field776 = null;

    @Subscriber
    public void Method139(Render3DEvent render3DEvent) {
        if (this.Field775.GetDifferenceTiming(this.ss.getValue().intValue()) && this.Field776 != null) {
            PingSpoof.mc.player.connection.sendPacket(this.Field776);
            this.Field776 = null;
        }
    }

    @Override
    public String Method756() {
        return this.ss.getValue() + "ms";
    }

    @Subscriber
    public void Method536(SendPacketEvent sendPacketEvent) {
        block0: {
            if (!(sendPacketEvent.getPacket() instanceof CPacketKeepAlive) || this.Field776 == sendPacketEvent.getPacket() || this.ss.getValue() == 0) break block0;
            this.Field776 = (CPacketKeepAlive) sendPacketEvent.getPacket();
            sendPacketEvent.Method1235();
            this.Field775.UpdateCurrentTime();
        }
    }

    public PingSpoof() {
        super("PingSpoof", Category.MISC);
    }
}