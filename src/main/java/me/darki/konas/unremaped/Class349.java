package me.darki.konas.unremaped;

import me.darki.konas.*;
import cookiedragon.eventsystem.Subscriber;
import java.util.List;

import me.darki.konas.event.events.UpdateEvent;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;

public class Class349
extends Module {
    @Subscriber
    public void Method135(UpdateEvent updateEvent) {
        if (!Class349.mc.player.onGround || Class349.mc.gameSettings.keyBindJump.isPressed()) {
            return;
        }
        if (Class349.mc.player.isSneaking() || Class349.mc.gameSettings.keyBindSneak.isPressed()) {
            return;
        }
        AxisAlignedBB axisAlignedBB = Class349.mc.player.getEntityBoundingBox();
        AxisAlignedBB axisAlignedBB2 = axisAlignedBB.offset(0.0, -0.5, 0.0).expand(-0.001, 0.0, -0.001);
        List list = Class349.mc.world.getCollisionBoxes((Entity)Class349.mc.player, axisAlignedBB2);
        if (!list.isEmpty()) {
            return;
        }
        Class349.mc.player.jump();
    }

    public Class349() {
        super("Parkour", Category.MOVEMENT, new String[0]);
    }
}