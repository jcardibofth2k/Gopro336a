package me.darki.konas.unremaped;

import me.darki.konas.*;
import me.darki.konas.event.CancelableEvent;
import net.minecraft.entity.EntityLivingBase;

public class Class139
extends CancelableEvent {
    public EntityLivingBase Field2052;

    public Class139(EntityLivingBase entityLivingBase) {
        this.Field2052 = entityLivingBase;
    }

    public EntityLivingBase Method1894() {
        return this.Field2052;
    }
}