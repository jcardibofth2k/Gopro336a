package me.darki.konas.module.movement;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Category;
import me.darki.konas.event.events.MoveEvent;
import me.darki.konas.util.PlayerUtil;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;

public class FastSwim
extends Module {
    public static Setting<Double> speed = new Setting<>("Speed", 2.0, 10.0, 0.1, 0.1);
    public static Setting<Boolean> antiKick = new Setting<>("AntiKick", true);

    @Subscriber
    public void Method503(MoveEvent moveEvent) {
        if (!FastSwim.mc.player.isInWater() || !PlayerUtil.Method1080()) {
            return;
        }
        double[] dArray = FastSwim.mc.player.ticksExisted % 4 == 0 && antiKick.getValue() != false ? PlayerUtil.Method1086(speed.getValue() / 40.0) : PlayerUtil.Method1086(speed.getValue() / 10.0);
        moveEvent.setX(dArray[0]);
        moveEvent.setZ(dArray[1]);
    }

    public FastSwim() {
        super("FastSwim", Category.MOVEMENT);
    }
}