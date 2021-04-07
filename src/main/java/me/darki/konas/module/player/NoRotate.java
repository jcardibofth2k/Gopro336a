package me.darki.konas.module.player;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Category;
import me.darki.konas.event.events.PacketEvent;
import me.darki.konas.mixin.mixins.ISPacketPlayerPosLook;
import me.darki.konas.module.Module;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.network.play.server.SPacketPlayerPosLook;

public class NoRotate
extends Module {
    public NoRotate() {
        super("NoRotate", "Cancels server to client rotations", Category.PLAYER);
    }

    @Subscriber
    public void Method131(PacketEvent packetEvent) {
        if (packetEvent.getPacket() instanceof SPacketPlayerPosLook && !(NoRotate.mc.currentScreen instanceof GuiDownloadTerrain)) {
            SPacketPlayerPosLook sPacketPlayerPosLook = (SPacketPlayerPosLook) packetEvent.getPacket();
            ((ISPacketPlayerPosLook)sPacketPlayerPosLook).setYaw(NoRotate.mc.player.rotationYaw);
            ((ISPacketPlayerPosLook)sPacketPlayerPosLook).setPitch(NoRotate.mc.player.rotationPitch);
            sPacketPlayerPosLook.getFlags().remove(SPacketPlayerPosLook.EnumFlags.X_ROT);
            sPacketPlayerPosLook.getFlags().remove(SPacketPlayerPosLook.EnumFlags.Y_ROT);
        }
    }
}