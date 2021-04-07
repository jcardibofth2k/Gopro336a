package me.darki.konas.unremaped;

import cookiedragon.eventsystem.Subscriber;
import io.netty.buffer.Unpooled;
import me.darki.konas.mixin.mixins.ICPacketCustomPayload;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;

public class NoForge
extends Module {
    @Subscriber
    public void Method536(Class24 class24) {
        block0: {
            block1: {
                if (mc.isIntegratedServerRunning()) break block0;
                if (!class24.getPacket().getClass().getName().equals("net.minecraftforge.fml.common.network.internal.FMLProxyPacket")) break block1;
                class24.setCanceled(true);
                break block0;
            }
            if (!(class24.getPacket() instanceof CPacketCustomPayload) || !((CPacketCustomPayload)class24.getPacket()).getChannelName().equalsIgnoreCase("MC|Brand")) break block0;
            ((ICPacketCustomPayload)class24.getPacket()).setData(new PacketBuffer(Unpooled.buffer()).writeString("vanilla"));
        }
    }

    public NoForge() {
        super("NoForge", "Prevents client from sending forge signature", Category.CLIENT, new String[0]);
    }
}