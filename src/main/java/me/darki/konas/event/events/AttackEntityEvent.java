package me.darki.konas.event.events;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class AttackEntityEvent {
    public static AttackEntityEvent Field179 = new AttackEntityEvent();
    public EntityPlayer Field180;
    public Entity Field181;

    public static AttackEntityEvent Method291(EntityPlayer entityPlayer, Entity entity) {
        AttackEntityEvent.Field179.Field180 = entityPlayer;
        AttackEntityEvent.Field179.Field181 = entity;
        return Field179;
    }

    public EntityPlayer Method292() {
        return this.Field180;
    }

    public Entity Method293() {
        return this.Field181;
    }
}
