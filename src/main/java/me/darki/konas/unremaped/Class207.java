package me.darki.konas.unremaped;

import me.darki.konas.*;
import me.darki.konas.gui.clickgui.component.Component;
import me.darki.konas.setting.ColorValue;
import me.darki.konas.util.RenderUtil2;
import me.darki.konas.module.client.ClickGUIModule;
import me.darki.konas.setting.Setting;
import me.darki.konas.util.math.RoundingUtil;
import net.minecraft.util.math.MathHelper;

public class Class207
extends Component {
    public Setting Field592;
    public boolean Field593;

    @Override
    public boolean Method106(int n, int n2, int n3) {
        super.Method106(n, n2, n3);
        if (Class207.isMouseWithinBounds(n, n2, this.Method1475(), this.Method1476(), this.Method1475() + this.Method1479(), this.Method1481()) && n3 == 0) {
            this.Method104(true);
            return true;
        }
        return false;
    }

    public Setting Method583() {
        return this.Field592;
    }

    public Class207(Setting setting, float f, float f2, float f3, float f4, float f5, float f6) {
        super(setting.Method1183(), f, f2, f3, f4, f5, f6);
        this.Field592 = setting;
    }

    @Override
    public void Method647(int n, int n2, int n3) {
        block0: {
            super.Method647(n, n2, n3);
            if (!this.Method648()) break block0;
            this.Method104(false);
        }
    }

    public boolean Method648() {
        return this.Field593;
    }

    public void Method104(boolean bl) {
        this.Field593 = bl;
    }

    @Override
    public void Method105(int n, int n2, float f) {
        block3: {
            block6: {
                block5: {
                    block4: {
                        super.Method105(n, n2, f);
                        RenderUtil2.Method1338(this.Method1475(), this.Method1476(), this.Method1479(), this.Method1481(), 0xFFFFFF);
                        float f2 = MathHelper.floor((float)((((Number)this.Method583().getValue()).floatValue() - ((Number)this.Method583().Method1187()).floatValue()) / (((Number)this.Method583().Method1182()).floatValue() - ((Number)this.Method583().Method1187()).floatValue()) * this.Method1479()));
                        if (f2 < 0.0f) {
                            this.Method583().setValue(this.Method583().Method1187());
                            this.Method104(false);
                        } else if (f2 > this.Method1479()) {
                            this.Method583().setValue(this.Method583().Method1182());
                            this.Method104(false);
                        }
                        RenderUtil2.Method1338(this.Method1488(), this.Method1476(), this.getXOffset(), this.Method1481(), ((ColorValue) ClickGUIModule.color.getValue()).Method774());
                        int n3 = this.Method648() ? ((ColorValue) ClickGUIModule.color.getValue()).Method775().brighter().getRGB() : ((ColorValue) ClickGUIModule.color.getValue()).Method775().darker().getRGB();
                        RenderUtil2.Method1338(this.Method1475(), this.Method1476(), f2, this.Method1481(), n3);
                        Class548.Method1016(this.Method1480() + ": " + this.Method583().getValue(), (int)(this.Method1475() + 5.0f), (int)(this.Method1476() + this.Method1481() / 2.0f - Class548.Method1023(this.Method1480() + ": " + this.Method583().getValue()) / 2.0f - 0.5f), ((ColorValue) ClickGUIModule.fony.getValue()).Method774());
                        if (!this.Field593) break block3;
                        if (!(this.Method583().getValue() instanceof Float)) break block4;
                        float f3 = ((float)n - this.Method1475()) * (((Number)this.Method583().Method1182()).floatValue() - ((Number)this.Method583().Method1187()).floatValue()) / this.Method1479() + ((Number)this.Method583().Method1187()).floatValue();
                        this.Method583().setValue(Float.valueOf(MathHelper.clamp((float)RoundingUtil.Method1998(RoundingUtil.Method1999(f3, ((Float)this.Method583().Method1176()).floatValue()), 2), (float)((Float)this.Method583().Method1187()).floatValue(), (float)((Float)this.Method583().Method1182()).floatValue())));
                        break block3;
                    }
                    if (!(this.Method583().getValue() instanceof Integer)) break block5;
                    int n4 = (int)(((float)n - this.Method1475()) * (float)(((Number)this.Method583().Method1182()).intValue() - ((Number)this.Method583().Method1187()).intValue()) / this.Method1479() + (float)((Number)this.Method583().Method1187()).intValue());
                    this.Method583().setValue(n4);
                    break block3;
                }
                if (!(this.Method583().getValue() instanceof Double)) break block6;
                double d = (double)((float)n - this.Method1475()) * (((Number)this.Method583().Method1182()).doubleValue() - ((Number)this.Method583().Method1187()).doubleValue()) / (double)this.Method1479() + ((Number)this.Method583().Method1187()).doubleValue();
                this.Method583().setValue(MathHelper.clamp((double)RoundingUtil.Method1996(RoundingUtil.Method1997(d, (Double)this.Method583().Method1176()), 2), (double)((Double)this.Method583().Method1187()), (double)((Double)this.Method583().Method1182())));
                break block3;
            }
            if (!(this.Method583().getValue() instanceof Long)) break block3;
            long l = (long)((double)((float)n - this.Method1475()) * (((Number)this.Method583().Method1182()).doubleValue() - ((Number)this.Method583().Method1187()).doubleValue()) / (double)this.Method1479() + ((Number)this.Method583().Method1187()).doubleValue());
            this.Method583().setValue(l);
        }
    }

    @Override
    public void Method649(int n, int n2, int n3, long l) {
        block0: {
            if (Class207.isMouseWithinBounds(n, n2, this.Method1475(), this.Method1476(), this.Method1475() + this.Method1479(), this.Method1481())) break block0;
            this.Method104(false);
        }
    }
}