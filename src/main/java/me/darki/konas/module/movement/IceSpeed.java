package me.darki.konas.module.movement;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.Category;
import me.darki.konas.TickEvent;
import me.darki.konas.module.Module;
import net.minecraft.init.Blocks;

public class IceSpeed
extends Module {
    public IceSpeed() {
        super("IceSpeed", Category.MOVEMENT, new String[0]);
    }

    @Subscriber
    public void Method462(TickEvent tickEvent) {
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
