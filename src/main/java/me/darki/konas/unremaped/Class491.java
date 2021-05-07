package me.darki.konas.unremaped;

import me.darki.konas.*;
import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;

public class Class491
extends Module {
    public static Setting<Boolean> offset = new Setting<>("Offset", false);
    public static Setting<Float> distance = new Setting<>("Distance", Float.valueOf(0.0f), Float.valueOf(10.0f), Float.valueOf(-10.0f), Float.valueOf(0.5f)).visibleIf(Field2203::getValue);

    @Subscriber
    public void Method1994(Class5 class5) {
        class5.Cancel();
        if (((Boolean)offset.getValue()).booleanValue()) {
            class5.Method89(((Float)distance.getValue()).floatValue());
        } else {
            class5.Method89(4.0);
        }
    }

    public Class491() {
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