package me.darki.konas.unremaped;

import cookiedragon.eventsystem.EventDispatcher;
import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.event.events.PacketEvent;
import net.minecraft.network.play.server.SPacketPlayerListItem;

public class Class57 {
    public static Class57 Field216 = new Class57();

    @Subscriber
    public void Method345(PacketEvent packetEvent) {
        block2: {
            SPacketPlayerListItem sPacketPlayerListItem;
            block3: {
                if (!(packetEvent.getPacket() instanceof SPacketPlayerListItem)) break block2;
                sPacketPlayerListItem = (SPacketPlayerListItem) packetEvent.getPacket();
                if (sPacketPlayerListItem.getAction() != SPacketPlayerListItem.Action.ADD_PLAYER) break block3;
                for (SPacketPlayerListItem.AddPlayerData addPlayerData : sPacketPlayerListItem.getEntries()) {
                    Class16 class16 = new Class16(addPlayerData.getProfile().getName(), addPlayerData.getProfile().getId());
                    EventDispatcher.Companion.dispatch(class16);
                }
                break block2;
            }
            if (sPacketPlayerListItem.getAction() != SPacketPlayerListItem.Action.REMOVE_PLAYER) break block2;
            for (SPacketPlayerListItem.AddPlayerData addPlayerData : sPacketPlayerListItem.getEntries()) {
                uuidHelper uuidHelper = new uuidHelper(addPlayerData.getProfile().getName(), addPlayerData.getProfile().getId());
                EventDispatcher.Companion.dispatch(uuidHelper);
            }
        }
    }
}