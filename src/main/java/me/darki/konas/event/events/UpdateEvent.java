package me.darki.konas.event.events;

public class UpdateEvent
extends Event {
    public static UpdateEvent Field161 = new UpdateEvent();

    public static UpdateEvent Method273(double d, double d2, double d3, float f, float f2, boolean bl) {
        Field161.setCanceled(false);
        UpdateEvent.Field161.Field206 = d;
        UpdateEvent.Field161.Field207 = d2;
        UpdateEvent.Field161.Field208 = d3;
        UpdateEvent.Field161.Field209 = f;
        UpdateEvent.Field161.Field210 = f2;
        UpdateEvent.Field161.Field211 = bl;
        return Field161;
    }
}
