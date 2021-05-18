package me.darki.konas.unremaped;

import me.darki.konas.*;
import me.darki.konas.event.CancelableEvent;

public class EntityTravelEvent
extends CancelableEvent {
    public float Field133;
    public float Field134;
    public float Field135;

    public float Method213() {
        return this.Field134;
    }

    public EntityTravelEvent(float f, float f2, float f3) {
        this.Field133 = f;
        this.Field134 = f2;
        this.Field135 = f3;
    }

    public float Method214() {
        return this.Field135;
    }

    public float Method215() {
        return this.Field133;
    }
}