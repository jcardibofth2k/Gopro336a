package me.darki.konas.unremaped;

import me.darki.konas.*;
import me.darki.konas.event.CancelableEvent;
import net.minecraft.entity.Entity;

public class Class34
extends CancelableEvent {
    public static Class34 Field171 = new Class34();
    public Entity Field172;
    public double Field173;
    public Class36 Field174;

    public double Method88() {
        return this.Field173;
    }

    public Class36 Method285() {
        return this.Field174;
    }

    public Entity Method286() {
        return this.Field172;
    }

    public static Class34 Method287(Entity entity, double d, Class36 class36) {
        Field171.setCanceled(false);
        Class34.Field171.Field172 = entity;
        Class34.Field171.Field173 = d;
        Class34.Field171.Field174 = class36;
        return Field171;
    }

    public void Method89(double d) {
        this.Field173 = d;
    }
}