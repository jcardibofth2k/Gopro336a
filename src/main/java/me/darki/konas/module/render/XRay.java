package me.darki.konas.module.render;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Category;
import me.darki.konas.unremaped.Class443;
import me.darki.konas.unremaped.Class70;
import me.darki.konas.unremaped.Class83;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;

public class XRay
extends Module {
    public static Setting<Class443> blocks = new Setting<>("Blocks", new Class443());

    @Subscriber
    public void Method585(Class83 class83) {
        block0: {
            if (blocks.getValue().Method682().contains(class83.Method449())) break block0;
            class83.setCanceled(true);
        }
    }

    @Subscriber
    public void Method586(Class70 class70) {
        class70.setCanceled(true);
    }

    @Override
    public void onDisable() {
        XRay.mc.renderGlobal.loadRenderers();
    }

    public XRay() {
        super("XRay", "Makes most blocks invisible so you can see ores", Category.RENDER);
    }

    @Override
    public void onEnable() {
        XRay.mc.renderGlobal.loadRenderers();
    }
}