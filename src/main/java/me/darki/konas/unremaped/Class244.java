package me.darki.konas.unremaped;

import me.darki.konas.*;
import org.lwjgl.util.vector.Vector2f;

public class Class244 {
    public Vector2f[] Field2243;
    public Vector2f Field2244;
    public float Field2245;
    public float Field2246;
    public float Field2247;
    public float Field2248;
    public float Field2249;
    public float Field2250;

    public float Method2028() {
        return this.Field2247;
    }

    public boolean Method1802() {
        return false;
    }

    public Vector2f[] Method2029() {
        return this.Field2243;
    }

    public float Method2030() {
        return this.Field2245;
    }

    public void Method2031() {
        block1: {
            if (this.Field2243.length <= 0) break block1;
            this.Field2247 = this.Field2243[0].x;
            this.Field2248 = this.Field2243[0].y;
            this.Field2249 = this.Field2243[0].x;
            this.Field2250 = this.Field2243[0].y;
            for (Vector2f vector2f : this.Field2243) {
                this.Field2247 = Math.max(vector2f.x, this.Field2247);
                this.Field2248 = Math.max(vector2f.y, this.Field2248);
                this.Field2249 = Math.min(vector2f.x, this.Field2249);
                this.Field2250 = Math.min(vector2f.y, this.Field2250);
            }
            this.Method2035();
        }
    }

    public float Method2032() {
        return this.Field2248;
    }

    public float Method2033() {
        return this.Field2250;
    }

    public Vector2f Method2034() {
        return this.Field2244;
    }

    public void Method2035() {
        this.Field2244 = new Vector2f(0.0f, 0.0f);
        int n = this.Field2243.length;
        for (Vector2f vector2f : this.Field2243) {
            this.Field2244.x += vector2f.x;
            this.Field2244.y += vector2f.y;
        }
        this.Field2244.x /= (float)n / 2.0f;
        this.Field2244.y /= (float)n / 2.0f;
    }

    public void Method1804() {
    }

    public float Method2036() {
        return this.Field2246;
    }

    public float Method2037() {
        return this.Field2249;
    }
}