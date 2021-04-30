package me.darki.konas.unremaped;

import me.darki.konas.*;
import java.awt.Color;

import me.darki.konas.gui.clickgui.component.Component;
import me.darki.konas.setting.ColorValue;
import me.darki.konas.util.RenderUtil2;
import me.darki.konas.module.client.ClickGUIModule;
import me.darki.konas.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;

public class Class204
extends Component {
    public Setting<Enum> Field565;

    public Class204(Setting<Enum> setting, float f, float f2, float f3, float f4, float f5, float f6) {
        super(setting.Method1183(), f, f2, f3, f4, f5, f6);
        this.Field565 = setting;
    }

    public boolean Method639(int n) {
        return n <= ((Enum[])((Enum)this.Method583().getValue()).getClass().getEnumConstants()).length - 1 && n >= 0;
    }

    @Override
    public void Method105(int n, int n2, float f) {
        super.Method105(n, n2, f);
        int n3 = Class204.isMouseWithinBounds(n, n2, this.Method1475(), this.Method1476(), this.Method1479(), this.Method1481()) ? new Color(96, 96, 96, 100).hashCode() : ((ColorValue) ClickGUIModule.secondary.getValue()).Method774();
        RenderUtil2.Method1338(this.Method1475(), this.Method1476(), this.Method1479(), this.Method1481(), n3);
        Class548.Method1016(this.Method1480(), (int)(this.Method1475() + 5.0f), (int)(this.Method1476() + this.Method1481() / 2.0f - Class548.Method1023(this.Method1480()) / 2.0f), ((ColorValue) ClickGUIModule.fony.getValue()).Method774());
        RenderUtil2.Method1338(this.Method1488(), this.Method1476(), this.getXOffset(), this.Method1481(), ((ColorValue) ClickGUIModule.color.getValue()).Method774());
        Class548.Method1016(((Enum)this.Method583().getValue()).toString(), (int)(this.Method1475() + 5.0f + Class548.Method1022(this.Method1480())), (int)(this.Method1476() + this.Method1481() / 2.0f - Class548.Method1023(((Enum)this.Method583().getValue()).toString()) / 2.0f), ((ColorValue) ClickGUIModule.color.getValue()).Method774());
    }

    @Override
    public boolean Method106(int n, int n2, int n3) {
        boolean bl;
        super.Method106(n, n2, n3);
        boolean bl2 = Class204.isMouseWithinBounds(n, n2, this.Method1475(), this.Method1476(), this.Method1479(), this.Method1481());
        boolean bl3 = bl = (float)n > this.Method1475() + this.Method1479() / 2.0f;
        if (bl2 && n3 == 0) {
            Minecraft.getMinecraft().getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord((SoundEvent)SoundEvents.UI_BUTTON_CLICK, (float)1.0f));
            int n4 = this.Method583().Method1195(((Enum)this.Method583().getValue()).toString());
            if (bl) {
                if (this.Method639(n4 + 1)) {
                    this.Method583().setValue(((Enum[])((Enum)this.Method583().getValue()).getClass().getEnumConstants())[n4 + 1]);
                } else {
                    this.Method583().setValue(((Enum[])((Enum)this.Method583().getValue()).getClass().getEnumConstants())[0]);
                }
            } else if (this.Method639(n4 - 1)) {
                this.Method583().setValue(((Enum[])((Enum)this.Method583().getValue()).getClass().getEnumConstants())[n4 - 1]);
            } else {
                this.Method583().setValue(((Enum[])((Enum)this.Method583().getValue()).getClass().getEnumConstants())[((Enum[])((Enum)this.Method583().getValue()).getClass().getEnumConstants()).length - 1]);
            }
            return true;
        }
        return false;
    }

    public Setting<Enum> Method583() {
        return this.Field565;
    }
}