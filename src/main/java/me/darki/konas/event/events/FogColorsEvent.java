package me.darki.konas.event.events;

import me.darki.konas.unremaped.StrangeClassThatExtendsCancelableEvent;

public class FogColorsEvent
extends StrangeClassThatExtendsCancelableEvent {
    public float Field213;
    public float Field214;
    public float Field215;

    public float Method214() {
        return this.Field215;
    }

    public float Method215() {
        return this.Field214;
    }

    public FogColorsEvent(float f, float f2, float f3) {
        this.Field213 = f;
        this.Field214 = f2;
        this.Field215 = f3;
    }

    public void Method341(float f) {
        this.Field213 = f;
    }

    public void Method294(float f) {
        this.Field214 = f;
    }

    public void Method344(float f) {
        this.Field215 = f;
    }

    public float Method213() {
        return this.Field213;
    }
}
