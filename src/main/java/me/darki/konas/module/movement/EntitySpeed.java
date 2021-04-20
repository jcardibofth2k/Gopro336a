package me.darki.konas.module.movement;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Category;
import me.darki.konas.unremaped.Class19;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.util.MovementInput;

public class EntitySpeed
extends Module {
    public static Setting<Float> speed = new Setting<>("Speed", Float.valueOf(3.0f), Float.valueOf(10.0f), Float.valueOf(0.1f), Float.valueOf(0.1f));
    public static Setting<Boolean> accelerate = new Setting<>("Accelerate", false);
    public static Setting<Float> acceleration = new Setting<>("Acceleration", Float.valueOf(0.1f), Float.valueOf(2.0f), Float.valueOf(0.1f), Float.valueOf(0.1f)).visibleIf(accelerate::getValue);
    public long Field831 = 0L;

    public EntitySpeed() {
        super("EntitySpeed", Category.MOVEMENT);
    }

    @Override
    public void onEnable() {
        this.Field831 = System.currentTimeMillis();
    }

    @Subscriber
    public void Method130(Class19 class19) {
        if (EntitySpeed.mc.player.getRidingEntity() != null) {
            MovementInput movementInput = EntitySpeed.mc.player.movementInput;
            double d = movementInput.moveForward;
            double d2 = movementInput.moveStrafe;
            float f = EntitySpeed.mc.player.rotationYaw;
            if (d == 0.0 && d2 == 0.0) {
                EntitySpeed.mc.player.getRidingEntity().motionX = 0.0;
                EntitySpeed.mc.player.getRidingEntity().motionZ = 0.0;
                this.Field831 = System.currentTimeMillis();
            } else {
                if (d != 0.0) {
                    if (d2 > 0.0) {
                        f += (float)(d > 0.0 ? -45 : 45);
                    } else if (d2 < 0.0) {
                        f += (float)(d > 0.0 ? 45 : -45);
                    }
                    d2 = 0.0;
                    if (d > 0.0) {
                        d = 1.0;
                    } else if (d < 0.0) {
                        d = -1.0;
                    }
                }
                float f2 = speed.getValue().floatValue();
                if (accelerate.getValue().booleanValue()) {
                    f2 *= Math.min(1.0f, (float)(System.currentTimeMillis() - this.Field831) / (1000.0f * acceleration.getValue().floatValue()));
                }
                double d3 = Math.sin(Math.toRadians(f + 90.0f));
                double d4 = Math.cos(Math.toRadians(f + 90.0f));
                EntitySpeed.mc.player.getRidingEntity().motionX = d * (double)f2 * d4 + d2 * (double)f2 * d3;
                EntitySpeed.mc.player.getRidingEntity().motionZ = d * (double)f2 * d3 - d2 * (double)f2 * d4;
            }
        }
    }
}