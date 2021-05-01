package me.darki.konas.gui.clickgui.unremappedGuiStuff;

import java.awt.Color;
import java.util.ArrayList;

import me.darki.konas.gui.clickgui.component.Component;
import me.darki.konas.setting.ColorValue;
import me.darki.konas.setting.ParentSetting;
import me.darki.konas.unremaped.*;
import me.darki.konas.util.RenderUtil2;
import me.darki.konas.gui.hud.Element;
import me.darki.konas.module.client.ClickGUIModule;
import me.darki.konas.setting.Setting;

public class Class180
extends Component {
    public Element Field1436;
    public ArrayList<Component> Field1437 = new ArrayList();

    @Override
    public void Method647(int n, int n2, int n3) {
        block0: {
            super.Method647(n, n2, n3);
            if (!this.Method1486()) break block0;
            this.Method1494().forEach(arg_0 -> Class180.Method673(n, n2, n3, arg_0));
        }
    }

    public static void Method669(char c, int n, Component class183) {
        class183.Method102(c, n);
    }

    public ArrayList<Component> Method672() {
        return this.Field1437;
    }

    public void Method666(Component class183) {
        class183.onMove(this.Method1475(), this.Method1476());
    }

    @Override
    public void Method102(char c, int n) {
        block0: {
            super.Method102(c, n);
            if (!this.Method1486()) break block0;
            this.Method1494().forEach(arg_0 -> Class180.Method669(c, n, arg_0));
        }
    }

    public ArrayList<Component> Method1494() {
        ArrayList<Component> arrayList = new ArrayList<Component>();
        for (Component class183 : this.Method672()) {
            if (class183 instanceof Class177) {
                if (!((Class177)class183).Method583().Method1180()) continue;
                arrayList.add(class183);
                continue;
            }
            if (class183 instanceof Class174) {
                if (!((Class174)class183).Method583().Method1180()) continue;
                arrayList.add(class183);
                continue;
            }
            if (class183 instanceof Class204) {
                if (!((Class204)class183).Method583().Method1180()) continue;
                arrayList.add(class183);
                continue;
            }
            if (class183 instanceof Class207) {
                if (!((Class207)class183).Method583().Method1180()) continue;
                arrayList.add(class183);
                continue;
            }
            arrayList.add(class183);
        }
        return arrayList;
    }

    public static void Method668(int n, int n2, int n3, long l, Component class183) {
        class183.Method649(n, n2, n3, l);
    }

    @Override
    public boolean Method106(int n, int n2, int n3) {
        super.Method106(n, n2, n3);
        boolean bl = Class180.isMouseWithinBounds(n, n2, this.Method1475(), this.Method1476(), this.Method1479(), this.Method1481());
        if (bl) {
            switch (n3) {
                case 0: {
                    this.Method1495().Method2123();
                    return true;
                }
                case 1: {
                    this.setExtended(!this.Method1486());
                    return true;
                }
            }
        }
        if (this.Method1486()) {
            for (Component class183 : this.Method1494()) {
                if (!class183.Method106(n, n2, n3)) continue;
                return true;
            }
        }
        return false;
    }

    public Class180(Element element, float f, float f2, float f3, float f4, float f5, float f6) {
        super(element.Method2316(), f, f2, f3, f4, f5, f6);
        this.Field1436 = element;
    }

    public Element Method1495() {
        return this.Field1436;
    }

    @Override
    public void Method105(int n, int n2, float f) {
        block2: {
            int n3;
            super.Method105(n, n2, f);
            float f2 = this.Method1481();
            for (Component class183 : this.Method1494()) {
                class183.setYOffset(f2);
                f2 += class183.Method1481();
            }
            int n4 = n3 = this.Field1436.Method2338() ? ((ColorValue) ClickGUIModule.color.getValue()).Method774() : ((ColorValue) ClickGUIModule.secondary.getValue()).Method774();
            if (((Boolean) ClickGUIModule.hover.getValue()).booleanValue() && Class180.isMouseWithinBounds(n, n2, this.Method1475(), this.Method1476(), this.Method1479(), this.Method1481())) {
                n3 = this.Field1436.Method2338() ? ((ColorValue) ClickGUIModule.color.getValue()).Method775().brighter().hashCode() : new Color(96, 96, 96, 100).hashCode();
            }
            RenderUtil2.Method1338(this.Method1475(), this.Method1476(), this.Method1479(), this.Method1481(), n3);
            Class548.Method1016(this.Method1480(), (int)(this.Method1475() + 4.0f), (int)(this.Method1476() + this.Method1481() / 2.0f - Class548.Method1023(this.Method1480()) / 2.0f - 0.5f), ((ColorValue) ClickGUIModule.fony.getValue()).Method774());
            if (!this.Method1486()) break block2;
            this.Method1494().forEach(arg_0 -> Class180.Method670(n, n2, f, arg_0));
        }
    }

    public static void Method670(int n, int n2, float f, Component class183) {
        class183.Method105(n, n2, f);
    }

    @Override
    public void onMove(float f, float f2) {
        super.onMove(f, f2);
        this.Method1494().forEach(this::Method666);
    }

    @Override
    public void Method667() {
        super.Method667();
        float f = this.Method1481();
        for (Setting setting : Class109.Method2198(this.Method1495())) {
            float f2;
            float f3 = setting.Method1199() ? 5.0f : 1.0f;
            float f4 = f2 = setting.Method1199() ? 6.0f : 2.0f;
            if (setting.getValue() instanceof Boolean) {
                this.Method672().add(new Class177(setting, this.Method1475(), this.Method1476(), f3, f, this.Method1479() - f2, 12.0f));
                f += 12.0f;
                continue;
            }
            if (setting.getValue() instanceof ParentSetting) {
                this.Method672().add(new Class210(setting, this.Method1475(), this.Method1476(), f3, f, this.Method1479() - f2, 12.0f));
                f += 12.0f;
                continue;
            }
            if (setting.getValue() instanceof Number) {
                this.Method672().add(new Class207(setting, this.Method1475(), this.Method1476(), f3, f, this.Method1479() - f2, 12.0f));
                f += 12.0f;
                continue;
            }
            if (setting.getValue() instanceof Enum) {
                this.Method672().add(new Class204(setting, this.Method1475(), this.Method1476(), f3, f, this.Method1479() - f2, 12.0f));
                f += 12.0f;
                continue;
            }
            if (!(setting.getValue() instanceof ColorValue)) continue;
            this.Method672().add(new Class174(setting, this.Method1475(), this.Method1476(), f3, f, this.Method1479() - f2, 60.0f));
            f += 12.0f;
        }
        this.Method1494().forEach(Component::Method667);
    }

    @Override
    public void Method649(int n, int n2, int n3, long l) {
        block0: {
            super.Method649(n, n2, n3, l);
            if (!this.Method1486()) break block0;
            this.Method1494().forEach(arg_0 -> Class180.Method668(n, n2, n3, l, arg_0));
        }
    }

    public static void Method673(int n, int n2, int n3, Component class183) {
        class183.Method647(n, n2, n3);
    }
}