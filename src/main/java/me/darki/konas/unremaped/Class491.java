package me.darki.konas;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;

public class Class491
extends Module {
    public static Setting<Boolean> Field2203 = new Setting<>("Offset", false);
    public static Setting<Float> Field2204 = new Setting<>("Distance", Float.valueOf(0.0f), Float.valueOf(10.0f), Float.valueOf(-10.0f), Float.valueOf(0.5f)).Method1191(Field2203::getValue);

    @Subscriber
    public void Method1994(Class5 class5) {
        class5.Cancel();
        if (Field2203.getValue().booleanValue()) {
            class5.Method89(Field2204.getValue().floatValue());
        } else {
            class5.Method89(4.0);
        }
    }

    public Class491() {
        super("CameraClip", Category.RENDER);
    }

    @Subscriber
    public void Method1995(Class3 class3) {
        block0: {
            if (!Field2203.getValue().booleanValue()) break block0;
            class3.Method89(Field2204.getValue().floatValue());
            class3.Cancel();
        }
    }
}
