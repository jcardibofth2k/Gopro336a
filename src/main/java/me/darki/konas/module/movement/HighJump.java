package me.darki.konas.module.movement;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Category;
import me.darki.konas.event.events.MoveEvent;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;

public class HighJump
extends Module {
    public static Setting<Double> height = new Setting<>("Height", 2.0, 10.0, 0.1, 0.1);
    public static Setting<Boolean> ground = new Setting<>("Ground", true);

    public HighJump() {
        super("HighJump", Category.MOVEMENT);
    }

    @Subscriber
    public void Method503(MoveEvent moveEvent) {
        block0: {
            if (!HighJump.mc.player.movementInput.jump || ground.getValue().booleanValue() && !HighJump.mc.player.onGround) break block0;
            HighJump.mc.player.motionY = height.getValue();
            moveEvent.setY(HighJump.mc.player.motionY);
        }
    }
}