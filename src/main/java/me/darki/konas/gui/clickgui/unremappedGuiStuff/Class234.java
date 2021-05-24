package me.darki.konas.gui.clickgui.unremappedGuiStuff;

import java.util.ArrayList;

import me.darki.konas.module.ModuleManager;
import me.darki.konas.setting.ColorValue;
import me.darki.konas.setting.ParentSetting;
import me.darki.konas.setting.PlayerPreview;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.*;
import me.darki.konas.util.Class247;

public class Class234
extends Class215 {
    public Module Field2453;
    public ParentSetting Field2454;
    public ArrayList<Class215> Field2455 = new ArrayList();

    @Override
    public void Method479(char c, int n) {
        super.Method479(c, n);
        this.Field2455.forEach(arg_0 -> Class234.Method2116(c, n, arg_0));
    }

    public static void Method2112(int n, int n2, int n3, Class215 class215) {
        class215.Method481(n, n2, n3);
    }

    public static void Method2113(int n, int n2, float f, Class215 class215) {
        class215.Method477(n, n2, f);
    }

    @Override
    public void Method481(int n, int n2, int n3) {
        super.Method481(n, n2, n3);
        this.Field2455.forEach(arg_0 -> Class234.Method2112(n, n2, n3, arg_0));
    }

    public void Method2114(Class215 class215) {
        class215.Method485(this.Method486(), this.Method492());
    }

    public void Method2115(Class215 class215) {
        class215.Method485(this.Method486(), this.Method492());
    }

    @Override
    public boolean Method494(int n, int n2, int n3) {
        for (Class215 class215 : this.Field2455) {
            if (!class215.Method494(n, n2, n3)) continue;
            return true;
        }
        return super.Method494(n, n2, n3);
    }

    @Override
    public boolean Method491(int n, int n2, int n3, long l) {
        for (Class215 class215 : this.Field2455) {
            if (!class215.Method491(n, n2, n3, l)) continue;
            return true;
        }
        return super.Method491(n, n2, n3, l);
    }

    @Override
    public void Method497(int n, int n2, float f) {
        super.Method497(n, n2, f);
        Class270 class270 = new Class270(this.Method486(), this.Method492(), this.Method489(), this.Method476(), 3.0f);
        Class247.Method2043(class270, Field319);
        Class247.cfontRenderer.Method863(this.Method483(), this.Method486() + 16.0f, this.Method492() + 16.0f, Field324);
        if (this.Field2454 == null && !this.Field2453.getDescription().isEmpty()) {
            Class247.smallCFontRenderer.Method863(this.Field2453.getDescription(), this.Method486() + 16.0f, this.Method492() + 32.0f, Field326);
        }
        this.Field2455.forEach(arg_0 -> Class234.Method2117(n, n2, f, arg_0));
    }

    @Override
    public void Method485(float f, float f2) {
        super.Method485(f, f2);
        this.Field2455.forEach(this::Method2115);
    }

    @Override
    public void Method484(float f) {
        super.Method484(f);
        this.Field2455.forEach(this::Method2114);
    }

    public static void Method2116(char c, int n, Class215 class215) {
        class215.Method479(c, n);
    }

    public static void Method2117(int n, int n2, float f, Class215 class215) {
        class215.Method497(n, n2, f);
    }

    public Class234(String string, Module module, ParentSetting parentSetting, float f, float f2, float f3, float f4, float f5, boolean bl) {
        super(string, f, f2, f3, f4, f5, 0.0f);
        this.Field2453 = module;
        this.Field2454 = parentSetting;
        float f6 = 48.0f;
        if (parentSetting == null && !module.getDescription().isEmpty()) {
            f6 += 16.0f;
        }
        for (Setting setting : ModuleManager.Method1615(module)) {
            Class215 class215;
            if ((setting.Method1199() || parentSetting != null) && (setting.Method1200() == null || setting.Method1200().getValue() != parentSetting)) continue;
            if (setting.getValue() instanceof Boolean) {
                class215 = new Class228(setting, this.Method486(), this.Method492(), 16.0f, f6, this.Method489() - 32.0f);
                this.Field2455.add(class215);
                f6 += class215.Method476() + 16.0f;
                continue;
            }
            if (setting.getValue() instanceof Class520) {
                class215 = new bindComponent(setting, this.Method486(), this.Method492(), 16.0f, f6, this.Method489() - 32.0f);
                this.Field2455.add(class215);
                f6 += class215.Method476() + 16.0f;
                continue;
            }
            if (setting.getValue() instanceof Enum) {
                class215 = new Class253(setting, this.Method486(), this.Method492(), 16.0f, f6, this.Method489() - 32.0f);
                this.Field2455.add(class215);
                f6 += 16.0f + class215.Method476();
                continue;
            }
            if (setting.getValue() instanceof Number) {
                class215 = new sliderComponent(setting, this.Method486(), this.Method492(), 16.0f, f6, this.Method489() - 32.0f);
                this.Field2455.add(class215);
                f6 += 16.0f + class215.Method476();
                continue;
            }
            if (setting.getValue() instanceof ColorValue) {
                class215 = new Class226(setting, this.Method486(), this.Method492(), 16.0f, f6, this.Method489() - 32.0f, bl);
                this.Field2455.add(class215);
                f6 += 16.0f + class215.Method476();
                continue;
            }
            if (!(setting.getValue() instanceof PlayerPreview)) continue;
            class215 = new Class250(setting, this.Method486(), this.Method492(), 16.0f, f6, this.Method489() - 32.0f, 200.0f);
            this.Field2455.add(class215);
            f6 += 16.0f + class215.Method476();
        }
        this.Method488(f6);
    }

    @Override
    public void Method477(int n, int n2, float f) {
        super.Method477(n, n2, f);
        this.Field2455.forEach(arg_0 -> Class234.Method2113(n, n2, f, arg_0));
    }
}