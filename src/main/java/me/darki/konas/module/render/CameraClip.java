package me.darki.konas.module.render;

import me.darki.konas.*;
import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.Class3;
import me.darki.konas.unremaped.Class5;

public class CameraClip
extends Module {
    public static Setting<Boolean> offset = new Setting<>("Offset", false);
    public static Setting<Float> distance = new Setting<>("Distance", 0.0f, 10.0f, -10.0f, 0.5f).visibleIf(offset::getValue);

    @Subscriber
    public void Method1994(Class5 class5) {
        class5.Cancel();
        if (((Boolean)offset.getValue()).booleanValue()) {
            class5.Method89(((Float)distance.getValue()).floatValue());
        } else {
            class5.Method89(4.0);
        }
    }

    public CameraClip() {
        super("CameraClip", Category.RENDER, new String[0]);
    }

    @Subscriber
    public void Method1995(Class3 class3) {
        block0: {
            if (!((Boolean)offset.getValue()).booleanValue()) break block0;
            class3.Method89(((Float)distance.getValue()).floatValue());
            class3.Cancel();
        }
    }
}