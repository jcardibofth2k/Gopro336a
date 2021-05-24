package me.darki.konas.unremaped;

public class Render3DEvent {
    public static Render3DEvent Field266 = new Render3DEvent();
    public Float Field267;

    public float Method436() {
        return this.Field267.floatValue();
    }

    public static Render3DEvent Method437(float f) {
        Render3DEvent.Field266.Field267 = Float.valueOf(f);
        return Field266;
    }

    public void Method438(float f) {
        this.Field267 = Float.valueOf(f);
    }
}