package me.darki.konas.gui.clickgui.unremappedGuiStuff;

import me.darki.konas.module.client.KonasGui;
import me.darki.konas.setting.Setting;
import me.darki.konas.util.Class247;
import me.darki.konas.unremaped.Class267;
import me.darki.konas.unremaped.Class270;

public class Class253
extends Class215 {
    public Setting<Enum> Field2173;
    public boolean Field2174 = false;

    @Override
    public boolean Method494(int n, int n2, int n3) {
        if (Class253.Method487(n, n2, this.Method486(), this.Method492() + (this.Field2173.hasDescription() ? 34.0f : 18.0f), this.Method489(), 20.0)) {
            if (n3 == 0) {
                this.Field2174 = !this.Field2174;
            } else if (n3 == 1) {
                int n4 = this.Field2173.Method1195(((Enum)this.Field2173.getValue()).toString());
                if (this.Method1974(n4 + 1)) {
                    this.Field2173.setValue(((Enum[])((Enum)this.Field2173.getValue()).getClass().getEnumConstants())[n4 + 1]);
                } else {
                    this.Field2173.setValue(((Enum[])((Enum)this.Field2173.getValue()).getClass().getEnumConstants())[0]);
                }
            }
            return true;
        }
        if (this.Field2174) {
            float f = this.Field2173.hasDescription() ? 54.0f : 38.0f;
            for (int i = 0; i <= ((Enum[])((Enum)this.Field2173.getValue()).getClass().getEnumConstants()).length - 1; ++i) {
                Enum enum_ = ((Enum[])((Enum)this.Field2173.getValue()).getClass().getEnumConstants())[i];
                if (enum_.name().equalsIgnoreCase(((Enum)this.Field2173.getValue()).toString())) continue;
                if (Class253.Method487(n, n2, this.Method486(), this.Method492() + f, this.Method489(), 20.0)) {
                    this.Field2173.setEnumValue(enum_.name());
                    this.Field2174 = false;
                    return true;
                }
                f += 20.0f;
            }
        }
        return super.Method494(n, n2, n3);
    }

    @Override
    public void Method477(int n, int n2, float f) {
        super.Method477(n, n2, f);
        if (this.Field2174) {
            float f2 = this.Field2173.hasDescription() ? 54.0f : 38.0f;
            for (int i = 0; i <= ((Enum[])((Enum)this.Field2173.getValue()).getClass().getEnumConstants()).length - 1; ++i) {
                Enum enum_ = ((Enum[])((Enum)this.Field2173.getValue()).getClass().getEnumConstants())[i];
                if (enum_.name().equalsIgnoreCase(((Enum)this.Field2173.getValue()).toString())) continue;
                boolean bl = i == ((Enum[])((Enum)this.Field2173.getValue()).getClass().getEnumConstants()).length - 1 || i == ((Enum[])((Enum)this.Field2173.getValue()).getClass().getEnumConstants()).length - 2 && ((Enum[])((Enum)this.Field2173.getValue()).getClass().getEnumConstants())[i + 1].name().equalsIgnoreCase(((Enum)this.Field2173.getValue()).toString());
                Class270 class270 = new Class270(this.Method486(), this.Method492() + f2, this.Method489(), 20.0f, 3.0f, 25, bl ? 12 : 0);
                Class247.Method2043(class270, Class253.Method487(n, n2, this.Method486(), this.Method492() + f2, this.Method489(), 20.0) ? Field320.Method2048(((Float) KonasGui.highlight.getValue()).floatValue()) : Field320);
                Class247.cfontRenderer.Method863(enum_.name(), this.Method486() + 6.0f, (int)(this.Method492() + f2 + 9.5f - Class247.cfontRenderer.Method831(enum_.name()) / 2.0f), Field325);
                f2 += 20.0f;
            }
        }
    }

    public Class253(Setting<Enum> setting, float f, float f2, float f3, float f4, float f5) {
        super(setting.Method1183(), f, f2, f3, f4, f5, setting.hasDescription() ? 54.0f : 38.0f);
        this.Field2173 = setting;
    }

    public boolean Method1974(int n) {
        return n <= ((Enum[])((Enum)this.Field2173.getValue()).getClass().getEnumConstants()).length - 1 && n >= 0;
    }

    @Override
    public void Method497(int n, int n2, float f) {
        super.Method497(n, n2, f);
        Class270 class270 = new Class270(this.Method486(), this.Method492() + (float)(this.Field2173.hasDescription() ? 34 : 18), this.Method489(), 20.0f, 3.0f, 25, this.Field2174 ? 3 : 15);
        Class247.Method2043(class270, Class253.Method487(n, n2, this.Method486(), this.Method492() + (float)(this.Field2173.hasDescription() ? 34 : 18), this.Method489(), 20.0) ? Field320.Method2048(((Float) KonasGui.highlight.getValue()).floatValue()) : Field320);
        Class267 class267 = new Class267(this.Method486() + this.Method489() - 17.0f, this.Method492() + (float)(this.Field2173.hasDescription() ? 41 : 25), 10.0f, 6.0f, this.Field2174);
        Class247.Method2042(class267, Field325, 3.0f);
        Class247.cfontRenderer.Method863(this.Field2173.Method1183(), this.Method486(), this.Method492(), Field324);
        if (this.Field2173.hasDescription()) {
            Class247.smallCFontRenderer.Method863(this.Field2173.Method1192(), this.Method486(), (int)(this.Method492() + 18.0f), Field326);
        }
        Class247.cfontRenderer.Method863(((Enum)this.Field2173.getValue()).toString(), (int)(this.Method486() + 6.0f), (int)(this.Method492() + (this.Field2173.hasDescription() ? 43.5f : 27.5f) - Class247.cfontRenderer.Method831(((Enum)this.Field2173.getValue()).toString()) / 2.0f), Field325);
    }
}