package me.darki.konas.unremaped;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.event.events.MoveEvent;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.util.TimerUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketPlayer;

public class Class239
extends Module {
    public TimerUtil Field2447 = new TimerUtil();
    public int Field2448 = 0;

    @Override
    public void onEnable() {
        if (Class239.mc.player == null || Class239.mc.world == null) {
            this.toggle();
            return;
        }
        this.Field2448 = 0;
        Class239.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Class239.mc.player.posX, Class239.mc.player.posY + 3.0, Class239.mc.player.posZ, Class239.mc.player.onGround));
    }

    @Subscriber
    public void Method503(MoveEvent moveEvent) {
        block1: {
            if (this.Field2448 == 0) break block1;
            moveEvent.setCanceled(true);
            if (this.Field2447.GetDifferenceTiming(2850.0)) {
                this.toggle();
            }
        }
    }

    @Subscriber
    public void Method536(SendPacketEvent sendPacketEvent) {
        block0: {
            if (!(sendPacketEvent.getPacket() instanceof CPacketConfirmTeleport)) break block0;
            this.Field2448 = ((CPacketConfirmTeleport) sendPacketEvent.getPacket()).getTeleportId();
            this.Field2447.UpdateCurrentTime();
            sendPacketEvent.setCanceled(true);
        }
    }

    public Class239() {
        super("Freeze", "Rubberbands you and gives you temporary godmode on some servers", Category.EXPLOIT, new String[0]);
    }

    @Override
    public void onDisable() {
        if (Class239.mc.player == null || Class239.mc.world == null) {
            return;
        }
        Class239.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(this.Field2448));
    }
}