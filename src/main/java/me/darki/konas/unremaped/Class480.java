package me.darki.konas.unremaped;

import me.darki.konas.*;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class Class480 {
    public static void Method2076(float[] fArray, Color color, boolean bl) {
        Class480.Method2084();
        if (bl) {
            GL11.glEnable((int)2848);
        } else {
            GL11.glDisable((int)2848);
        }
        GL11.glColor4f((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
        GL11.glBegin((int)9);
        for (int i = 0; i < fArray.length; i += 2) {
            GL11.glVertex2f((float)fArray[i], (float)fArray[i + 1]);
        }
        GL11.glEnd();
        Class480.Method2079();
    }

    public static void Method2077(float f, float f2, float f3, float f4, float f5, Color color, boolean bl) {
        Class480.Method2082(new float[]{f, f2, f3, f2, f3, f2, f3, f4, f, f4, f3, f4, f, f2, f, f4}, f5, color, bl);
    }

    public static void Method2078(String string, int n, int n2, Color color) {
        GL11.glColor4f((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
        Minecraft.getMinecraft().fontRenderer.drawString(string, n, n2, 0);
    }

    public static void Method2079() {
        GL11.glEnable((int)3553);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public static void Method2080(float f, float f2, float f3, int n, int n2, float f4, Color color, boolean bl) {
        Class480.Method2084();
        if (n > n2) {
            int n3 = n;
            n = n2;
            n2 = n3;
        }
        if (n < 0) {
            n = 0;
        }
        if (n2 > 360) {
            n2 = 360;
        }
        if (bl) {
            GL11.glEnable((int)2848);
        } else {
            GL11.glDisable((int)2848);
        }
        GL11.glLineWidth((float)f4);
        GL11.glColor4f((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
        GL11.glBegin((int)3);
        float f5 = 0.01745328f;
        for (int i = n; i <= n2; ++i) {
            float f6 = (float)(i - 90) * f5;
            GL11.glVertex2f((float)(f + (float)Math.cos(f6) * f3), (float)(f2 + (float)Math.sin(f6) * f3));
        }
        GL11.glEnd();
        Class480.Method2079();
    }

    public static void Method2081(float f, float f2, float f3, float f4, float f5, Color color, boolean bl) {
        Class480.Method2082(new float[]{f, f2, f3, f4}, f5, color, bl);
    }

    public static void Method2082(float[] fArray, float f, Color color, boolean bl) {
        Class480.Method2084();
        if (bl) {
            GL11.glEnable((int)2848);
        } else {
            GL11.glDisable((int)2848);
        }
        GL11.glLineWidth((float)f);
        GL11.glColor4f((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
        GL11.glBegin((int)1);
        for (int i = 0; i < fArray.length; i += 2) {
            GL11.glVertex2f((float)fArray[i], (float)fArray[i + 1]);
        }
        GL11.glEnd();
        Class480.Method2079();
    }

    public static void Method2083(float f, float f2, float f3, float f4, Color color, boolean bl) {
        Class480.Method2076(new float[]{f, f2, f, f4, f3, f4, f3, f2}, color, bl);
    }

    public static void Method2084() {
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
    }
}