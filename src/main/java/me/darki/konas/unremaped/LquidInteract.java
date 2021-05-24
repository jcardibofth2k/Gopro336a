package me.darki.konas.unremaped;

import me.darki.konas.*;
import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;

public class LquidInteract
extends Module {
    public LquidInteract() {
        super("LiquidInteract", Category.EXPLOIT, "LiquidPlace");
    }

    @Subscriber
    public void Method2010(Class655 class655) {
        class655.Cancel();
    }
}