package me.darki.konas.module.render;

import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;

public class Render
extends Module {
    public static Setting<Boolean> slowRender = new Setting<>("SlowRender", false).setDescription("Prevents culling");

    @Override
    public void onEnable() {
        this.toggle();
    }

    public Render() {
        super("Render", "Customize global rendering options", Category.CLIENT);
    }
}