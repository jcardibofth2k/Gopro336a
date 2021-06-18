package me.darki.konas.module.misc;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.SendPacketEvent;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketVehicleMove;

public class PacketCanceller
extends Module {
    public Setting<Boolean> cPacketInput = new Setting<>("CPacketInput", false);
    public Setting<Boolean> cPacketPlayer = new Setting<>("CPacketPlayer", false);
    public Setting<Boolean> cPacketEntityAction = new Setting<>("CPacketEntityAction", false);
    public Setting<Boolean> cPacketUseEntity = new Setting<>("CPacketUseEntity", false);
    public Setting<Boolean> cPacketVehicleMove = new Setting<>("CPacketVehicleMove", false);
    public int Field1860 = 0;

    public void Method124() {
        block0: {
            if (PacketCanceller.mc.player != null && PacketCanceller.mc.world != null) break block0;
            this.toggle();
        }
    }

    @Subscriber
    public void Method536(SendPacketEvent sendPacketEvent) {
        block0: {
            if (!((Boolean)this.cPacketInput.getValue() != false && sendPacketEvent.getPacket() instanceof CPacketInput || (Boolean)this.cPacketPlayer.getValue() != false && sendPacketEvent.getPacket() instanceof CPacketPlayer && PacketCanceller.mc.player.getRidingEntity() == null || (Boolean)this.cPacketEntityAction.getValue() != false && sendPacketEvent.getPacket() instanceof CPacketEntityAction || (Boolean)this.cPacketUseEntity.getValue() != false && sendPacketEvent.getPacket() instanceof CPacketUseEntity) && (!((Boolean)this.cPacketVehicleMove.getValue()).booleanValue() || !(sendPacketEvent.getPacket() instanceof CPacketVehicleMove))) break block0;
            sendPacketEvent.Cancel();
            ++this.Field1860;
            this.Method1645(Integer.toString(this.Field1860));
        }
    }

    public PacketCanceller() {
        super("PacketCanceller", Category.EXPLOIT, new String[0]);
    }

    @Override
    public void onEnable() {
        this.Field1860 = 0;
    }
}