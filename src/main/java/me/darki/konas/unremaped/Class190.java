package me.darki.konas.unremaped;

import cookiedragon.eventsystem.EventDispatcher;
import java.awt.Color;

import me.darki.konas.gui.clickgui.component.Component;
import me.darki.konas.gui.clickgui.unremappedGuiStuff.Class193;
import me.darki.konas.setting.ColorValue;
import me.darki.konas.util.RenderUtil2;
import me.darki.konas.module.Module;
import me.darki.konas.module.client.ClickGUIModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;

public class Class190
extends Component {
    public Module Field112;
    public boolean Field113;

    @Override
    public boolean Method106(int n, int n2, int n3) {
        super.Method106(n, n2, n3);
        if (this.Method103()) {
            this.Method146().setBind(n3 - 100);
            this.Method104(false);
            return true;
        }
        boolean bl = Class190.isMouseWithinBounds(n, n2, this.Method1475(), this.Method1476(), this.Method1479(), this.Method1481());
        if (bl && n3 == 0) {
            Minecraft.getMinecraft().getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord((SoundEvent)SoundEvents.UI_BUTTON_CLICK, (float)1.0f));
            this.Method104(!this.Method103());
            return true;
        }
        if (bl && n3 == 1) {
            Minecraft.getMinecraft().getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord((SoundEvent)SoundEvents.UI_BUTTON_CLICK, (float)1.0f));
            this.Method146().setHoldBind(!this.Method146().Method1630());
            return true;
        }
        return false;
    }

    @Override
    public void Method102(char c, int n) {
        block1: {
            super.Method102(c, n);
            if (!this.Method103()) break block1;
            if (n == 1) {
                this.Method146().setBind(0);
                this.Method104(false);
                return;
            }
            this.Method146().setBind(n);
            this.Method104(false);
        }
    }

    public Class190(Module module, float f, float f2, float f3, float f4, float f5, float f6) {
        super(module.getName(), f, f2, f3, f4, f5, f6);
        this.Field112 = module;
        EventDispatcher.Companion.register(this);
        EventDispatcher.Companion.subscribe(this);
    }

    public boolean Method103() {
        return this.Field113;
    }

    @Override
    public void Method105(int n, int n2, float f) {
        super.Method105(n, n2, f);
        RenderUtil2.Method1338(this.Method1488(), this.Method1476(), this.getXOffset(), this.Method1481(), ((ColorValue) ClickGUIModule.color.getValue()).Method774());
        int n3 = Class190.isMouseWithinBounds(n, n2, this.Method1475(), this.Method1476(), this.Method1479(), this.Method1481()) ? new Color(96, 96, 96, 100).hashCode() : ((ColorValue) ClickGUIModule.secondary.getValue()).Method774();
        RenderUtil2.Method1338(this.Method1475(), this.Method1476(), this.Method1479(), this.Method1481(), n3);
        Class548.Method1016(this.Method103() ? "Press new bind..." : (this.Method146().Method1630() ? "Hold: " : "Bind: ") + GameSettings.getKeyDisplayString((int)this.Method146().Method1646()), (int)(this.Method1475() + 5.0f), (int)(this.Method1476() + this.Method1481() / 2.0f - Class548.Method1023(this.Method103() ? "Press new bind..." : "Bind: " + GameSettings.getKeyDisplayString((int)this.Method146().Method1646())) / 2.0f - 0.5f), 0xFFFFFF);
    }

    public Module Method146() {
        return this.Field112;
    }

    public void Method104(boolean bl) {
        this.Field113 = bl;
        Class193.Method118(bl);
    }
}