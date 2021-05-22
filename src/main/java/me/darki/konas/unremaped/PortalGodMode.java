package me.darki.konas.unremaped;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import net.minecraft.network.play.client.CPacketConfirmTeleport;

public class PortalGodMode
extends Module {
    public PortalGodMode() {
        super("PortalGodmode", Category.EXPLOIT, new String[0]);
    }

    @Subscriber
    public void Method536(SendPacketEvent sendPacketEvent) {
        block0: {
            if (!(sendPacketEvent.getPacket() instanceof CPacketConfirmTeleport)) break block0;
            sendPacketEvent.setCanceled(true);
        }
    }
}