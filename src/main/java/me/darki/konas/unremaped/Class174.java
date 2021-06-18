package me.darki.konas.unremaped;

import java.awt.Color;

import me.darki.konas.gui.clickgui.component.Component;
import me.darki.konas.setting.ColorValue;
import me.darki.konas.util.RenderUtil2;
import me.darki.konas.module.client.ClickGUIModule;
import me.darki.konas.setting.Setting;
import me.darki.konas.util.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;

public class Class174
extends Component {
    public Setting Field1467;
    public int Field1468;
    public int Field1469;
    public int Field1470;
    public int Field1471;
    public float Field1472 = 45.0f;
    public float Field1473 = this.Field1472 / 10.0f;
    public float Field1474 = this.Method1475() + 1.0f;
    public float Field1475 = this.Method1476() + 48.0f;
    public float Field1476 = 10.0f;
    public float Field1477 = this.Method1475() + 1.0f;
    public float Field1478 = this.Method1476() + 18.0f;
    public float Field1479 = 26.0f;
    public float Field1480 = 44.0f;
    public boolean Field1481 = false;
    public TimerUtil Field1482 = new TimerUtil();

    public Class174(Setting setting, float f, float f2, float f3, float f4, float f5, float f6) {
        super(setting.Method1183(), f, f2, f3, f4, f5, f6);
        this.Field1467 = setting;
        this.Field1468 = this.Method1506().Method778() >> 24 & 0xFF;
        this.Field1469 = this.Method1506().Method778() >> 16 & 0xFF;
        this.Field1470 = this.Method1506().Method778() >> 8 & 0xFF;
        this.Field1471 = this.Method1506().Method778() & 0xFF;
    }

    public boolean Method1504(int n, int n2, int n3) {
        block2: {
            block5: {
                block4: {
                    block3: {
                        if (!Class174.isMouseWithinBounds(n, n2, this.Method1475(), this.Method1476(), this.Method1479(), this.Method1481())) {
                            return false;
                        }
                        if (n3 != 0) {
                            return false;
                        }
                        if (!this.Method1505()) break block2;
                        if (!Class174.isMouseWithinBounds(n, n2, this.Field1477, this.Field1478, this.Field1480, this.Field1479 - 1.0f)) break block3;
                        this.Method1507(n, n2);
                        break block2;
                    }
                    if (!Class174.isMouseWithinBounds(n, n2, this.Field1474, this.Field1475, this.Field1472 - 2.0f, this.Field1476)) break block4;
                    this.Method1508(n);
                    break block2;
                }
                if (!Class174.isMouseWithinBounds(n, n2, this.Method1475() + this.Method1479() - 11.0f, this.Method1476() + 17.0f, 10.0, 42.0)) break block5;
                this.Method1511(n2);
                break block2;
            }
            if (!Class174.isMouseWithinBounds(n, n2, this.Method1475() + 4.0f + this.Field1472, this.Method1476() + 25.0f + (float)(Class548.Method1020() * 3), 8.0, 8.0) || !this.Field1482.GetDifferenceTiming(500.0)) break block2;
            this.Method1506().Method772();
            this.Field1482.UpdateCurrentTime();
        }
        return true;
    }

    public boolean Method1505() {
        return this.Field1481;
    }

    public ColorValue Method1506() {
        return (ColorValue)this.Field1467.getValue();
    }

    public void Method104(boolean bl) {
        this.Field1481 = bl;
    }

    public void Method1507(float f, float f2) {
        float f3 = ((f -= this.Method1475()) - 1.0f) / this.Field1480;
        float f4 = 1.0f - ((f2 -= this.Method1476()) - 18.0f) / this.Field1479;
        float[] fArray = Color.RGBtoHSB(this.Field1469, this.Field1470, this.Field1471, null);
        int n = Color.HSBtoRGB(fArray[0], f3, f4);
        this.Field1469 = n >> 16 & 0xFF;
        this.Field1470 = n >> 8 & 0xFF;
        this.Field1471 = n & 0xFF;
        this.Method1509();
    }

    @Override
    public void Method649(int n, int n2, int n3, long l) {
        this.Method1504(n, n2, n3);
    }

    public void Method1508(float f) {
        float f2 = ((f -= this.Method1475()) - 1.0f) / this.Field1472;
        float[] fArray = Color.RGBtoHSB(this.Field1469, this.Field1470, this.Field1471, null);
        int n = Color.HSBtoRGB(f2, fArray[1], fArray[2]);
        this.Field1469 = n >> 16 & 0xFF;
        this.Field1470 = n >> 8 & 0xFF;
        this.Field1471 = n & 0xFF;
        this.Method1509();
    }

    public void Method1509() {
        int n = (this.Field1468 & 0xFF) << 24 | (this.Field1469 & 0xFF) << 16 | (this.Field1470 & 0xFF) << 8 | this.Field1471 & 0xFF;
        this.Method1506().Method771(n);
    }

    public void Method1510() {
        this.Field1468 = this.Method1506().Method778() >> 24 & 0xFF;
        this.Field1469 = this.Method1506().Method778() >> 16 & 0xFF;
        this.Field1470 = this.Method1506().Method778() >> 8 & 0xFF;
        this.Field1471 = this.Method1506().Method778() & 0xFF;
    }

    @Override
    public float Method1481() {
        return this.Method1505() ? super.Method1481() : 12.0f;
    }

    @Override
    public boolean Method106(int n, int n2, int n3) {
        if (Class174.isMouseWithinBounds(n, n2, this.Method1475(), this.Method1476(), this.Method1479(), 12.0)) {
            if (n3 == 2) {
                if (ClickGUIModule.Field1521 == -1) {
                    ClickGUIModule.Field1521 = this.Method1506().Method778();
                } else {
                    this.Method1506().Method771(ClickGUIModule.Field1521);
                    ClickGUIModule.Field1521 = -1;
                    this.Method1510();
                }
            } else {
                Minecraft.getMinecraft().getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord((SoundEvent)SoundEvents.UI_BUTTON_CLICK, (float)1.0f));
                this.Method104(!this.Method1505());
            }
            return true;
        }
        return this.Method1504(n, n2, n3);
    }

    public void Method1511(float f) {
        this.Field1468 = 255 - (int)(((f -= this.Method1476()) - 17.0f) / 42.0f * 255.0f);
        this.Method1509();
    }

    @Override
    public void Method105(int n, int n2, float f) {
        block3: {
            int n3;
            super.Method105(n, n2, f);
            float[] fArray = Color.RGBtoHSB(this.Field1469, this.Field1470, this.Field1471, null);
            RenderUtil2.Method1338(this.Method1488(), this.Method1476(), this.getXOffset(), 12.0f, ((ColorValue) ClickGUIModule.color.getValue()).Method774());
            RenderUtil2.Method1338(this.Method1475(), this.Method1476(), this.Method1479(), 12.0f, ((ColorValue) ClickGUIModule.secondary.getValue()).Method774());
            int n4 = n3 = this.Method1505() ? ((ColorValue) ClickGUIModule.color.getValue()).Method775().darker().getRGB() : ((ColorValue) ClickGUIModule.secondary.getValue()).Method774();
            if (((Boolean) ClickGUIModule.hover.getValue()).booleanValue() && Class174.isMouseWithinBounds(n, n2, this.Method1475(), this.Method1476(), this.Method1479(), this.Method1481())) {
                n3 = this.Method1505() ? ((ColorValue) ClickGUIModule.color.getValue()).Method775().brighter().getRGB() : new Color(96, 96, 96, 100).hashCode();
            }
            RenderUtil2.Method1338(this.Method1475(), this.Method1476(), this.Method1479(), 12.0f, n3);
            Class548.Method1016(this.Method1480(), (int)(this.Method1475() + 5.0f), (int)(this.Method1476() + 6.0f - (float)(Class548.Method1020() / 2) - 0.5f), ((ColorValue) ClickGUIModule.fony.getValue()).Method774());
            Class548.Method1016(this.Method1505() ? "-" : "+", (int)(this.Method1475() + this.Method1479() - 5.0f - Class548.Method1022(this.Method1505() ? "-" : "+")), (int)(this.Method1476() + 6.0f - Class548.Method1023(this.Method1505() ? "-" : "+") / 2.0f - 0.5f), ((ColorValue) ClickGUIModule.fony.getValue()).Method774());
            if (!this.Field1481) break block3;
            RenderUtil2.Method1338(this.Method1475(), this.Method1476() + 17.0f, 46.0f, 28.0f, ((ColorValue) ClickGUIModule.color.getValue()).Method774());
            this.Field1477 = this.Method1475() + 1.0f;
            this.Field1478 = this.Method1476() + 18.0f;
            Class516.Method1277(this.Field1477, this.Field1478, this.Field1477 + this.Field1480, this.Field1478 + this.Field1479, Color.getHSBColor(fArray[0], 0.0f, 0.0f).getRGB(), Color.getHSBColor(fArray[0], 0.0f, 1.0f).getRGB(), Color.getHSBColor(fArray[0], 1.0f, 0.0f).getRGB(), Color.getHSBColor(fArray[0], 1.0f, 1.0f).getRGB());
            RenderUtil2.Method1338(this.Method1475(), this.Method1476() + 47.0f, 46.0f, 12.0f, ((ColorValue) ClickGUIModule.color.getValue()).Method774());
            this.Field1474 = this.Method1475() + 1.0f;
            this.Field1475 = this.Method1476() + 48.0f;
            float f2 = 0.0f;
            while (f2 + this.Field1473 <= this.Field1472) {
                Class516.Method1275(this.Field1474 + f2, this.Field1475, this.Field1474 + this.Field1473 + f2, this.Field1475 + this.Field1476, Color.getHSBColor(f2 / this.Field1472, 1.0f, 1.0f).getRGB(), Color.getHSBColor((f2 + this.Field1473) / this.Field1472, 1.0f, 1.0f).getRGB());
                f2 += this.Field1473;
            }
            RenderUtil2.Method1338(this.Method1475() + 45.0f, this.Method1476() + 47.0f, 1.0f, 12.0f, ((ColorValue) ClickGUIModule.color.getValue()).Method774());
            int n5 = 0xFF000000 | (this.Field1469 & 0xFF) << 16 | (this.Field1470 & 0xFF) << 8 | this.Field1471 & 0xFF;
            int n6 = 0 | (this.Field1469 & 0xFF) << 16 | (this.Field1470 & 0xFF) << 8 | this.Field1471 & 0xFF;
            float f3 = this.Method1475() + this.Method1479() - 11.0f;
            float f4 = this.Method1476() + 17.0f;
            float f5 = 10.0f;
            float f6 = 42.0f;
            Class516.Method1277(f3, f4, this.Method1475() + this.Method1479() - 1.0f, this.Method1476() + this.Method1481() - 2.0f, n6, n5, n6, n5);
            Class548.Method1019("R" + this.Field1469, (int)this.Method1475() + 3 + (int)this.Field1472, (int)this.Method1476() + 16, 0xFFFFFF);
            Class548.Method1019("G" + this.Field1470, (int)this.Method1475() + 3 + (int)this.Field1472, (float)((int)this.Method1476() + 18) + Class548.Method1023("RGB0:1234567890") - 0.5f, 0xFFFFFF);
            Class548.Method1019("B" + this.Field1471, (int)this.Method1475() + 3 + (int)this.Field1472, (float)((int)this.Method1476() + 20) + Class548.Method1023("RGB0:1234567890") * 2.0f - 0.5f, 0xFFFFFF);
            if (this.Method1506().Method783()) {
                RenderUtil2.Method1336(this.Method1475() + 3.0f + this.Field1472, (int)this.Method1476() + 22 + Class548.Method1020() * 3, 10.0f, 10.0f, 2.0f, this.Method1506().Method774());
            }
            RenderUtil2.Method1338(this.Method1475() + 4.0f + this.Field1472, (int)this.Method1476() + 23 + Class548.Method1020() * 3, 8.0f, 8.0f, this.Method1506().Method774());
            Class548.Method1019("RB", (int)(this.Method1475() + 15.0f + this.Field1472), (int)this.Method1476() + 22 + Class548.Method1020() * 3, 0xFFFFFF);
            int n7 = new Color(255, 255, 255, 140).hashCode();
            int n8 = (int)(this.Field1474 + (float)((int)(fArray[0] * this.Field1472)));
            RenderUtil2.Method1338(n8, this.Field1475, 2.0f, this.Field1476, n7);
            int n9 = (int)(f6 + f4 - (float)((int)((float)this.Field1468 / 255.0f * f6)));
            RenderUtil2.Method1338(f3, MathHelper.clamp((float)((float)n9 - 1.0f), (float)f4, (float)(f4 + f6)), f5, 2.0f, n7);
            int n10 = (int)(this.Field1477 + (float)((int)(fArray[1] * this.Field1480)));
            int n11 = (int)(this.Field1479 + this.Field1478 - (float)((int)(fArray[2] * this.Field1479)));
            RenderUtil2.Method1338((float)n10 - 1.0f, (float)n11 - 1.0f, 2.0f, 2.0f, n7);
        }
    }

    public Setting Method583() {
        return this.Field1467;
    }
}