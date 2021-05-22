package me.darki.konas.event.events;

public class RenderWorldLastEvent {
    public Float Field265;

    public void Method434(float f) {
        this.Field265 = Float.valueOf(f);
    }

    public RenderWorldLastEvent(float f) {
        this.Field265 = Float.valueOf(f);
    }

    public float Method435() {
        return this.Field265.floatValue();
    }
}
