package me.darki.konas.unremaped;

import cookiedragon.eventsystem.Subscriber;
import io.netty.buffer.Unpooled;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;

public class Class223
extends Module {
    public static int Field2629 = 1;
    public boolean Field2630 = true;

    @Subscriber
    public void Method536(SendPacketEvent sendPacketEvent) {
        if (Class223.mc.world == null || Class223.mc.player == null) {
            return;
        }
        if (sendPacketEvent.getPacket() instanceof CPacketCustomPayload && ((CPacketCustomPayload) sendPacketEvent.getPacket()).getChannelName().equals("MC|Beacon")) {
            if (this.Field2630) {
                this.Field2630 = false;
                CPacketCustomPayload cPacketCustomPayload = (CPacketCustomPayload) sendPacketEvent.getPacket();
                PacketBuffer packetBuffer = cPacketCustomPayload.getBufferData();
                int n = packetBuffer.readInt();
                int n2 = packetBuffer.readInt();
                sendPacketEvent.setCanceled(true);
                PacketBuffer packetBuffer2 = new PacketBuffer(Unpooled.buffer());
                packetBuffer2.writeInt(Field2629);
                packetBuffer2.writeInt(n2);
                Class223.mc.player.connection.sendPacket((Packet)new CPacketCustomPayload("MC|Beacon", packetBuffer2));
                this.Field2630 = true;
            }
        }
    }

    public Class223() {
        super("BeaconSelector", "Allows you to change beacon effect", Category.EXPLOIT, "BeaconBypass");
    }

    @Subscriber
    public void Method2282(Class0 class0) {
        class0.Cancel();
    }
}