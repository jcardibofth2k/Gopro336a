package me.darki.konas.unremaped;

import cookiedragon.eventsystem.EventDispatcher;
import java.awt.Color;

import me.darki.konas.gui.clickgui.component.Component;
import me.darki.konas.gui.clickgui.unremappedGuiStuff.Class193;
import me.darki.konas.setting.ColorValue;
import me.darki.konas.util.RenderUtil2;
import me.darki.konas.module.client.ClickGUIModule;
import me.darki.konas.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;

public class Class198
extends Component {
    public Setting Field48;
    public boolean Field49;

    public Class198(Setting setting, float f, float f2, float f3, float f4, float f5, float f6) {
        super(setting.Method1183(), f, f2, f3, f4, f5, f6);
        this.Field48 = setting;
        EventDispatcher.Companion.register(this);
        EventDispatcher.Companion.subscribe(this);
    }

    @Override
    public void Method102(char c, int n) {
        block1: {
            super.Method102(c, n);
            if (!this.Method103()) break block1;
            if (n == 1) {
                ((Class537)this.Field48.getValue()).Method850(0);
                this.Method104(false);
                return;
            }
            ((Class537)this.Field48.getValue()).Method850(n);
            this.Method104(false);
        }
    }

    public boolean Method103() {
        return this.Field49;
    }

    public void Method104(boolean bl) {
        this.Field49 = bl;
        Class193.Method118(bl);
    }

    @Override
    public void Method105(int n, int n2, float f) {
        super.Method105(n, n2, f);
        RenderUtil2.Method1338(this.Method1488(), this.Method1476(), this.getXOffset(), this.Method1481(), ((ColorValue) ClickGUIModule.color.getValue()).Method774());
        int n3 = Class198.isMouseWithinBounds(n, n2, this.Method1475(), this.Method1476(), this.Method1479(), this.Method1481()) ? new Color(96, 96, 96, 100).hashCode() : ((ColorValue) ClickGUIModule.secondary.getValue()).Method774();
        RenderUtil2.Method1338(this.Method1475(), this.Method1476(), this.Method1479(), this.Method1481(), n3);
        Class548.Method1016(this.Method103() ? "Press new bind..." : this.Method1480() + ": " + GameSettings.getKeyDisplayString((int)((Class537)this.Field48.getValue()).Method851()), (int)(this.Method1475() + 5.0f), (int)(this.Method1476() + this.Method1481() / 2.0f - Class548.Method1023(this.Method103() ? "Press new bind..." : this.Method1480() + ": " + GameSettings.getKeyDisplayString((int)((Class537)this.Field48.getValue()).Method851())) / 2.0f - 0.5f), 0xFFFFFF);
    }

    @Override
    public boolean Method106(int n, int n2, int n3) {
        super.Method106(n, n2, n3);
        if (this.Method103()) {
            ((Class537)this.Field48.getValue()).Method850(n3 - 100);
            this.Method104(false);
            return true;
        }
        boolean bl = Class198.isMouseWithinBounds(n, n2, this.Method1475(), this.Method1476(), this.Method1479(), this.Method1481());
        if (bl && n3 == 0) {
            Minecraft.getMinecraft().getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord((SoundEvent)SoundEvents.UI_BUTTON_CLICK, (float)1.0f));
            this.Method104(!this.Method103());
            return true;
        }
        return false;
    }

    public Setting Method107() {
        return this.Field48;
    }
}