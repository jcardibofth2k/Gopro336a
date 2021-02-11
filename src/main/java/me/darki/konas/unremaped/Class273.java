package me.darki.konas;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketVehicleMove;

public class Class273
extends Module {
    public Setting<Boolean> Field1855 = new Setting<>("CPacketInput", false);
    public Setting<Boolean> Field1856 = new Setting<>("CPacketPlayer", false);
    public Setting<Boolean> Field1857 = new Setting<>("CPacketEntityAction", false);
    public Setting<Boolean> Field1858 = new Setting<>("CPacketUseEntity", false);
    public Setting<Boolean> Field1859 = new Setting<>("CPacketVehicleMove", false);
    public int Field1860 = 0;

    public void Method124() {
        block0: {
            if (Class273.mc.player != null && Class273.mc.world != null) break block0;
            this.toggle();
        }
    }

    @Subscriber
    public void Method536(Class24 class24) {
        block0: {
            if (!(this.Field1855.getValue() != false && class24.getPacket() instanceof CPacketInput || this.Field1856.getValue() != false && class24.getPacket() instanceof CPacketPlayer && Class273.mc.player.getRidingEntity() == null || this.Field1857.getValue() != false && class24.getPacket() instanceof CPacketEntityAction || this.Field1858.getValue() != false && class24.getPacket() instanceof CPacketUseEntity) && (!this.Field1859.getValue().booleanValue() || !(class24.getPacket() instanceof CPacketVehicleMove))) break block0;
            class24.Cancel();
            ++this.Field1860;
            this.Method1645(Integer.toString(this.Field1860));
        }
    }

    public Class273() {
        super("PacketCanceller", Category.EXPLOIT);
    }

    @Override
    public void onEnable() {
        this.Field1860 = 0;
    }
}
