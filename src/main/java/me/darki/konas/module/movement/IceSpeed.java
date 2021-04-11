package me.darki.konas.module.movement;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Category;
import me.darki.konas.event.events.TickEvent;
import me.darki.konas.module.Module;
import net.minecraft.init.Blocks;

public class IceSpeed
extends Module {
    public IceSpeed() {
        super("IceSpeed", Category.MOVEMENT);
    }

    @Subscriber
    public void onTickEvent(TickEvent tickEvent) {
        Blocks.ICE.slipperiness = 0.4f;
        Blocks.FROSTED_ICE.slipperiness = 0.4f;
        Blocks.PACKED_ICE.slipperiness = 0.4f;
    }

    @Override
    public void onDisable() {
        Blocks.ICE.slipperiness = 0.98f;
        Blocks.FROSTED_ICE.slipperiness = 0.98f;
        Blocks.PACKED_ICE.slipperiness = 0.98f;
    }
}