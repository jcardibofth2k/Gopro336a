package me.darki.konas.module.movement;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.unremaped.Class412;
import me.darki.konas.unremaped.Class545;
import me.darki.konas.util.TimerUtil;
import me.darki.konas.event.events.MoveEvent;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.util.math.BlockPos;

public class Anchor
extends Module {
    public static Setting<Boolean> center = new Setting<>("Center", true);
    public static Setting<Integer> vRange = new Setting<>("VRange", 4, 10, 1, 1);
    public static Setting<Class412> mode = new Setting<>("Mode", Class412.BEDROCK);
    public static Setting<Boolean> pitchTrigger = new Setting<>("PitchTrigger", false);
    public static Setting<Integer> pitch = new Setting<>("Pitch", 0, 90, -90, 1).visibleIf(pitchTrigger::getValue);
    public static Setting<Boolean> turnOffAfter = new Setting<>("TurnOffAfter", true);
    public TimerUtil Field1260 = new TimerUtil();

    @Subscriber
    public void Method503(MoveEvent moveEvent) {
        block7: {
            if (pitchTrigger.getValue().booleanValue() && Anchor.mc.player.rotationPitch < (float) pitch.getValue().intValue()) {
                return;
            }
            BlockPos blockPos = new BlockPos(Anchor.mc.player);
            if (this.Method512(blockPos)) {
                this.Field1260.UpdateCurrentTime();
                if (turnOffAfter.getValue().booleanValue()) {
                    this.toggle();
                }
            }
            boolean bl = false;
            for (int i = 1; i < vRange.getValue(); ++i) {
                if (!this.Method512(blockPos.down(i))) continue;
                bl = true;
                break;
            }
            if (!bl) break block7;
            double d = Math.floor(Anchor.mc.player.posX);
            double d2 = Math.floor(Anchor.mc.player.posZ);
            double d3 = Math.abs(Anchor.mc.player.posX - d);
            double d4 = Math.abs(Anchor.mc.player.posZ - d2);
            if (d3 < (double)0.3f || d3 > (double)0.7f || d4 < (double)0.3f || d4 > (double)0.7f) {
                if (center.getValue().booleanValue()) {
                    Anchor.mc.player.setPosition((double)blockPos.getX() + 0.5, Anchor.mc.player.posY, (double)blockPos.getZ() + 0.5);
                } else {
                    return;
                }
            }
            moveEvent.setX(0.0);
            moveEvent.setZ(0.0);
        }
    }

    public boolean Method512(BlockPos blockPos) {
        if (mode.getValue() == Class412.BOTH) {
            return Class545.Method1009(blockPos);
        }
        return Class545.Method1001(blockPos);
    }

    public Anchor() {
        super("Anchor", Category.MOVEMENT);
    }
}