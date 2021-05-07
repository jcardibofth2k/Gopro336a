package me.darki.konas.unremaped;

import me.darki.konas.*;
import me.darki.konas.event.CancelableEvent;
import net.minecraft.entity.passive.AbstractHorse;

public class Class72
extends CancelableEvent {
    public AbstractHorse Field535;

    public AbstractHorse Method322() {
        return this.Field535;
    }

    public Class72(AbstractHorse abstractHorse) {
        this.Field535 = abstractHorse;
    }

    public void Method610(AbstractHorse abstractHorse) {
        this.Field535 = abstractHorse;
    }
}