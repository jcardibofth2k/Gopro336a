package me.darki.konas.module.client;

import cookiedragon.eventsystem.Subscriber;
import io.netty.buffer.Unpooled;
import me.darki.konas.mixin.mixins.ICPacketCustomPayload;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.unremaped.SendPacketEvent;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;

public class NoForge
extends Module {
    @Subscriber
    public void Method536(SendPacketEvent sendPacketEvent) {
        block0: {
            block1: {
                if (mc.isIntegratedServerRunning()) break block0;
                if (!sendPacketEvent.getPacket().getClass().getName().equals("net.minecraftforge.fml.common.network.internal.FMLProxyPacket")) break block1;
                sendPacketEvent.setCanceled(true);
                break block0;
            }
            if (!(sendPacketEvent.getPacket() instanceof CPacketCustomPayload) || !((CPacketCustomPayload) sendPacketEvent.getPacket()).getChannelName().equalsIgnoreCase("MC|Brand")) break block0;
            ((ICPacketCustomPayload) sendPacketEvent.getPacket()).setData(new PacketBuffer(Unpooled.buffer()).writeString("vanilla"));
        }
    }

    public NoForge() {
        super("NoForge", "Prevents client from sending forge signature", Category.CLIENT, new String[0]);
    }
}