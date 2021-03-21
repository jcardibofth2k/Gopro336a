package me.darki.konas.module.misc;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.Class24;
import me.darki.konas.unremaped.Class566;
import me.darki.konas.unremaped.Class89;
import net.minecraft.network.play.client.CPacketKeepAlive;

public class PingSpoof
extends Module {
    public Setting<Integer> ss = new Setting<>("ss", 200, 2000, 0, 1);
    public Class566 Field775 = new Class566();
    public CPacketKeepAlive Field776 = null;

    @Subscriber
    public void Method139(Class89 class89) {
        if (this.Field775.Method737(this.ss.getValue().intValue()) && this.Field776 != null) {
            PingSpoof.mc.player.connection.sendPacket(this.Field776);
            this.Field776 = null;
        }
    }

    @Override
    public String Method756() {
        return this.ss.getValue() + "ms";
    }

    @Subscriber
    public void Method536(Class24 class24) {
        block0: {
            if (!(class24.getPacket() instanceof CPacketKeepAlive) || this.Field776 == class24.getPacket() || this.ss.getValue() == 0) break block0;
            this.Field776 = (CPacketKeepAlive)class24.getPacket();
            class24.Method1235();
            this.Field775.Method739();
        }
    }

    public PingSpoof() {
        super("PingSpoof", Category.MISC);
    }
}