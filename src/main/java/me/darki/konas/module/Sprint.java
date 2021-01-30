package me.darki.konas.module;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.util.MovementInput;

public class Sprint
extends Module {
    public static Setting<Class342> mode = new Setting<>("Mode", Class342.LEGIT);
    public boolean Field2318 = false;

    public Sprint() {
        super("Sprint", "Makes you Sprint!", 0, Category.MOVEMENT, new String[0]);
    }

    @Subscriber
    public void Method462(Class63 class63) {
        block1: {
            if (Sprint.mc.player == null || Sprint.mc.world == null) {
                return;
            }
            if (Sprint.mc.player.isSneaking() || Sprint.mc.player.collidedHorizontally) break block1;
            Sprint.mc.player.setSprinting(this.Field2318);
        }
    }

    @Subscriber
    public void Method2050(Class27 class27) {
        MovementInput movementInput = class27.Method218();
        switch (Class344.Field2631[((Class342)((Object) mode.getValue())).ordinal()]) {
            case 1: {
                this.Field2318 = movementInput.moveForward > 0.0f;
                break;
            }
            case 2: {
                this.Field2318 = Math.abs(movementInput.moveForward) > 0.0f || Math.abs(movementInput.moveStrafe) > 0.0f;
            }
        }
    }
}
