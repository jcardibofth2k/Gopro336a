package me.darki.konas.module.movement;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.Category;
import me.darki.konas.MoveEvent;
import me.darki.konas.MathUtil;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;

public class FastSwim
extends Module {
    public static Setting<Double> speed = new Setting<>("Speed", 2.0, 10.0, 0.1, 0.1);
    public static Setting<Boolean> antiKick = new Setting<>("AntiKick", true);

    @Subscriber
    public void Method503(MoveEvent moveEvent) {
        if (!FastSwim.mc.player.isInWater() || !MathUtil.Method1080()) {
            return;
        }
        double[] dArray = FastSwim.mc.player.ticksExisted % 4 == 0 && (Boolean) antiKick.getValue() != false ? MathUtil.Method1086((Double) speed.getValue() / 40.0) : MathUtil.Method1086((Double) speed.getValue() / 10.0);
        moveEvent.setX(dArray[0]);
        moveEvent.setZ(dArray[1]);
    }

    public FastSwim() {
        super("FastSwim", Category.MOVEMENT, new String[0]);
    }
}
