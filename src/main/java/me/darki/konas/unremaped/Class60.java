package me.darki.konas.unremaped;

import me.darki.konas.*;
import me.darki.konas.event.CancelableEvent;
import net.minecraft.entity.passive.AbstractHorse;

public class Class60
extends CancelableEvent {
    public static Class60 Field196 = new Class60();
    public AbstractHorse Field197;

    public AbstractHorse Method322() {
        return this.Field197;
    }

    public static Class60 Method323(AbstractHorse abstractHorse) {
        Field196.setCanceled(false);
        Class60.Field196.Field197 = abstractHorse;
        return Field196;
    }
}