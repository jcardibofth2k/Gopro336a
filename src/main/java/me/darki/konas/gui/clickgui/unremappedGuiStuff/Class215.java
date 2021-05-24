package me.darki.konas.gui.clickgui.unremappedGuiStuff;

import me.darki.konas.setting.ColorValue;
import me.darki.konas.module.client.KonasGui;
import me.darki.konas.module.client.KonasGlobals;
import me.darki.konas.unremaped.Class135;
import me.darki.konas.unremaped.Class241;

public class Class215
implements Class135 {
    public String Field307;
    public float Field308;
    public float Field309;
    public float Field310;
    public float Field311;
    public float Field312;
    public float Field313;
    public static Class241 Field314;
    public static Class241 Field315;
    public static Class241 Field316;
    public static Class241 Field317;
    public static Class241 Field318;
    public static Class241 Field319;
    public static Class241 Field320;
    public static Class241 Field321;
    public static Class241 Field322;
    public static Class241 Field323;
    public static Class241 Field324;
    public static Class241 Field325;
    public static Class241 Field326;
    public static int Field327;

    public float Method476() {
        return this.Field313;
    }

    @Override
    public void Method477(int n, int n2, float f) {
    }

    public Class215(String string, float f, float f2, float f3, float f4, float f5, float f6) {
        this.Field307 = string;
        this.Field308 = f;
        this.Field309 = f2;
        this.Field310 = f3;
        this.Field311 = f4;
        this.Field312 = f5;
        this.Field313 = f6;
    }

    public float Method478() {
        return this.Field310;
    }

    @Override
    public void Method479(char c, int n) {
    }

    public static void Method480(int n) {
        Field327 = n;
    }

    @Override
    public void Method481(int n, int n2, int n3) {
    }

    public void Method482(float f) {
        this.Field308 = f;
    }

    public String Method483() {
        return this.Field307;
    }

    public void Method484(float f) {
        this.Field311 = f;
    }

    @Override
    public void Method485(float f, float f2) {
        this.Method482(f);
        this.Method502(f2);
        this.Method499();
    }

    public float Method486() {
        return this.Field308 + this.Field310;
    }

    static {
        Field327 = 0;
    }

    public static boolean Method487(int n, int n2, double d, double d2, double d3, double d4) {
        return (double)n >= d && (double)n <= d + d3 && (double)n2 >= d2 && (double)n2 <= d2 + d4;
    }

    public void Method488(float f) {
        this.Field313 = f;
    }

    public float Method489() {
        return this.Field312;
    }

    public float Method490() {
        return this.Field308;
    }

    @Override
    public boolean Method491(int n, int n2, int n3, long l) {
        return false;
    }

    public float Method492() {
        return this.Field309 + this.Field311;
    }

    public float Method493() {
        return this.Field309;
    }

    @Override
    public boolean Method494(int n, int n2, int n3) {
        return false;
    }

    public float Method495() {
        return this.Field311;
    }

    public void Method496(float f) {
        this.Field310 = f;
    }

    @Override
    public void Method497(int n, int n2, float f) {
    }

    public void Method498(float f) {
        this.Field312 = f;
    }

    public void Method499() {
    }

    public static void Method500() {
        float f = KonasGlobals.INSTANCE.Field1131.Method1828().Method486();
        float f2 = KonasGlobals.INSTANCE.Field1131.Method1828().Method492();
        float f3 = KonasGlobals.INSTANCE.Field1131.Method1828().Method489();
        float f4 = KonasGlobals.INSTANCE.Field1131.Method1828().Method476();
        Field314 = new Class241(f, f2, f3, f4, (ColorValue) KonasGui.mainStart.getValue(), (ColorValue) KonasGui.mainEnd.getValue());
        Field315 = new Class241(f, f2, f3, f4, (ColorValue) KonasGui.accentStart.getValue(), (ColorValue) KonasGui.accentEnd.getValue());
        Field316 = new Class241(f, f2, f3, f4, (ColorValue) KonasGui.categoryStart.getValue(), (ColorValue) KonasGui.categoryEnd.getValue());
        Field317 = new Class241(f, f2, f3, f4, (ColorValue) KonasGui.outlineStart.getValue(), (ColorValue) KonasGui.outlineEnd.getValue());
        Field318 = new Class241(f, f2, f3, f4, (ColorValue) KonasGui.backgroundStart.getValue(), (ColorValue) KonasGui.backgroundEnd.getValue());
        Field319 = new Class241(f, f2, f3, f4, (ColorValue) KonasGui.foregroundStart.getValue(), (ColorValue) KonasGui.foregroundEnd.getValue());
        Field320 = new Class241(f, f2, f3, f4, (ColorValue) KonasGui.primaryStart.getValue(), (ColorValue) KonasGui.primaryEnd.getValue());
        Field321 = new Class241(f, f2, f3, f4, (ColorValue) KonasGui.secondaryStart.getValue(), (ColorValue) KonasGui.secondaryEnd.getValue());
        Field322 = new Class241(f, f2, f3, f4, (ColorValue) KonasGui.sliderStart.getValue(), (ColorValue) KonasGui.sliderEnd.getValue());
        Field323 = new Class241(f, f2, f3, f4, (ColorValue) KonasGui.textBoxStart.getValue(), (ColorValue) KonasGui.textBoxEnd.getValue());
        Field324 = new Class241(f, f2, f3, f4, (ColorValue) KonasGui.fontStart.getValue(), (ColorValue) KonasGui.fontEnd.getValue());
        Field325 = new Class241(f, f2, f3, f4, (ColorValue) KonasGui.darkFontStart.getValue(), (ColorValue) KonasGui.darkFontEnd.getValue());
        Field326 = new Class241(f, f2, f3, f4, (ColorValue) KonasGui.subFontStart.getValue(), (ColorValue) KonasGui.subFontEnd.getValue());
    }

    public boolean Method501(int n, int n2) {
        return (float)n >= this.Method486() && (float)n <= this.Method486() + this.Field312 && (float)n2 >= this.Method492() && (float)n2 <= this.Method492() + this.Field313;
    }

    public void Method502(float f) {
        this.Field309 = f;
    }
}