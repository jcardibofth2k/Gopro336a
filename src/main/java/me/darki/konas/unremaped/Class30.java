package me.darki.konas.unremaped;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class Class30 {
    public static Class30 Field179 = new Class30();
    public EntityPlayer Field180;
    public Entity Field181;

    public static Class30 Method291(EntityPlayer entityPlayer, Entity entity) {
        Class30.Field179.Field180 = entityPlayer;
        Class30.Field179.Field181 = entity;
        return Field179;
    }

    public EntityPlayer Method292() {
        return this.Field180;
    }

    public Entity Method293() {
        return this.Field181;
    }
}
