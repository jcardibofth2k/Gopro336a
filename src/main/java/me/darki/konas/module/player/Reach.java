package me.darki.konas.module.player;

import me.darki.konas.*;
import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.Class571;

public class Reach
extends Module {
    public static Setting<Float> reach = new Setting<>("Reach", Float.valueOf(4.0f), Float.valueOf(10.0f), Float.valueOf(0.5f), Float.valueOf(0.5f));

    @Subscriber
    public void Method1947(Class571 class571) {
        class571.Method572(((Float)reach.getValue()).floatValue());
    }

    public Reach() {
        super("Reach", "Increaces your block reach range", Category.PLAYER, new String[0]);
    }
}