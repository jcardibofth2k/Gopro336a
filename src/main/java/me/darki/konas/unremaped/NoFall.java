package me.darki.konas.unremaped;

import cookiedragon.eventsystem.Subscriber;
import java.util.ArrayList;

import me.darki.konas.event.events.MoveEvent;
import me.darki.konas.event.events.PacketEvent;
import me.darki.konas.mixin.mixins.ICPacketPlayer;
import me.darki.konas.mixin.mixins.ISPacketPlayerPosLook;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.module.movement.PacketFly;
import me.darki.konas.setting.Setting;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;

public class NoFall
extends Module {
    public static Setting<Integer> fallDistance = new Setting<>("FallDistance", 3, 30, 1, 1);
    public static Setting<Class268> mode = new Setting<>("Mode", Class268.PACKET);
    public double Field2019 = 256.0;
    public int Field2020;
    public ArrayList<CPacketPlayer> Field2021 = new ArrayList();

    @Override
    public void onEnable() {
        this.Field2019 = 256.0;
    }

    @Subscriber
    public void Method536(SendPacketEvent sendPacketEvent) {
        if (sendPacketEvent.getPacket() instanceof CPacketPlayer) {
            switch (Class245.Field2222[((Class268)((Object)mode.getValue())).ordinal()]) {
                case 3: {
                    if (!(NoFall.mc.player.fallDistance > Math.min(3.0f, (float)((Integer)fallDistance.getValue()).intValue()))) break;
                    if (((CPacketPlayer) sendPacketEvent.getPacket()).getY(NoFall.mc.player.posY) < this.Field2019) {
                        ((ICPacketPlayer) sendPacketEvent.getPacket()).setY(NoFall.mc.player.posY + (double)Math.min(3.0f, (float)((Integer)fallDistance.getValue()).intValue()));
                        this.Field2019 = ((CPacketPlayer) sendPacketEvent.getPacket()).getY(NoFall.mc.player.posY);
                        break;
                    }
                    this.Field2019 = 256.0;
                    NoFall.mc.player.fallDistance = 0.0f;
                    break;
                }
                case 4: {
                    if (!(NoFall.mc.player.fallDistance > (float)((Integer)fallDistance.getValue()).intValue())) break;
                    ((ICPacketPlayer) sendPacketEvent.getPacket()).setOnGround(true);
                    break;
                }
            }
        }
    }

    @Subscriber
    public void Method503(MoveEvent moveEvent) {
        block2: {
            if (NoFall.mc.player == null || NoFall.mc.world == null) {
                return;
            }
            if (mode.getValue() != Class268.PACKET) break block2;
            if (NoFall.mc.player.fallDistance > (float)((Integer)fallDistance.getValue()).intValue() && !NoFall.mc.player.onGround) {
                moveEvent.setX(0.0);
                moveEvent.setY(-0.062);
                moveEvent.setZ(0.0);
            }
        }
    }

    @Subscriber
    public void Method130(Class19 class19) {
        if (NoFall.mc.player == null || NoFall.mc.world == null) {
            return;
        }
        switch (Class245.Field2222[((Class268)((Object)mode.getValue())).ordinal()]) {
            case 1: {
                if (!(NoFall.mc.player.fallDistance > (float)((Integer)fallDistance.getValue()).intValue())) break;
                if (this.Field2020 <= 0) {
                    CPacketPlayer.Position position = new CPacketPlayer.Position(PacketFly.Method2055(), 1.0, PacketFly.Method2055(), NoFall.mc.player.onGround);
                    this.Field2021.add((CPacketPlayer)position);
                    NoFall.mc.player.connection.sendPacket((Packet)position);
                    break;
                }
                CPacketPlayer.Position position = new CPacketPlayer.Position(NoFall.mc.player.posX, NoFall.mc.player.posY - 0.062, NoFall.mc.player.posZ, NoFall.mc.player.onGround);
                this.Field2021.add((CPacketPlayer)position);
                NoFall.mc.player.connection.sendPacket((Packet)position);
                CPacketPlayer.Position position2 = new CPacketPlayer.Position(NoFall.mc.player.posX, 1.0, NoFall.mc.player.posZ, NoFall.mc.player.onGround);
                this.Field2021.add((CPacketPlayer)position2);
                NoFall.mc.player.connection.sendPacket((Packet)position2);
                ++this.Field2020;
                NoFall.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(this.Field2020 - 1));
                NoFall.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(this.Field2020));
                NoFall.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(this.Field2020 + 1));
                break;
            }
            case 2: {
                if (!(NoFall.mc.player.fallDistance > (float)((Integer)fallDistance.getValue()).intValue())) break;
                NoFall.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(NoFall.mc.player.posX, 10000.0, NoFall.mc.player.posZ, NoFall.mc.player.onGround));
                break;
            }
        }
    }

    public NoFall() {
        super("NoFall", Category.EXPLOIT, "AntiFall", "NoFallDamage");
    }

    @Subscriber
    public void Method131(PacketEvent packetEvent) {
        if (NoFall.mc.player == null || NoFall.mc.world == null) {
            return;
        }
        if (mode.getValue() == Class268.PACKET && NoFall.mc.player.fallDistance > (float)((Integer)fallDistance.getValue()).intValue() && packetEvent.getPacket() instanceof SPacketPlayerPosLook) {
            if (!(NoFall.mc.currentScreen instanceof GuiDownloadTerrain)) {
                if (NoFall.mc.player.isEntityAlive()) {
                    if (this.Field2020 <= 0) {
                        this.Field2020 = ((SPacketPlayerPosLook) packetEvent.getPacket()).getTeleportId();
                    } else {
                        SPacketPlayerPosLook sPacketPlayerPosLook = (SPacketPlayerPosLook) packetEvent.getPacket();
                        ((ISPacketPlayerPosLook)sPacketPlayerPosLook).setYaw(NoFall.mc.player.rotationYaw);
                        ((ISPacketPlayerPosLook)sPacketPlayerPosLook).setPitch(NoFall.mc.player.rotationPitch);
                    }
                }
            } else {
                this.Field2020 = 0;
            }
        }
    }
}