package me.darki.konas.unremaped;

import me.darki.konas.CancelableEvent;
import net.minecraft.entity.player.EntityPlayer;

public class Class572
extends CancelableEvent {
    public static Class572 Field529 = new Class572();
    public EntityPlayer Field530;

    public EntityPlayer Method597() {
        return this.Field530;
    }

    public static Class572 Method598(EntityPlayer entityPlayer) {
        Field529.setCanceled(false);
        Class572.Field529.Field530 = entityPlayer;
        return Field529;
    }
}
