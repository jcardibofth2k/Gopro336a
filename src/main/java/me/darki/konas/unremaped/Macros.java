package me.darki.konas.unremaped;

import me.darki.konas.*;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;

public class Macros
extends Module {
    public static Setting<Boolean> oneLine = new Setting<>("OneLine", false);

    public Macros() {
        super("Macros", Category.CLIENT, new String[0]);
        this.toggle();
    }
}