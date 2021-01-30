package me.darki.konas;

import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;

public class Class478
extends Module {
    public static Setting<ColorValue> Field2508 = new Setting<>("EnchantColor", new ColorValue(0x77FF00FF, true));

    public Class478() {
        super("CustomEnchants", "Makes your enchant glint change color", Category.RENDER, "RainbowEnchants");
    }
}
