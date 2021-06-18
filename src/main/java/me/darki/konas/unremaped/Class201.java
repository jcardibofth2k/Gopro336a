package me.darki.konas.unremaped;

import java.awt.Color;
import java.util.ArrayList;

import me.darki.konas.gui.clickgui.component.Component;
import me.darki.konas.gui.clickgui.unremappedGuiStuff.Class92;
import me.darki.konas.module.ModuleManager;
import me.darki.konas.setting.ColorValue;
import me.darki.konas.setting.Keybind;
import me.darki.konas.setting.ParentSetting;
import me.darki.konas.util.RenderUtil2;
import me.darki.konas.module.Module;
import me.darki.konas.module.client.ClickGUIModule;
import me.darki.konas.setting.Setting;
import me.darki.konas.util.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;

public class Class201
extends Component {
    public Module Field620;
    public ArrayList<Component> Field621 = new ArrayList();
    public TimerUtil Field622 = new TimerUtil();

    public Module Method146() {
        return this.Field620;
    }

    @Override
    public void onMove(float f, float f2) {
        super.onMove(f, f2);
        this.Method672().forEach(this::Method666);
    }

    public void Method666(Component class183) {
        class183.onMove(this.Method1475(), this.Method1476());
    }

    @Override
    public void Method667() {
        super.Method667();
        float f = this.Method1481();
        for (Setting setting : ModuleManager.Method1615(this.Method146())) {
            float f2;
            float f3 = f2 = setting.Method1199() ? 4.0f : 2.0f;
            if (setting.getValue() instanceof Keybind) {
                this.Method671().add(new Class190(this.Method146(), this.Method1475(), this.Method1476(), f2, f, this.Method1479() - f2, 12.0f));
                f += 12.0f;
                continue;
            }
            if (setting.getValue() instanceof Class537) {
                this.Method671().add(new Class198(setting, this.Method1475(), this.Method1476(), f2, f, this.Method1479() - f2, 12.0f));
                f += 12.0f;
                continue;
            }
            if (setting.getValue() instanceof Boolean) {
                this.Method671().add(new Class177(setting, this.Method1475(), this.Method1476(), f2, f, this.Method1479() - f2, 12.0f));
                f += 12.0f;
                continue;
            }
            if (setting.getValue() instanceof ParentSetting) {
                this.Method671().add(new Class210(setting, this.Method1475(), this.Method1476(), f2, f, this.Method1479() - f2, 12.0f));
                f += 12.0f;
                continue;
            }
            if (setting.getValue() instanceof Number) {
                this.Method671().add(new Class207(setting, this.Method1475(), this.Method1476(), f2, f, this.Method1479() - f2, 12.0f));
                f += 12.0f;
                continue;
            }
            if (setting.getValue() instanceof Enum) {
                this.Method671().add(new Class204(setting, this.Method1475(), this.Method1476(), f2, f, this.Method1479() - f2, 12.0f));
                f += 12.0f;
                continue;
            }
            if (!(setting.getValue() instanceof ColorValue)) continue;
            this.Method671().add(new Class174(setting, this.Method1475(), this.Method1476(), f2, f, this.Method1479() - f2, 60.0f));
            f += 12.0f;
        }
        this.Method672().forEach(Component::Method667);
    }

    @Override
    public boolean Method106(int n, int n2, int n3) {
        super.Method106(n, n2, n3);
        boolean bl = Class201.isMouseWithinBounds(n, n2, this.Method1475(), this.Method1476(), this.Method1479(), this.Method1481());
        if (bl) {
            switch (n3) {
                case 0: {
                    Minecraft.getMinecraft().getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord((SoundEvent)SoundEvents.UI_BUTTON_CLICK, (float)1.0f));
                    this.Method146().toggle();
                    return true;
                }
                case 1: {
                    Minecraft.getMinecraft().getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord((SoundEvent)SoundEvents.UI_BUTTON_CLICK, (float)1.0f));
                    this.setExtended(!this.Method1486());
                    return true;
                }
            }
        }
        if (this.Method1486()) {
            for (Component class183 : this.Method672()) {
                if (!class183.Method106(n, n2, n3)) continue;
                return true;
            }
        }
        return false;
    }

    public static void Method668(int n, int n2, int n3, long l, Component class183) {
        class183.Method649(n, n2, n3, l);
    }

    public static void Method669(char c, int n, Component class183) {
        class183.Method102(c, n);
    }

    public static void Method670(int n, int n2, float f, Component class183) {
        class183.Method105(n, n2, f);
    }

    public Class201(Module module, float f, float f2, float f3, float f4, float f5, float f6) {
        super(module.getName(), f, f2, f3, f4, f5, f6);
        this.Field620 = module;
    }

    public ArrayList<Component> Method671() {
        return this.Field621;
    }

    public ArrayList<Component> Method672() {
        ArrayList<Component> arrayList = new ArrayList<Component>();
        for (Component class183 : this.Method671()) {
            if (class183 instanceof Class190) {
                arrayList.add(class183);
                continue;
            }
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
            if (class183 instanceof Class198) {
                if (!((Class198)class183).Method107().Method1180()) continue;
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

    public static void Method673(int n, int n2, int n3, Component class183) {
        class183.Method647(n, n2, n3);
    }

    @Override
    public void Method647(int n, int n2, int n3) {
        block0: {
            super.Method647(n, n2, n3);
            if (!this.Method1486()) break block0;
            this.Method672().forEach(arg_0 -> Class201.Method673(n, n2, n3, arg_0));
        }
    }

    @Override
    public void Method105(int n, int n2, float f) {
        block4: {
            int n3;
            super.Method105(n, n2, f);
            float f2 = this.Method1481();
            for (Component class183 : this.Method672()) {
                class183.setYOffset(f2);
                f2 += class183.Method1481();
            }
            int n4 = n3 = this.Field620.isEnabled() ? ((ColorValue) ClickGUIModule.color.getValue()).Method774() : ((ColorValue) ClickGUIModule.secondary.getValue()).Method774();
            if (((Boolean) ClickGUIModule.hover.getValue()).booleanValue()) {
                if (Class201.isMouseWithinBounds(n, n2, this.Method1475(), this.Method1476(), this.Method1479(), this.Method1481())) {
                    n3 = this.Field620.isEnabled() ? ((ColorValue) ClickGUIModule.color.getValue()).Method775().brighter().hashCode() : new Color(96, 96, 96, 100).hashCode();
                }
            }
            RenderUtil2.Method1338(this.Method1475(), this.Method1476(), this.Method1479(), this.Method1481(), n3);
            int n5 = (int)(this.Method1476() + this.Method1481() / 2.0f - Class548.Method1023(this.Method1480()) / 2.0f);
            Class548.Method1016(this.Method1480(), (int)(this.Method1475() + 4.0f), n5, ((ColorValue) ClickGUIModule.fony.getValue()).Method774());
            if (this.Field620.Method1646() != 0 && ((Boolean) ClickGUIModule.binds.getValue()).booleanValue()) {
                String string = GameSettings.getKeyDisplayString((int)this.Method146().Method1646());
                string = string.replaceAll("NUMPAD", "");
                Class548.Method1016(string, (int)(this.Method1475() + this.Method1479() - 4.0f - (Class548.Method1022(string) - 0.5f)), n5, ((ColorValue) ClickGUIModule.fony.getValue()).Method774());
            }
            if (!this.Method1486()) break block4;
            this.Method672().forEach(arg_0 -> Class201.Method670(n, n2, f, arg_0));
        }
    }

    @Override
    public void Method102(char c, int n) {
        block0: {
            super.Method102(c, n);
            if (!this.Method1486()) break block0;
            this.Method672().forEach(arg_0 -> Class201.Method669(c, n, arg_0));
        }
    }

    public void Method674(int n, int n2) {
        block2: {
            block1: {
                if (!Class201.isMouseWithinBounds(n, n2, this.Method1475(), this.Method1476(), this.Method1479(), this.Method1481())) break block1;
                if (!this.Field622.GetDifferenceTiming(500.0)) break block2;
                Class92.Field861 = this.Method146().getDescription();
                break block2;
            }
            if (Class92.Field861 != null && Class92.Field861.equals(this.Method146().getDescription())) {
                Class92.Field861 = null;
            }
            this.Field622.UpdateCurrentTime();
        }
    }

    @Override
    public void Method649(int n, int n2, int n3, long l) {
        block0: {
            super.Method649(n, n2, n3, l);
            if (!this.Method1486()) break block0;
            this.Method672().forEach(arg_0 -> Class201.Method668(n, n2, n3, l, arg_0));
        }
    }
}