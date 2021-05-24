package me.darki.konas.unremaped;

import me.darki.konas.event.CancelableEvent;
import net.minecraft.inventory.EntityEquipmentSlot;

public class RenderArmorEvent
extends CancelableEvent {
    public static RenderArmorEvent Field618 = new RenderArmorEvent();
    public EntityEquipmentSlot Field619;

    public EntityEquipmentSlot Method663() {
        return this.Field619;
    }

    public static RenderArmorEvent Method664(EntityEquipmentSlot entityEquipmentSlot) {
        Field618.setCanceled(false);
        RenderArmorEvent.Field618.Field619 = entityEquipmentSlot;
        return Field618;
    }
}