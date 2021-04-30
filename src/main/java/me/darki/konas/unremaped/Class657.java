package me.darki.konas.unremaped;

import me.darki.konas.*;
import me.darki.konas.event.CancelableEvent;
import net.minecraft.util.EnumHandSide;

public class Class657
extends CancelableEvent {
    public EnumHandSide Field1141;

    public Class657(EnumHandSide enumHandSide) {
        this.Field1141 = enumHandSide;
    }

    public EnumHandSide Method1141() {
        return this.Field1141;
    }
}