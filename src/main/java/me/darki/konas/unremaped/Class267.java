package me.darki.konas.unremaped;

import me.darki.konas.*;
import org.lwjgl.util.vector.Vector2f;

public class Class267
extends Class244 {
    public float Field1989;
    public float Field1990;
    public boolean Field1991;

    @Override
    public void Method1804() {
        this.Field2243 = new Vector2f[3];
        if (this.Field1991) {
            this.Field2243[0] = new Vector2f(0.0f + this.Field2245, this.Field1990 + this.Field2246);
            this.Field2243[1] = new Vector2f(0.5f * this.Field1989 + this.Field2245, this.Field2246);
            this.Field2243[2] = new Vector2f(this.Field1989 + this.Field2245, this.Field1990 + this.Field2246);
        } else {
            this.Field2243[0] = new Vector2f(0.0f + this.Field2245, this.Field2246);
            this.Field2243[1] = new Vector2f(0.5f * this.Field1989 + this.Field2245, this.Field1990 + this.Field2246);
            this.Field2243[2] = new Vector2f(this.Field1989 + this.Field2245, this.Field2246);
        }
    }

    public Class267(float f, float f2, float f3, float f4, boolean bl) {
        this.Field2245 = f;
        this.Field2246 = f2;
        this.Field1989 = f3;
        this.Field1990 = f4;
        this.Field1991 = bl;
        this.Method1804();
        this.Method2031();
    }

    @Override
    public boolean Method1802() {
        return false;
    }
}