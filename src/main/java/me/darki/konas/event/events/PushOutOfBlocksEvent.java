package me.darki.konas.event.events;

import me.darki.konas.event.CancelableEvent;
import net.minecraft.entity.player.EntityPlayer;

public class PushOutOfBlocksEvent
extends CancelableEvent {
    public static PushOutOfBlocksEvent Field529 = new PushOutOfBlocksEvent();
    public EntityPlayer Field530;

    public EntityPlayer Method597() {
        return this.Field530;
    }

    public static PushOutOfBlocksEvent Method598(EntityPlayer entityPlayer) {
        Field529.setCanceled(false);
        PushOutOfBlocksEvent.Field529.Field530 = entityPlayer;
        return Field529;
    }
}
