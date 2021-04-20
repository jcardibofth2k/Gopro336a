package me.darki.konas.module.movement;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.event.events.TickEvent;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import me.darki.konas.settingEnums.SprintMode;
import me.darki.konas.event.events.PlayerInputUpdateEvent;
import me.darki.konas.unremaped.Class344;
import net.minecraft.util.MovementInput;

public class Sprint
extends Module {
    public static Setting<SprintMode> mode = new Setting<>("Mode", SprintMode.LEGIT);
    public boolean Field2318 = false;

    public Sprint() {
        super("Sprint", "Makes you Sprint!", 0, Category.MOVEMENT);
    }

    @Subscriber
    public void Method462(TickEvent class63) {
        block1: {
            if (Sprint.mc.player == null || Sprint.mc.world == null) {
                return;
            }
            if (Sprint.mc.player.isSneaking() || Sprint.mc.player.collidedHorizontally) break block1;
            Sprint.mc.player.setSprinting(this.Field2318);
        }
    }

    @Subscriber
    public void Method2050(PlayerInputUpdateEvent playerInputUpdateEvent) {
        MovementInput movementInput = playerInputUpdateEvent.Method218();
        switch (Class344.Field2631[mode.getValue().ordinal()]) {
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