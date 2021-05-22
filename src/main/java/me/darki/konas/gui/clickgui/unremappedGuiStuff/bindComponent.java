package me.darki.konas.gui.clickgui.unremappedGuiStuff;

import me.darki.konas.module.client.KonasGui;
import me.darki.konas.setting.Setting;
import me.darki.konas.util.Class247;
import me.darki.konas.unremaped.Class270;
import me.darki.konas.unremaped.Class520;
import net.minecraft.client.settings.GameSettings;

public class bindComponent
extends Class215 {
    public Setting<Class520> Field2634;
    public boolean Field2635 = false;

    @Override
    public void Method479(char c, int n) {
        super.Method479(c, n);
        if (this.Field2635) {
            if (n == 1) {
                ((Class520)this.Field2634.getValue()).Method850(0);
                this.Field2635 = false;
                Class147.Field2001 = true;
                return;
            }
            ((Class520)this.Field2634.getValue()).Method850(n);
            this.Field2635 = false;
        }
    }

    @Override
    public void Method497(int n, int n2, float f) {
        super.Method497(n, n2, f);
        Class270 class270 = new Class270(this.Method486(), this.Method492() + (float)(this.Field2634.hasDescription() ? 34 : 18), 132.0f, 20.0f, 3.0f);
        Class247.Method2043(class270, bindComponent.Method487(n, n2, this.Method486(), this.Method492() + (float)(this.Field2634.hasDescription() ? 34 : 18), 132.0, 20.0) ? Field320.Method2048(((Float) KonasGui.highlight.getValue()).floatValue()) : Field320);
        Class247.cfontRenderer.Method863(this.Field2634.Method1183(), this.Method486(), this.Method492(), Field324);
        if (this.Field2634.hasDescription()) {
            Class247.smallCFontRenderer.Method863(this.Field2634.Method1192(), this.Method486(), (int)(this.Method492() + 18.0f), Field326);
        }
        String string = this.Field2635 ? "Press a key..." : GameSettings.getKeyDisplayString((int)((Class520)this.Field2634.getValue()).Method851());
        Class247.cfontRenderer.Method863(string, (int)(this.Method486() + 66.0f - Class247.cfontRenderer.Method830(string) / 2.0f), (int)(this.Method492() + (this.Field2634.hasDescription() ? 43.5f : 27.5f) - Class247.cfontRenderer.Method831(string) / 2.0f), Field325);
    }

    @Override
    public boolean Method494(int n, int n2, int n3) {
        if (bindComponent.Method487(n, n2, this.Method486(), this.Method492() + (float)(this.Field2634.hasDescription() ? 34 : 18), 132.0, 20.0)) {
            this.Field2635 = true;
            return true;
        }
        return super.Method494(n, n2, n3);
    }

    public bindComponent(Setting<Class520> setting, float f, float f2, float f3, float f4, float f5) {
        super(setting.Method1183(), f, f2, f3, f4, f5, setting.hasDescription() ? 54.0f : 38.0f);
        this.Field2634 = setting;
    }
}