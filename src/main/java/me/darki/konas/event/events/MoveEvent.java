package me.darki.konas.event.events;

import me.darki.konas.event.CancelableEvent;
import net.minecraft.entity.MoverType;

public class MoveEvent
extends CancelableEvent {
    public static MoveEvent Field41 = new MoveEvent();
    public MoverType mvType;
    public double X;
    public double Y;
    public double Z;

    public static MoveEvent Method87(MoverType moverType, double x, double y, double z) {
        Field41.setCanceled(false);
        MoveEvent.Field41.mvType = moverType;
        MoveEvent.Field41.X = x;
        MoveEvent.Field41.Y = y;
        MoveEvent.Field41.Z = z;
        return Field41;
    }

    public double getY() {
        return this.Y;
    }

    public void setZ(double d) {
        this.Z = d;
    }

    public double getZ() {
        return this.Z;
    }

    public double getX() {
        return this.X;
    }

    public void setMoverType(MoverType moverType) {
        this.mvType = moverType;
    }

    public MoverType getMoverType() {
        return this.mvType;
    }

    public void setX(double d) {
        this.X = d;
    }

    public void setY(double d) {
        this.Y = d;
    }
}
