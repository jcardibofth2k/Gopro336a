package me.darki.konas.module.client;

import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;

public class Config
extends Module {
    public static Setting<Boolean> overwriteFriends = new Setting<>("OverwriteFriends", false);

    public Config() {
        super("Config", "Settings for Config", Category.CLIENT);
    }

    @Override
    public void onEnable() {
        this.toggle();
    }
}