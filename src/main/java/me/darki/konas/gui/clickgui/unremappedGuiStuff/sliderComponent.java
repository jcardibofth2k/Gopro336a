package me.darki.konas.gui.clickgui.unremappedGuiStuff;

import me.darki.konas.module.client.KonasGui;
import me.darki.konas.setting.Setting;
import me.darki.konas.util.Class247;
import me.darki.konas.unremaped.Class270;
import me.darki.konas.util.math.RoundingUtil;
import net.minecraft.util.math.MathHelper;

public class sliderComponent
extends Class215 {
    public Setting<Number> Field2063;
    public boolean Field2064 = false;

    @Override
    public boolean Method494(int n, int n2, int n3) {
        block3: {
            block8: {
                block7: {
                    block6: {
                        block1: {
                            block5: {
                                block4: {
                                    block2: {
                                        if (sliderComponent.Method487(n, n2, this.Method486() + 16.0f, this.Method492() + (float)(this.Field2063.hasDescription() ? 31 : 15), this.Method489() - 32.0f, 12.0) && n3 == 0) {
                                            this.Field2064 = true;
                                            return true;
                                        }
                                        if (!sliderComponent.Method487(n, n2, this.Method486(), this.Method492() + (float)(this.Field2063.hasDescription() ? 31 : 15), 12.0, 12.0)) break block1;
                                        if (!(this.Field2063.getValue() instanceof Float)) break block2;
                                        float f = ((Number)this.Field2063.getValue()).floatValue() - ((Number)this.Field2063.Method1176()).floatValue();
                                        this.Field2063.setValue(Float.valueOf(MathHelper.clamp((float)RoundingUtil.Method1998(RoundingUtil.Method1999(f, ((Float)((Number)this.Field2063.Method1176())).floatValue()), 2), (float)((Float)((Number)this.Field2063.Method1187())).floatValue(), (float)((Float)((Number)this.Field2063.Method1182())).floatValue())));
                                        break block3;
                                    }
                                    if (!(this.Field2063.getValue() instanceof Integer)) break block4;
                                    int n4 = ((Number)this.Field2063.getValue()).intValue() - ((Number)this.Field2063.Method1176()).intValue();
                                    this.Field2063.setValue(Integer.valueOf(n4));
                                    break block3;
                                }
                                if (!(this.Field2063.getValue() instanceof Double)) break block5;
                                double d = ((Number)this.Field2063.getValue()).doubleValue() + -((Number)this.Field2063.Method1176()).doubleValue();
                                this.Field2063.setValue(Double.valueOf(MathHelper.clamp((double)RoundingUtil.Method1996(RoundingUtil.Method1997(d, (Double)((Number)this.Field2063.Method1176())), 2), (double)((Double)((Number)this.Field2063.Method1187())), (double)((Double)((Number)this.Field2063.Method1182())))));
                                break block3;
                            }
                            if (!(this.Field2063.getValue() instanceof Long)) break block3;
                            long l = ((Number)this.Field2063.getValue()).longValue() - ((Number)this.Field2063.Method1176()).longValue();
                            this.Field2063.setValue(Long.valueOf(l));
                            break block3;
                        }
                        if (!sliderComponent.Method487(n, n2, this.Method486() + this.Method489() - 12.0f, this.Method492() + (float)(this.Field2063.hasDescription() ? 31 : 15), 12.0, 12.0)) break block3;
                        if (!(this.Field2063.getValue() instanceof Float)) break block6;
                        float f = ((Number)this.Field2063.getValue()).floatValue() + ((Number)this.Field2063.Method1176()).floatValue();
                        this.Field2063.setValue(Float.valueOf(MathHelper.clamp((float)RoundingUtil.Method1998(RoundingUtil.Method1999(f, ((Float)((Number)this.Field2063.Method1176())).floatValue()), 2), (float)((Float)((Number)this.Field2063.Method1187())).floatValue(), (float)((Float)((Number)this.Field2063.Method1182())).floatValue())));
                        break block3;
                    }
                    if (!(this.Field2063.getValue() instanceof Integer)) break block7;
                    int n5 = ((Number)this.Field2063.getValue()).intValue() + ((Number)this.Field2063.Method1176()).intValue();
                    this.Field2063.setValue(Integer.valueOf(n5));
                    break block3;
                }
                if (!(this.Field2063.getValue() instanceof Double)) break block8;
                double d = ((Number)this.Field2063.getValue()).doubleValue() + ((Number)this.Field2063.Method1176()).doubleValue();
                this.Field2063.setValue(Double.valueOf(MathHelper.clamp((double)RoundingUtil.Method1996(RoundingUtil.Method1997(d, (Double)((Number)this.Field2063.Method1176())), 2), (double)((Double)((Number)this.Field2063.Method1187())), (double)((Double)((Number)this.Field2063.Method1182())))));
                break block3;
            }
            if (!(this.Field2063.getValue() instanceof Long)) break block3;
            long l = ((Number)this.Field2063.getValue()).longValue() + ((Number)this.Field2063.Method1176()).longValue();
            this.Field2063.setValue(Long.valueOf(l));
        }
        return super.Method494(n, n2, n3);
    }

    @Override
    public void Method497(int n, int n2, float f) {
        super.Method497(n, n2, f);
        Class247.cfontRenderer.Method863(this.Field2063.Method1183(), this.Method486(), this.Method492(), Field324);
        if (this.Field2063.hasDescription()) {
            Class247.smallCFontRenderer.Method863(this.Field2063.Method1192(), this.Method486(), (int)(this.Method492() + 18.0f), Field326);
        }
        Class270 class270 = new Class270(this.Method486() + 16.0f, this.Method492() + (float)(this.Field2063.hasDescription() ? 34 : 18), this.Method489() - 32.0f, 6.0f, 3.0f);
        Class247.Method2043(class270, Field321);
        float f2 = MathHelper.floor((float)((((Number)this.Field2063.getValue()).floatValue() - ((Number)this.Field2063.Method1187()).floatValue()) / (((Number)this.Field2063.Method1182()).floatValue() - ((Number)this.Field2063.Method1187()).floatValue()) * (this.Method489() - 32.0f)));
        if (f2 < 0.0f) {
            this.Field2063.setValue(this.Field2063.Method1187());
            this.Field2064 = false;
        } else if (f2 > this.Method489()) {
            this.Field2063.setValue(this.Field2063.Method1182());
            this.Field2064 = false;
        }
        if (this.Field2064) {
            if (this.Field2063.getValue() instanceof Float) {
                float f3 = ((float)n - (this.Method486() + 16.0f)) * (((Number)this.Field2063.Method1182()).floatValue() - ((Number)this.Field2063.Method1187()).floatValue()) / (this.Method489() - 32.0f) + ((Number)this.Field2063.Method1187()).floatValue();
                this.Field2063.setValue(Float.valueOf(MathHelper.clamp((float)RoundingUtil.Method1998(RoundingUtil.Method1999(f3, ((Float)((Number)this.Field2063.Method1176())).floatValue()), 2), (float)((Float)((Number)this.Field2063.Method1187())).floatValue(), (float)((Float)((Number)this.Field2063.Method1182())).floatValue())));
            } else if (this.Field2063.getValue() instanceof Integer) {
                int n3 = (int)(((float)n - (this.Method486() + 16.0f)) * (float)(((Number)this.Field2063.Method1182()).intValue() - ~((Number)this.Field2063.Method1187()).intValue() + 1) / (this.Method489() - 32.0f) + (float)((Number)this.Field2063.Method1187()).intValue());
                this.Field2063.setValue(Integer.valueOf(n3));
            } else if (this.Field2063.getValue() instanceof Double) {
                double d = (double)((float)n - (this.Method486() + 16.0f)) * (((Number)this.Field2063.Method1182()).doubleValue() - ((Number)this.Field2063.Method1187()).doubleValue()) / (double)(this.Method489() - 32.0f) + ((Number)this.Field2063.Method1187()).doubleValue();
                this.Field2063.setValue(Double.valueOf(MathHelper.clamp((double)RoundingUtil.Method1996(RoundingUtil.Method1997(d, (Double)((Number)this.Field2063.Method1176())), 2), (double)((Double)((Number)this.Field2063.Method1187())), (double)((Double)((Number)this.Field2063.Method1182())))));
            } else if (this.Field2063.getValue() instanceof Long) {
                long l = (long)((double)((float)n - (this.Method486() + 16.0f)) * (((Number)this.Field2063.Method1182()).doubleValue() - ((Number)this.Field2063.Method1187()).doubleValue()) / (double)(this.Method489() - 32.0f) + ((Number)this.Field2063.Method1187()).doubleValue());
                this.Field2063.setValue(Long.valueOf(l));
            }
        }
        Class270 class2702 = new Class270(this.Method486() + 10.0f, this.Method492() + (float)(this.Field2063.hasDescription() ? 34 : 18), 12.0f + f2, 6.0f, 6.0f);
        Class247.Method2043(class2702, sliderComponent.Method487(n, n2, this.Method486() + 10.0f + f2, this.Method492() + (float)(this.Field2063.hasDescription() ? 31 : 15), 12.0, 12.0) ? Field320.Method2048(((Float) KonasGui.highlight.getValue()).floatValue()) : Field320);
        Class247.Method2042(class2702, Field317, 1.0f);
        Class270 class2703 = new Class270(this.Method486() + 10.0f + f2, this.Method492() + (float)(this.Field2063.hasDescription() ? 31 : 15), 12.0f, 12.0f, 6.0f);
        Class247.Method2043(class2703, sliderComponent.Method487(n, n2, this.Method486() + 10.0f + f2, this.Method492() + (float)(this.Field2063.hasDescription() ? 31 : 15), 12.0, 12.0) ? Field320.Method2048(((Float) KonasGui.highlight.getValue()).floatValue()) : Field320);
        Class247.Method2042(class2703, Field317, 1.0f);
        Class247.smallCFontRenderer.Method863(((Number)this.Field2063.getValue()).toString(), (int)(this.Method486() + 16.0f + f2 - Class247.smallCFontRenderer.Method830(((Number)this.Field2063.getValue()).toString()) / 2.0f), (int)(this.Method492() + (float)(this.Field2063.hasDescription() ? 48 : 32)), Field324);
        Class247.smallCFontRenderer.Method863("-", this.Method486(), this.Method492() + (float)(this.Field2063.hasDescription() ? 34 : 18) + 3.0f - Class247.smallCFontRenderer.Method831("-"), Field324);
        Class247.smallCFontRenderer.Method863("+", this.Method486() + this.Method489() - Class247.smallCFontRenderer.Method830("+"), this.Method492() + (float)(this.Field2063.hasDescription() ? 34 : 18) + 3.0f - Class247.smallCFontRenderer.Method831("+"), Field324);
    }

    public sliderComponent(Setting<Number> setting, float f, float f2, float f3, float f4, float f5) {
        super(setting.Method1183(), f, f2, f3, f4, f5, setting.hasDescription() ? 54.0f : 38.0f);
        this.Field2063 = setting;
    }

    @Override
    public boolean Method491(int n, int n2, int n3, long l) {
        block0: {
            if (sliderComponent.Method487(n, n2, this.Method486() + 16.0f, this.Method492() + (float)(this.Field2063.hasDescription() ? 31 : 15), this.Method489() - 32.0f, 12.0)) break block0;
            this.Field2064 = false;
        }
        return super.Method491(n, n2, n3, l);
    }

    @Override
    public void Method481(int n, int n2, int n3) {
        super.Method481(n, n2, n3);
        if (this.Field2064) {
            this.Field2064 = false;
        }
    }
}