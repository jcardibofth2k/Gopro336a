package me.darki.konas.event.events;

import me.darki.konas.event.CancelableEvent;

public class Event
extends CancelableEvent {
    public double Field206;
    public double Field207;
    public double Field208;
    public float Field209;
    public float Field210;
    public boolean Field211;

    public void Method294(float f) {
        this.Field209 = f;
    }

    public double Method216() {
        return this.Field206;
    }

    public void Method89(double d) {
        this.Field208 = d;
    }

    public double Method90() {
        return this.Field208;
    }

    public void Method94(double d) {
        this.Field206 = d;
    }

    public boolean Method339() {
        return this.Field211;
    }

    public float Method215() {
        return this.Field209;
    }

    public double Method91() {
        return this.Field207;
    }

    public void Method95(double d) {
        this.Field207 = d;
    }

    public void Method277(boolean bl) {
        this.Field211 = bl;
    }

    public float Method340() {
        return this.Field210;
    }

    public void Method341(float f) {
        this.Field210 = f;
    }
}
