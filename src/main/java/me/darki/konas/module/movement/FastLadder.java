package me.darki.konas.module.movement;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Category;
import me.darki.konas.TickEvent;
import me.darki.konas.MathUtil;
import me.darki.konas.module.Module;

public class FastLadder
extends Module {
    @Subscriber
    public void Method462(TickEvent tickEvent) {
        if (FastLadder.mc.world == null || FastLadder.mc.player == null) {
            return;
        }
        if (FastLadder.mc.player.isOnLadder() && MathUtil.Method1080()) {
            FastLadder.mc.player.motionY = 0.169;
        }
    }

    public FastLadder() {
        super("FastLadder", Category.MOVEMENT, new String[0]);
    }
}
