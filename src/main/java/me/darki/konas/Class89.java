package me.darki.konas;

public class Class89 {
    public static Class89 Field266 = new Class89();
    public Float Field267;

    public float Method436() {
        return this.Field267.floatValue();
    }

    public static Class89 Method437(float f) {
        Class89.Field266.Field267 = Float.valueOf(f);
        return Field266;
    }

    public void Method438(float f) {
        this.Field267 = Float.valueOf(f);
    }
}
