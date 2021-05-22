package me.darki.konas.module.player;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Category;
import me.darki.konas.unremaped.SendPacketEvent;
import me.darki.konas.module.Module;
import net.minecraft.network.play.client.CPacketCloseWindow;

public class XCarry
extends Module {
    public XCarry() {
        super("XCarry", Category.EXPLOIT, "MoreInventory");
    }

    @Subscriber
    public void Method536(SendPacketEvent sendPacketEvent) {
        block0: {
            if (!(sendPacketEvent.getPacket() instanceof CPacketCloseWindow)) break block0;
            sendPacketEvent.setCanceled(true);
        }
    }
}