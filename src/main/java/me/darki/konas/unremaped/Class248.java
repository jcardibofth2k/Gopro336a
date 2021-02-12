package me.darki.konas.unremaped;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;

public class Class248
extends Module {
    public Class248() {
        super("LiquidInteract", Category.EXPLOIT, "LiquidPlace");
    }

    @Subscriber
    public void Method2010(Class655 class655) {
        class655.Cancel();
    }
}
