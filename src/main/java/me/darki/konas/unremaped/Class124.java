package me.darki.konas.unremaped;

import me.darki.konas.*;
import me.darki.konas.event.CancelableEvent;
import net.minecraft.entity.Entity;

public class Class124
extends CancelableEvent {
    public Entity Field2216;
    public double Field2217;
    public double Field2218;
    public double Field2219;
    public float Field2220;
    public float Field2221;

    public double Method91() {
        return this.Field2218;
    }

    public double Method216() {
        return this.Field2219;
    }

    public float Method215() {
        return this.Field2220;
    }

    public void Method89(double d) {
        this.Field2218 = d;
    }

    public Entity Method2019() {
        return this.Field2216;
    }

    public float Method213() {
        return this.Field2221;
    }

    public void Method95(double d) {
        this.Field2217 = d;
    }

    public Class124(Entity entity, double d, double d2, double d3, float f, float f2) {
        this.Field2216 = entity;
        this.Field2217 = d;
        this.Field2218 = d2;
        this.Field2219 = d3;
        this.Field2220 = f;
        this.Field2221 = f2;
    }

    public void Method294(float f) {
        this.Field2220 = f;
    }

    public void Method94(double d) {
        this.Field2219 = d;
    }

    public double Method2020() {
        return this.Field2217;
    }
}