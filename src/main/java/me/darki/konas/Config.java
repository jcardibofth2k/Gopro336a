package me.darki.konas;

import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;

public class Config
extends Module {
    public static Setting<Boolean> Field1792 = new Setting<>("OverwriteFriends", false);

    public Config() {
        super("Config", "Settings for Config", Category.CLIENT, new String[0]);
    }

    @Override
    public void onEnable() {
        this.toggle();
    }
}
