package me.darki.konas.module;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;

public class XRay
extends Module {
    public static Setting<Class443> blocks = new Setting<>("Blocks", new Class443(new String[0]));

    @Subscriber
    public void Method585(Class83 class83) {
        block0: {
            if (((Class443) blocks.getValue()).Method682().contains(class83.Method449())) break block0;
            class83.Method1236(true);
        }
    }

    @Subscriber
    public void Method586(Class70 class70) {
        class70.Method1236(true);
    }

    @Override
    public void onDisable() {
        XRay.mc.renderGlobal.loadRenderers();
    }

    public XRay() {
        super("XRay", "Makes most blocks invisible so you can see ores", Category.RENDER, new String[0]);
    }

    @Override
    public void onEnable() {
        XRay.mc.renderGlobal.loadRenderers();
    }
}
