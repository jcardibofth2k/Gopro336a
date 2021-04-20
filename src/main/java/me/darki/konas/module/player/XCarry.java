package me.darki.konas.module.player;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Category;
import me.darki.konas.unremaped.Class24;
import me.darki.konas.module.Module;
import net.minecraft.network.play.client.CPacketCloseWindow;

public class XCarry
extends Module {
    public XCarry() {
        super("XCarry", Category.EXPLOIT, "MoreInventory");
    }

    @Subscriber
    public void Method536(Class24 class24) {
        block0: {
            if (!(class24.getPacket() instanceof CPacketCloseWindow)) break block0;
            class24.setCanceled(true);
        }
    }
}