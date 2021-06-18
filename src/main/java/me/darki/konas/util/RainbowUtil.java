package me.darki.konas.util;

import me.darki.konas.*;
import java.awt.Color;

public class RainbowUtil {
    public static int Method805(int n, float[] fArray, float f, float f2, float f3) {
        double d = Math.sin((double)f * ((double)System.currentTimeMillis() / Math.pow(10.0, 2.0) * (double)(f2 / 10.0f) + (double)n));
        return Color.getHSBColor(fArray[0], fArray[1], (float)(((d *= (double)f3) + 1.0) / 2.0) + (1.0f - f3) * 0.5f).getRGB();
    }

    public static float[] Method806(int n) {
        float f = (float)(n >> 24 & 0xFF) / 255.0f;
        float f2 = (float)(n >> 16 & 0xFF) / 255.0f;
        float f3 = (float)(n >> 8 & 0xFF) / 255.0f;
        float f4 = (float)(n & 0xFF) / 255.0f;
        return new float[]{f2, f3, f4, f};
    }

    public static int Method807(int n, float[] fArray) {
        double d = Math.ceil((double)(System.currentTimeMillis() + (long)n) / 20.0);
        return Color.getHSBColor((float)((d %= 360.0) / 360.0), fArray[1], fArray[2]).getRGB();
    }
}