package me.darki.konas.module.client;

import me.darki.konas.module.Category;
import me.darki.konas.setting.ListenableSettingDecorator;
import me.darki.konas.setting.ColorValue;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;

public class KonasGui
extends Module {
    public static Setting<ColorValue> mainStart = new Setting<>("MainStart", new ColorValue(-3659736));
    public static Setting<ColorValue> mainEnd = new Setting<>("MainEnd", new ColorValue(-3659736));
    public static Setting<ColorValue> accentStart = new Setting<>("AccentStart", new ColorValue(-2343896));
    public static Setting<ColorValue> accentEnd = new Setting<>("AccentEnd", new ColorValue(-2343896));
    public static Setting<ColorValue> categoryStart = new Setting<>("CategoryStart", new ColorValue(-6612197));
    public static Setting<ColorValue> categoryEnd = new Setting<>("CategoryEnd", new ColorValue(-6612197));
    public static Setting<ColorValue> outlineStart = new Setting<>("OutlineStart", new ColorValue(0x33FFFFFF));
    public static Setting<ColorValue> outlineEnd = new Setting<>("OutlineEnd", new ColorValue(0x33FFFFFF));
    public static Setting<ColorValue> backgroundStart = new Setting<>("BackgroundStart", new ColorValue(-1440603614));
    public static Setting<ColorValue> backgroundEnd = new Setting<>("BackgroundEnd", new ColorValue(-1440603614));
    public static Setting<ColorValue> foregroundStart = new Setting<>("ForegroundStart", new ColorValue(0x60000000));
    public static Setting<ColorValue> foregroundEnd = new Setting<>("ForegroundEnd", new ColorValue(0x60000000));
    public static Setting<ColorValue> primaryStart = new Setting<>("PrimaryStart", new ColorValue(-4934476));
    public static Setting<ColorValue> primaryEnd = new Setting<>("PrimaryEnd", new ColorValue(-4934476));
    public static Setting<ColorValue> secondaryStart = new Setting<>("SecondaryStart", new ColorValue(-13092808));
    public static Setting<ColorValue> secondaryEnd = new Setting<>("SecondaryEnd", new ColorValue(-13092808));
    public static Setting<ColorValue> sliderStart = new Setting<>("SliderStart", new ColorValue(-6842473));
    public static Setting<ColorValue> sliderEnd = new Setting<>("SliderEnd", new ColorValue(-6842473));
    public static Setting<ColorValue> textBoxStart = new Setting<>("TextBoxStart", new ColorValue(-2302756));
    public static Setting<ColorValue> textBoxEnd = new Setting<>("TextBoxEnd", new ColorValue(-2302756));
    public static Setting<ColorValue> fontStart = new Setting<>("FontStart", new ColorValue(-1));
    public static Setting<ColorValue> fontEnd = new Setting<>("FontEnd", new ColorValue(-1));
    public static Setting<ColorValue> darkFontStart = new Setting<>("DarkFontStart", new ColorValue(-16777216));
    public static Setting<ColorValue> darkFontEnd = new Setting<>("DarkFontEnd", new ColorValue(-16777216));
    public static Setting<ColorValue> subFontStart = new Setting<>("SubFontStart", new ColorValue(-3355444));
    public static Setting<ColorValue> subFontEnd = new Setting<>("SubFontEnd", new ColorValue(-3355444));
    public static Setting<Float> highlight = new Setting<>("Highlight", Float.valueOf(0.7f), Float.valueOf(0.9f), Float.valueOf(0.5f), Float.valueOf(0.1f));
    public static Setting<Boolean> reload = new Setting<>("Reload", false);
    public static Setting<Integer> height = new Setting<>("Height", 400, 600, 300, 10);
    public static ListenableSettingDecorator<Boolean> singleColumn = new ListenableSettingDecorator("SingleColumn", false, KonasGui::Method145);
    public static int Field110 = -1;

    @Override
    public void onEnable() {
        this.toggle();
        mc.displayGuiScreen(KonasGlobals.INSTANCE.Field1131);
    }

    public KonasGui() {
        super("KonasGui", "Aimware-Styled single panel GUI", 54, Category.CLIENT);
    }

    public static void Method145(Boolean bl) {
        reload.setValue(Boolean.TRUE);
    }
}