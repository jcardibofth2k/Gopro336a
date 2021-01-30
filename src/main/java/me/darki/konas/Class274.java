package me.darki.konas;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Module;
import net.minecraft.network.play.client.CPacketConfirmTeleport;

public class Class274
extends Module {
    public Class274() {
        super("PortalGodmode", Category.EXPLOIT, new String[0]);
    }

    @Subscriber
    public void Method536(Class24 class24) {
        block0: {
            if (!(class24.getPacket() instanceof CPacketConfirmTeleport)) break block0;
            class24.setCanceled(true);
        }
    }
}
