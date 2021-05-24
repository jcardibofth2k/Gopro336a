package me.darki.konas.unremaped;

import me.darki.konas.*;
import me.darki.konas.setting.ColorValue;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;

public class EnchantColor
extends Module {
    public static Setting<ColorValue> enchantColor = new Setting<>("EnchantColor", new ColorValue(0x77FF00FF, true));

    public EnchantColor() {
        super("CustomEnchants", "Makes your enchant glint change color", Category.RENDER, "RainbowEnchants");
    }
}