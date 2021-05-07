package me.darki.konas.unremaped;

import me.darki.konas.*;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.util.FastTrig;

public class Class270
extends Class244 {
    public static int Field1933;
    public static int Field1934;
    public static int Field1935;
    public static int Field1936;
    public static int Field1937;
    public static int Field1938;
    public float Field1939;
    public float Field1940;
    public float Field1941;
    public int Field1942;
    public int Field1943;

    @Override
    public boolean Method1802() {
        return true;
    }

    public Class270(float f, float f2, float f3, float f4, float f5) {
        this(f, f2, f3, f4, f5, 25);
    }

    public List Method1803(int n, float f, float f2, float f3, float f4, float f5) {
        ArrayList<Vector2f> arrayList = new ArrayList<Vector2f>();
        int n2 = 360 / n;
        for (float f6 = f4; f6 >= f5 + (float)n2; f6 -= (float)n2) {
            float f7 = f6;
            if (f7 < f5) {
                f7 = f5;
            }
            float f8 = (float)((double)f2 + FastTrig.cos(Math.toRadians(f7)) * (double)f);
            float f9 = (float)((double)f3 + FastTrig.sin(Math.toRadians(f7)) * (double)f);
            arrayList.add(new Vector2f(f8, f9));
        }
        return arrayList;
    }

    public Class270(float f, float f2, float f3, float f4, float f5, int n, int n2) {
        if (f5 < 0.0f) {
            throw new IllegalArgumentException("corner radius must be >= 0");
        }
        this.Field1941 = f5;
        this.Field1942 = n;
        this.Field1943 = n2;
        this.Field2245 = f;
        this.Field2246 = f2;
        this.Field1939 = f3;
        this.Field1940 = f4;
        this.Method1804();
        this.Method2031();
    }

    @Override
    public void Method1804() {
        ArrayList<Vector2f> arrayList = new ArrayList<Vector2f>();
        if (this.Field1941 > this.Field1939 / 2.0f) {
            this.Field1941 = this.Field1939 / 2.0f;
        }
        if (this.Field1941 > this.Field1940 / 2.0f) {
            this.Field1941 = this.Field1940 / 2.0f;
        }
        if ((this.Field1943 & 1) != 0) {
            arrayList.addAll(this.Method1803(this.Field1942, this.Field1941, this.Field2245 + this.Field1941, this.Field2246 + this.Field1941, 270.0f, 180.0f));
        } else {
            arrayList.add(new Vector2f(this.Field2245, this.Field2246));
        }
        if ((this.Field1943 & 8) != 0) {
            arrayList.addAll(this.Method1803(this.Field1942, this.Field1941, this.Field2245 + this.Field1941, this.Field2246 + this.Field1940 - this.Field1941, 180.0f, 90.0f));
        } else {
            arrayList.add(new Vector2f(this.Field2245, this.Field2246 + this.Field1940));
        }
        if ((this.Field1943 & 4) != 0) {
            arrayList.addAll(this.Method1803(this.Field1942, this.Field1941, this.Field2245 + this.Field1939 - this.Field1941, this.Field2246 + this.Field1940 - this.Field1941, 90.0f, 0.0f));
        } else {
            arrayList.add(new Vector2f(this.Field2245 + this.Field1939, this.Field2246 + this.Field1940));
        }
        if ((this.Field1943 & 2) != 0) {
            arrayList.addAll(this.Method1803(this.Field1942, this.Field1941, this.Field2245 + this.Field1939 - this.Field1941, this.Field2246 + this.Field1941, 360.0f, 270.0f));
        } else {
            arrayList.add(new Vector2f(this.Field2245 + this.Field1939, this.Field2246));
        }
        this.Field2243 = new Vector2f[arrayList.size()];
        for (int i = 0; i < arrayList.size(); ++i) {
            this.Field2243[i] = (Vector2f)arrayList.get(i);
        }
    }

    public Class270(float f, float f2, float f3, float f4, float f5, int n) {
        this(f, f2, f3, f4, f5, n, 15);
    }

    static {
        Field1938 = 25;
        Field1937 = 15;
        Field1936 = 8;
        Field1935 = 4;
        Field1934 = 2;
        Field1933 = 1;
    }
}