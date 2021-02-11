package me.darki.konas;

import java.awt.Color;

import me.darki.konas.module.client.ClickGUIModule;
import me.darki.konas.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;

public class Class210
extends Class183 {
    public Setting<ParentSetting> Field513;

    @Override
    public void Method105(int n, int n2, float f) {
        int n3;
        super.Method105(n, n2, f);
        RenderUtil2.Method1338(this.Method1488(), this.Method1476(), this.Method1473(), this.Method1481(), ClickGUIModule.color.getValue().Method774());
        int n4 = n3 = this.Method583().getValue().Method1230() ? ClickGUIModule.color.getValue().Method774() : ClickGUIModule.secondary.getValue().Method774();
        if (ClickGUIModule.hover.getValue().booleanValue() && Class210.Method1493(n, n2, this.Method1475(), this.Method1476(), this.Method1479(), this.Method1481())) {
            n3 = this.Method583().getValue().Method1230() ? ClickGUIModule.color.getValue().Method775().brighter().hashCode() : new Color(96, 96, 96, 100).hashCode();
        }
        RenderUtil2.Method1338(this.Method1475(), this.Method1476(), this.Method1479(), this.Method1481(), n3);
        Class548.Method1016(this.Method1480(), (int)(this.Method1475() + 5.0f), (int)(this.Method1476() + this.Method1481() / 2.0f - Class548.Method1023(this.Method1480()) / 2.0f), ClickGUIModule.fony.getValue().Method774());
        Class548.Method1016(this.Method583().getValue().Method1230() ? "-" : "+", (int)(this.Method1475() + this.Method1479() - 5.0f - Class548.Method1022(this.Method583().getValue().Method1230() ? "-" : "+")), (int)(this.Method1476() + this.Method1481() / 2.0f - Class548.Method1023(this.Method583().getValue().Method1230() ? "-" : "+") / 2.0f - 0.5f), ClickGUIModule.fony.getValue().Method774());
    }

    @Override
    public boolean Method106(int n, int n2, int n3) {
        super.Method106(n, n2, n3);
        boolean bl = Class210.Method1493(n, n2, this.Method1475(), this.Method1476(), this.Method1479(), this.Method1481());
        if (bl && (n3 == 0 || n3 == 1)) {
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
            this.Method583().getValue().Method1231(!this.Method583().getValue().Method1230());
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
