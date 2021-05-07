package me.darki.konas.unremaped;

import me.darki.konas.*;
import org.lwjgl.util.vector.Vector2f;

public class Class272
extends Class244 {
    public float Field1969;
    public float Field1970;

    @Override
    public boolean Method1802() {
        return true;
    }

    @Override
    public void Method1804() {
        this.Field2243 = new Vector2f[4];
        this.Field2243[0] = new Vector2f(this.Field2245, this.Field2246);
        this.Field2243[1] = new Vector2f(this.Field2245, this.Field2246 + this.Field1970);
        this.Field2243[2] = new Vector2f(this.Field2245 + this.Field1969, this.Field2246 + this.Field1970);
        this.Field2243[3] = new Vector2f(this.Field2245 + this.Field1969, this.Field2246);
    }

    public Class272(float f, float f2, float f3, float f4) {
        this.Field2245 = f;
        this.Field2246 = f2;
        this.Field1969 = f3;
        this.Field1970 = f4;
        this.Method1804();
        this.Method2031();
    }
}