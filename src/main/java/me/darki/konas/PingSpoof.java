package me.darki.konas;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketKeepAlive;

public class PingSpoof
extends Module {
    public Setting<Integer> Field774 = new Setting<>("ss", 200, 2000, 0, 1);
    public Class566 Field775 = new Class566();
    public CPacketKeepAlive Field776 = null;

    @Subscriber
    public void Method139(Class89 class89) {
        if (this.Field775.Method737(((Integer)this.Field774.getValue()).intValue()) && this.Field776 != null) {
            PingSpoof.mc.player.connection.sendPacket((Packet)this.Field776);
            this.Field776 = null;
        }
    }

    @Override
    public String Method756() {
        return this.Field774.getValue() + "ms";
    }

    @Subscriber
    public void Method536(Class24 class24) {
        block0: {
            if (!(class24.getPacket() instanceof CPacketKeepAlive) || this.Field776 == class24.getPacket() || (Integer)this.Field774.getValue() == 0) break block0;
            this.Field776 = (CPacketKeepAlive)class24.getPacket();
            class24.Method1235();
            this.Field775.Method739();
        }
    }

    public PingSpoof() {
        super("PingSpoof", Category.MISC, new String[0]);
    }
}
