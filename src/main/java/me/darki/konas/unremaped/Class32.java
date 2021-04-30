package me.darki.konas.unremaped;

import me.darki.konas.*;
import me.darki.konas.event.CancelableEvent;
import net.minecraft.entity.Entity;

public class Class32
extends CancelableEvent {
    public static Class32 Field166 = new Class32();
    public Entity Field167;

    public static Class32 Method281(Entity entity) {
        Field166.setCanceled(false);
        Class32.Field166.Field167 = entity;
        return Field166;
    }

    public Entity Method282() {
        return this.Field167;
    }
}