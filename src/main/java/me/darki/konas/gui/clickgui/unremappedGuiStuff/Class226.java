package me.darki.konas.gui.clickgui.unremappedGuiStuff;

import java.awt.Color;

import me.darki.konas.setting.ColorValue;
import me.darki.konas.util.Class247;
import me.darki.konas.unremaped.Class272;
import me.darki.konas.unremaped.Class516;
import me.darki.konas.util.RenderUtil2;
import me.darki.konas.module.client.KonasGui;
import me.darki.konas.setting.Setting;

public class Class226
extends Class215 {
    public Setting<ColorValue> Field2579;
    public boolean Field2580 = false;
    public int Field2581;
    public int Field2582;
    public int Field2583;
    public int Field2584;
    public float Field2585 = 100.0f;
    public float Field2586 = this.Field2585 / 10.0f;
    public float Field2587 = this.Method486() + 246.0f;
    public float Field2588 = this.Method492() + 82.0f;
    public float Field2589 = 12.0f;
    public boolean Field2590;

    public void Method2171() {
        if (this.Field2581 > 255) {
            this.Field2581 = 255;
        }
        if (this.Field2582 > 255) {
            this.Field2582 = 255;
        }
        if (this.Field2583 > 255) {
            this.Field2583 = 255;
        }
        if (this.Field2584 > 255) {
            this.Field2584 = 255;
        }
        if (this.Field2581 < 0) {
            this.Field2581 = 0;
        }
        if (this.Field2582 < 0) {
            this.Field2582 = 0;
        }
        if (this.Field2583 < 0) {
            this.Field2583 = 0;
        }
        if (this.Field2584 < 0) {
            this.Field2584 = 0;
        }
    }

    @Override
    public void Method497(int n, int n2, float f) {
        block20: {
            super.Method497(n, n2, f);
            Class247.cfontRenderer.Method863(this.Field2579.Method1183(), this.Method486(), this.Method492(), Field324);
            if (this.Field2579.hasDescription()) {
                Class247.smallCFontRenderer.Method863(this.Field2579.Method1192(), this.Method486(), (int)(this.Method492() + 18.0f), Field326);
            }
            RenderUtil2.Method1338(this.Method486() + 240.0f, this.Method492(), 24.0f, 12.0f, ((ColorValue)this.Field2579.getValue()).Method774());
            RenderUtil2.Method1336(this.Method486() + 240.0f, this.Method492(), 24.0f, 12.0f, 1.0f, ((ColorValue)this.Field2579.getValue()).Method784(255).Method774());
            if (!this.Field2580) break block20;
            if (Class226.Method487(n, n2, this.Method486() + 350.0f - (float)(this.Field2590 ? 108 : 0), this.Method492() + 6.0f, 40.0, 17.5)) {
                if (Field327 > 0) {
                    ++this.Field2582;
                } else if (Field327 < 0) {
                    --this.Field2582;
                }
                Field327 = 0;
            } else if (Class226.Method487(n, n2, this.Method486() + 350.0f - (float)(this.Field2590 ? 108 : 0), this.Method492() + 6.0f + 17.5f, 40.0, 17.5)) {
                if (Field327 > 0) {
                    ++this.Field2583;
                } else if (Field327 < 0) {
                    --this.Field2583;
                }
                Field327 = 0;
            } else if (Class226.Method487(n, n2, this.Method486() + 350.0f - (float)(this.Field2590 ? 108 : 0), this.Method492() + 6.0f + 35.0f, 40.0, 17.5)) {
                if (Field327 > 0) {
                    ++this.Field2584;
                } else if (Field327 < 0) {
                    --this.Field2584;
                }
                Field327 = 0;
            } else if (Class226.Method487(n, n2, this.Method486() + 350.0f - (float)(this.Field2590 ? 108 : 0), this.Method492() + 6.0f + 52.5f, 40.0, 17.5)) {
                if (Field327 > 0) {
                    ++this.Field2581;
                } else if (Field327 < 0) {
                    --this.Field2581;
                }
                Field327 = 0;
            }
            this.Method2171();
            this.Method2212();
        }
    }

    @Override
    public boolean Method491(int n, int n2, int n3, long l) {
        block7: {
            if (!this.Field2580) break block7;
            if (Class226.Method487(n, n2, this.Method486() + 248.0f - (float)(this.Field2590 ? 108 : 0), this.Method492() + 6.0f, 98.0, 70.0)) {
                this.Method2210(n, n2);
                return true;
            }
            if (Class226.Method487(n, n2, this.Field2587, this.Field2588, this.Field2585 - 2.0f, this.Field2589)) {
                this.Method2211(n);
                return true;
            }
            if (Class226.Method487(n, n2, this.Method486() + 350.0f - (float)(this.Field2590 ? 108 : 0), this.Method492() + 6.0f, 40.0, 17.5)) {
                this.Field2582 = (int)(((float)n - (this.Method486() + 350.0f - (float)(this.Field2590 ? 108 : 0))) / 40.0f * 255.0f);
                this.Method2212();
                return true;
            }
            if (Class226.Method487(n, n2, this.Method486() + 350.0f - (float)(this.Field2590 ? 108 : 0), this.Method492() + 6.0f + 17.5f, 40.0, 17.5)) {
                this.Field2583 = (int)(((float)n - (this.Method486() + 350.0f - (float)(this.Field2590 ? 108 : 0))) / 40.0f * 255.0f);
                this.Method2212();
                return true;
            }
            if (Class226.Method487(n, n2, this.Method486() + 350.0f - (float)(this.Field2590 ? 108 : 0), this.Method492() + 6.0f + 35.0f, 40.0, 17.5)) {
                this.Field2584 = (int)(((float)n - (this.Method486() + 350.0f - (float)(this.Field2590 ? 108 : 0))) / 40.0f * 255.0f);
                this.Method2212();
                return true;
            }
            if (Class226.Method487(n, n2, this.Method486() + 350.0f - (float)(this.Field2590 ? 108 : 0), this.Method492() + 6.0f + 52.5f, 40.0, 17.5)) {
                this.Field2581 = (int)(((float)n - (this.Method486() + 350.0f - (float)(this.Field2590 ? 108 : 0))) / 40.0f * 255.0f);
                this.Method2212();
                return true;
            }
            if (Class226.Method487(n, n2, this.Method486() + 240.0f - (float)(this.Field2590 ? 108 : 0), this.Method492(), 156.0, 100.0)) {
                return true;
            }
            this.Field2580 = false;
        }
        return super.Method491(n, n2, n3, l);
    }

    public void Method2210(float f, float f2) {
        float f3 = ((f -= this.Method486() + 246.0f - (float)(this.Field2590 ? 108 : 0)) - 1.0f) / 100.0f;
        float f4 = 1.0f - (f2 -= this.Method492() + 6.0f) / 70.0f;
        float[] fArray = Color.RGBtoHSB(this.Field2582, this.Field2583, this.Field2584, null);
        int n = Color.HSBtoRGB(fArray[0], f3, f4);
        this.Field2582 = n >> 16 & 0xFF;
        this.Field2583 = n >> 8 & 0xFF;
        this.Field2584 = n & 0xFF;
        this.Method2212();
    }

    public void Method2211(float f) {
        float f2 = (f -= this.Field2587) / this.Field2585;
        float[] fArray = Color.RGBtoHSB(this.Field2582, this.Field2583, this.Field2584, null);
        int n = Color.HSBtoRGB(f2, fArray[1], fArray[2]);
        this.Field2582 = n >> 16 & 0xFF;
        this.Field2583 = n >> 8 & 0xFF;
        this.Field2584 = n & 0xFF;
        this.Method2212();
    }

    public void Method2212() {
        int n = (this.Field2581 & 0xFF) << 24 | (this.Field2582 & 0xFF) << 16 | (this.Field2583 & 0xFF) << 8 | this.Field2584 & 0xFF;
        this.Method2214().Method771(n);
    }

    @Override
    public boolean Method494(int n, int n2, int n3) {
        if (this.Field2580) {
            if (Class226.Method487(n, n2, this.Method486() + 248.0f - (float)(this.Field2590 ? 108 : 0), this.Method492() + 6.0f, 98.0, 70.0)) {
                this.Method2210(n, n2);
                return true;
            }
            if (Class226.Method487(n, n2, this.Field2587, this.Field2588, this.Field2585 - 2.0f, this.Field2589)) {
                this.Method2211(n);
                return true;
            }
            if (Class226.Method487(n, n2, this.Method486() + 350.0f - (float)(this.Field2590 ? 108 : 0), this.Method492() + 6.0f, 40.0, 17.5)) {
                this.Field2582 = (int)(((float)n - (this.Method486() + 350.0f - (float)(this.Field2590 ? 108 : 0))) / 40.0f * 255.0f);
                this.Method2212();
                return true;
            }
            if (Class226.Method487(n, n2, this.Method486() + 350.0f - (float)(this.Field2590 ? 108 : 0), this.Method492() + 6.0f + 17.5f, 40.0, 17.5)) {
                this.Field2583 = (int)(((float)n - (this.Method486() + 350.0f - (float)(this.Field2590 ? 108 : 0))) / 40.0f * 255.0f);
                this.Method2212();
                return true;
            }
            if (Class226.Method487(n, n2, this.Method486() + 350.0f - (float)(this.Field2590 ? 108 : 0), this.Method492() + 6.0f + 35.0f, 40.0, 17.5)) {
                this.Field2584 = (int)(((float)n - (this.Method486() + 350.0f - (float)(this.Field2590 ? 108 : 0))) / 40.0f * 255.0f);
                this.Method2212();
                return true;
            }
            if (Class226.Method487(n, n2, this.Method486() + 350.0f - (float)(this.Field2590 ? 108 : 0), this.Method492() + 6.0f + 52.5f, 40.0, 17.5)) {
                this.Field2581 = (int)(((float)n - (this.Method486() + 350.0f - (float)(this.Field2590 ? 108 : 0))) / 40.0f * 255.0f);
                this.Method2212();
                return true;
            }
            if (Class226.Method487(n, n2, this.Method486() + 350.0f - (float)(this.Field2590 ? 108 : 0), this.Field2588, 40.0, this.Field2589)) {
                ((ColorValue)this.Field2579.getValue()).Method780(!((ColorValue)this.Field2579.getValue()).Method783());
            }
            if (Class226.Method487(n, n2, this.Method486() + 240.0f - (float)(this.Field2590 ? 108 : 0), this.Method492(), 156.0, 100.0)) {
                return true;
            }
            this.Field2580 = false;
        }
        if (Class226.Method487(n, n2, this.Method486() + 240.0f, this.Method492(), 24.0, 12.0)) {
            if (n3 == 0) {
                this.Field2580 = !this.Field2580;
            } else if (n3 == 1) {
                ((ColorValue)this.Field2579.getValue()).Method771(KonasGui.Field110);
            } else if (n3 == 2) {
                KonasGui.Field110 = ((ColorValue)this.Field2579.getValue()).Method778();
            }
            return true;
        }
        this.Field2580 = false;
        return super.Method494(n, n2, n3);
    }

    @Override
    public void Method477(int n, int n2, float f) {
        super.Method477(n, n2, f);
        if (this.Field2580) {
            float[] fArray = Color.RGBtoHSB(this.Field2582, this.Field2583, this.Field2584, null);
            Class272 class272 = new Class272(this.Method486() + 240.0f - (float)(this.Field2590 ? 108 : 0), this.Method492(), 156.0f, 100.0f);
            Class247.Method2043(class272, Field319);
            Class516.Method1277(this.Method486() + 246.0f - (float)(this.Field2590 ? 108 : 0), this.Method492() + 6.0f, this.Method486() + 346.0f - (float)(this.Field2590 ? 108 : 0), this.Method492() + 76.0f, Color.getHSBColor(fArray[0], 0.0f, 0.0f).getRGB(), Color.getHSBColor(fArray[0], 0.0f, 1.0f).getRGB(), Color.getHSBColor(fArray[0], 1.0f, 0.0f).getRGB(), Color.getHSBColor(fArray[0], 1.0f, 1.0f).getRGB());
            this.Field2587 = this.Method486() + 246.0f - (float)(this.Field2590 ? 108 : 0);
            this.Field2588 = this.Method492() + 82.0f;
            float f2 = 0.0f;
            while (f2 + this.Field2586 <= this.Field2585) {
                Class516.Method1275(this.Field2587 + f2, this.Field2588, this.Field2587 + this.Field2586 + f2, this.Field2588 + this.Field2589, Color.getHSBColor(f2 / this.Field2585, 1.0f, 1.0f).getRGB(), Color.getHSBColor((f2 + this.Field2586) / this.Field2585, 1.0f, 1.0f).getRGB());
                f2 += this.Field2586;
            }
            int n3 = (int)(this.Field2587 + (float)((int)(fArray[0] * this.Field2585)));
            RenderUtil2.Method1338(n3, this.Field2588, 2.0f, this.Field2589, -855638017);
            int n4 = (int)(this.Method486() + 246.0f + fArray[1] * 100.0f);
            int n5 = (int)(70.0f + this.Method492() + 6.0f - (float)((int)(fArray[2] * 70.0f)));
            RenderUtil2.Method1338((float)n4 - 1.0f - (float)(this.Field2590 ? 108 : 0), (float)n5 - 1.0f, 2.0f, 2.0f, -855638017);
            RenderUtil2.Method1338(this.Method486() + 350.0f - (float)(this.Field2590 ? 108 : 0), this.Method492() + 6.0f, 40.0f * ((float)this.Field2582 / 255.0f), 17.5f, 0x44FF0000);
            RenderUtil2.Method1338(this.Method486() + 350.0f - (float)(this.Field2590 ? 108 : 0), this.Method492() + 6.0f + 17.5f, 40.0f * ((float)this.Field2583 / 255.0f), 17.5f, 0x4400FF00);
            RenderUtil2.Method1338(this.Method486() + 350.0f - (float)(this.Field2590 ? 108 : 0), this.Method492() + 6.0f + 35.0f, 40.0f * ((float)this.Field2584 / 255.0f), 17.5f, 0x440000FF);
            RenderUtil2.Method1338(this.Method486() + 350.0f - (float)(this.Field2590 ? 108 : 0), this.Method492() + 6.0f + 52.5f, 40.0f * ((float)this.Field2581 / 255.0f), 17.5f, 0x44FFFFFF);
            RenderUtil2.Method1338(this.Method486() + 350.0f - (float)(this.Field2590 ? 108 : 0), this.Field2588, 40.0f, this.Field2589, ((ColorValue)this.Field2579.getValue()).Method783() ? ((ColorValue)this.Field2579.getValue()).Method784(50).Method774() : 0);
            Class247.cfontRenderer.Method863("R" + this.Field2582, this.Method486() + 352.0f - (float)(this.Field2590 ? 108 : 0), (int)(this.Method492() + 6.0f + 8.75f - Class247.cfontRenderer.Method831("R") / 2.0f), Field324);
            Class247.cfontRenderer.Method863("G" + this.Field2583, this.Method486() + 352.0f - (float)(this.Field2590 ? 108 : 0), (int)((double)(this.Method492() + 6.0f) + 26.25 - (double)(Class247.cfontRenderer.Method831("G") / 2.0f)), Field324);
            Class247.cfontRenderer.Method863("B" + this.Field2584, this.Method486() + 352.0f - (float)(this.Field2590 ? 108 : 0), (int)(this.Method492() + 6.0f + 43.75f - Class247.cfontRenderer.Method831("B") / 2.0f), Field324);
            Class247.cfontRenderer.Method863("A" + this.Field2581, this.Method486() + 352.0f - (float)(this.Field2590 ? 108 : 0), (int)(this.Method492() + 6.0f + 61.25f - Class247.cfontRenderer.Method831("A") / 2.0f), Field324);
            Class247.cfontRenderer.Method863("Cycle", this.Method486() + 352.0f - (float)(this.Field2590 ? 108 : 0), (int)(this.Field2588 + this.Field2589 / 2.0f - Class247.cfontRenderer.Method831("Cycle") / 2.0f), Field324);
        }
    }

    public void Method2213() {
        this.Field2581 = this.Method2214().Method778() >> 24 & 0xFF;
        this.Field2582 = this.Method2214().Method778() >> 16 & 0xFF;
        this.Field2583 = this.Method2214().Method778() >> 8 & 0xFF;
        this.Field2584 = this.Method2214().Method778() & 0xFF;
    }

    public ColorValue Method2214() {
        return (ColorValue)this.Field2579.getValue();
    }

    public Class226(Setting<ColorValue> setting, float f, float f2, float f3, float f4, float f5, boolean bl) {
        super(setting.Method1183(), f, f2, f3, f4, f5, setting.hasDescription() ? 28.0f : 12.0f);
        this.Field2579 = setting;
        this.Field2590 = bl;
        this.Method2213();
    }
}