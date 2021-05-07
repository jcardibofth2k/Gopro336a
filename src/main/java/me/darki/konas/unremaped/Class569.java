package me.darki.konas.unremaped;

import me.darki.konas.*;
import me.darki.konas.event.CancelableEvent;
import net.minecraft.inventory.EntityEquipmentSlot;

public class Class569
extends CancelableEvent {
    public static Class569 Field618 = new Class569();
    public EntityEquipmentSlot Field619;

    public EntityEquipmentSlot Method663() {
        return this.Field619;
    }

    public static Class569 Method664(EntityEquipmentSlot entityEquipmentSlot) {
        Field618.setCanceled(false);
        Class569.Field618.Field619 = entityEquipmentSlot;
        return Field618;
    }
}