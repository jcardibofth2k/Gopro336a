package me.darki.konas.unremaped;

import me.darki.konas.*;
import java.awt.Color;

import me.darki.konas.setting.ColorValue;
import org.lwjgl.util.vector.Vector2f;

public class Class241
implements Class255 {
    public Vector2f Field2311;
    public Vector2f Field2312;
    public Color Field2313;
    public Color Field2314;
    public ColorValue Field2315 = null;
    public ColorValue Field2316 = null;

    public Class241(float f, float f2, float f3, float f4, Color color, Color color2) {
        this.Field2311 = new Vector2f(f, f2);
        this.Field2312 = new Vector2f(f + f3, f2 + f4);
        this.Field2313 = color;
        this.Field2314 = color2;
    }

    @Override
    public Color Method1920(Class244 class244, float f, float f2) {
        float f3;
        float f4;
        float f5;
        float f6;
        float f7;
        if (this.Field2315 != null && this.Field2316 != null) {
            this.Field2313 = this.Field2315.Method775();
            this.Field2314 = this.Field2316.Method775();
        }
        if ((f7 = (f6 = (f5 = this.Field2312.getX() - this.Field2311.getX())) * f5 - (f4 = -(f3 = this.Field2312.getY() - this.Field2311.getY())) * f3) == 0.0f) {
            return Color.BLACK;
        }
        float f8 = f4 * (this.Field2311.getY() - f2) - f6 * (this.Field2311.getX() - f);
        f8 /= f7;
        float f9 = f5 * (this.Field2311.getY() - f2) - f3 * (this.Field2311.getX() - f);
        f9 /= f7;
        float f10 = f8;
        if (f10 < 0.0f) {
            f10 = 0.0f;
        }
        if (f10 > 1.0f) {
            f10 = 1.0f;
        }
        float f11 = 1.0f - f10;
        int n = (int)(f10 * (float)this.Field2314.getRed() + f11 * (float)this.Field2313.getRed());
        int n2 = (int)(f10 * (float)this.Field2314.getBlue() + f11 * (float)this.Field2313.getBlue());
        int n3 = (int)(f10 * (float)this.Field2314.getGreen() + f11 * (float)this.Field2313.getGreen());
        int n4 = (int)(f10 * (float)this.Field2314.getAlpha() + f11 * (float)this.Field2313.getAlpha());
        return new Color(n, n3, n2, n4);
    }

    public Class241(float f, float f2, float f3, float f4, ColorValue colorValue, ColorValue class4402) {
        this.Field2311 = new Vector2f(f, f2);
        this.Field2312 = new Vector2f(f + f3, f2 + f4);
        this.Field2315 = colorValue;
        this.Field2316 = class4402;
        this.Field2313 = colorValue.Method775();
        this.Field2314 = class4402.Method775();
    }

    public Class241 Method2048(float f) {
        block1: {
            if (this.Field2315 == null) break block1;
            if (this.Field2316 != null) {
                this.Field2313 = this.Field2315.Method775();
                this.Field2314 = this.Field2316.Method775();
            }
        }
        return new Class241(this.Field2311.getX(), this.Field2311.getY(), this.Field2312.getX() - this.Field2311.getX(), this.Field2312.getY() - this.Field2311.getY(), this.Method2049(this.Field2313, f), this.Method2049(this.Field2314, f));
    }

    public Color Method2049(Color color, float f) {
        int n;
        int n2;
        int n3;
        int n4;
        block4: {
            n4 = color.getRed();
            n3 = color.getGreen();
            n2 = color.getBlue();
            n = color.getAlpha();
            int n5 = (int)(1.0 / (1.0 - (double)f));
            if (n4 == 0) {
                if (n3 == 0 && n2 == 0) {
                    return new Color(n5, n5, n5, n);
                }
            }
            if (n4 > 0 && n4 < n5) {
                n4 = n5;
            }
            if (n3 > 0 && n3 < n5) {
                n3 = n5;
            }
            if (n2 <= 0 || n2 >= n5) break block4;
            n2 = n5;
        }
        return new Color(Math.min((int)((float)n4 / f), 255), Math.min((int)((float)n3 / f), 255), Math.min((int)((float)n2 / f), 255), n);
    }
}