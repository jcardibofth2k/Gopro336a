package me.darki.konas.gui.hud;

import me.darki.konas.gui.clickgui.unremappedGuiStuff.Class193;
import me.darki.konas.setting.ColorValue;
import me.darki.konas.util.RenderUtil2;
import me.darki.konas.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public abstract class Element {
    public Setting<ColorValue> color = new Setting<>("Color", new ColorValue(0, false));
    public Setting<ColorValue> outline = new Setting<>("Outline", new ColorValue(0x888888, false));
    public String Field2650;
    public float Field2651;
    public float Field2652;
    public float Field2653;
    public float Field2654;
    public boolean Field2655 = false;
    public boolean Field2656 = false;
    public int Field2657 = 1;
    public static Minecraft mc = Minecraft.getMinecraft();
    public static ScaledResolution Field2659 = new ScaledResolution(mc);

    public float Method2315() {
        return this.Field2652;
    }

    public String Method2316() {
        return this.Field2650;
    }

    public int Method2317() {
        return this.Field2657;
    }

    public void Method2123() {
        this.Field2655 = !this.Field2655;
    }

    public Element(String string, float f, float f2, float f3, float f4) {
        this.Method2326(f);
        this.Method2331(f2);
        this.Field2650 = string;
        this.Field2653 = f3;
        this.Field2654 = f4;
    }

    public void Method2318() {
    }

    public void onRender2D() {
        RenderUtil2.Method1337(this.Method2320(), this.Method2324(), this.Method2329(), this.Method2322(), this.outline.getValue().Method774(), this.color.getValue().Method774());
    }

    public void Method2319(float f) {
        this.Field2654 = f;
    }

    public float Method2320() {
        switch (this.Field2657) {
            case 1: 
            case 3: {
                return this.Method2336();
            }
            case 2: 
            case 4: {
                return this.Method2321() + this.Method2336() - this.Field2653;
            }
        }
        return 0.0f;
    }

    public float Method2321() {
        switch (this.Field2657) {
            case 1: 
            case 3: {
                return 0.0f;
            }
            case 2: 
            case 4: {
                return Field2659.getScaledWidth();
            }
        }
        return 0.0f;
    }

    public float Method2322() {
        return this.Field2654;
    }

    public void Method2323(float f) {
        this.Field2653 = f;
    }

    public float Method2324() {
        switch (this.Field2657) {
            case 1: 
            case 2: {
                return this.Method2315();
            }
            case 3: 
            case 4: {
                return this.Method2330() + this.Method2315() - this.Field2654;
            }
        }
        return 0.0f;
    }

    public void Method2325(boolean bl) {
        this.Field2655 = bl;
    }

    public void Method2326(float f) {
        this.Field2651 = f;
    }

    public void Method2327(boolean bl) {
        this.Field2656 = bl;
    }

    public void Method2328(float f) {
        if (this.Method2320() + this.Method2329() / 2.0f > (float)Field2659.getScaledWidth() / 2.0f) {
            if (f + this.Method2322() / 2.0f > (float)Field2659.getScaledHeight() / 2.0f) {
                this.Method2334(4);
                this.Method2331(f + this.Method2322() - this.Method2330());
            } else {
                this.Method2334(2);
                this.Method2331(f);
            }
        } else if (f + this.Method2322() / 2.0f > (float)Field2659.getScaledHeight() / 2.0f) {
            this.Method2334(3);
            this.Method2331(f + this.Method2322() - this.Method2330());
        } else {
            this.Method2334(1);
            this.Method2331(f);
        }
    }

    public float Method2329() {
        return this.Field2653;
    }

    public float Method2330() {
        switch (this.Field2657) {
            case 1: 
            case 2: {
                return 0.0f;
            }
            case 3: 
            case 4: {
                return Field2659.getScaledHeight();
            }
        }
        return 0.0f;
    }

    public void Method2331(float f) {
        this.Field2652 = f;
    }

    public void Method2332(int n, int n2, int n3) {
    }

    public boolean Method2333() {
        if (Element.mc.currentScreen instanceof Class193) {
            return this.Field2656;
        }
        return false;
    }

    public void Method2334(int n) {
        this.Field2657 = n;
    }

    public static boolean Method2335(int n, int n2, double d, double d2, double d3, double d4) {
        return (double)n >= d && (double)n <= d + d3 && (double)n2 >= d2 && (double)n2 <= d2 + d4;
    }

    public float Method2336() {
        return this.Field2651;
    }

    public void Method2337(float f) {
        if (this.Method2324() + this.Method2322() / 2.0f > (float)Field2659.getScaledHeight() / 2.0f) {
            if (f + this.Method2329() / 2.0f > (float)Field2659.getScaledWidth() / 2.0f) {
                this.Method2334(4);
                this.Method2326(f + this.Method2329() - this.Method2321());
            } else {
                this.Method2334(3);
                this.Method2326(f);
            }
        } else if (f + this.Method2329() / 2.0f > (float)Field2659.getScaledWidth() / 2.0f) {
            this.Method2334(2);
            this.Method2326(f + this.Method2329() - this.Method2321());
        } else {
            this.Method2334(1);
            this.Method2326(f);
        }
    }

    public boolean Method2338() {
        return this.Field2655;
    }

    public void Method2158(int n) {
    }
}