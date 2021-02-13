package me.darki.konas.unremaped;

import me.darki.konas.setting.ColorValue;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;

public class Class478
extends Module {
    public static Setting<ColorValue> Field2508 = new Setting<>("EnchantColor", new ColorValue(0x77FF00FF, true));

    public Class478() {
        super("CustomEnchants", "Makes your enchant glint change color", Category.RENDER, "RainbowEnchants");
    }
}
