package me.darki.konas.unremaped;

import me.darki.konas.*;
import net.minecraft.util.math.MathHelper;

public class Class541 {
    public static float Method1050(float f, float f2, float f3, float f4) {
        if (f4 <= 0.0f) {
            return f2;
        }
        float f5 = f2 - f;
        if (Math.pow(f5, 2.0) < 20.0) {
            return f2;
        }
        float f6 = f5 * MathHelper.clamp((float)(f3 * f4), (float)0.0f, (float)1.0f);
        return f + f6;
    }
}