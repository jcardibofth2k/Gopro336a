package me.darki.konas;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Module;

public class BreakTweaks
extends Module {
    public BreakTweaks() {
        super("BreakTweaks", "Lets you pause breaking blocks", Category.PLAYER, "StickyBreak");
    }

    @Subscriber
    public void Method1907(Class38 class38) {
        class38.Cancel();
    }
}
