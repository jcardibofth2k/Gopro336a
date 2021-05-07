package me.darki.konas.unremaped;

import me.darki.konas.*;
import java.awt.Color;

import me.darki.konas.gui.clickgui.component.Component;
import me.darki.konas.setting.ColorValue;
import me.darki.konas.setting.ParentSetting;
import me.darki.konas.util.RenderUtil2;
import me.darki.konas.module.client.ClickGUIModule;
import me.darki.konas.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;

public class Class210
extends Component {
    public Setting<ParentSetting> Field513;

    @Override
    public void Method105(int n, int n2, float f) {
        int n3;
        super.Method105(n, n2, f);
        RenderUtil2.Method1338(this.Method1488(), this.Method1476(), this.getXOffset(), this.Method1481(), ((ColorValue) ClickGUIModule.color.getValue()).Method774());
        int n4 = n3 = ((ParentSetting)this.Method583().getValue()).Method1230() ? ((ColorValue) ClickGUIModule.color.getValue()).Method774() : ((ColorValue) ClickGUIModule.secondary.getValue()).Method774();
        if (((Boolean) ClickGUIModule.hover.getValue()).booleanValue() && Class210.isMouseWithinBounds(n, n2, this.Method1475(), this.Method1476(), this.Method1479(), this.Method1481())) {
            n3 = ((ParentSetting)this.Method583().getValue()).Method1230() ? ((ColorValue) ClickGUIModule.color.getValue()).Method775().brighter().hashCode() : new Color(96, 96, 96, 100).hashCode();
        }
        RenderUtil2.Method1338(this.Method1475(), this.Method1476(), this.Method1479(), this.Method1481(), n3);
        Class548.Method1016(this.Method1480(), (int)(this.Method1475() + 5.0f), (int)(this.Method1476() + this.Method1481() / 2.0f - Class548.Method1023(this.Method1480()) / 2.0f), ((ColorValue) ClickGUIModule.fony.getValue()).Method774());
        Class548.Method1016(((ParentSetting)this.Method583().getValue()).Method1230() ? "-" : "+", (int)(this.Method1475() + this.Method1479() - 5.0f - Class548.Method1022(((ParentSetting)this.Method583().getValue()).Method1230() ? "-" : "+")), (int)(this.Method1476() + this.Method1481() / 2.0f - Class548.Method1023(((ParentSetting)this.Method583().getValue()).Method1230() ? "-" : "+") / 2.0f - 0.5f), ((ColorValue) ClickGUIModule.fony.getValue()).Method774());
    }

    @Override
    public boolean Method106(int n, int n2, int n3) {
        super.Method106(n, n2, n3);
        boolean bl = Class210.isMouseWithinBounds(n, n2, this.Method1475(), this.Method1476(), this.Method1479(), this.Method1481());
        if (bl && (n3 == 0 || n3 == 1)) {
            Minecraft.getMinecraft().getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord((SoundEvent)SoundEvents.UI_BUTTON_CLICK, (float)1.0f));
            ((ParentSetting)this.Method583().getValue()).Method1231(!((ParentSetting)this.Method583().getValue()).Method1230());
            return true;
        }
        return false;
    }

    public Class210(Setting<ParentSetting> setting, float f, float f2, float f3, float f4, float f5, float f6) {
        super(setting.Method1183(), f, f2, f3, f4, f5, f6);
        this.Field513 = setting;
    }

    public Setting<ParentSetting> Method583() {
        return this.Field513;
    }
}