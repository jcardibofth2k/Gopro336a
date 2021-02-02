package me.darki.konas;

import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;

public class Class191
extends Module {
    public static Setting<Boolean> Field111 = new Setting<>("OneLine", false);

    public Class191() {
        super("Macros", Category.CLIENT, new String[0]);
        this.toggle();
    }
}
