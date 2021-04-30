package me.darki.konas.unremaped;

import me.darki.konas.*;
import me.darki.konas.event.CancelableEvent;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;

public class Class126
extends CancelableEvent {
    public ModelBase Field2253;
    public Entity Field2254;
    public float Field2255;
    public float Field2256;
    public float Field2257;
    public float Field2258;
    public float Field2259;
    public float Field2260;

    public Entity Method2040() {
        return this.Field2254;
    }

    public float Method258() {
        return this.Field2259;
    }

    public float Method1108() {
        return this.Field2256;
    }

    public float Method260() {
        return this.Field2255;
    }

    public float Method214() {
        return this.Field2258;
    }

    public ModelBase Method2041() {
        return this.Field2253;
    }

    public Class126(ModelBase modelBase, Entity entity, float f, float f2, float f3, float f4, float f5, float f6) {
        this.Field2253 = modelBase;
        this.Field2254 = entity;
        this.Field2255 = f;
        this.Field2256 = f2;
        this.Field2257 = f3;
        this.Field2258 = f4;
        this.Field2259 = f5;
        this.Field2260 = f6;
    }

    public float Method213() {
        return this.Field2257;
    }

    public float Method340() {
        return this.Field2260;
    }
}