package me.darki.konas.unremaped;

import me.darki.konas.event.CancelableEvent;
import net.minecraft.client.entity.AbstractClientPlayer;

public class Class145
extends CancelableEvent {
    public AbstractClientPlayer Field1993;

    public Class145(AbstractClientPlayer abstractClientPlayer) {
        this.Field1993 = abstractClientPlayer;
    }

    public AbstractClientPlayer Method1823() {
        return this.Field1993;
    }
}
