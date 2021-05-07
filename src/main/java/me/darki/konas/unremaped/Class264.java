package me.darki.konas.unremaped;

import me.darki.konas.*;
import org.lwjgl.util.vector.Vector2f;

public class Class264
extends Class244 {
    public float Field2029;
    public float Field2030;

    public Class264(float f, float f2, float f3, float f4) {
        this.Field2245 = f;
        this.Field2246 = f2;
        this.Field2029 = f3;
        this.Field2030 = f4;
        this.Method1804();
        this.Method2031();
    }

    @Override
    public void Method1804() {
        this.Field2243 = new Vector2f[3];
        this.Field2243[0] = new Vector2f(0.22222222f * this.Field2029 + this.Field2245, 0.5f * this.Field2030 + this.Field2246);
        this.Field2243[1] = new Vector2f(0.44444445f * this.Field2029 + this.Field2245, 0.7222222f * this.Field2030 + this.Field2246);
        this.Field2243[2] = new Vector2f(0.7777778f * this.Field2029 + this.Field2245, 0.2777778f * this.Field2030 + this.Field2246);
    }

    @Override
    public boolean Method1802() {
        return false;
    }
}