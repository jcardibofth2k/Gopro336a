package me.darki.konas.module.render;

import me.darki.konas.module.Category;
import me.darki.konas.module.ModuleManager;
import me.darki.konas.setting.ListenableSettingDecorator;
import me.darki.konas.module.Module;

public class Zoom
extends Module {
    public static float Field777;
    public static ListenableSettingDecorator<Float> Field778;

    @Override
    public void onEnable() {
        Field777 = Zoom.mc.gameSettings.fovSetting;
        Zoom.mc.gameSettings.fovSetting *= 1.6f - ((Float)Field778.getValue()).floatValue();
    }

    public Zoom() {
        super("Zoom", "Zoom in properly, not with perspective modification", Category.RENDER);
    }

    @Override
    public void onDisable() {
        Zoom.mc.gameSettings.fovSetting = Field777;
    }

    static {
        Field778 = new ListenableSettingDecorator("Zoom", Float.valueOf(1.1f), Float.valueOf(1.5f), Float.valueOf(0.5f), Float.valueOf(0.05f), Zoom::Method825);
    }

    public static void Method825(Float f) {
        if (ModuleManager.getModuleByClass(Zoom.class).isEnabled()) {
            Zoom.mc.gameSettings.fovSetting = Field777 * (1.6f - f.floatValue());
        }
    }
}