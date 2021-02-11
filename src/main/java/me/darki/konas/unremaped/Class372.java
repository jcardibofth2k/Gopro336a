package me.darki.konas;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;

public class Class372
extends Module {
    public static Setting<Float> Field2095 = new Setting<>("Reach", Float.valueOf(4.0f), Float.valueOf(10.0f), Float.valueOf(0.5f), Float.valueOf(0.5f));

    @Subscriber
    public void Method1947(Class571 class571) {
        class571.Method572(Field2095.getValue().floatValue());
    }

    public Class372() {
        super("Reach", "Increaces your block reach range", Category.PLAYER);
    }
}
