package me.darki.konas.unremaped;

import java.awt.Color;

import me.darki.konas.gui.clickgui.unremappedGuiStuff.Class71;
import me.darki.konas.setting.ColorValue;
import me.darki.konas.gui.hud.Element;
import me.darki.konas.setting.Setting;

public class Class115
extends Element {
    public static Class71 Field2502 = new Class71();
    public static Setting<ColorValue> selected = new Setting<>("Selected", new ColorValue(Color.BLUE.hashCode(), true));
    public static Setting<ColorValue> toggled = new Setting<>("Toggled", new ColorValue(Color.BLUE.hashCode(), true));

    @Override
    public void Method2158(int n) {
        block1: {
            block3: {
                block2: {
                    block0: {
                        super.Method2158(n);
                        if (n != 208) break block0;
                        Field2502.Method620();
                        break block1;
                    }
                    if (n != 200) break block2;
                    Field2502.Method615();
                    break block1;
                }
                if (n != 203) break block3;
                Field2502.Method613();
                break block1;
            }
            if (n != 205 && n != 28) break block1;
            Field2502.Method121();
        }
    }

    public Class115() {
        super("TabGUI", 3.0f, 80.0f, 120.0f, 100.0f);
    }

    @Override
    public void onRender2D() {
        super.onRender2D();
        Field2502.Method614(this.Method2320(), this.Method2324());
        this.Method2319(Field2502.Method618());
        this.Method2323(Field2502.Method616());
    }
}