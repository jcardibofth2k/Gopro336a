package me.darki.konas.gui.clickgui.unremappedGuiStuff;

import me.darki.konas.module.client.KonasGui;
import me.darki.konas.setting.Setting;
import me.darki.konas.util.Class247;
import me.darki.konas.unremaped.Class264;
import me.darki.konas.unremaped.Class270;

public class Class228
extends Class215 {
    public Setting<Boolean> Field2624;

    @Override
    public void Method497(int n, int n2, float f) {
        super.Method497(n, n2, f);
        Class270 class270 = new Class270(this.Method486(), this.Method492(), 18.0f, 18.0f, 3.0f);
        Class247.Method2043(class270, Class228.Method487(n, n2, this.Method486(), this.Method492(), 18.0, 18.0) ? Field321.Method2048(((Float) KonasGui.highlight.getValue()).floatValue()) : Field321);
        Class247.Method2042(class270, Field317, 1.0f);
        if (((Boolean)this.Field2624.getValue()).booleanValue()) {
            Class264 class264 = new Class264(this.Method486(), this.Method492(), 18.0f, 18.0f);
            Class247.Method2042(class264, Field324, 2.0f);
        }
        Class247.cfontRenderer.Method863(this.Field2624.Method1183(), this.Method486() + 26.0f, (int)(this.Method492() + (9.0f - Class247.cfontRenderer.Method831(this.Field2624.Method1183()) / 2.0f)), Field324);
        if (this.Field2624.hasDescription()) {
            Class247.smallCFontRenderer.Method863(this.Field2624.Method1192(), this.Method486(), (int)(this.Method492() + 26.0f), Field326);
        }
    }

    public Class228(Setting<Boolean> setting, float f, float f2, float f3, float f4, float f5) {
        super(setting.Method1183(), f, f2, f3, f4, f5, setting.hasDescription() ? 38.0f : 18.0f);
        this.Field2624 = setting;
    }

    @Override
    public boolean Method494(int n, int n2, int n3) {
        if (Class228.Method487(n, n2, this.Method486(), this.Method492(), 18.0, 18.0) && n3 == 0) {
            this.Field2624.setValue(Boolean.valueOf((Boolean)this.Field2624.getValue() == false));
            return true;
        }
        return super.Method494(n, n2, n3);
    }
}